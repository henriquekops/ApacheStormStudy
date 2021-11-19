#!/usr/bin/env bash

docker exec -it nimbus storm jar /target/storm-study-1.0-SNAPSHOT.jar storm.TopTopology --jars /jars/storm-kafka-client-2.3.0.jar,/jars/kafka-clients-2.3.0.jar,/jars/storm-kafka-1.2.4.jar,/jars/jackson-core-2.13.0.jar,/jars/jackson-databind-2.13.0.jar,/jars/jackson-annotations-2.13.0.jar,/jars/commons-lang-2.6.jar
