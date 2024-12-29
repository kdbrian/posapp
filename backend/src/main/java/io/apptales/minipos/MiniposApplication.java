package io.apptales.minipos;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "io.apptales.minipos.data.dao")
public class MiniposApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniposApplication.class, args);
	}

	/*Json scalar for deserialization */
	@Bean
	public GraphQLScalarType jsonScalar(){
		return ExtendedScalars.Json;
	}

}
