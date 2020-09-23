package com.cnu.lwm2m.server.init;

import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.queue.PresenceListener;
import org.eclipse.leshan.server.registration.Registration;

import com.cnu.lwm2m.server.vo.EVENT;

public class ClientPresenceListener extends AbsClientListener implements PresenceListener {
	public ClientPresenceListener(LeshanServer server) {
		super(server);
	}

	@Override
	public void onSleeping(Registration registration) {
		String data = new StringBuilder("{\"ep\":\"").append(registration.getEndpoint()).append("\"}").toString();

		sendEvent(EVENT.SLEEPING, data, registration.getEndpoint());
	}

	@Override
	public void onAwake(Registration registration) {
		String data = new StringBuilder("{\"ep\":\"").append(registration.getEndpoint()).append("\"}").toString();
		sendEvent(EVENT.AWAKE, data, registration.getEndpoint());
	}
}