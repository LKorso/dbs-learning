package org.dbs.mongodb.sync.driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.util.List;
import java.util.stream.StreamSupport;

public class ClientDemo {

    private final String url;

    public ClientDemo(String url) {
        this.url = url;
    }

    public List<Document> findAllBooks() {
        try (MongoClient client = MongoClients.create(url)) {
            return StreamSupport.stream(
                    client.getDatabase("test-db")
                            .getCollection("books")
                            .find()
                            .spliterator(),
                    false
            ).toList();
        }
    }
}
