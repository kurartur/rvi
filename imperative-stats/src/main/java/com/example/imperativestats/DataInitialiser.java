package com.example.imperativestats;

import com.example.data.StatsGeneratorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitialiser {

    private static final int BATCH_SIZE = 1000;

    private final StatsGeneratorFactory statsGeneratorFactory;
    private final JdbcTemplate jdbcTemplate;

    public void reinsertData() {
        List<Stat> batch = new ArrayList<>();
        AtomicLong id = new AtomicLong(1L);
        log.info("Deleting...");
        jdbcTemplate.execute("truncate table stats;");
        log.info("Generating...");
        statsGeneratorFactory.withDefaults().generate(generatedStat -> {
            batch.add(Stat.builder()
                .date(generatedStat.getDate())
                .roomId(generatedStat.getRoomId())
                .roomType(generatedStat.getRoomType())
                .amount(generatedStat.getAmount())
                .companyId(generatedStat.getCompanyId())
                .id(id.getAndAdd(1L))
                .build()
            );
            if (batch.size() == BATCH_SIZE) {
                updateBatch(batch);
                log.info("Saved stats batch");
                batch.clear();
            }
        });
        if (!batch.isEmpty()) {
            updateBatch(batch);
        }
        log.info("Finished inserting stats");
    }

    private void updateBatch(List<Stat> batch) {
        jdbcTemplate.batchUpdate("insert into stats (id, company_id, date, room_id, room_type, amount) values (?, ?, ?, ?, ?, ?)",
                batch,
                BATCH_SIZE,
                (ps, argument) -> {
                    ps.setLong(1, argument.getId());
                    ps.setLong(2, argument.getCompanyId());
                    ps.setDate(3, Date.valueOf(argument.getDate()));
                    ps.setLong(4, argument.getRoomId());
                    ps.setString(5, argument.getRoomType());
                    ps.setInt(6, argument.getAmount());
                });
    }
}
