package com.cnu.lwm2m.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SenderService {

	private static final int REPEAT = 10;
	private final Map<ResponseBodyEmitter, AtomicInteger> emitterCountMap = new HashMap<>();

	public void add(ResponseBodyEmitter emitter) {
		emitterCountMap.put(emitter, new AtomicInteger(0));
	}

	@Scheduled(fixedRate = 1000L)
	public void emit() {

		List<ResponseBodyEmitter> toBeRemoved = new ArrayList<>(emitterCountMap.size());

		for (Map.Entry<ResponseBodyEmitter, AtomicInteger> entry : emitterCountMap.entrySet()) {

			Integer count = entry.getValue().incrementAndGet();
			User user = new RestTemplate().getForObject("https://jsonplaceholder.typicode.com/users/{id}", User.class,
					count);

			ResponseBodyEmitter emitter = entry.getKey();

			try {
				log.info("{}", user); // 로그 동작함
				emitter.send(user);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				toBeRemoved.add(emitter);
			}

			if (count >= REPEAT) {
				toBeRemoved.add(emitter);
			}
		}

		for (ResponseBodyEmitter emitter : toBeRemoved) {
			emitterCountMap.remove(emitter);
		}
	}

}
