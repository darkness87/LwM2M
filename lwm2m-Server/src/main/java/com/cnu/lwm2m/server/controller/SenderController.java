package com.cnu.lwm2m.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.cnu.lwm2m.server.service.SenderService;

import lombok.extern.slf4j.Slf4j;

/**
 * ESS 이벤트 스트림 처리
 **/
@Slf4j
@Controller
public class SenderController {
	@Autowired
	SenderService senderService;

	@RequestMapping("/sender.do")
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseBodyEmitter users() {
		SseEmitter emitter = new SseEmitter();
		log.info("{}", emitter);
		senderService.add(emitter);
		
		log.info("{}",emitter.toString());
		
		return emitter;
	}

}