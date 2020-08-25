package com.cnu.lwm2m.server;

import java.io.File;
import java.net.SocketException;
import java.util.Arrays;

import org.eclipse.californium.core.network.Endpoint;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.springframework.beans.factory.DisposableBean;
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

import com.cnu.lwm2m.server.init.CNUCoapServer;
import com.cnu.lwm2m.server.init.ClientObservationListener;
import com.cnu.lwm2m.server.init.ClientPresenceListener;
import com.cnu.lwm2m.server.init.ClientRegistrationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Lwm2mServerApplication implements ApplicationRunner, DisposableBean {

	@Autowired
	public ResourceLoader resourceLoader;

	private static CNUCoapServer cnuCoapServer;
	private static LeshanServer server;
	private static String[] modelPaths;

	public static void main(String[] args) {
		SpringApplication.run(Lwm2mServerApplication.class, args);
		Lwm2mServerApplication app = new Lwm2mServerApplication();
		app.lwm2mServerStart();
		app.coapServerStart();
	}

	private void lwm2mServerStart() {
		LeshanServerBuilder builder = new LeshanServerBuilder();
		server = builder.build();

		server.getRegistrationService().addListener(new ClientRegistrationListener(server));
		server.getObservationService().addListener(new ClientObservationListener(server));
		server.getPresenceService().addListener(new ClientPresenceListener(server));
		server.start();
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

		if (server != null) {
			server.stop();
			server.destroy();
			log.debug("LeshanServer destroy!!!");
		}

		log.info("destroyed cnuCoapServer: {}", cnuCoapServer);
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
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
}
