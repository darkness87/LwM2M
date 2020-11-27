package com.cnu.lwm2m.server.init;

import java.util.Collection;
import java.util.Date;

import org.eclipse.leshan.core.Link;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
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
		log.info("{},{}", previousReg, previousObsersations);

		String jReg = new Gson().toJson(registration);
		log.info("jReg : {}", jReg);

		sendEvent(EVENT.REGISTRATION, jReg, registration.getEndpoint());

		try {
			ReadResponse response = server.send(registration, new ReadRequest(3, 0, 13));
			if (response.isSuccess()) {
				log.info("Device time: {}", ((LwM2mResource) response.getContent()).getValue());
				// 시간을 읽어와서 확인 후에 client에 시간 업데이트
				ContentFormat contentFormat = ContentFormat.fromName("TLV");
				LwM2mPath lwM2mPath = new LwM2mPath("/3/0/13");
				Date date = new Date();
				WriteRequest request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
						lwM2mPath.getResourceId(), date);
				
				log.info("=== request : {}", request);

				try {
					WriteResponse cResponse = server.send(registration, request, 10000);
					ResponseCode responseCode = cResponse.getCode();

					log.info("=== response : {}", cResponse);
					log.debug("=== result : {}", responseCode.isSuccess());

				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}

			} else {
				log.info("Failed to read:" + response.getCode() + " " + response.getErrorMessage());
			}

			///// Registration Observe
			ContentFormat contentFormat = ContentFormat.fromName("TLV");
			Link[] oblink = registration.getObjectLinks();

			for (int i = 0; i < oblink.length; i++) {
				String uri = oblink[i].getUrl();

				if (uri.equals("/")) {
					continue;
				}

				ObserveRequest request = new ObserveRequest(contentFormat, uri);
				ObserveResponse cResponse = server.send(registration, request, 10000);
				if (cResponse.isSuccess()) {
					log.info("Registration Observe : {} {}", uri, "Success");
				} else {
					log.info("Registration Observe : {} {}", uri, "fail");
				}
			}
			/////

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) {
		log.info("device is updated here: {}", updatedReg.getEndpoint());
		log.info("{}", update);
		log.info("{}", updatedReg);
		String jReg = new Gson().toJson(updatedReg);
		sendEvent(EVENT.UPDATED, jReg, updatedReg.getEndpoint());
	}

	@Override
	public void unregistered(Registration registration, Collection<Observation> observations, boolean expired,
			Registration newReg) {
		log.info("device left: {}", registration.getEndpoint());
		log.info("{},{}", observations, expired);
		// TODO
		String jReg = new Gson().toJson(registration);
		sendEvent(EVENT.DEREGISTRATION, jReg, registration.getEndpoint());
	}
}