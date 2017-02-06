package stream.input.kafka;

import config.kafka.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import service.Service;

import java.util.Arrays;
import java.util.List;

/**
 * misterbykl
 * <p>
 * 5/2/17 15:55
 */
public class Consumer {
    private List<String> topicList;

    @Autowired
    private KafkaConsumer<String, String> consumer;
    @Autowired
    private Service service;


    /**
     * Instantiates a new Consumer.
     *
     * @param topics   the topics
     * @param consumer the consumer
     *                 <p>
     *                 misterbykl
     *                 <p>
     *                 5/2/17 15:55
     */
    public Consumer(String topics, KafkaConsumer<String, String> consumer) {
        this.topicList = Arrays.asList(topics.split(","));
        this.consumer = consumer;
        this.consumer.subscribe(this.topicList);
        String server = System.getProperties().getProperty(ConsumerConfig.CONSUMER_BOOTSTRAP_SERVERS);
        String group = System.getProperties().getProperty(ConsumerConfig.GROUP_ID);
        System.out.println("Kafka consumer started. - Server: " + server + ", Group: " + group + ". Topics: " + topics);
    }

    /**
     * Consume.
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:55
     */
    @Scheduled(fixedDelayString = "10")
    public void consume() {
        ConsumerRecords<String, String> records = consumer.poll(0);
        if (!records.isEmpty()) {
            for (ConsumerRecord<String, String> record : records) {
                if (this.getTopicList().get(0).equals(record.topic())) {
                    this.service.manageKafka(record.value());
                }
            }
        }
    }

    /**
     * Get Topics
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:55
     */
    private List<String> getTopicList() {
        return this.topicList;
    }
}
