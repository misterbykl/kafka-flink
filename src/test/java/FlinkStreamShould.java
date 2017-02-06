import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 16:04
 */
public class FlinkStreamShould {

    private StreamExecutionEnvironment env;
    private String data;

    @Before
    public void setUp() throws Exception {
        env = StreamExecutionEnvironment.createLocalEnvironment();
        data = "{\"s\"=1;\"b\"=2}";
    }

    @Test
    public void parse() {
        String str = "id:123456,value:124124124235235";
        String[] stringArray = str.split(",");

        List<Tuple2<String, String>> data = new ArrayList<>();
        Tuple2<String, String> tuple = new Tuple2<>(stringArray[0], stringArray[1]);
        data.add(tuple);

        DataStream<Tuple2<String, String>> tuples = this.env.fromCollection(data);
        KeyedStream<Tuple2<String, String>, Tuple> members = tuples.keyBy(0);
        DataStream<Tuple2<String, String>> filtered = members.filter((FilterFunction<Tuple2<String, String>>) argStringStringTuple2
                -> {
            String[] stringArrayId = argStringStringTuple2.f0.split(":");

            return stringArrayId[1].equals("123456");
        });

        DataStream<String> result = filtered.map((MapFunction<Tuple2<String, String>, String>) argStringStringTuple2
                -> "result: " + argStringStringTuple2.f1);

        result.print().setParallelism(1);


    }
}
