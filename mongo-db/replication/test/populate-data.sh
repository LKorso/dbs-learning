#!/bin/bash
echo 'Enabling sharding for test db'

MONGOS_ID=$(docker ps -aqf "name=mongos")
ENABLE_SHARDING_SCRIPT=enable-sharding.js
DATA_FILE=mongoCities100000.json

copy_to_mongos() {
  docker cp ./"$1" "$MONGOS_ID":"$1"
}

copy_to_mongos $ENABLE_SHARDING_SCRIPT

docker exec mongos mongosh $ENABLE_SHARDING_SCRIPT

echo 'Populating cities collection'

copy_to_mongos $DATA_FILE

docker exec mongos mongoimport --db test --collection cities --jsonArray $DATA_FILE

docker exec mongos mongosh --eval 'sh.status()'

#docker exec mongos mongosh --eval 'sh.moveChunk("test.cities", {name: "Mascalucia"}, "rs2")'
