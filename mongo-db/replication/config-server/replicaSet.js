rs.initiate(
    {
        _id: "configServers",
        configsvr: true,
        members: [
            { _id : 0, host : "config-server-1:27017" },
            { _id : 1, host : "config-server-2:27017" },
            { _id : 2, host : "config-server-3:27017" }
        ]
    }
)
