package com.cnu.lwm2m.client;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.cnu.lwm2m.client.init.CNULwm2mClient;

@SpringBootApplication
public class Lwm2mClientApplication {

	public static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(Lwm2mClientApplication.class, args);
		Lwm2mClientApplication.context.getBean("CNULwm2mClient", CNULwm2mClient.class).run();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
}
