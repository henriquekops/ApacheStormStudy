package storm;

import org.apache.storm.Config;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.TopologyBuilder;

import storm.bolts.TopBolt;

public class TopTopology extends ConfigurableTopology {

    public static void main(String[] args) {
        ConfigurableTopology.start(new TopTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        Config conf = new Config();

        builder.setSpout("kafka_proc_info", new KafkaSpout<>(KafkaSpoutConfig.builder("kafka-broker:9092", "TOP").build()), 1);
        builder.setBolt("filter_proc_info", new TopBolt(), 1).shuffleGrouping("kafka_proc_info");        
        
        conf.setNumWorkers(1);
        
        return submit("kafka_proc_info_topology", conf, builder);
    }

}
