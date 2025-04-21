package com.bidemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@EnableJpaAuditing
@SpringBootApplication
public class BidemyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidemyApplication.class, args);
	}

}
