#!/bin/bash
echo '**********************************************************'
echo 'Setting up sharding'
echo '**********************************************************'

mongosh add-shards.js

mongosh --eval "sh.status()"