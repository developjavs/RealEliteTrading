package com.realelitetrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.realelitetrading.*"})
public class RealelitetradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealelitetradingApplication.class, args);
	}

}
