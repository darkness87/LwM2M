package com.cnu.lwm2m.server.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SenderService {
	/*
	 * @Autowired LwM2mRequestSender lwM2mRequestSender;
	 */

	public boolean sendCoapObserve(String endpoint) {
		log.info("");
//		lwM2mRequestSender.send(destination, request, timeoutInMs)
		return false;
	}

}
