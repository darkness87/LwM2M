package com.cnu.lwm2m.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.RedisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RedisController {

	@Autowired
	RedisService redisService;

	@RequestMapping("/getRedisKeyList.do")
	public @ResponseBody List<String> getRedisKeyList(@RequestParam String typeData, @RequestParam String dateData)
			throws Exception {
		log.info("=== getRedisKeyList ===");
		log.info("{},{}", typeData, dateData);

		String key = typeData;

		// TODO 조건 추가
		if (typeData.equals("HIS")) {
			key = typeData + ":OBS:" + dateData.replaceAll("-", "");
		}

		log.info(key);

		return redisService.getKeyList(key);
	}

	@RequestMapping("/getRedisKeyData.do")
	public @ResponseBody String getRedisKeyData(String key, String keyType) throws Exception {
		log.info("=== getRedisKeyData ===");
		log.info(keyType);
		if (keyType.equals("hash")) { // 타입별 나누기
			String data = redisService.getHgetAllData(key); // HASH 타입
			log.info(data);
			return data.toString();
		} else {
			String data = redisService.getRedisKeyData(key); // String 타입
			log.info(data);
			return data;
		}
	}

	@RequestMapping("/getHgetAllData.do")
	public @ResponseBody String getHgetAllData(String key) throws Exception {
		log.info("=== getHgetAllData ===");
		return redisService.getHgetAllData(key);
	}

}