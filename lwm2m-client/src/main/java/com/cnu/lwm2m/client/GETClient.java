package com.cnu.lwm2m.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfig.Keys;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;
import org.eclipse.californium.elements.exception.ConnectorException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GETClient {

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

	/*
	 * Application entry point.
	 * 
	 */	
	public static void main(String args[]) {
		NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
		NetworkConfig.setStandard(config);

		URI uri = null; // URI parameter of the request
		args = new String[]{"coap://127.0.0.1:15683/helloWorld"};

		if (args.length > 0) {
			
			// input URI from command line arguments
			try {
				uri = new URI(args[0]);
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

				if (args.length > 1) {
					try (FileOutputStream out = new FileOutputStream(args[1])) {
						out.write(response.getPayload());
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				} else {
					log.info("getResponseText : {}", response.getResponseText());
					log.info("{}ADVANCED{}", System.lineSeparator(), System.lineSeparator());
					// access advanced API with access to more details through
					// .advanced()
					log.info("\n{}", Utils.prettyPrint(response));
				}
			} else {
				log.error("No response received.");
			}

			client.shutdown();
		} else {
			// display help
			log.info("Californium (Cf) GET Client");
			log.info("(c) 2014, Institute for Pervasive Computing, ETH Zurich\n");
			log.info("Usage : " + GETClient.class.getSimpleName() + " URI [file]");
			log.info("  URI : The CoAP URI of the remote resource to GET");
			log.info("  file: optional filename to save the received payload");
		}
	}

}