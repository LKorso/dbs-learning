#!/bin/bash
echo **********************************************************
echo Starting the replica set rs0
echo **********************************************************

sleep 10 | echo Sleeping
mongosh mongodb://mongo-rs0-01:27017 replicaSet-rs0.js

echo **********************************************************
echo Starting the replica set rs1
echo **********************************************************

mongosh mongodb://mongo-rs1-01:27017 replicaSet-rs1.js