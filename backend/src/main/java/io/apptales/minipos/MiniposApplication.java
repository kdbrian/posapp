package io.apptales.minipos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "io.apptales.minipos.data.dao")
public class MiniposApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniposApplication.class, args);
	}

}
