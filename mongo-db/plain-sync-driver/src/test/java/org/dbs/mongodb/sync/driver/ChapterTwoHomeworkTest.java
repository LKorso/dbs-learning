package org.dbs.mongodb.sync.driver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class ChapterTwoHomeworkTest {

    @Container
    private static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    private final ChapterTwoHomework testInstance = new ChapterTwoHomework(
            container.getReplicaSetUrl(),
            new PhoneReportDtoMapper()
    );

    @BeforeAll
    static void populateData() throws IOException, InterruptedException {
        TestContainerUtil.importData("test", "phones", "phones.json", container);
    }

    @Test
    void mapReduce() {
        var actual = testInstance.mapReduce();

        assertThat(actual).containsExactlyInAnyOrder(
                expectedItem(5, 1, List.of(1, 2, 3, 4, 5, 6, 7)),
                expectedItem(6, 1, List.of(1, 2, 3, 4, 5, 6, 7)),
                expectedItem(8, 2, List.of(0, 2, 5)),
                expectedItem(8, 3, List.of(0, 4, 5)),
                expectedItem(3, 2, List.of(1, 5))
        );
    }

    private PhoneReportDto expectedItem(int country, int total, List<Integer> digits) {
        return PhoneReportDto.builder()
                .total(total)
                ._id(
                        PhoneReportDto.Id.builder()
                                .country(country)
                                .digits(digits)
                                .build()
                )
                .build();

    }
}