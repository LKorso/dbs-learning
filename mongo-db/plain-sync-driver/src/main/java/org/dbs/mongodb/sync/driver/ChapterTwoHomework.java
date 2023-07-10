package org.dbs.mongodb.sync.driver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Accumulators.*;

public class ChapterTwoHomework {

    private final String url;
    private final PhoneReportDtoMapper mapper;

    public ChapterTwoHomework(String url, PhoneReportDtoMapper mapper) {
        this.url = url;
        this.mapper = mapper;
    }

    /**
     * Implement a finalize method to output the count as the total.
     */
    List<PhoneReportDto> mapReduce() {
        try (MongoClient client = MongoClients.create(url)) {
            return client.getDatabase("test")
                    .getCollection("phones")
                    .aggregate(
                            List.of(
                                    project(
                                            computed(
                                                    "emits",
                                                    fields(
                                                            computed(
                                                                    "key",
                                                                    fields(
                                                                            computed("digits", function("function(number) { return [...new Set(number.toString().split('').map(Number))].sort() }", List.of("$components.number"))),
                                                                            new Document("country", "$components.country")
                                                                    )
                                                            ),
                                                            computed("value", new Document("$literal", 1))
                                                    )
                                            )
                                    ),
                                    unwind("$emits"),
                                    group(
                                            "$emits.key",
                                            accumulator(
                                                    "total",
                                                    "function() { return {count: 0} }",
                                                    List.of(),
                                                    "function(state, count) { return { count: state.count + 1 } }",
                                                    List.of("$emits.value"),
                                                    "function(state1, state2) { return { count: state1.count + state2.count } }",
                                                    "function(state) { return state.count }"
                                            )
                                    )
                            )
                    )
                    .map(mapper::apply)
                    .into(new ArrayList<>());
        }
    }                                                                                                                                                                                   //--

    private Document function(String body, List<String> args) {
        return new Document(
                "$function",
                new Document().append("body", body).append("args", args).append("lang", "js")
        );
    }

}
