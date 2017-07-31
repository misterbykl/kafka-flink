#!/bin/bash

KAFKA_HOME=/home/baykal/Documents/tools/

${KAFKA_HOME}/zookeeper/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic kafka-topic
${KAFKA_HOME}/zookeeper/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic flink-topic