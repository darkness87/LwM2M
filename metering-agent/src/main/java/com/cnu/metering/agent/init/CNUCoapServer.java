package com.cnu.metering.agent.init;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.Endpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.util.NetworkInterfacesUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnu.metering.agent.dao.RedisDao;
import com.cnu.metering.agent.service.CommonService;
import com.cnu.metering.agent.service.MeterService;
import com.cnu.metering.agent.vo.MeterInfoVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CNUCoapServer extends CoapServer implements DisposableBean {

	private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);
//	private static final int TCP_THREADS = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.TCP_WORKER_THREADS);
//	private static final int TCP_IDLE_TIMEOUT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.TCP_CONNECTION_IDLE_TIMEOUT);

	@Autowired
	CommonService commonService;
	@Autowired
	MeterService meterService;
	@Autowired
	RedisDao redisDAO;

	public CNUCoapServer() throws SocketException {
		log.debug("init Construct CNUCoapServer()");
		add(new CNUCoapResource("cmd"));
	}

	public void run() {
		// add endpoints on all IP addresses
		addEndpoints();
		start();

		for (Endpoint endpoint : this.getEndpoints()) {
			log.debug("init complete meterCoapserver: {}", endpoint.getUri() + "/" + this.getRoot().getURI());
		}
	}

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

	@Override
	public void destroy() {
		this.stop();
		this.destroy();
		log.info("destroyed cnuCoapServer!!");
	}

	class CNUCoapResource extends CoapResource {

		public CNUCoapResource(String resourceId) {
			// set resource identifier
			super(resourceId);

			// set display name
			getAttributes().setTitle("Hello-World Resource title?");
		}

		@Override
		public void handleGET(CoapExchange exchange) {
			log.info("GET method 접근");
			// respond to the request
			MeterInfoVO meterVO;
			String data = "";
			ObjectMapper mapper = new ObjectMapper();

			try {
				meterVO = redisDAO.getRedisData("Meter:Info:One",MeterInfoVO.class);
				data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meterVO);
			} catch (Exception e) {
				e.printStackTrace();
				exchange.respond(e.getMessage());
				return;
			}
			exchange.respond(data);
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
