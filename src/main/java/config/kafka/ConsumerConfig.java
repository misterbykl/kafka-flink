package config.kafka;

import stream.input.kafka.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import util.ApplicationUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * misterbykl
 * <p>
 * 5/2/17 15:55
 */
@EnableScheduling
@EnableAsync
@PropertySource(value = ApplicationUtil.APPLICATION_PROPERTIES)
@Configuration
public class ConsumerConfig {

    public static String CONSUMER_BOOTSTRAP_SERVERS = "bootstrap.servers";
    public static String GROUP_ID = "group.id";

    /**
     * Arg property sources placeholder configurer property sources placeholder configurer.
     *
     * @return the property sources placeholder configurer
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:55
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer argPropertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Create consumer kafka consumer.
     *
     * @param bootstrapServers the bootstrap servers
     * @param groupID          the group id
     * @return the kafka consumer
     * @throws IOException the io exception
     *                     <p>
     *                     misterbykl
     *                     <p>
     *                     5/2/17 15:55
     */
    @Bean
    public KafkaConsumer<String, String> createConsumer(@Value("${kafka.bootstrap.servers.enable}") String bootstrapServersEnable,
                                                        @Value("${kafka.bootstrap.servers}") String bootstrapServers,
                                                        @Value("${kafka.consumer.groupid}") String groupID,
                                                        @Value("${kafka.consumer.from.beginning}") String fromBeginning,
                                                        @Value("${kafka.consumer.from.beginning.groupid}") String customGroupId) throws IOException

    {
        String KEY_DESERIALIZER = "key.deserializer";
        String KEY_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";
        String VALUE_DESERIALIZER = "value.deserializer";
        String VALUE_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";
        String AUTO_OFFSET_RESET = "auto.offset.reset";

        Properties properties = System.getProperties();
        if (bootstrapServersEnable.equalsIgnoreCase("on")) {
            properties.setProperty(CONSUMER_BOOTSTRAP_SERVERS, bootstrapServers);
        }
        properties.setProperty(KEY_DESERIALIZER, KEY_DESERIALIZER_CLASS);
        properties.setProperty(VALUE_DESERIALIZER, VALUE_DESERIALIZER_CLASS);
        properties.setProperty(GROUP_ID, Boolean.valueOf(fromBeginning) ? customGroupId : groupID);
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
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:55
     */
    @Bean
    public Consumer consumer(@Value("${kafka.consumer.topic}") String topics, KafkaConsumer<String, String> consumer) {
        return new Consumer(topics, consumer);
    }
}
