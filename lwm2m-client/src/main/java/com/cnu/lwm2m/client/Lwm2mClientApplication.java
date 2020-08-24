package com.cnu.lwm2m.client;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lwm2mClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lwm2mClientApplication.class, args);

		// endpoint는 DCU ID???
		String endpoint = "CNU0492010001";
		LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
		LeshanClient client = builder.build();
		client.start();
	}
}
