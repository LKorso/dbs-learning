#!/bin/bash
echo **********************************************************
echo Starting the replica set rs0
echo **********************************************************

sleep 10 | echo Sleeping
mongosh replicaSet.js
