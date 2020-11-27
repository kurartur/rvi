package com.example.reactivestatsr2dbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("stats")
public class Stat {

    @Id
    private Long id;

    private Long companyId;

    private Long roomId;

    private String roomType;

    private LocalDate date;

    private Integer amount;


}
