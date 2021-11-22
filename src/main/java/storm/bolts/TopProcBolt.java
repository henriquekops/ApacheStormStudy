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

public class TopProcBolt implements IRichBolt {

    OutputCollector collector;
    Map<String, Double> percents = new HashMap<String, Double>();
    private static final Logger LOG = LoggerFactory.getLogger(BasicBolt.class);

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        final String cmd = input.getString(0);
        Double sum = percents.get(cmd);
        if (sum == null) {sum = 0.0;}
        sum += input.getDouble(1);
        percents.put(cmd, sum);
        collector.emit(new Values(cmd, Double.toString(sum)));
        LOG.info("processing: pid={} cpu={}", cmd, sum);
        this.collector.ack(input);
    }

    @Override
    public void cleanup() {      
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("pid", "cpu"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
    
}
