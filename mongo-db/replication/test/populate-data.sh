#!/bin/bash
echo 'Enabling sharding for test db'

MONGOS_ID=$(docker ps -aqf "name=mongos")
CHANGE_CHUNK_SIZE_SCRIPT=change-chunk-size.js
ENABLE_SHARDING_SCRIPT=enable-sharding.js
DATA_FILE=mongoCities100000.json

docker cp ./$CHANGE_CHUNK_SIZE_SCRIPT $MONGOS_ID:$CHANGE_CHUNK_SIZE_SCRIPT

docker exec mongos mongosh mongodb://localhost:27017/config $CHANGE_CHUNK_SIZE_SCRIPT

docker cp ./$ENABLE_SHARDING_SCRIPT $MONGOS_ID:$ENABLE_SHARDING_SCRIPT

docker exec mongos bash -c "mongosh mongodb://localhost:27017/admin $ENABLE_SHARDING_SCRIPT"

echo 'Populating cities collection'

docker cp ./$DATA_FILE $MONGOS_ID:$DATA_FILE

docker exec mongos bash -c "mongoimport --db test --collection cities --jsonArray $DATA_FILE"
