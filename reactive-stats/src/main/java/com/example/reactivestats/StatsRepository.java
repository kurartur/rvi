package com.example.reactivestats;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StatsRepository extends ReactiveMongoRepository<Stat, Long>, CustomStatsRepository {

    Flux<Stat> findByCompanyId(Long companyId);

}
