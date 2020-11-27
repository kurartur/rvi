package com.example.reactivestats;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
@Slf4j
public class CustomStatsRepositoryImpl implements CustomStatsRepository {

    private final ReactiveMongoOperations mongoOperations;

    public Mono<Integer> countByCompanyId(long companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyId").is(companyId));
        return mongoOperations.count(query, Stat.class)
            .map(Long::intValue)
            .doOnNext(s -> log.info("Count ready"));
    }

    @Override
    public Mono<Long> sumByCompanyId(long companyId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("companyId").is(companyId)),
                group("companyId").sum("amount").as("sum"),
                project("sum")
        );
        return mongoOperations.aggregate(aggregation, Stat.class, Sum.class)
                .next()
                .map(Sum::getSum)
                .doOnNext(s -> log.info("Sum ready"));
    }

    @Override
    public Flux<Stat> findByCompanyIdWithTemplate(long companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyId").is(companyId));
        return mongoOperations.find(query, Stat.class);
    }

    @Data
    private static class Sum {
        private Long sum;
    }

}
