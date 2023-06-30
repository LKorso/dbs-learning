package org.dbs.mongodb.sync.driver;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class ClientDemoTest {

    @Container
    private static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Test
    void findAllBooks() throws IOException, InterruptedException {
        var fileWithData = "books_data.json";
        container.copyFileToContainer(MountableFile.forClasspathResource(fileWithData), fileWithData);
        container.execInContainer("sh", "-c", "bin/mongoimport --db test-db --collection books --file " + fileWithData + " --jsonArray");

        var actual = new ClientDemo(container.getReplicaSetUrl()).findAllBooks();

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual)
                .anyMatch(hasFieldWithValue("name", "The Invincible").and(hasFieldWithValue("author", "Stanislaw Lem")))
                .anyMatch(hasFieldWithValue("name", "4321").and(hasFieldWithValue("author", "Paul Auster")));
    }

    private Predicate<Document> hasFieldWithValue(String field, String value) {
        return document -> document != null && value.equals(document.getString(field));
    }
}