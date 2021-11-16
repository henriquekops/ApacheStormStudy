#!/usr/bin/env bash

curl https://dlcdn.apache.org/maven/maven-3/3.8.3/binaries/apache-maven-3.8.3-bin.tar.gz --output - | tar -xvzf - -C .

curl https://dlcdn.apache.org/storm/apache-storm-2.3.0/apache-storm-2.3.0.tar.gz --output - | tar -xvzf - -C .

curl https://dlcdn.apache.org/zookeeper/zookeeper-3.6.3/apache-zookeeper-3.6.3-bin.tar.gz --output - | tar -xvzf - -C .

curl https://dlcdn.apache.org/kafka/3.0.0/kafka_2.12-3.0.0.tgz --output - | tar -xvzf - -C .
