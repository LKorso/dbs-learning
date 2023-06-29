package org.dbs.mongodb.sync.driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ClientDemo {

    private final String url;

    public ClientDemo(String url) {
        this.url = url;
    }

    public List<Document> findAllBooks() {
        try (MongoClient client = MongoClients.create(url)) {
            var books = client.getDatabase("test-db")
                    .getCollection("books");
            var result = new ArrayList<Document>();
            books.find().forEach(result::add);
            return result;
        }
    }
}
