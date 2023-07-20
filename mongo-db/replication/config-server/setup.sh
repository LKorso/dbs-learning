#!/bin/bash
echo '**********************************************************'
echo 'Setting up replica set configServers'
echo '**********************************************************'

mongosh replicaSet.js

mongosh --eval "rs.status()"
