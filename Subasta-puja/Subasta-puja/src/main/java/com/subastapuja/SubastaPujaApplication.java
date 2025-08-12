package com.subastapuja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubastaPujaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubastaPujaApplication.class, args);
	}

}
