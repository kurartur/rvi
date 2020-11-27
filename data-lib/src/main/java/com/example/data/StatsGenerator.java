package com.example.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.function.Consumer;

public class StatsGenerator {

    private final int numberOfCompanies;
    private final int minNumberOfRoomTypes;
    private final int maxNumberOfRoomTypes;
    private final int minNumberOfRooms;
    private final int maxNumberOfRooms;
    private final DataProvider.DateRange stayDateRange;

    private final DataProvider dataProvider;

    StatsGenerator(int numberOfCompanies,
                   int minNumberOfRoomTypes, int maxNumberOfRoomTypes,
                   int minNumberOfRooms, int maxNumberOfRooms,
                   DataProvider.DateRange stayDateRange, DataProvider dataProvider) {
        this.numberOfCompanies = numberOfCompanies;
        this.minNumberOfRoomTypes = minNumberOfRoomTypes;
        this.maxNumberOfRoomTypes = maxNumberOfRoomTypes;
        this.minNumberOfRooms = minNumberOfRooms;
        this.maxNumberOfRooms = maxNumberOfRooms;
        this.stayDateRange = stayDateRange;
        this.dataProvider = dataProvider;
    }

    public void generate(Consumer<GeneratedStat> consumer) {
        Long roomId = 1L;
        for (Long companyId : dataProvider.getCompanyIds(numberOfCompanies)) {
            for (String roomType : dataProvider.getRoomTypes(minNumberOfRoomTypes, maxNumberOfRoomTypes)) {
                for (Integer roomNumber : dataProvider.getRoomNumbers(minNumberOfRooms, maxNumberOfRooms)) {
                    for (DataProvider.DateRange stayDate : dataProvider.getStayDates(stayDateRange.getDateFrom(), stayDateRange.getDateTo())) {
                        for (LocalDate pointer = stayDate.getDateFrom(); pointer.isBefore(stayDate.getDateTo()); pointer = pointer.plusDays(1)) {
                            consumer.accept(GeneratedStat.builder()
                                    .companyId(companyId)
                                    .roomId(roomId)
                                    .amount(dataProvider.generateAmount())
                                    .roomType(roomType)
                                    .date(pointer)
                                    .build());
                        }
                    }
                    roomId++;
                }
            }
        }
    }

}
