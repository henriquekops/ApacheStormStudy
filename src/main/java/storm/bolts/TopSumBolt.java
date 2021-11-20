package storm.bolts;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.Flat3Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopSumBolt implements IRichBolt {

    OutputCollector collector;
    Map<String, Float> counts = new HashMap<String, Float>();
    private static final Logger LOG = LoggerFactory.getLogger(BasicBolt.class);

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String cmd = input.getString(0);
        Float sum = counts.get(cmd);
        sum += input.getFloat(1);
        counts.put(cmd, sum);
        collector.emit(new Values(cmd, sum));
        LOG.info("Processing sum for cmd {}: cpu={}", cmd, sum);
        this.collector.ack(input);
    }

    @Override
    public void cleanup() {      
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("cmd", "sum"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
    
}
