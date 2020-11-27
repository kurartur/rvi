package com.example.imperativestatsmongo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsRepository statsRepository;

    @GetMapping("/stats")
    public List<Stat> getStatsByCompanyId(@RequestParam(name = "companyId") long companyId) {
        return statsRepository.findByCompanyId(companyId, null);
    }

    @GetMapping("/stats/count")
    public Count count() {
        return new Count(statsRepository.count());
    }

    @GetMapping("/stats/summary")
    public StatSummary summary(@RequestParam long companyId) {
        Integer count = statsRepository.countByCompanyId(companyId);
        Long sum = statsRepository.sumByCompanyId(companyId);
        return new StatSummary(count, new BigDecimal(sum));
    }

    @GetMapping("/stats/fetching-all-summary")
    public StatSummary fetchingAllSummary(@RequestParam long companyId) {
        List<Stat> stats = statsRepository.findByCompanyId(companyId, null);
        BigDecimal sum = new BigDecimal(0);
        for (Stat stat : stats) {
            sum = sum.add(new BigDecimal(stat.getAmount()));
        }
        return new StatSummary(stats.size(), sum);
    }

}
