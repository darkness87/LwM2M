package com.cnu.lwm2m.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Lwm2mServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lwm2mServerApplication.class, args);
	}

}
