package com.example.imperativestatsmongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatsRepository extends MongoRepository<Stat, Long>, CustomStatsRepository {

    List<Stat> findByCompanyId(long companyId, Pageable pageable);

}
