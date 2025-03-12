package com.devzees.healthprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.devzees"})
public class HealthproviderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthproviderServiceApplication.class, args);
	}

}
