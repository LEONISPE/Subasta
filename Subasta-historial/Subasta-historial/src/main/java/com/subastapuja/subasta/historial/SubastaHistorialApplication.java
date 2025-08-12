package com.subastapuja.subasta.historial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SubastaHistorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubastaHistorialApplication.class, args);
	}

}
