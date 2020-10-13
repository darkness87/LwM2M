package com.cnu.lwm2m.server.init;

import java.util.Collection;

import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;

import com.cnu.lwm2m.server.vo.EVENT;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientRegistrationListener extends AbsClientListener implements RegistrationListener {
	public ClientRegistrationListener(LeshanServer server) {
		super(server);
	}

	@Override
	public void registered(Registration registration, Registration previousReg,
			Collection<Observation> previousObsersations) {
		log.info("new device: {}", registration.getEndpoint());
		String jReg = new Gson().toJson(registration);
		log.info("jReg : {}", jReg);
		// sendEvent(EVENT.REGISTRATION, jReg, registration.getEndpoint());

		try {
			ReadResponse response = server.send(registration, new ReadRequest(3, 0, 13));
			if (response.isSuccess()) {
				log.info("Device time: {}", ((LwM2mResource) response.getContent()).getValue());
			} else {
				log.info("Failed to read:" + response.getCode() + " " + response.getErrorMessage());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) {
		log.info("device is updated here: {}", updatedReg.getEndpoint());
		String jReg = new Gson().toJson(updatedReg);
		sendEvent(EVENT.UPDATED, jReg, updatedReg.getEndpoint());
	}

	@Override
	public void unregistered(Registration registration, Collection<Observation> observations, boolean expired,
			Registration newReg) {
		log.info("device left: {}", registration.getEndpoint());
		String jReg = new Gson().toJson(registration);
		sendEvent(EVENT.DEREGISTRATION, jReg, registration.getEndpoint());
	}
}