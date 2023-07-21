#!/bin/bash
echo '**********************************************************'
echo 'STARTING MongoDB CLUSTER WITH REPLICATION AND SHARDING'
echo '**********************************************************'

export MONGO_NETWORK=mongo-db-network

echo "Creating network for MongoDB cluster: $MONGO_NETWORK"

docker network create $MONGO_NETWORK

set_up_replica_set() {
  echo "Setting up replication for $1"

  docker-compose -f ./"$1"/docker-compose.yml up -d

  sleep 5

  docker exec "$2" mongosh replicaSet.js
  docker exec "$2" mongosh --eval "rs.status()"
}

set_up_replica_set config-server config-server-1
set_up_replica_set replica-set-1 mongo-rs1-01
set_up_replica_set replica-set-2 mongo-rs2-01

echo 'Starting sharding cluster entry point'

docker-compose -f ./sharding/docker-compose.yml up -d

sleep 10

docker exec mongos mongosh add-shards.js
docker exec mongos mongosh --eval "sh.status()"

echo 'SETTING UP REPLICATION AND SHARDED CLUSTER HAS BEEN FINISHED'
