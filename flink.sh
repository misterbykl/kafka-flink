#!/bin/bash
FLINK_HOME=/home/baykal/Documents/tools/

case $1 in
start)
    echo "start"
    $FLINK_HOME/flink-1.3.1/bin/start-local.sh
    ;;
stop)
    echo "stop"
    $FLINK_HOME/flink-1.3.1/bin/stop-local.sh
    ;;
*)
    echo "kullanim $0 <start | stop | restart>"
    exit 1
    ;;
esac

