package com.cnu.lwm2m.server.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.leshan.core.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mMultipleResource;
import org.eclipse.leshan.core.node.LwM2mObjectInstance;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.eclipse.leshan.core.node.ObjectLink;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.eclipse.leshan.core.tlv.TlvDecoder;
import org.eclipse.leshan.core.tlv.TlvException;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServer.CoapAPI;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.lwm2m.server.vo.ObserveDataVO;
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

	public List<ObserveDataVO> sendCoapTLVObserve(String endpoint, String uri, String contentType, int timeout) {
		Registration registration = regService.getByEndpoint(endpoint);
		ContentFormat contentFormat = ContentFormat.fromName(contentType);
		ObserveRequest request = new ObserveRequest(contentFormat, uri);

		log.info("=== registration : {}", registration);
		log.info("=== request : {}", request);

		try {
			ObserveResponse cResponse = server.send(registration, request, timeout); // TODO timeout 설정 필요
			log.info("=== response : {}", cResponse);

			List<ObserveDataVO> list = new ArrayList<ObserveDataVO>();
			ObserveDataVO observeDataVO = new ObserveDataVO();
			LwM2mPath lwM2mPath = new LwM2mPath(uri);

			if (cResponse.getContent().getClass() == LwM2mSingleResource.class) {
				LwM2mSingleResource content = (LwM2mSingleResource) cResponse.getContent();
				log.debug("=== content : {}", content);

				observeDataVO.setId(content.getId());
				observeDataVO.setTid("tid" + lwM2mPath.getObjectId() + "" + lwM2mPath.getObjectInstanceId() + ""
						+ lwM2mPath.getResourceId() + "");
				observeDataVO.setValue(String.valueOf(content.getValue()));

				list.add(observeDataVO);

			} else if (cResponse.getContent().getClass() == LwM2mObjectInstance.class) {
				LwM2mObjectInstance content = (LwM2mObjectInstance) cResponse.getContent();
				log.debug("=== content : {}", content);

				Map<Integer, LwM2mResource> map = content.getResources();
				log.debug("=== map : {}", map);

				Iterator<Map.Entry<Integer, LwM2mResource>> entries = map.entrySet().iterator();
				while (entries.hasNext()) {
					observeDataVO = new ObserveDataVO();
					Entry<Integer, LwM2mResource> entry = (Entry<Integer, LwM2mResource>) entries.next();
					observeDataVO.setId(entry.getKey());
					observeDataVO.setTid("tid" + lwM2mPath.getObjectId() + "" + lwM2mPath.getObjectInstanceId() + ""
							+ entry.getKey() + "");
					if (entry.getValue().isMultiInstances() == true) {
						LwM2mMultipleResource val = (LwM2mMultipleResource) entry.getValue();
						log.debug("=== val : {}", val);
						observeDataVO.setValue(String.valueOf(val.getValues()));
					} else {

						if (String.valueOf(entry.getValue().getType()).equals("OPAQUE")) {
							LwM2mSingleResource val = (LwM2mSingleResource) entry.getValue();
							byte[] data = (byte[]) val.getValue();
							log.info("OPAQUE Data : {} , {}", data, new String(data));
							observeDataVO.setValue(new String(data));
						} else {
							observeDataVO.setValue(String.valueOf(entry.getValue().getValue()));
						}

					}

					list.add(observeDataVO);
				}

			}

			return list;
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);

			return null;
		}
	}

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

	public String sendCoapTLVRead(String endpoint, String uri, String type, String contentType, int timeout) {
		Registration registration = regService.getByEndpoint(endpoint);
		ContentFormat contentFormat = ContentFormat.fromName(contentType);
		ReadRequest request = new ReadRequest(contentFormat, uri);

		log.info("=== registration : {}", registration);
		log.info("=== request : {}", request);

		try {
			ReadResponse cResponse = server.send(registration, request, timeout); // TODO timeout 설정 필요
			log.info("=== response : {}", cResponse);

			LwM2mResource content = (LwM2mResource) cResponse.getContent();
			log.debug("=== content : {}", content);

			if (content != null) {

				if (content.isMultiInstances() == true) {
					LwM2mMultipleResource val = (LwM2mMultipleResource) content;
					log.debug("=== val : {}", val);
					return String.valueOf(val.getValues());
				}

				if (String.valueOf(content.getType()).equals("OPAQUE")) {
					LwM2mSingleResource val = (LwM2mSingleResource) content;
					byte[] data = (byte[]) val.getValue();
					log.info("OPAQUE Data : {} , {}", data, new String(data));

					return new String(data);
				}

				return String.valueOf(content.getValue());
			} else {
				return String.valueOf(cResponse.getCode());
			}

		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);

			return null;
		}
	}

	public boolean sendCoapTLVWrite(String endpoint, String uri, String type, String data, String contentType,
			int timeout) {
		Registration registration = regService.getByEndpoint(endpoint);
		ContentFormat contentFormat = ContentFormat.fromName(contentType);

		LwM2mPath lwM2mPath = new LwM2mPath(uri);
		WriteRequest request = null;

		switch (type) {
		case "STRING":
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), String.valueOf(data));
			break;
		case "INTEGER":
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), Integer.valueOf(data));
			break;
		case "FLOAT":
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), Float.valueOf(data));
			break;
		case "BOOLEAN":
			// TODO
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), Boolean.valueOf(data));
			break;
		case "TIME":
			// TODO Time값 넘기는 거 구현 필요
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), data);
			break;
		case "OBJLNK":
			// TODO
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), data);
			break;
		case "OPAQUE":
			byte[] bdata = data.getBytes(); // byte값 전달
			log.info("Opaque Byte : {}", bdata);
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), bdata);
			break;
		default:
			request = new WriteRequest(contentFormat, lwM2mPath.getObjectId(), lwM2mPath.getObjectInstanceId(),
					lwM2mPath.getResourceId(), data);
		}

		log.info("=== registration : {}", registration);
		log.info("=== request : {}", request);

		try {
			WriteResponse cResponse = server.send(registration, request, timeout);
			ResponseCode responseCode = cResponse.getCode();

			log.info("=== response : {}", cResponse);
			log.debug("=== result : {}", responseCode.isSuccess());

			return responseCode.isSuccess();

		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);

			return false;
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

			try {
				log.info("=== registration : {}", registration);
				log.info("=== request : {}", request);
				Response response = coapAPI.send(registration, request);
				log.info("=== response : {}" + response);

				if (response != null) {
					log.info("=== result : {}", Utils.prettyPrint(response));
					result = response.getPayloadString();

					log.info("=== Type : {}", type);

					if (response.getPayload() != null) {

						ObjectLink objectLink;

						switch (type) {
						case "STRING":
							result = TlvDecoder.decodeString(response.getPayload());
							log.info("STRING : {}", result);
							break;
						case "INTEGER":
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("INTEGER : {}", result);
							break;
						case "FLOAT":
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("FLOAT : {}", result);
							break;
						case "BOOLEAN":
							byte[] value = response.getPayload();
							log.info("{} , {} , {}", value[0], value[1], value[2]);

							if (value[2] == 1) {
								result = "false";
							} else if (value[2] == 0) {
								result = "true";
							}
							log.info("BOOLEAN : {}", result);
							break;
						case "TIME":
							result = String.valueOf(TlvDecoder.decodeDate(response.getPayload()));
							log.info("TIME : {}", result);
							break;
						case "OBJLNK":
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("OBJLNK : {}", result);
							break;
						case "OPAQUE":
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("OPAQUE : {}", result);
							break;
						default:
							objectLink = TlvDecoder.decodeObjlnk(response.getPayload());
							result = String.valueOf(objectLink.getObjectInstanceId());
							log.info("default : {}", result);
						}

					} else {
						result = "Payload Null.";
					}

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
			log.info("=== uripath Exec.. : {}", uripath);
			request.setURI(uripath);
			request.setType(Type.CON);

			try {
				Response response = coapAPI.send(registration, request);
				log.info("=== Exec Result : {}", Utils.prettyPrint(response));
				result = "ExecSuccess";
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return result;
		}

		return result;
	}

}