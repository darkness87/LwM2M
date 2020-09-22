package com.cnu.lwm2m.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.SenderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SenderController {
	@Autowired
	SenderService senderService;

	@RequestMapping("/sender.do")
	public @ResponseBody boolean sender() {
		log.info("");
		return false;
	}
	
}
