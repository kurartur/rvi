package com.example.data;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class GeneratedStat {

    private Long companyId;
    private Long roomId;
    private String roomType;
    private LocalDate date;
    private Integer amount;

}
