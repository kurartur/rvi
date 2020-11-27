package com.example.imperativestats;

import com.example.data.DataLibConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
@Import(DataLibConfiguration.class)
public class ImperativeStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImperativeStatsApplication.class, args);
	}

}
