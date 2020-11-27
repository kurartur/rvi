package com.example.data;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@PropertySource("classpath:defaults.properties")
@Configuration
@Getter
public class Defaults {

    @Value("${com.example.data.default.numberofcompanies}")
    private Integer numberOfCompanies;

    @Value("${com.example.data.default.numberofroomtypes.min}")
    private Integer minNumberOfRoomsTypes;

    @Value("${com.example.data.default.numberofroomtypes.max}")
    private Integer maxNumberOfRoomsTypes;

    @Value("${com.example.data.default.numberofrooms.min}")
    private Integer minNumberOfRooms;

    @Value("${com.example.data.default.numberofrooms.max}")
    private Integer maxNumberOfRooms;

    @Value("${com.example.data.default.staydatefrom}")
    private String stayDateFrom;

    @Value("${com.example.data.default.staydateto}")
    private String stayDateTo;

    public LocalDate getStayDateFrom() {
        return LocalDate.parse(stayDateFrom, DateTimeFormatter.ISO_DATE);
    }

    public LocalDate getStayDateTo() {
        return LocalDate.parse(stayDateTo, DateTimeFormatter.ISO_DATE);
    }

}
