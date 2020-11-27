package com.example.reactivestats;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final DataInitialiser dataInitialiser;
    private final StatsRepository statsRepository;

    @GetMapping("/stats")
    public Flux<Stat> getStatsByCompanyId(@RequestParam(name = "companyId") long companyId) {
        return statsRepository.findByCompanyId(companyId)
                .doOnSubscribe(s -> log.info("Subscribed!"))
                .doOnCancel(() -> log.info("Canceled"))
                .doOnTerminate(() -> log.info("Terminated"))
                .doOnError(throwable -> log.info("Error " + throwable.getMessage()))
                .doOnComplete(() -> log.info("On complete"));
    }

    @GetMapping("/stats/count")
    public Mono<Count> getStatsCount() {
        return statsRepository.count()
                .map(Count::new);
    }

    @PostMapping("/stats/regenerate")
    public void regenerate() {
        dataInitialiser.reinsertData();
    }

    @GetMapping("/stats/summary")
    public Mono<StatSummary> summary(@RequestParam long companyId) {
        return Mono.zip(
                statsRepository.countByCompanyId(companyId),
                statsRepository.sumByCompanyId(companyId))
                .map(tuple -> new StatSummary(tuple.getT1(), new BigDecimal(tuple.getT2())));
    }

    @GetMapping("/stats/fetching-all-summary")
    public Mono<StatSummary> fetchingAllSummary(@RequestParam long companyId) {
        return statsRepository.findByCompanyIdWithTemplate(companyId)
                .reduce(new StatSummary(), (sum, stat) -> {
                    sum.setCount(sum.getCount() + 1);
                    sum.setAmount(sum.getAmount().add(new BigDecimal(stat.getAmount())));
                    return sum;
                });
    }

}