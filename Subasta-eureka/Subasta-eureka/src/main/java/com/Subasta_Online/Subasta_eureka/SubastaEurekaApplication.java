package com.Subasta_Online.Subasta_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SubastaEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubastaEurekaApplication.class, args);
	}

}
