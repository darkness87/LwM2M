package com.cnu.lwm2m.server.service;

import java.util.List;

import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.node.LwM2mNode;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.eclipse.leshan.core.node.ObjectLink;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.request.ContentFormat;

import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.response.ReadResponse;

import org.eclipse.leshan.core.tlv.TlvDecoder;
import org.eclipse.leshan.core.tlv.TlvException;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServer.CoapAPI;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
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
	private LeshanServer server;

	@Autowired
	RegistrationService regService;

	@Autowired
	LwM2mModelProvider modelProvider;

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
				log.info("=== response : {}" + response);

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

	public String sendCoapTLV(String endpoint, String uri) {
		Registration registration = regService.getByEndpoint(endpoint);
		ContentFormat contentFormat = ContentFormat.fromName("TLV");
		// create & process request
		ReadRequest request = new ReadRequest(contentFormat, uri);

		try {
			ReadResponse cResponse = server.send(registration, request, 5000L);
			LwM2mResource content = (LwM2mResource) cResponse.getContent();
			log.debug("{}", content);

			return (String) content.getValue();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);

			return null;
		}
	}

	public String sendCoapRead(String endpoint, String uri, String type) {
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
				log.info("=== response : {}" + response);

				DefaultLwM2mNodeDecoder defaultLwM2mNodeDecoder = new DefaultLwM2mNodeDecoder();
				LwM2mPath lwM2mPath = new LwM2mPath(uri);
				LwM2mNode lw = defaultLwM2mNodeDecoder.decode(response.getPayload(), ContentFormat.TLV, lwM2mPath, null);
				log.info("=== lwlwlwlw : {}", lw);
				
				if (response != null) {
					log.info("=== result : {}", Utils.prettyPrint(response));
					result = response.getPayloadString();

					log.info("=== Type : {}", type);

					if (response.getPayload() != null) {

						ObjectLink objectLink;

						switch (type) {
						case "STRING":
							// log.info("{}", TlvDecoder.decodeString(response.getPayload()));
							result = TlvDecoder.decodeString(response.getPayload());
							log.info("STRING : {}", result);
							break;
						case "INTEGER":
							// log.info("{}", TlvDecoder.decodeObjlnk(response.getPayload()));
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("INTEGER : {}", result);
							break;
						case "FLOAT":
							// log.info("{}", TlvDecoder.decodeObjlnk(response.getPayload()));
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("FLOAT : {}", result);
							break;
						case "BOOLEAN":
							// log.info("{}", TlvDecoder.decodeBoolean(response.getPayload()));
							byte[] value = response.getPayload();
							log.info("{} , {} , {}", value[0], value[1], value[2]);

							if (value[2] == 1) {
								result = "false";
							} else if (value[2] == 0) {
								result = "true";
							}
							// result = String.valueOf(TlvDecoder.decodeBoolean(response.getPayload()));
							log.info("BOOLEAN : {}", result);
							break;
						case "TIME":
							// log.info("{}", TlvDecoder.decodeDate(response.getPayload()));
							result = String.valueOf(TlvDecoder.decodeDate(response.getPayload()));
							log.info("TIME : {}", result);
							break;
						case "OBJLNK":
							// log.info("{}", TlvDecoder.decodeObjlnk(response.getPayload()));
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("OBJLNK : {}", result);
							break;
						case "OPAQUE":
							// log.info("{}", TlvDecoder.decodeObjlnk(response.getPayload()));
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("OPAQUE : {}", result);
							break;
						default:
							// log.info("{}", TlvDecoder.decodeObjlnk(response.getPayload()));
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("default : {}", result);
						}

					} else {
						result = "Payload Null.";
					}

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
			OptionSet options = new OptionSet();
			options.setContentFormat(MediaTypeRegistry.APPLICATION_VND_OMA_LWM2M_TLV);
			request.setOptions(options);

			LwM2mPath lwM2mPath = new LwM2mPath(uri);
			DefaultLwM2mNodeEncoder defaultLwM2mNodeEncoder = new DefaultLwM2mNodeEncoder();
			LwM2mModel model;
//			byte[] value = defaultLwM2mNodeEncoder.encode(1, ContentFormat.TLV, lwM2mPath, model);
//			request.setPayload(value);
			
			try {
				log.info("=== request : {}", request);
				Response response = coapAPI.send(registration, request);
				log.info("=== response : {}", response);
				result = Utils.prettyPrint(response);
				log.info("=== result : {}", result);
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