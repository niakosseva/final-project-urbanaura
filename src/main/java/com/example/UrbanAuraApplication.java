package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.example")
public class UrbanAuraApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbanAuraApplication.class, args);
	}

}
