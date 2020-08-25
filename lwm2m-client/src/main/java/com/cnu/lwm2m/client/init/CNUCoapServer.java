package com.cnu.lwm2m.client.init;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.List;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.util.NetworkInterfacesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnu.lwm2m.client.service.CommonService;
import com.cnu.lwm2m.client.service.MeterService;
import com.cnu.lwm2m.client.vo.MeterVO;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CNUCoapServer extends CoapServer {

	private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);
//	private static final int TCP_THREADS = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.TCP_WORKER_THREADS);
//	private static final int TCP_IDLE_TIMEOUT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.TCP_CONNECTION_IDLE_TIMEOUT);

//	public static void main(String[] args) {
//		try {
//			CNUCoapServer server = new CNUCoapServer();
//			server.addEndpoints();
//			server.start();
//		} catch (SocketException e) {
//			log.error("Failed to initialize server: " + e.getMessage(), e);
//		}
//	}

	@Autowired
	CommonService commonService;
	@Autowired
	MeterService meterService;

	public void addEndpoints() {
		NetworkConfig config = NetworkConfig.getStandard();

		for (InetAddress addr : NetworkInterfacesUtil.getNetworkInterfaces()) {
			InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);

			CoapEndpoint.Builder builder = new CoapEndpoint.Builder();
			builder.setInetSocketAddress(bindToAddress);
			builder.setNetworkConfig(config);
			addEndpoint(builder.build());
/*			TCP 모드일경우
			TcpServerConnector connector = new TcpServerConnector(bindToAddress, TCP_THREADS, TCP_IDLE_TIMEOUT);
			CoapEndpoint.Builder builder = new CoapEndpoint.Builder();
			builder.setConnector(connector);
			builder.setNetworkConfig(config);
			addEndpoint(builder.build());
*/
		}
	}

	public CNUCoapServer() throws SocketException {
		// provide an instance of a Hello-World resource
		log.debug("init Construct CNUCoapServer()");
		add(new HelloWorldResource("cmd"));
	}

	class HelloWorldResource extends CoapResource {

		public HelloWorldResource(String resourceId) {
			// set resource identifier
			super(resourceId);

			// set display name
			getAttributes().setTitle("Hello-World Resource title?");
		}

		@Override
		public void handleGET(CoapExchange exchange) {
			log.info("GET method 접근");
			// respond to the request
			String requestText = exchange.getRequestText();
			// TODO: json 데이터에 대한 처리 후 분기
			log.debug(requestText);
			// 분기 후 처리내용
			Gson gson = new Gson();
			List<MeterVO> meterList = meterService.getMeterList();
			exchange.respond(gson.toJson(meterList));
		}

		@Override
		public void handlePOST(CoapExchange exchange) {
			log.info("POST method 접근");
			// respond to the request
			exchange.respond("Hello World! POST");
		}

		@Override
		public void handlePUT(CoapExchange exchange) {
			log.info("PUT method 접근");
			// respond to the request
			exchange.respond("Hello World! PUT");
		}

		@Override
		public void handleDELETE(CoapExchange exchange) {
			log.info("DELETE method 접근");
			// respond to the request
			exchange.respond("Hello World! DELETE");
		}
	}
}