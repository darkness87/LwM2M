package com.cnu.lwm2m.server.init;

import java.io.IOException;

import org.eclipse.leshan.server.californium.LeshanServer;

import com.cnu.lwm2m.server.vo.EVENT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbsClientListener implements EventSource {

	protected final LeshanServer server;

	public AbsClientListener(LeshanServer server) {
		this.server = server;
	}

	public synchronized void sendEvent(EVENT event, String data, String endpoint) {
		log.debug("Dispatching {} event from endpoint {}", event, endpoint);

		log.info("{} Data : {}",event,data);
		// TODO 여기서 이벤트를 던져야함 DEREGISTRATION, UPDATED, REGISTRATION, AWAKE, SLEEPING, NOTIFICATION
//		if (event == EVENT.REGISTRATION) {
//			// Registration
//			log.info("Registration Data : {}", data);
//
//		} else if (event == EVENT.NOTIFICATION) {
//			// Notify
//			log.info("Notify Data : {}", data);
//		}

	}

	@Override
	public void onOpen(Emitter emitter) throws IOException {
		// Auto-generated method stub

		log.info("onOpen : {}", emitter);

	}

	@Override
	public void onClose() {
		// Auto-generated method stub

	}
}