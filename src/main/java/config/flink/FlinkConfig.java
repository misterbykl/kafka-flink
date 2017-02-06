package config.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stream.process.flink.FlinkProcessor;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 15:47
 */
@Configuration
public class FlinkConfig {

    /**
     * Flink processor flink processor.
     *
     * @return the flink processor
     * <p>
     * misterbaykal
     * <p>
     * 05/02/2017 15:49
     */
    @Bean
    public FlinkProcessor flinkProcessor() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        return new FlinkProcessor(env);
    }
}
