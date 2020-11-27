package com.example.reactivestats;

import com.example.data.DataLibConfiguration;
import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@Import(DataLibConfiguration.class)
public class ReactiveStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveStatsApplication.class, args);
	}

}
