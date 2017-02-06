package stream.process.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 15:49
 */
public class FlinkProcessor {

    private StreamExecutionEnvironment streamExecutionEnvironment;

    /**
     * Instantiates a new Flink processor.
     *
     * @param env the env
     *            <p>
     *            misterbaykal
     *            <p>
     *            05/02/2017 15:49
     */
    public FlinkProcessor(StreamExecutionEnvironment env) {
        this.streamExecutionEnvironment = env;
    }

    private void getStreamData(String data) {

        DataStream<Tuple2<String, String>> tuples = this.streamExecutionEnvironment.fromCollection(Arrays.asList(data.split(";")))
                .flatMap(
                        (FlatMapFunction<String, Tuple2<String, String>>) (argS, argCollector) -> {
                            String[] stringArray = argS.split("=");
                            argCollector.collect(new Tuple2<String, String>(stringArray[0], stringArray[1]));
                        });

        KeyedStream<Tuple2<String, String>, Tuple> members = tuples.keyBy(0);

        DataStream<Tuple2<String, String>> filtered = members.filter((FilterFunction<Tuple2<String, String>>) argStringStringTuple2
                -> {
            String[] stringArrayId = argStringStringTuple2.f0.split(":");

            return stringArrayId[1].equals("");
        });

        DataStream<String> result = filtered.map((MapFunction<Tuple2<String, String>, String>) argStringStringTuple2
                -> "result: " + argStringStringTuple2.f1);

        result.print().setParallelism(1);
    }
}
