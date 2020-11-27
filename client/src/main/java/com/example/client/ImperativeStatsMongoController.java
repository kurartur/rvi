package com.example.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/client/ismongo/stats")
@RequiredArgsConstructor
public class ImperativeStatsMongoController {

    private final ImperativeStatsMongoClient client;

    @GetMapping("/summary")
    public StatSummary computeTotal(@RequestParam long companyId) {
        BigDecimal sum = new BigDecimal(0);
        List<Stat> stats = client.getStats(companyId);
        for (Stat stat : stats) {
            sum = sum.add(new BigDecimal(stat.getAmount()));
        }
        return new StatSummary(stats.size(), sum);
    }

}
