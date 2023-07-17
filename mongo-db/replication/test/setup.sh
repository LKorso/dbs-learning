#!/bin/bash
echo **********************************************************
echo Testing mongo set up
echo **********************************************************

mongod --shardsvr --replSet rs1 --quiet &

mongosh sleep 10 | echo Sleeping
echo $(mongosh mongodb://localhost:27017 --eval "show dbs")

echo Script end
