package com.cnu.lwm2m.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.PresenceLwService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PresenceController {
	@Autowired
	PresenceLwService presenceLwService;

	@RequestMapping("/awakeDevice.do")
	public @ResponseBody boolean awakeDevice() {
		boolean result = presenceLwService.awakeAllDevice();
		log.info("=== awakeAllDevice : "+result);
		return result;
	}
	
}
