package com.example.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ImperativeStatsClient {

    @Value("${com.example.client.imperativestats.url}")
    private String url;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Stat> getStats(long companyId) {
        return restTemplate.exchange(url + "/stats?companyId=" + companyId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Stat>>() {}).getBody();
    }

}
