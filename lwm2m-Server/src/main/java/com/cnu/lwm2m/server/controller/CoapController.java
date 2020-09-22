package com.cnu.lwm2m.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cnu.lwm2m.server.service.CoapService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CoapController {
	@Autowired
	CoapService coapService;

	@RequestMapping("/coapObserve.do")
	public @ResponseBody String sendCoapObserve(@RequestParam String endpoint,@RequestParam String uri) {
		log.info("=== sendCoapObserve ===");
		String result = coapService.sendCoapObserve(endpoint,uri);
		if(result==null) {
			result="error";
		}
		return result;
	}
	
	@RequestMapping("/coapObserveCancel.do")
	public @ResponseBody String sendCoapObserveCancel(@RequestParam String endpoint,@RequestParam String uri) {
		log.info("=== sendCoapObserve Cancel ===");
		String result = coapService.sendCoapObserve(endpoint,uri);
		if(result==null) {
			result="error";
		}
		return result;
	}
	
}
