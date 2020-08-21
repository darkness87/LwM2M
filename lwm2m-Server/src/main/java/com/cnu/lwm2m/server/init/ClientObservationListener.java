package com.cnu.lwm2m.server.init;

import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.observation.ObservationListener;
import org.eclipse.leshan.server.registration.Registration;

import com.cnu.lwm2m.server.vo.EVENT;
import com.google.gson.Gson;

import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientObservationListener extends AbsClientListener implements ObservationListener{
	public ClientObservationListener(LeshanServer server) {
		super(server);
	}

	@Override
	public void cancelled(Observation observation) {
	}

	@Override
	public void onResponse(Observation observation, Registration registration, ObserveResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("Received notification from [{}] containing value [{}]", observation.getPath(),
					response.getContent().toString());
		}

		if (registration != null) {
			String data = new StringBuilder("{\"ep\":\"").append(registration.getEndpoint()).append("\",\"res\":\"")
					.append(observation.getPath().toString()).append("\",\"val\":")
					.append(new Gson().toJson(response.getContent())).append("}").toString();

			sendEvent(EVENT.NOTIFICATION, data, registration.getEndpoint());
		}
	}

	@Override
	public void onError(Observation observation, Registration registration, Exception error) {
		if (log.isWarnEnabled()) {
			log.warn(String.format("Unable to handle notification of [%s:%s]", observation.getRegistrationId(),
					observation.getPath()), error);
		}
	}

	@Override
	public void newObservation(Observation observation, Registration registration) {
	}
}
