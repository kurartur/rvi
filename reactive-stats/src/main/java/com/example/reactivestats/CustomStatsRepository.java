package com.example.reactivestats;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomStatsRepository {

    Mono<Integer> countByCompanyId(long companyId);

    Mono<Long> sumByCompanyId(long companyId);

    Flux<Stat> findByCompanyIdWithTemplate(long companyId);

}
