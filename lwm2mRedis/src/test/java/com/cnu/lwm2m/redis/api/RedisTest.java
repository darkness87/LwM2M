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
	
	public String getObjectStringData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("=== Redis Connect Error");
			return null;
		}
		String data = jedis.get(key);
		if(data==null||data.equals("")) {
			log.info("KEY에 해당되는 데이터 없음");
			return null;
		}
		redisConnect.close();
		return data;
	}

	public int setObjectData(String key, Object object) throws Exception{
		String setString = mapper.writeValueAsString(object);
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("=== Redis Connect Error");
			return 1;
		}
		jedis.set(key, setString);
		redisConnect.close();
		return 0;
	}
		
	// TODO
	public <T> T getTData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("=== Redis Connect Error");
			return null;
		}
		T object = mapper.readValue(jedis.get(key),new TypeReference<T>(){});
		Object object2 = mapper.readValue(jedis.get(key),Object.class);
		redisConnect.close();
		return object;
	}
	
	// TODO // 제네릭 리스트 전달 완료
	public <T> List<T> getTListData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("=== Redis Connect Error");
			return null;
		}
		List<T> object = mapper.readValue(jedis.get(key),new TypeReference<List<T>>(){});
		redisConnect.close();
		return object;
	}
	
}
