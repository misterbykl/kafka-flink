package stream.process.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer09;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import util.ExceptionUtil;
import util.StringUtil;

import java.util.*;

/**
 * misterbaykal
 * <p>
 * 31/07/2017 11:23
 */
@SuppressWarnings({"CanBeFinal", "unused"})
public class Processor {

    private List<String> topicList;
    private Properties properties;
    private StreamExecutionEnvironment streamExecutionEnvironment;

    @Autowired
    private Logger logger;

    /**
     * Instantiates a new Processor.
     *
     * @param topics     the topics
     * @param env        the env
     * @param properties the properties
     *                   <p>
     *                   misterbaykal
     *                   <p>
     *                   31/07/2017 11:23
     */
    public Processor(String topics, StreamExecutionEnvironment env, Properties properties) {
        this.topicList = Arrays.asList(topics.split(","));
        this.properties = properties;
        this.streamExecutionEnvironment = env;
    }

    /**
     * Process.
     *
     * @param jsonArray the json array
     *                  <p>
     *                  misterbaykal
     *                  <p>
     *                  31/07/2017 11:29
     */
    public void process(String[] jsonArray) {
        try {
            List<String> data = new ArrayList<>();
            Collections.addAll(data, jsonArray);

            this.streamExecutionEnvironment.fromCollection(data)
                    .flatMap(new TuplesFlatMapFunction())
                    .keyBy(0)
                    .filter(new FilterFunction())
                    .map(new ResultMapFunction())
                    .addSink(new FlinkKafkaProducer09<>(this.getTopicList().get(0), new SimpleStringSchema(), this.properties));

            this.streamExecutionEnvironment.execute("kafka-flink manager");
        } catch (Exception e) {
            this.logger.error(StringUtil.append("ERROR while procession message with Flink"));
            ExceptionUtil.getStackTraceString(e, "process");
        }
    }

    /**
     * The type Tuples flat map function.
     * <p>
     * misterbaykal
     * 31/07/2017 14:21
     */
    private class TuplesFlatMapFunction implements FlatMapFunction<String, Tuple5<String, String, String, String, String>> {
        @Override
        public void flatMap(String s, Collector<Tuple5<String, String, String, String, String>> collector) throws Exception {
            String[] stringArrayId = s.split(":");
            collector.collect(new Tuple5<>(stringArrayId[0], stringArrayId[1], stringArrayId[2], stringArrayId[3], stringArrayId[4]));
        }
    }
    /**
     * The type Filtered meter function.
     * <p>
     * misterbaykal
     * 31/07/2017 14:21
     */
    private class FilterFunction extends RichFilterFunction<Tuple5<String, String, String, String, String>> {
        @Override
        public boolean filter(Tuple5<String, String, String, String, String> filteredValueTuple5) throws Exception {
            for (int i = 0; i < 5; i++) {
                if (filteredValueTuple5.getField(i).equals("2")) {
                    filteredValueTuple5.setField("Baykal", i);
                }
            }
            return true;
        }
    }

    /**
     * The type Result map function.
     * <p>
     * misterbaykal
     * 31/07/2017 14:21
     */

    private class ResultMapFunction extends RichMapFunction<Tuple5<String, String, String, String, String>, String> {
        @Override
        public String map(Tuple5<String, String, String, String, String> filteredMetricTuple5) throws Exception {
            return filteredMetricTuple5.f0 + "--" + filteredMetricTuple5.f1 +
                    "--" + filteredMetricTuple5.f2 + "--" + filteredMetricTuple5.f3 +
                    "--" + filteredMetricTuple5.f4;
        }
    }

    /**
     * Gets topic list.
     *
     * @return the topic list
     * <p>
     * misterbaykal
     * <p>
     * 31/07/2017 11:29
     */
    private List<String> getTopicList() {
        return this.topicList;
    }
}
