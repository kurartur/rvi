package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	@Bean
	public RouteLocator routeRelocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("imperativeStats", r -> r.path("/is/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8081"))
				.route("reactiveStats", r -> r.path("/rs/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8082"))
				.route("imperativeStatsMongo", r -> r.path("/ismongo/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8087"))
				.route("r2dbc", r -> r.path("/r2dbc/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8086"))
				.route("client", r -> r.host("*:8080").uri("http://localhost:8083"))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
