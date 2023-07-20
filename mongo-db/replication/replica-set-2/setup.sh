#!/bin/bash
echo '**********************************************************'
echo 'Starting the replica set rs2'
echo '**********************************************************'

mongosh replicaSet.js

mongosh --eval "rs.status()"
