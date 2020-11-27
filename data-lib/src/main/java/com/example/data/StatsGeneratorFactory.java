package com.example.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StatsGeneratorFactory {

    @Autowired
    private DataProvider dataProvider;

    @Autowired
    private Defaults defaults;

    public StatsGenerator withDefaults() {
        return new StatsGenerator(defaults.getNumberOfCompanies(),
                defaults.getMinNumberOfRoomsTypes(),
                defaults.getMaxNumberOfRoomsTypes(),
                defaults.getMinNumberOfRooms(),
                defaults.getMaxNumberOfRooms(),
                new DataProvider.DateRange(defaults.getStayDateFrom(), defaults.getStayDateTo()),
                dataProvider);
    }

}
