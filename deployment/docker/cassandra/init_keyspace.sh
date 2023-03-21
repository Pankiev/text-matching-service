#!/usr/bin/env bash

until printf "" 2>>/dev/null >>/dev/tcp/cassandra-text-matching-seed/9042; do
    sleep 5;
    echo "Waiting for cassandra...";
done

echo "Creating keyspace..."
cqlsh cassandra-text-matching-seed -u cassandra -p cassandra -e "CREATE KEYSPACE IF NOT EXISTS text_matching WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '2'};"
