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
	
	static Runtime runtime = Runtime.getRuntime();

	public static void main(String[] args) throws Exception {
		
		log.info("=== start test ===");

		ObjectMapper mapper = new ObjectMapper();
		RedisTest redisTest = new RedisTest();

		List<MeterVo> meter = redisTest.getRedisListData("Meter:Info:List"); // List화된 Object 가져올시
		String dataList = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meter);
		log.info(dataList);
		
		MeterVo meterOne = redisTest.getRedisData("Meter:Info:One",MeterVo.class); // Object 가져올시
		String dataOne = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meterOne);
		log.info(dataOne);
		
		log.info("=== end test ===");
		
		long max = runtime.maxMemory() / 1024 / 1024;
	    long total = runtime.totalMemory() / 1024 / 1024;
	    long free = runtime.freeMemory() / 1024 / 1024;
	    log.info(String.format("Max: %,dMB, Total: %,dMB, Free: %,dMB, Used: %,dMB", max, total, free, total - free));

	}
}
