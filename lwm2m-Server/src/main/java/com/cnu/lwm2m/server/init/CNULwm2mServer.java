package com.cnu.lwm2m.server.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.observation.ObservationService;
import org.eclipse.leshan.server.queue.PresenceService;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CNULwm2mServer implements DisposableBean {

	private LeshanServer server;

	@Autowired
	ResourceLoader resourceLoader;

	private Resource resource = new ClassPathResource("lwm2mServer.properties");

	public CNULwm2mServer() {
		LeshanServerBuilder builder = new LeshanServerBuilder();
		try {
			builder.setCoapConfig(createConfig());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		server = builder.build();

		server.getRegistrationService().addListener(new ClientRegistrationListener(server));
		server.getObservationService().addListener(new ClientObservationListener(server));
		server.getPresenceService().addListener(new ClientPresenceListener(server));
		server.start();

		log.info("LeshanServer started!!");
	}

	private NetworkConfig createConfig() throws FileNotFoundException {
		NetworkConfig coapConfig;
		File configFile;

		try {
			configFile = resource.getFile();
		} catch (IOException e) {
			throw new FileNotFoundException(resource.getFilename());
		} catch (Exception e) {
			throw new FileNotFoundException(e.getMessage());
		}

		coapConfig = new NetworkConfig();
		coapConfig.load(configFile);

		return coapConfig;
	}

	@Override
	public void destroy() {
		log.info("{} is destroyed!!", getClass().getSimpleName());
		server.stop();
		server.destroy();
	}

	@Bean
	public RegistrationService getRegistrationService() {
		return server.getRegistrationService();
	}

	@Bean
	public ObservationService getObservationService() {
		return server.getObservationService();
	}

	@Bean
	public PresenceService getPresenceService() {
		return server.getPresenceService();
	}

	@Bean
	public LwM2mModelProvider getModelProvider() {
		return server.getModelProvider();
	}
}
