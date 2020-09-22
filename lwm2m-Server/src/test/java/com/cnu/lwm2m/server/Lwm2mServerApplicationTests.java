package com.cnu.lwm2m.server;

import java.io.IOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Lwm2mServerApplicationTests {

	/*
	 * @Test void contextLoads() { }
	 */
	
	public static void main(String[] args) throws ConnectorException, IOException {
	  	CoapClient client = new CoapClient("coap://localhost:50534/1/0");

		CoapResponse response = null;
		String result = null;
		try {
			response = client.get();
		} catch (ConnectorException | IOException e) {
			System.out.println("Got an error: " + e.getMessage());
		}

		if (response!=null) {
			System.out.println("code : " + response.getCode());
			System.out.println("option : " + response.getOptions());
			System.out.println("responseText : " + response.getResponseText());
			System.out.println("advanced : " + response.advanced());
			result = Utils.prettyPrint(response);
			System.out.println("result : " + result);
		} else {
			System.out.println("No response received.");
		}
		client.shutdown();
        
	  }
	
}
