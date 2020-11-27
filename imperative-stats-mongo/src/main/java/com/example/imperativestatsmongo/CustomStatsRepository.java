package com.example.imperativestatsmongo;

import java.util.List;

public interface CustomStatsRepository {

    Integer countByCompanyId(long companyId);

    Long sumByCompanyId(long companyId);

    List<Stat> findByCompanyIdWithTemplate(long companyId);

}
