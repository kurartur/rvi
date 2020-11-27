package com.example.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class R2dbcStatsClient {

    @Value("${com.example.client.r2dbcstats.url}")
    private String url;

    private WebClient client;

    public Flux<Stat> getStats(long companyId) {
        return client.get().uri("/stats?companyId=" + companyId)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Stat>() {
                });
                //.doOnNext(s -> log.info(s.toString()));
    }

    @PostConstruct
    private void postConstruct() {
        client = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(conf -> conf
                            .defaultCodecs()
                            .maxInMemorySize(16 * 1024 * 1024 * 50)
                        ).build())
                .baseUrl(url)
                .build();
    }

}
