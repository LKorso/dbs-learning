package org.dbs.mongodb.sync.driver;

import org.bson.Document;

import java.util.function.Function;

public class PhoneReportDtoMapper implements Function<Document, PhoneReportDto> {

    @Override
    public PhoneReportDto apply(Document document) {
        var id = document.get("_id", Document.class);
        return PhoneReportDto.builder()
                ._id(
                        PhoneReportDto.Id.builder()
                                .digits(id.getList("digits", Double.class).stream().map(Double::intValue).toList())
                                .country(id.getInteger("country"))
                                .build()
                )
                .total(document.getDouble("total").intValue())
                .build();
    }

}
