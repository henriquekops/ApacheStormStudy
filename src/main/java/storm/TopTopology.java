package storm;

import java.util.Properties;

// import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.storm.Config;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import storm.bolts.TopProcBolt;
import storm.bolts.TopRecvBolt;

public class TopTopology extends ConfigurableTopology {

    public static void main(String[] args) {
        ConfigurableTopology.start(new TopTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        Config conf = new Config();

        builder.setSpout("kafka_proc_info", new KafkaSpout<>(
            KafkaSpoutConfig
                .builder("kafka-broker:9092", "TOP")
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "TopKafkaSpoutConsumer")
                .build()
            ), 1);
        
        builder.setBolt("recv_proc_info", new TopRecvBolt(), 1).shuffleGrouping("kafka_proc_info");
        builder.setBolt("proc_proc_info", new TopProcBolt(), 1).fieldsGrouping("recv_proc_info", new Fields("pid"));

        builder.setBolt("kafka_proc_fwd", new KafkaBolt<String, String>()
            .withProducerProperties(producerProps("kafka-broker:9092", "PID_CPU_SUM"))
            .withTopicSelector(new DefaultTopicSelector("PID_CPU_SUM"))
            .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper<>("pid", "cpu")),
            1
        ).shuffleGrouping("proc_proc_info");

        conf.setDebug(true);
        conf.setNumWorkers(1);
        
        return submit("kafka_proc_info_topology", conf, builder);
    }

    private static Properties producerProps(String brokerUrl, String topic) {
        return new Properties() {
            {
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
                put(ProducerConfig.CLIENT_ID_CONFIG, topic);
            }
        };
    }
}
