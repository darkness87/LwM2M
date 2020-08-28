package com.cnu.metering.agent;

import java.io.IOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cnu.metering.agent.service.RedisSampleService;

@SpringBootTest
class MeteringAgentApplicationTests {

	@Test
	void contextLoads() {
	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * RedisSampleService RedisSampleService = new RedisSampleService();
	 * 
	 * 
	 * }
	 */
	
	public static void main(String[] args) throws ConnectorException, IOException {
	  	CoapClient client1 = new CoapClient("coap://localhost:25683/cmd");

	  	String text = client1.get().getResponseText(); // blocking call
//	  	String xml = client1.get("").getResponseText();

		/*
		 * CoapClient client2 = new CoapClient("coap://localhost:15683/cmd");
		 * 
		 * CoapResponse resp = client2.post("payload", "text"); // for response details
		 * System.out.println( resp.isSuccess() ); System.out.println( resp.getOptions()
		 * );
		 * 
		 * client2.delete();
		 */
	  	
	  }
	
}
