package com.example.reactivestats;

import com.example.data.GeneratedStat;
import com.example.data.StatsGeneratorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
@ImportResource
@Slf4j
public class DataInitialiser {

    @Autowired
    private StatsGeneratorFactory statsGeneratorFactory;

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    public void reinsertData() {
        reactiveMongoOperations.dropCollection(Stat.class)
                .doOnSuccess(res -> insert())
                .subscribe();
    }

    private void insert() {
        log.info("Generating and inserting data");

        Flux<GeneratedStat> flux = Flux.create(fluxSink -> {
            statsGeneratorFactory.withDefaults().generate(fluxSink::next);
            fluxSink.complete();
        }, FluxSink.OverflowStrategy.BUFFER);

        flux
                .map(generatedStat -> {
                    return Stat.builder()
                            .roomId(generatedStat.getRoomId())
                            .roomType(generatedStat.getRoomType())
                            .date(generatedStat.getDate())
                            .companyId(generatedStat.getCompanyId())
                            .amount(generatedStat.getAmount())
                            .build();
                })
                .buffer(1000)
                .flatMap(list -> {
                    return reactiveMongoOperations.insert(list, Stat.class).then();
                })
                .limitRate(3)
                .count()
                .subscribe(c -> log.info("Saved {} batches", c), throwable -> log.error(throwable.getMessage(), throwable));
    }

}
