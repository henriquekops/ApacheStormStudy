package storm;

import org.apache.storm.utils.Utils;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import storm.bolts.TopBolt;

public class TopTopology {

    public static void main(String[] args) throws Exception{
        TopologyBuilder builder = new TopologyBuilder();
        LocalCluster cluster = new LocalCluster();
        Config conf = new Config();

        builder.setSpout("kafka_proc_info", new KafkaSpout<>(KafkaSpoutConfig.builder("localhost:9092", "TOP").build()), 1);
        builder.setBolt("filter_proc_info", new TopBolt(), 1).shuffleGrouping("kafka_proc_info");        
        cluster.submitTopology("kafka_proc_info_topology", conf, builder.createTopology());
        Utils.sleep(100000);
        cluster.close();
    }

}
