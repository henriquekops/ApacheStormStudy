package storm.spouts;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSpout implements IRichSpout {
    
    private static final Logger LOG = LoggerFactory.getLogger(BasicSpout.class);

    SpoutOutputCollector collector;
    Random rand;
    String[] words;

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.rand = new Random();
        this.words = new String[] {"apache", "storm", "is", "awesome"};
    }

    @Override
    public void close() {
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        final String word = this.words[this.rand.nextInt(words.length)];
        
        LOG.info("Emitting tuple: {}", word);

        this.collector.emit(new Values(word));
    }

    @Override
    public void ack(Object msgId) {
    }

    @Override
    public void fail(Object msgId) {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }    

}
