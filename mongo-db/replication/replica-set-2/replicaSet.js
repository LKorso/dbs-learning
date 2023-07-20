rs.initiate(
    {
        _id: "rs2",
        members: [
            { _id : 1, host : "mongo-rs2-01:27017" },
            { _id : 2, host : "mongo-rs2-02:27017" },
            { _id : 3, host : "mongo-rs2-03:27017" }
        ]
    }
)
