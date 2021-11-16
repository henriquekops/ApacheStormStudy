#!/usr/bin/env bash

kafka_2.12-3.0.0/bin/kafka-topics.sh --create --topic TOP --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
