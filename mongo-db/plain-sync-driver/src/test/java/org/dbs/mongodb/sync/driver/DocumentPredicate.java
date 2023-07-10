package org.dbs.mongodb.sync.driver;

import org.bson.Document;

import java.util.function.Predicate;


public record DocumentPredicate(String field, String value) implements Predicate<Document> {

    public static DocumentPredicate hasFieldWithValue(String field, String value) {
        return new DocumentPredicate(field, value);
    }

    @Override
    public boolean test(Document document) {
        return document != null && value.equals(document.getString(field));
    }

}
