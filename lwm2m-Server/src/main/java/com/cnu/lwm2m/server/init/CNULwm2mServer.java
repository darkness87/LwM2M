package com.cnu.lwm2m.server.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
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

	private final String[] KEPCO_MODELS = { "LWM2M_1_Server_Custom.xml", "LWM2M_2_AccessControl_Custom.xml", "LWM2M_3_Device_Custom.xml", "LWM2M_4_ConnectivityMonitoring_Custom.xml",
			"LWM2M_5_Firmware_Custom.xml", "LWM2M_6_Location_Custom.xml", "LWM2M_7_ConnectivityStatistics_Custom.xml", "LWM2M_9_SoftwareManagement_Custom.xml",
			"KEPCO_26241_AMICommonControl.xml", "KEPCO_26243_AMINetwork.xml", "KEPCO_26245_AMISecurity.xml", "KEPCO_26247_AMIServer.xml", "KEPCO_26249_AMISoftware.xml",
			"KEPCO_27241_MeterControl.xml", "KEPCO_27242_FixedMeteringInformation.xml", "KEPCO_27243_MeterEntryInformation.xml", "KEPCO_27244_FixedMeteringData.xml", "KEPCO_27245_OBISMeteringData.xml",
			"KEPCO_27246_TOUInformation.xml", "KEPCO_27247_TOUSpecialDay.xml", "KEPCO_27248_ModemControl.xml", "KEPCO_27249_FixedModemInformation.xml", "KEPCO_27250_ModemEntryInformation.xml"};

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
		try {
			NetworkConfig coapConfig;
			coapConfig = new NetworkConfig();
			coapConfig.load(resource.getInputStream());

			log.info("COAP_PORT : {}",coapConfig.getInt("COAP_PORT"));

			return coapConfig;
		} catch (IOException e) {
			log.error(e.getMessage(), e);

			return null;
		}
	}

	private LwM2mModelProvider initModelProviders() throws IOException, InvalidModelException, InvalidDDFFileException {
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

	// TODO
	/*
	 * @Bean public RegistrationStore getRegistrationStore() { return server.reg();
	 * }
	 */
	/*
	 * @Bean public RegistrationListener getRegistrationListener() { return server.;
	 * }
	 */

}