package com.cnu.metering.agent.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;
import org.eclipse.californium.core.network.config.NetworkConfig.Keys;
import org.eclipse.californium.elements.exception.ConnectorException;

import com.cnu.metering.agent.vo.MeterVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbsService {

	private static final File CONFIG_FILE = new File("Californium.properties");
	private static final String CONFIG_HEADER = "Californium CoAP Properties file for Fileclient";
	private static final int DEFAULT_COAP_PORT = 15683;
	private static final int DEFAULT_MAX_RESOURCE_SIZE = 2 * 1024 * 1024; // 2 MB
	private static final int DEFAULT_BLOCK_SIZE = 512;

	private static NetworkConfigDefaultHandler DEFAULTS = new NetworkConfigDefaultHandler() {
		@Override
		public void applyDefaults(NetworkConfig config) {
			config.setInt(Keys.COAP_PORT, DEFAULT_COAP_PORT);
			config.setInt(Keys.MAX_RESOURCE_BODY_SIZE, DEFAULT_MAX_RESOURCE_SIZE);
			config.setInt(Keys.MAX_MESSAGE_SIZE, DEFAULT_BLOCK_SIZE);
			config.setInt(Keys.PREFERRED_BLOCK_SIZE, DEFAULT_BLOCK_SIZE);
		}
	};

	public String send(String url, MeterVO meterVO) {
		NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
		NetworkConfig.setStandard(config);

		URI uri = null; // URI parameter of the request
		String result = null;

		// input URI from command line arguments
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			log.error("Invalid URI: " + e.getMessage(), e);
			System.exit(-1);
		}

		log.debug("init Coap Client!! URI : {}", uri);
		CoapClient client = new CoapClient(uri);
		log.debug("init Complete!");

		CoapResponse response = null;
		try {
			response = client.get();
		} catch (ConnectorException | IOException e) {
			log.error("Got an error: " + e.getMessage(), e);
		}

		if (response!=null) {
			log.info("{}", response.getCode());
			log.info("{}", response.getOptions());

			log.info("getResponseText : {}", response.getResponseText());
			log.info("{}", response.advanced());
			result = Utils.prettyPrint(response);
			log.info("\n{}", result);
		} else {
			log.error("No response received.");
		}

		client.shutdown();

		return result;
	}
}
