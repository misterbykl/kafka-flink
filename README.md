#kafka-flink
Java module for reading from Kafka and processing data using Apache Flink.
#Demo
This is a quick and very simple demo for kafka-flink integration.

By default, the app waits for the input data like this: `1:2:3:4:5,2:3:4:5:6,3:4:5:6:7`

After entering the input, the app simply checks if the stream consists number '2' and change it with 'Baykal' string

Output: 

`Baykal--3--4--5--6`

`3--4--5--6--7`
 
`1--Baykal--3--4--5`
#Installation
Following tools is needed for running the project. 
##Kafka
1. Download Kafka:
    `wget http://ftp.itu.edu.tr/Mirror/Apache/kafka/0.10.0.0/kafka_2.11-0.10.0.0.tgz`
2. Extract: `tar xf kafka_2.11-0.10.0.0.tgz`
3. Change name to kafka1: `mv kafka_2.11-0.10.0.0 kafka1`
4. Edit 'server.properties' file under 'config' folder for kafka. Fields that need to be changed are listed in the next section
5. Create 2 more instances: `cp -r kafka1 kafka2` and `cp -r kafka1 kafka3`
6. Create zookeeper instance: `cp -r kafka1 zookeeper`
##Flink
1. Download Flink: `wget http://ftp.itu.edu.tr/Mirror/Apache/flink/flink-1.3.1/flink-1.3.1-bin-hadoop27-scala_2.10.tgz`
2. Extract: `tar xf flink-1.3.1-bin-hadoop27-scala_2.10.tgz`
##Fields to be edited
### server.properties
- `broker.id=1`
- `listeners=PLAINTEXT://localhost:9092` (Port needs to be incremented for multiple partitions. i.e. kafka2:9093, kafka3:9094)
- `log.dirs=/tmp/kafka-logs-1` (Log number needs to be incremented for multiple partitions)
- `num.partitions=3`
- `delete.topic.enable=true`
- `auto.create.topics.enable=false`
#How to run
Following commands should be executed in exact order as below
1. `./zookeeper.sh start`
2. `./kafka.sh start`
3. `./flink.sh start`
##Creating topic
Following commands shoule be executed under 'zookeeper' folder
###Input
`bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic kafka-topic`
###Output
`bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic flink-topic`

##Following data over console
Following commands shoule be executed under 'zookeeper' folder
###Input
`bin/kafka-console-producer.sh --broker-list localhost:9092,localhost:9093,localhost:9094 --topic kafka-topic`
###Output
`bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic flink-topic --from-beginning`