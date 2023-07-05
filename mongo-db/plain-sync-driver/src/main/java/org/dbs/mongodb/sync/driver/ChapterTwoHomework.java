package org.dbs.mongodb.sync.driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import javax.swing.text.Document;
import java.util.List;

public class ChapterTwoHomework {

    private final String url;

    public ChapterTwoHomework(String url) {
        this.url = url;
    }

    /**
     * Implement a finalize method to output the count as the total.
     */
    List<Document> mapReduce() {
        try (MongoClient client = MongoClients.create(url)) {
            return client.getDatabase("test")
                    .getCollection("phones").mapReduce()
        }
    }
}
