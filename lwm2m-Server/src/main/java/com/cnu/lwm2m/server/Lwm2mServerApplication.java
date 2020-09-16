package com.cnu.lwm2m.server;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Lwm2mServerApplication implements ApplicationRunner {

	public static ApplicationContext context;

	@Autowired
	public ResourceLoader resourceLoader;

	private static String[] modelPaths;

	public static void main(String[] args) {
		context = SpringApplication.run(Lwm2mServerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Resource resource = resourceLoader.getResource("classpath:models");
		File file = resource.getFile();

		if (file.isDirectory() && file.exists()) {
			modelPaths = file.list();
			log.info("Resources found files : {}", Arrays.toString(modelPaths));
		} else {
			log.error("리소스를 찾을 수 없습니다!!");
		}
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			log.info("Spring bean List : {}", Arrays.toString(beanNames));
		};
	}
}
