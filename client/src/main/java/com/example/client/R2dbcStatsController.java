package com.example.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequestMapping("/client/r2dbc/stats")
@RestController
@RequiredArgsConstructor
public class R2dbcStatsController {

    private final R2dbcStatsClient reactiveStatsClient;

    @GetMapping("/summary")
    public Mono<StatSummary> computeTotal(@RequestParam long companyId) {
        return reactiveStatsClient.getStats(companyId)
                .reduce(new StatSummary(), (sum, stat) -> {
                    sum.setCount(sum.getCount() + 1);
                    sum.setAmount(sum.getAmount().add(new BigDecimal(stat.getAmount())));
                    return sum;
                });
    }

}
