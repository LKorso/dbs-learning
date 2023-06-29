package org.dbs.mongodb.sync.driver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientDemoTest {

    /**
     * This test expects running instance of mongodb on localhost:27017.
     * Also, data from mongo_db_data file should be inserted to mongodb instance.
     */
    @Test
    void findAllBooks() {
        var testInstance = new ClientDemo("mongodb://localhost:27017");
        var actual = testInstance.findAllBooks();
        assertThat(actual.size()).isEqualTo(1);
        var firstItem = actual.stream().findFirst().get();
        assertThat(firstItem.getString("name")).isEqualTo("The Invincible");
        assertThat(firstItem.getString("author")).isEqualTo("Stanislaw Lem");
        assertThat(firstItem.get("_id")).isNotNull();
    }
}