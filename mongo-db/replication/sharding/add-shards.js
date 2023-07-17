sh.addShard("rs0/mongo-rs0-01:27017,mongo-rs0-02:27017,mongo-rs0-03:27017");
sh.addShard("rs1/mongo-rs1-01:27017,mongo-rs1-02:27017,mongo-rs1-03:27017");

sh.status();
