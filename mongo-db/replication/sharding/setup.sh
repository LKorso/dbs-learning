#!/bin/bash
echo **********************************************************
echo Setting up sharding
echo **********************************************************

sleep 10 | echo Sleeping
mongosh mongodb://sharding:27017/admin add-shards.js