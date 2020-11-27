package com.example.imperativestats;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatsRepository extends JpaRepository<Stat, Long> {

    List<Stat> findByCompanyId(long companyId, Pageable pageable);

    @Query("select count(stat.id) from Stat stat where stat.companyId = :companyId")
    Integer countByCompanyId(long companyId);

    @Query("select sum(stat.amount) from Stat stat where stat.companyId = :companyId")
    Long sumByCompanyId(long companyId);
}
