package com.example.reactivestatsr2dbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatSummary {

    private Integer count = 0;
    private BigDecimal amount = new BigDecimal(0);

}
