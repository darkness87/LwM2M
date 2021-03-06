package com.cnu.lwm2m.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cnu.lwm2m.redis.api.RedisConnect;

import redis.clients.jedis.Jedis;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class App {
	
	static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) throws Exception {
		
		log.info("=== start test ===");

		RedisConnect redisConnect = new RedisConnect();
		ObjectMapper mapper = new ObjectMapper();
		Jedis jedis = redisConnect.connect();


		
		redisConnect.close();
		
		log.info("=== end test ===");

	}
}
