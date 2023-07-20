#!/bin/bash
echo '**********************************************************'
echo 'STARTING MongoDB CLUSTER WITH REPLICATION AND SHARDING'
echo '**********************************************************'

echo 'Starting replicated config servers'

docker-compose -f ./config-server/docker-compose.yml up -d

sleep 5

docker exec config-server-1 bash -c "./setup.sh"

echo 'Starting replica set: rs1'

docker-compose -f ./replica-set-1/docker-compose.yml up -d

sleep 5

docker exec mongo-rs1-01 bash -c "./setup.sh"

echo 'Starting replica set: rs2'

docker-compose -f ./replica-set-2/docker-compose.yml up -d

sleep 5

docker exec mongo-rs2-01 bash -c "./setup.sh"

echo 'Starting sharding cluster entry point'

docker-compose -f ./sharding/docker-compose.yml up -d

sleep 10

docker exec mongos bash -c "./setup.sh"

echo 'SETTING UP REPLICATION AND SHARDED CLUSTER HAS BEEN FINISHED'
