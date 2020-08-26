package com.cnu.metering.agent;

import java.net.SocketException;
import java.util.Arrays;

import org.eclipse.californium.core.network.Endpoint;
import org.springframework.beans.factory.DisposableBean;
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
public class Lwm2mClientApplication implements ApplicationRunner, DisposableBean {

	private static CNUCoapServer cnuCoapServer;

	public static void main(String[] args) {
		SpringApplication.run(Lwm2mClientApplication.class, args);
		Lwm2mClientApplication app = new Lwm2mClientApplication();
		app.coapServerStart();
	}

	private void coapServerStart() {
		// add endpoints on all IP addresses
		try {
			cnuCoapServer = new CNUCoapServer();
		} catch (SocketException e) {
			log.error(e.getMessage(), e);
			return;
		}

		log.debug("coapServer Object : {}", cnuCoapServer);
		cnuCoapServer.addEndpoints();
		cnuCoapServer.start();

		for (Endpoint endpoint : cnuCoapServer.getEndpoints()) {
			log.debug("init complete meterCoapserver: {}", endpoint.getUri() + "/" + cnuCoapServer.getRoot().getURI());
		}
	}

	@Override
	public void destroy() throws Exception {
		log.info("destroy!!!");

		if (cnuCoapServer != null) {
			cnuCoapServer.stop();
			cnuCoapServer.destroy();
			log.debug("CNUCoapServer destroy!!!");
		}

		log.info("destroyed cnuCoapServer: {}", cnuCoapServer);
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
