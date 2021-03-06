package storm.bolts;

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

public class TopRecvBolt implements IRichBolt {

    OutputCollector collector;
    private static final Logger LOG = LoggerFactory.getLogger(BasicBolt.class);

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        final String[] procInfo = input.getString(4).split(" ");
        final String pid = procInfo[0];
        final Double cpu = Double.parseDouble(procInfo[1]);
        this.collector.emit(new Values(pid, cpu));
        LOG.info("receiving: pid={} cpu={}", pid, cpu);
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
