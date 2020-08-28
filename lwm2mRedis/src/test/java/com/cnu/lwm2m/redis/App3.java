package com.cnu.lwm2m.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cnu.lwm2m.redis.api.RedisTest;
import com.cnu.lwm2m.redis.vo.MeterVo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class App3 {
	
	static final Logger log = LoggerFactory.getLogger(App3.class);
	
	public static void main(String[] args) throws Exception {
		
		log.info("=== start test ===");

		ObjectMapper mapper = new ObjectMapper();

		RedisTest redisTest = new RedisTest();

		List<MeterVo> meter = redisTest.getTListData("Meter:Info:List");

		//String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meter);
		
		log.info(meter.toString());
		
		
		MeterVo meterOne = redisTest.getTData("Meter:Info:One");
		
		log.info(meterOne.toString());
		
		log.info("=== end test ===");

	}
}
