package com.sattlerjoshua.spring.api.docs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple Application demonstrating how to document Spring-based web applications.
 *
 * @author joshua.sattler@mailbox.org
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	Map<Long, Person> persons(){
		return new HashMap<>();
	}
}
