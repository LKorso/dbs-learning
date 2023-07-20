db.runCommand({enablesharding: 'test'});

db.runCommand({shardcollection: "test.cities", key: {name: 1}});
