package storm.bolts;

import java.util.HashMap;
import java.util.Map;

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
    Map<String, Double> counts = new HashMap<String, Double>();
    private static final Logger LOG = LoggerFactory.getLogger(BasicBolt.class);

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        final String cmd = input.getString(0);
        Double sum = counts.get(cmd);
        if (sum == null) {
            sum = 0.0;
        }
        sum += input.getDouble(1);
        counts.put(cmd, sum);
        collector.emit(new Values(cmd, sum));
        LOG.info("Processing sum info: cmd={} cpu={}", cmd, sum);
        this.collector.ack(input);
    }

    @Override
    public void cleanup() {      
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // declarer.declare(new Fields("cmd", "sum"));
        declarer.declare(new Fields("input"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
    
}
