# ApacheStormStudy

A simple program to run apache storm stream processing


## How to run

Start the cluster with
```sh
$ docker-compose up -d
```

Install python dependencies using virtual environments with
```
$ python -m venv venv
$ . venv/bin/activate
$ pip install -r requirements.txt
```

Execute the kafka producer with
```sh
$ python producer.py
```
> Note: this producer uses MacOSX top command, so you may change it at variable `CMD`

Run the topology with
```sh
$ . bin/run-top-topology.sh
```

Finally, access the storm UI at http://localhost:8080

## Refs

- http://storm.apache.org/releases/current/Setting-up-development-environment.html
- https://storm.apache.org/releases/2.3.0/index.html
- https://github.com/apache/storm/tree/v2.3.0/examples/storm-starter
- https://storm.apache.org/releases/2.3.0/Tutorial.html
- http://storm.apache.org/releases/current/Command-line-client.html
