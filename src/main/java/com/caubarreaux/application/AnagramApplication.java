package com.caubarreaux.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.caubarreaux.integration"})
@ComponentScan(basePackages = {"com.caubarreaux.service", "com.caubarreaux.controller", "com.caubarreaux.error", "com.caubarreaux.swagger"})
public class AnagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnagramApplication.class, args);
	}
}
