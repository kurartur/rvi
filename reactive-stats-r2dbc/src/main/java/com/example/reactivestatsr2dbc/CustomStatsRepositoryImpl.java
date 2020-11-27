package com.example.reactivestatsr2dbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class CustomStatsRepositoryImpl implements CustomStatsRepository {

    private final DatabaseClient databaseClient;

    private final R2dbcEntityOperations operations;

    public Mono<Integer> countByCompanyId(long companyId) {
        Query query = Query.query(Criteria.where("companyId").is(companyId));
        return operations.count(query, Stat.class)
                .map(Long::intValue);
    }

    @Override
    public Mono<Long> sumByCompanyId(long companyId) {
        return databaseClient.sql("select sum(amount) as sum from stats where company_id = :companyId")
                .bind("companyId", companyId)
                .fetch()
                .first()
                .map(m -> m.get("sum"))
                .cast(Long.class);
    }

}
