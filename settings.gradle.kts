rootProject.name = "dbs-learning"
include("mongo-db", "mongo-db:plain-sync-driver")
findProject(":mongo-db:plain-sync-driver")?.name = "plain-sync-driver"
