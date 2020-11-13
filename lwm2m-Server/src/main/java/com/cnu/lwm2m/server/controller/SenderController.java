package com.cnu.lwm2m.server.controller;

import java.io.IOException;

import org.eclipse.leshan.server.californium.LeshanServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.cnu.lwm2m.server.init.AbsClientListener;
import com.cnu.lwm2m.server.service.SenderService;

import lombok.extern.slf4j.Slf4j;

/**
 * ESS 이벤트 스트림 처리
 **/
@Slf4j
@Controller
public class SenderController extends AbsClientListener {
	public SenderController(LeshanServer server) {
		super(server);
	}

	@Autowired
	SenderService senderService;

	@GetMapping(path = "/sender", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public @ResponseBody ResponseBodyEmitter sender(@RequestParam String uri) {

		// TODO 디버깅 필요
		SseEmitter emitter = new SseEmitter();
		log.info("{}", emitter);
		log.info("{}", emitter.getTimeout());

		senderService.add(emitter);

		Emitter emitter2 = null;
		try {
			onOpen(emitter2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("{}", emitter);

		return emitter;
	}

}