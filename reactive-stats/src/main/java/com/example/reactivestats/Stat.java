package com.example.reactivestats;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Document
@CompoundIndexes({
        @CompoundIndex(name = "idx_unique", def = "{'roomId' : 1, 'companyId' : 1, 'date' : 1}", unique = true)
})
public class Stat {

    @Id
    private String id;

    private Long roomId;

    @Indexed
    private Long companyId;

    @Indexed
    private String roomType;

    private LocalDate date;

    private Integer amount;

}
