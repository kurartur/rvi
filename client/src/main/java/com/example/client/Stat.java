package com.example.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stat {

    private String id;

    private Long companyId;
    private String roomId;
    private String  roomType;
    private LocalDate date;

    private Integer amount;

}
