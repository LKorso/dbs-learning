rs0_conf = {
    _id: "rs0",
    members: [
        { _id: 1, host: "mongo-rs0-01:27017"},
        { _id: 2, host: "mongo-rs0-02:27017"},
        { _id: 3, host: "mongo-rs0-03:27017"}
    ]
}

rs.initiate(rs0_conf);

rs.conf();

rsConfigSet_conf = {
    _id: "configSet",
    configsvr: true,
    members: [
        { _id: 1, host: "mongo-rs0-01:27019"},
        { _id: 2, host: "mongo-rs0-02:27019"},
        { _id: 3, host: "mongo-rs0-03:27019"}
    ]
}

rs.initiate(rsConfigSet_conf);

rs.conf();
