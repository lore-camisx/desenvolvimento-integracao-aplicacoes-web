package com.example.Fipe_RestAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class FipeRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FipeRestApiApplication.class, args);
	}
}
