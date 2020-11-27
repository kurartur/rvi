package com.example.data;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Component
class DataProvider {

    private final Random random = new Random(1L);

    public List<Long> getCompanyIds(int size) {
        return LongStream.rangeClosed(1, size).boxed().collect(Collectors.toList());
    }

    public List<String> getRoomTypes(int min, int max) {
        Set<String> roomTypes = new HashSet<>();
        int size = random.nextInt(max - min) + min;
        while (roomTypes.size() < size) {
            roomTypes.add(generateString(5));
        }
        return new ArrayList<>(roomTypes);
    }

    public List<Integer> getRoomNumbers(int min, int max) {
        int size = random.nextInt(max - min) + min;
        return IntStream.rangeClosed(1, size).boxed().collect(Collectors.toList());
    }

    public List<DateRange> getStayDates(LocalDate dateFrom, LocalDate dateTo) {
        List<DateRange> dateRanges = new ArrayList<>();
        LocalDate pointer = dateFrom;
        while (!pointer.isAfter(dateTo)) {
            int days = random.nextInt(9) + 1;
            dateRanges.add(new DateRange(pointer, pointer.plusDays(days)));
            pointer = pointer.plusDays(days);
        }
        return dateRanges;
    }

    private String generateString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Integer generateAmount() {
        return new Random().nextInt(1000) + 1;
    }

    public static class DateRange {
        private LocalDate dateFrom;
        private LocalDate dateTo;

        public DateRange(LocalDate dateFrom, LocalDate dateTo) {
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }
    }

}
