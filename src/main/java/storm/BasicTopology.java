package storm;

import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.TopologyBuilder;

import storm.bolts.BasicBolt;
import storm.spouts.BasicSpout;

public class BasicTopology extends ConfigurableTopology {

    public static void main(String[] args) {
        ConfigurableTopology.start(new BasicTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("word", new BasicSpout(), 1);
        builder.setBolt("exclaim1", new BasicBolt(), 1).shuffleGrouping("word");
        builder.setBolt("exclaim2", new BasicBolt(), 1).shuffleGrouping("exclaim1");

        conf.setDebug(true);
        conf.setNumWorkers(1);

        return submit("test", conf, builder);
    }

}
