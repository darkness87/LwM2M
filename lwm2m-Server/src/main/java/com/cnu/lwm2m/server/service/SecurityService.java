package com.cnu.lwm2m.server.service;

import org.eclipse.leshan.server.security.SecurityInfo;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecurityService {
	/*
	 * @Autowired SecurityStore securityStore;
	 */

	public SecurityInfo getByEndpointSecurity(String endpoint) {
		log.info("");
//		log.info("{}",securityStore.getByEndpoint(endpoint));
//		return securityStore.getByEndpoint(endpoint);
		return null;
	}
	
}
