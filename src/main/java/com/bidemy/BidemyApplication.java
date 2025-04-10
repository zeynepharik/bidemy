package com.bidemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages ={"com.bidemy"})
@ComponentScan(basePackages ={"com.bidemy"})
@EnableJpaRepositories(basePackages ={"com.bidemy"})
@EnableJpaAuditing
@SpringBootApplication
public class BidemyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidemyApplication.class, args);
	}

}
