plugins {
    java
}

repositories {
    mavenCentral()
}

val jUnitVersion = "5.9.2"

dependencies {
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}