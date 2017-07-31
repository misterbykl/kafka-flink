package stream.input.kafka;

import json.JsonTemplate;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import stream.process.flink.Processor;
import util.ExceptionUtil;
import util.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * misterbykl
 * <p>
 * 5/2/17 15:55
 */
@SuppressWarnings({"CanBeFinal", "unused"})
public class Consumer {
    private List<String> topicList;
    private KafkaConsumer<String, String> consumer;

    @Value("${kafka.bootstrap.servers}")
    private String servers;

    @Value("${kafka.consumer.groupid}")
    private String group;

    @Autowired
    private JsonTemplate jsonTemplate;
    @Autowired
    private Processor processor;
    @Autowired
    private
    @Qualifier("threadPoolTaskExecutor")
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private Logger logger;


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
        try {
            ConsumerRecords<String, String> records = this.consumer.poll(0);
            if (!records.isEmpty()) {
                for (ConsumerRecord<String, String> record : records) {
                    if (record.value() != null) {
                        Runnable thread = new ConsumerThread(this.getTopicList(), processor, record, jsonTemplate);
                        this.taskExecutor.execute(thread);
                    }
                }
            }
        } catch (Exception e) {
            this.logger.error(StringUtil.append("ERROR while consuming Kafka message"));
            ExceptionUtil.getStackTraceString(e, "consume");
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

    /**
     * The type Consumer thread.
     * <p>
     * misterbykl
     * <p>
     * 31/07/17 11:57
     */
    @SuppressWarnings("unused")
    private class ConsumerThread implements Runnable {
        ConsumerRecord<String, String> record;
        Processor processor;
        List<String> topicList;
        JsonTemplate jsonTemplate;

        /**
         * Instantiates a new Consumer thread.
         *
         * @param argTopicList the arg topic list
         * @param argProcessor the arg processor
         * @param argValue     the arg value
         * @param jsonTemplate <b>Created at</b> 12 Haz 2017 17:43
         * @author emreb
         * @since 3.0.0
         */
        ConsumerThread(List<String> argTopicList, Processor argProcessor, ConsumerRecord<String, String> argValue, JsonTemplate jsonTemplate) {
            this.record = argValue;
            this.topicList = argTopicList;
            this.processor = argProcessor;
            this.jsonTemplate = jsonTemplate;
        }

        @Override
        public void run() {
            if (this.topicList.get(0).equals(record.topic())) {
                this.processor.process(this.jsonTemplate.createJsonArray(record.value()));
            }
        }
    }
}
