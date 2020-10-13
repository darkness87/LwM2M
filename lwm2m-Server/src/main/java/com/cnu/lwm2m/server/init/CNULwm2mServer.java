package com.cnu.lwm2m.server.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServer.CoapAPI;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.VersionedModelProvider;
import org.eclipse.leshan.server.observation.ObservationService;
import org.eclipse.leshan.server.queue.PresenceService;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.eclipse.leshan.server.security.SecurityStore;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CNULwm2mServer implements DisposableBean {
	private final String[] KEPCO_MODELS = {"LWM2M_Server_1_Custom.xml", "LWM2M_Device_3_Custom.xml", "LWM2M_ConnectivityMonitoring_4_Custom.xml", "LWM2M_Location_6_Custom.xml", "LWM2M_ConnectivityStatistics_7_Custom.xml",
			"26241.xml", "26243.xml", "26245.xml", "26247.xml"};

	private LeshanServer server;

	private Resource resource = new ClassPathResource("lwm2mServer.properties");

	public CNULwm2mServer() {
		LeshanServerBuilder builder = new LeshanServerBuilder();

		try {
			builder.setObjectModelProvider(initModelProviders());
			builder.setCoapConfig(createConfig());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
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
			log.info("{}",resource.getFile());
			log.info("{}",resource.getURI());
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

	private LwM2mModelProvider initModelProviders() {
		List<ObjectModel> models = ObjectLoader.loadDefault();

		models = ObjectLoader.loadDefault();
		models.addAll(ObjectLoader.loadDdfResources("/models", KEPCO_MODELS));

		return new VersionedModelProvider(models);
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

	@Bean
	public SecurityStore getSecurityStore() {
		return server.getSecurityStore();
	}

	@Bean
	public CoapAPI coap() {
		return server.coap();
	}

	@Bean
	public LeshanServer server() {
		return server;
	}

}