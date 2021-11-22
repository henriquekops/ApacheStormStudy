#!/usr/bin/env python
# -*- coding: utf-8 -*-

# built-in dependencies
import subprocess
import sys

# external dependencies
from kafka import KafkaProducer

__authors__ = 'Henrique Kops & Gabriel Castro'

HELP = "Usage:\n\tpython producer.py <SERVERS> <TOPIC>"
CMD = "top -l 0 -stats pid,cpu"

if __name__ == "__main__":
    args = sys.argv
    
    if len(args) != 3:
        print(HELP)
    else:
        servers = args[1]
        topic = args[2]
        print(f"Producing top events to {servers} on topic {topic}")
        print("Ctrl+C to exit")
        try:
            prod = KafkaProducer(bootstrap_servers=servers)
            proc = subprocess.Popen(
                CMD,
                shell=True,
                stdout=subprocess.PIPE,
                universal_newlines=True
            )
            for line in iter(proc.stdout.readline, ""):
                array = line.split()
                if array and array[0].isnumeric():
                    prod.send(topic, bytes(" ".join(array), "UTF-8"))
        except KeyboardInterrupt:
            print("\nBye")
