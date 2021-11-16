#!/usr/bin/env bash

apache-storm-2.3.0/bin/storm local target/storm-study-1.0-SNAPSHOT.jar storm.TopTopology --jars jars/storm-kafka-client-2.3.0.jar,jars/kafka-clients-2.3.0.jar,jars/storm-kafka-1.2.4.jar