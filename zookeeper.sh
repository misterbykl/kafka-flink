#!/bin/bash

KAFKA_HOME=/home/baykal/Documents/tools/

case $1 in
start)
    echo "start"
    $KAFKA_HOME/zookeeper/bin/zookeeper-server-start.sh -daemon $KAFKA_HOME/zookeeper/config/zookeeper.properties
    ;;
stop)
    echo "stop"
    $KAFKA_HOME/zookeeper/bin/zookeeper-server-stop.sh $KAFKA_HOME/zookeeper/config/zookeeper.properties
    ;;
*)
    echo "kullanim $0 <start | stop | restart>"
    exit 1
    ;;
esac