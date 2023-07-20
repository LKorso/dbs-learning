rs.initiate(
    {
        _id: "rs1",
        members: [
            { _id : 1, host : "mongo-rs1-01:27017" },
            { _id : 2, host : "mongo-rs1-02:27017" },
            { _id : 3, host : "mongo-rs1-03:27017" }
        ]
    }
)
