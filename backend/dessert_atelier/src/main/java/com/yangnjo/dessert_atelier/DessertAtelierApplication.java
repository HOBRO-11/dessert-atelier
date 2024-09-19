package com.yangnjo.dessert_atelier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DessertAtelierApplication {

	public static void main(String[] args) {
		SpringApplication.run(DessertAtelierApplication.class, args);
	}

}
