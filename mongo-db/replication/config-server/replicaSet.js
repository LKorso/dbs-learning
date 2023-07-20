rs.initiate(
    {
        _id: "configServers",
        configsvr: true,
        members: [
            { _id : 0, host : "172.20.240.1:40001" },
            { _id : 1, host : "172.20.240.1:40002" },
            { _id : 2, host : "172.20.240.1:40003" }
        ]
    }
)
