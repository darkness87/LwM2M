package com.cnu.lwm2m.server;

import java.io.File;
import java.util.Arrays;

import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.cnu.lwm2m.server.init.ClientObservationListener;
import com.cnu.lwm2m.server.init.ClientPresenceListener;
import com.cnu.lwm2m.server.init.ClientRegistrationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Lwm2mServerApplication implements ApplicationRunner, DisposableBean {

	@Autowired
	ResourceLoader resourceLoader;

	private static LeshanServer server;
	private static String[] modelPaths;

	public static void main(String[] args) {
		SpringApplication.run(Lwm2mServerApplication.class, args);
		LeshanServerBuilder builder = new LeshanServerBuilder();
		server = builder.build();

		server.getRegistrationService().addListener(new ClientRegistrationListener(server));
		server.getObservationService().addListener(new ClientObservationListener(server));
		server.getPresenceService().addListener(new ClientPresenceListener(server));
		server.start();
	}

	@Override
	public void destroy() throws Exception {
		log.info("destory!!!");
		server.stop();
		server.destroy();
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
}
