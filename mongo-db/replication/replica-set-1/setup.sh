#!/bin/bash
echo '**********************************************************'
echo 'Starting the replica set rs1'
echo '**********************************************************'

mongosh replicaSet.js

mongosh --eval "rs.status()"