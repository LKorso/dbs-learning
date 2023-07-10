package org.dbs.mongodb.sync.driver;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PhoneReportDto {

    private final Id _id;
    private final Integer total;

    @Data
    @Builder
    public static class Id {
        private final List<Integer> digits;
        private final Integer country;
    }

}
