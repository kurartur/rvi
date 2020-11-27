package com.example.imperativestatsmongo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
@Slf4j
public class CustomStatsRepositoryImpl implements CustomStatsRepository {

    private final MongoOperations mongoOperations;

    public Integer countByCompanyId(long companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyId").is(companyId));
        return Long.valueOf(mongoOperations.count(query, Stat.class)).intValue();
    }

    @Override
    public Long sumByCompanyId(long companyId) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("companyId").is(companyId)),
                group("companyId").sum("amount").as("sum"),
                project("sum")
        );
        return mongoOperations.aggregate(aggregation, Stat.class, Sum.class).getMappedResults().get(0).getSum();
    }

    @Override
    public List<Stat> findByCompanyIdWithTemplate(long companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyId").is(companyId));
        return mongoOperations.find(query, Stat.class);
    }

    @Data
    private static class Sum {
        private Long sum;
    }

}
