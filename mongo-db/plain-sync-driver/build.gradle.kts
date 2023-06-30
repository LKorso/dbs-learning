plugins {
    id("dbs-learning.java-convention")
}

group = "org.dbs.mongodb.sync.driver"
version = "1.0-SNAPSHOT"

// TODO refactor to use value from conventional plugin
val testContainersVersion = "1.18.3"

dependencies {
    implementation("org.mongodb:mongodb-driver-sync:4.10.0")

    testImplementation("org.testcontainers:mongodb:$testContainersVersion")
}
