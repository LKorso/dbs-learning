package org.dbs.mongodb.sync.driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class ChapterOneHomework {

    private final String url;

    public ChapterOneHomework(String url) {
        this.url = url;
    }

    /**
     * Select a town via a case-insensitive regular expression containing the word new.
     */
    public List<Document> getTownsWithNew() {
        return findInTowns(containsIgnoringCase("new"));
    }

    /**
     * Find all cities whose names contain an e and are famous for food or beer.
     */
    public List<Document> getTownsWithEFamousByFoodOrBeer() {
        return findInTowns(and(containsIgnoringCase("e"), in("famousFor", "food", "beer")));
    }

    /**
     * Create a new database named blogger with a collection named articles.
     * Insert a new article with an author name and email, creation date, and text.
     */
    public String addArticle() {
        try (MongoClient client = MongoClients.create(url)) {
            return client.getDatabase("blogger")
                    .getCollection("articles")
                    .insertOne(
                            new Document()
                                    .append("author", "LKorso")
                                    .append("name", "Article about Mongodb and testcontainers")
                                    .append("email", "lkorso@gmail.com")
                                    .append("created", "2023-06-30T12:06:48")
                                    .append("text", "Wow, it's so easy to test Mongo with testcontainers!!!")
                    )
                    .getInsertedId()
                    .asObjectId()
                    .getValue()
                    .toString();
        }
    }

    public void addCommentToArticle(String articleId, String comment, String author) {
        try (MongoClient client = MongoClients.create(url)) {
            var result = client.getDatabase("blogger")
                    .getCollection("articles")
                    .updateOne(
                            eq("_id", articleId),
                            push("comments", new Document().append("text", comment).append("author", author))
                    );
            System.out.println(result);
        }
    }

    private List<Document> findInTowns(Bson filter) {
        try (MongoClient client = MongoClients.create(url)) {
            return StreamSupport.stream(
                    client.getDatabase("test-db")
                            .getCollection("towns")
                            .find(filter)
                            .spliterator(),
                    false
            ).toList();
        }
    }

    private Bson containsIgnoringCase(String value) {
        return regex("name", ".*" + value + ".*", "i");
    }
}
