package com.v1gateway.entrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EntraceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntraceApplication.class, args);
	}

}
