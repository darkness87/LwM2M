package com.cnu.lwm2m.redis.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

/**
 * Object 데이터를 Redis에 저장
 * List Object 포함
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.13
 */
public class RedisTest {

	static final Logger log = LoggerFactory.getLogger(RedisObjectData.class);

	RedisConnect redisConnect = new RedisConnect();
	ObjectMapper mapper = new ObjectMapper();
		
	public <T> T getRedisData(String key,Class<T> T) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		T object = (T) mapper.readValue(jedis.get(key),T);
		if(object==null) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		redisConnect.close();
		return object;
	}
	
	public <T> List<T> getRedisListData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("=== Redis Connect Error");
			return null;
		}
		List<T> object = mapper.readValue(jedis.get(key),new TypeReference<List<T>>(){});
		if(object==null) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		redisConnect.close();
		return object;
	}
	
}
