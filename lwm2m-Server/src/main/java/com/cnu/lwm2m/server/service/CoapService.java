package com.cnu.lwm2m.server.service;

import java.util.List;

import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.coap.CoAP.Code;
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
	
	public String sendCoapObserve(String endpoint,String uri) {
		
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		String result = null;
		
		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}
			Request request = new Request(Code.GET);
			
			String uripath = "coap:/"+registration.getAddress()+":"+ registration.getPort() + uri;
			log.info("=== uripath : {}",uripath);
			request.setURI(uripath);
			
			// TODO 실행하면 오류
			request.setObserve(); // observe set이 0일 경우 observe 확인, 1일 경우 observe 취소

			try {
				log.info("=== registration : {}",registration);
				log.info("=== request : {}",request);
				
				Response response = coapAPI.send(registration, request);
				log.info("=== send : {}"+response);
				
				if (response!=null) {
					log.info("{}",response.getPayload());
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
	
	public String sendCoapObserveCancel(String endpoint,String uri) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		String result = null;
		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}
			Request request = new Request(Code.GET);
			String uripath = "coap:/"+registration.getAddress()+":"+ registration.getPort() + uri;
			log.info("=== uripath : {}",uripath);
			request.setURI(uripath);
			request.setObserveCancel(); // observe cancel

			try {
				log.info("=== registration : {}",registration);
				log.info("=== request : {}",request);
				
				Response response = coapAPI.send(registration, request);
				log.info("=== send : {}"+response);
				
				if (response!=null) {
					log.info("{}",response.getPayload());
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
	
	public String sendCoapRead(String endpoint,String uri) {
		
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		String result = null;
		
		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}
			Request request = new Request(Code.GET);
			
			String uripath = "coap:/"+registration.getAddress()+":"+ registration.getPort()+uri;
			log.info("=== uripath : {}",uripath);
			request.setURI(uripath);

			try {
				log.info("=== registration : {}",registration);
				log.info("=== request : {}",request);
				
				Response response = coapAPI.send(registration, request);
				log.info("=== send : {}"+response);
				
				if (response!=null) {
					log.info("{}",response.getPayload());
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
	
}
