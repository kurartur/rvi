package com.example.reactivestatsr2dbc;

import reactor.core.publisher.Mono;

public interface CustomStatsRepository {

    Mono<Integer> countByCompanyId(long companyId);

    Mono<Long> sumByCompanyId(long companyId);

}
