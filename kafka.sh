#!/bin/bash

KAFKA_HOME=/home/baykal/Documents/tools/

case $1 in
start)
	echo "start"
	env JMX_PORT=9997 ${KAFKA_HOME}/kafka1/bin/kafka-server-start.sh -daemon ${KAFKA_HOME}/kafka1/config/server.properties
	env JMX_PORT=9998 ${KAFKA_HOME}/kafka2/bin/kafka-server-start.sh -daemon ${KAFKA_HOME}/kafka2/config/server.properties
	env JMX_PORT=9999 ${KAFKA_HOME}/kafka3/bin/kafka-server-start.sh -daemon ${KAFKA_HOME}/kafka3/config/server.properties
	;;
stop)
	echo "stop"
	${KAFKA_HOME}/kafka1/bin/kafka-server-stop.sh
	${KAFKA_HOME}/kafka2/bin/kafka-server-stop.sh
	${KAFKA_HOME}/kafka3/bin/kafka-server-stop.sh
	;;
*)
	echo "kullanim $0 <start | stop | restart>"
	exit 1
	;;
esac
