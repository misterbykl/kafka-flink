package config;

import json.JsonTemplate;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import stream.input.kafka.Consumer;
import stream.process.flink.Processor;

import javax.annotation.PreDestroy;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 15:22
 */
@SuppressWarnings("unused")
@Configuration
@EnableScheduling
@EnableAsync
@PropertySource(value = {"file:conf/application.properties"})
public class MainConfig implements SchedulingConfigurer {

    private int threadSize = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(this.taskExecutor());
    }

    /**
     * Logger logger.
     *
     * @return the logger
     * <p>
     * misterbaykal
     * <p>
     * 31/07/2017 14:12
     */
    @Bean(name = "logger")
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Task executor executor.
     *
     * @return the executor
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:21
     */
    @SuppressWarnings("WeakerAccess")
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(this.threadSize);
    }

    /**
     * Thread pool task executor executor.
     *
     * @param argThreadSize the arg thread size
     * @return the executor
     * <p>
     * misterbykl
     * <p>
     * 31/07/17 12:05
     */
    @Bean(destroyMethod = "shutdown")
    public Executor threadPoolTaskExecutor(@Value("${thread.size}") String argThreadSize) {
        this.threadSize = Integer.parseInt(argThreadSize);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.threadSize);
        executor.setKeepAliveSeconds(15);
        executor.initialize();
        return executor;
    }

    /**
     * Create consumer kafka consumer.
     *
     * @param bootstrapServersEnable the bootstrap servers enable
     * @param bootstrapServers       the bootstrap servers
     * @param groupID                the group id
     * @param fromBeginning          the from beginning
     * @param customGroupId          the custom group id
     * @return the kafka consumer
     * <p>
     * misterbykl
     * <p>
     * 31/07/17 11:02
     */
    @Bean(name = "kafkaConsumer")
    public KafkaConsumer<String, String> createConsumer(
            @Value("${kafka.bootstrap.servers.enable}") String bootstrapServersEnable,
            @Value("${kafka.bootstrap.servers}") String bootstrapServers,
            @Value("${kafka.consumer.groupid}") String groupID,
            @Value("${kafka.consumer.from.beginning}") String fromBeginning,
            @Value("${kafka.consumer.from.beginning.groupid}") String customGroupId)

    {
        String KEY_DESERIALIZER = "key.deserializer";
        String KEY_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";
        String VALUE_DESERIALIZER = "value.deserializer";
        String VALUE_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";
        String AUTO_OFFSET_RESET = "auto.offset.reset";

        Properties properties = System.getProperties();
        if (bootstrapServersEnable.equalsIgnoreCase("on")) {
            properties.setProperty("bootstrap.servers", bootstrapServers);
        }
        properties.setProperty(KEY_DESERIALIZER, KEY_DESERIALIZER_CLASS);
        properties.setProperty(VALUE_DESERIALIZER, VALUE_DESERIALIZER_CLASS);
        properties.setProperty("group.id", Boolean.valueOf(fromBeginning) ? customGroupId : groupID);
        properties.setProperty(AUTO_OFFSET_RESET, Boolean.valueOf(fromBeginning) ? "earliest" : "latest");

        return new KafkaConsumer<>(properties);
    }

    /**
     * Consumer consumer.
     *
     * @param topics   the topics
     * @param consumer the consumer
     * @return the consumer
     * <p>
     * misterbykl
     * <p>
     * 31/07/17 11:04
     */
    @Bean
    @DependsOn({"kafkaConsumer", "logger"})
    public Consumer consumer(@Value("${kafka.consumer.topic}") String topics,
                             @Qualifier("kafkaConsumer") KafkaConsumer<String, String> consumer) {
        return new Consumer(topics, consumer);
    }

    /**
     * Processor processor.
     *
     * @param topics                 the topics
     * @param bootstrapServers       the bootstrap servers
     * @param bootstrapServersEnable the bootstrap servers enable
     * @return the processor
     * <p>
     * misterbykl
     * <p>
     * 31/07/17 11:06
     */
    @Bean
    public Processor processor(@Value("${kafka.producer.topic}") String topics,
                               @Value("${kafka.bootstrap.servers}") String bootstrapServers,
                               @Value("${kafka.bootstrap.servers.enable}") String bootstrapServersEnable) {

        String KEY_SERIALIZER = "key.serializer";
        String KEY_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer";
        String VALUE_SERIALIZER = "value.serializer";
        String VALUE_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer";

        Properties properties = System.getProperties();
        if (bootstrapServersEnable.equalsIgnoreCase("on")) {
            properties.setProperty("bootstrap.servers", bootstrapServers);
        }
        properties.setProperty(KEY_SERIALIZER, KEY_SERIALIZER_CLASS);
        properties.setProperty(VALUE_SERIALIZER, VALUE_SERIALIZER_CLASS);

        return new Processor(topics, StreamExecutionEnvironment.createLocalEnvironment(), properties);
    }

    /**
     * Json template json template.
     *
     * @return the json template
     * <p>
     * misterbykl
     * <p>
     * 31/07/17 11:07
     */
    @Bean
    public JsonTemplate jsonTemplate(@Value("${kafka.seperator}") String seperator) {
        return new JsonTemplate(seperator);
    }

    /**
     * Destroy.
     * <p>
     * misterbykl
     * <p>
     * 31/07/17 11:16
     */
    @PreDestroy
    public void destroy() {
        System.out.println("kafka-flink is closing...");
    }

}
