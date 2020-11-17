package com.cnu.lwm2m.server.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.RedisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DataController {

	@Autowired
	RedisService redisService;

	@RequestMapping("/getSearchData.do")
	public @ResponseBody List<String> getSearchData(String typeData, String dateData) throws Exception {
		// TODO
		log.info("=== getChart  ===");
		String key = typeData + ":" + dateData.replaceAll("-", "");
		log.info("{}", key);

		List<String> val = redisService.getSearchKey(key);
		Collections.sort(val); // 정렬 !!

		for (int i = 0; i < val.size(); i++) {
			log.info("{}", val.get(i));
			String data = redisService.getHgetAllData(val.get(i));
//			Collections.sort(data);
			log.info("{}", data);
		}

		return val;
	}

}
