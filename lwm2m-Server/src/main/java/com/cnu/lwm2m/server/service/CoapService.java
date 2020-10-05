package com.cnu.lwm2m.server.service;

import java.util.List;

import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.leshan.core.node.ObjectLink;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.tlv.TlvDecoder;
import org.eclipse.leshan.core.tlv.TlvException;
import org.eclipse.leshan.server.californium.LeshanServer.CoapAPI;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoapService {

	@Autowired
	CoapAPI coapAPI;

	@Autowired
	RegistrationService regService;

	public String sendCoapObserve(String endpoint, String uri) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		String result = null;

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			Request request = new Request(Code.GET);
			String uripath = "coap:/" + registration.getAddress() + ":" + registration.getPort() + uri;
			log.info("=== uripath : {}", uripath);
			request.setURI(uripath);
			// TODO 실행하면 오류
			request.setObserve(); // observe set이 0일 경우 observe 확인, 1일 경우 observe 취소

//			request.setPayload("");
//			OptionSet options = new OptionSet();
//			options.setContentFormat(MediaTypeRegistry.APPLICATION_VND_OMA_LWM2M_JSON); // APPLICATION_JSON or
			// APPLICATION_VND_OMA_LWM2M_JSON
//			request.setOptions(options);

			try {
				log.info("=== registration : {}", registration);
				log.info("=== request : {}", request);
				Response response = coapAPI.send(registration, request);
				log.info("=== send : {}" + response);

				if (response != null) {
					result = Utils.prettyPrint(response);
					log.info("=== result : {}", result);
				} else {
					log.info("=== No response received.");
					result = "No response received.";
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return result;
		}

		return result;
	}

	public String sendCoapObserveCancel(String endpoint, String uri) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		String result = null;

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			Request request = new Request(Code.GET);
			String uripath = "coap:/" + registration.getAddress() + ":" + registration.getPort() + uri;
			log.info("=== uripath : {}", uripath);
			request.setURI(uripath);
			request.setObserveCancel(); // observe cancel

//			OptionSet options = new OptionSet();
//			options.setContentFormat(MediaTypeRegistry.APPLICATION_VND_OMA_LWM2M_JSON); // APPLICATION_JSON or
																						// APPLICATION_VND_OMA_LWM2M_JSON
//			options.setObserve(1);
//			request.setOptions(options);

			try {
				log.info("=== request : {}", request);
				Response response = coapAPI.send(registration, request);
				log.info("=== send : {}" + response);

				if (response != null) {
					result = Utils.prettyPrint(response);
					log.info("=== result : {}", result);
				} else {
					log.info("=== No response received.");
					result = "No response received.";
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return result;
		}

		return result;
	}

	public String sendCoapRead(String endpoint, String uri) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		String result = null;

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			Request request = new Request(Code.GET);
			String uripath = "coap:/" + registration.getAddress() + ":" + registration.getPort() + uri;
			log.info("=== uripath : {}", uripath);
			request.setURI(uripath);
			request.setType(Type.CON);

//			OptionSet options = new OptionSet();
//			options.setContentFormat(MediaTypeRegistry.APPLICATION_VND_OMA_LWM2M_JSON); // APPLICATION_JSON or
			// APPLICATION_VND_OMA_LWM2M_JSON
			// TEXT_PLAIN
//			request.setOptions(options);

			try {
				log.info("=== registration : {}", registration);
				log.info("=== request : {}", request);
				Response response = coapAPI.send(registration, request);
				log.info("=== send : {}" + response);

				if (response != null) {
					result = Utils.prettyPrint(response);
					log.info("=== result : {}", result);

					// TLV
					// log.info("{}",TlvDecoder.decodeInteger(response.getPayload()));
					// log.info("{}",TlvDecoder.decodeString(response.getPayload()));
					// log.info("{}",TlvDecoder.decodeFloat(response.getPayload()));
					// log.info("{}",TlvDecoder.decodeDate(response.getPayload()));
					// log.info("{}",TlvDecoder.decodeBoolean(response.getPayload()));

					log.info("{}", TlvDecoder.decodeObjlnk(response.getPayload()));
					ObjectLink objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
					log.info("{}", objectLink.getObjectId());
					log.info("{}", objectLink.getObjectInstanceId()); // TODO 들어온 값이긴 한데 모르겠음 int 값은 동일함

//					LwM2mNodeDecoder LwM2mNodeDecoder = new LwM2mNodeDecoder();
//					leshanServerBuilder.setDecoder(decoder);
//					LwM2mNodeDecoder decoder;
//					log.info("{}",LwM2mNodeDecoder.decode(response.getPayload(), ContentFormat.TLV,null,null));

					result = String.valueOf(objectLink.getObjectInstanceId());

				} else {
					log.info("=== No response received.");
					result = "No response received.";
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (TlvException e) {
				e.printStackTrace();
			}

			return result;
		}

		return result;
	}

	public String sendCoapWrite(String endpoint, String uri, String data) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		String result = null;

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			Request request = new Request(Code.PUT);
			String uripath = "coap:/" + registration.getAddress() + ":" + registration.getPort() + uri;
			log.info("=== uripath : {}", uripath);
			request.setURI(uripath);
			request.setType(Type.CON);
			request.setPayload(data);

			try {
				Response response = coapAPI.send(registration, request);
				result = Utils.prettyPrint(response);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return result;
		}

		return result;
	}

	public String sendCoapExec(String endpoint, String uri) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		String result = null;

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			Request request = new Request(Code.POST);
			String uripath = "coap:/" + registration.getAddress() + ":" + registration.getPort() + uri;
			log.info("=== uripath : {}", uripath);
			request.setURI(uripath);
			request.setType(Type.CON);

			try {
				Response response = coapAPI.send(registration, request);
				result = Utils.prettyPrint(response);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return result;
		}

		return result;
	}

}