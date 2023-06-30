package org.dbs.mongodb.sync.driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static com.mongodb.client.model.Filters.*;

@Testcontainers
class ClientDemoTest {

    @Container
    private static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    private final ChapterOneHomework testInstance = new ChapterOneHomework(container.getReplicaSetUrl());

    @BeforeAll
    static void populateData() throws IOException, InterruptedException {
        TestContainerUtil.importData("test-db", "towns", "towns.json", container);
    }

    @Test
    void getTownsWithNew() {
        var actual = testInstance.getTownsWithNew();

        assertThat(actual).hasSize(1);
        assertThat(actual).allMatch(hasFieldWithValue("name", "New York"));
    }

    @Test
    void getTownsWithEFamousByFoodOrBeer() {
        var actual = testInstance.getTownsWithEFamousByFoodOrBeer();

        assertThat(actual).hasSize(1);
        assertThat(actual).allMatch(hasFieldWithValue("name", "New York"));
    }

    @Test
    void addArticle() {
        var id = testInstance.addArticle();

        try (MongoClient client = MongoClients.create(container.getReplicaSetUrl())) {
            var actual = client.getDatabase("blogger")
                    .getCollection("articles")
                    .find(eq("_id", new ObjectId(id)));

            assertThat(actual).hasSize(1);
            assertThat(actual).allMatch(
                    hasFieldWithValue("author", "LKorso")
                            .and(hasFieldWithValue("name", "Article about Mongodb and testcontainers"))
                            .and(hasFieldWithValue("email", "lkorso@gmail.com"))
                            .and(hasFieldWithValue("created", "2023-06-30T12:06:48"))
                            .and(hasFieldWithValue("text", "Wow, it's so easy to test Mongo with testcontainers!!!"))
            );
        }
    }

    @Test
    void updateArticle() throws IOException, InterruptedException {
        TestContainerUtil.importData("blogger", "articles", "articles.json", container);

        var articleId = "649ead82f175542e950eb34f";
        testInstance.addCommentToArticle(articleId, "Great article", "author-1");
        testInstance.addCommentToArticle(articleId, "It's dog water", "author-2");

        try (MongoClient client = MongoClients.create(container.getReplicaSetUrl())) {
            var actual = client.getDatabase("blogger")
                    .getCollection("articles")
                    .find(eq("_id", articleId))
                    .first();

            var actualComments = actual.getList("comments", Document.class);
            assertThat(actualComments).hasSize(2);
            assertThat(actualComments)
                    .anyMatch(hasFieldWithValue("text", "Great article").and(hasFieldWithValue("author", "author-1")))
                    .anyMatch(hasFieldWithValue("text", "It's dog water").and(hasFieldWithValue("author", "author-2")));
        }
    }

    private Predicate<Document> hasFieldWithValue(String field, String value) {
        return document -> document != null && value.equals(document.getString(field));
    }
}