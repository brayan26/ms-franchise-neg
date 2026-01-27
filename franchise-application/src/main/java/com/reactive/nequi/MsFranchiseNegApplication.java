package com.reactive.nequi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MsFranchiseNegApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsFranchiseNegApplication.class, args);
		System.out.println("Cores: " + Runtime.getRuntime().availableProcessors());
	}

}
