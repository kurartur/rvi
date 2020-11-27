package com.example.reactivestatsr2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface StatsRepository extends ReactiveCrudRepository<Stat, Long>, CustomStatsRepository {

    Flux<Stat> findByCompanyId(Long companyId);

}
