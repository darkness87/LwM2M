package com.cnu.metering.agent;

import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.cnu.metering.agent.init.CNUCoapServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MeteringAgentApplication implements ApplicationRunner {

	private static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(MeteringAgentApplication.class, args);
		MeteringAgentApplication.context.getBean("CNUCoapServer", CNUCoapServer.class).run();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("application args check: {}", Arrays.toString(args.getSourceArgs()));
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
