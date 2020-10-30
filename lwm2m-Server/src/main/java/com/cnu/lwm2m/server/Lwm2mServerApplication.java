package com.cnu.lwm2m.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableScheduling
public class Lwm2mServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lwm2mServerApplication.class, args);
	}

}
