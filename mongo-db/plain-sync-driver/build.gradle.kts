plugins {
    id("dbs-learning.java-convention")
}

group = "org.dbs.mongodb.sync.driver"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.mongodb:mongodb-driver-sync:4.10.0")
}
