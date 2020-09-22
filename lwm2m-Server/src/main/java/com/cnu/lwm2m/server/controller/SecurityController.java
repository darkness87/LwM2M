package com.cnu.lwm2m.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SecurityController {
	@Autowired
	SecurityService securityService;

	@RequestMapping("/getByEndpointSecurity.do")
	public @ResponseBody boolean getByEndpointSecurity() {
		log.info("");
		return false;
	}
	
}
