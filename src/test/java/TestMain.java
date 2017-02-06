import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 16:20
 */
public class TestMain {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        String str = "id:123456,value:124124124235235";
        String[] stringArray = str.split(",");

        List<Tuple2<String, String>> data = new ArrayList<>();
        Tuple2<String, String> tuple = new Tuple2<>(stringArray[0], stringArray[1]);
        data.add(tuple);

        DataStream<Tuple2<String, String>> tuples = env.fromCollection(data);
        KeyedStream<Tuple2<String, String>, Tuple> members = tuples.keyBy(0);
        DataStream<Tuple2<String, String>> filtered = members.filter(new FilterFunction<Tuple2<String, String>>()
        {
            public boolean filter(Tuple2<String, String> argStringStringTuple2) throws Exception
            {
                String[] stringArrayId = argStringStringTuple2.f0.split(":");

                return stringArrayId[1].equals("12345");
            }
        });

        filtered.print().setParallelism(1);
    }
}
