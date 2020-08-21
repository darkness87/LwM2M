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

//		for (LeshanEventSource eventSource : eventSources) {
//			if (eventSource.getEndpoint() == null || eventSource.getEndpoint().equals(endpoint)) {
//				eventSource.sentEvent(event, data);
//			}
//		}
	}

	@Override
	public void onOpen(Emitter emitter) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
}
