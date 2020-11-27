package com.example.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/client/is/stats")
@RequiredArgsConstructor
public class ImperativeStatsController {

    private final ImperativeStatsClient imperativeStatsClient;

    @GetMapping("/summary")
    public StatSummary computeTotal(@RequestParam long companyId) {
        BigDecimal sum = new BigDecimal(0);
        List<Stat> stats = imperativeStatsClient.getStats(companyId);
        for (Stat stat : stats) {
            sum = sum.add(new BigDecimal(stat.getAmount()));
        }
        return new StatSummary(stats.size(), sum);
    }

}
