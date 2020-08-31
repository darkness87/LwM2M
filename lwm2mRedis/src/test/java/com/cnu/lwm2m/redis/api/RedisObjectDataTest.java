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
public class RedisObjectDataTest {

	static final Logger log = LoggerFactory.getLogger(RedisObjectData.class);
	
	/*
	 * Class 생성시 RedisConnect, ObjectMapper 선언하여 사용
	 * 레디스 연동을 위한 Jedis 라이브러리 사용
	 * DbConnect 사용 후 종료 시 항상 close();
	 */
	
	RedisConnect redisConnect = new RedisConnect();
	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Object 데이터 GET, 리스트 GET
	 * @param key
	 * @return 
	 * @return Object
	 * @throws Exception
	 */
	public String getObjectStringData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
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
	
	/**
	 * Object 데이터 SET
	 * @param key
	 * @param object
	 * @return 0:success, 1:fail
	 * @throws Exception
	 */
	public int setObjectData(String key, Object object) throws Exception{
		String setString = mapper.writeValueAsString(object);
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return 1;
		}
		jedis.set(key, setString);
		redisConnect.close();
		return 0;
	}
	
	/**
	 * Object 데이터 GET
	 * @param key
	 * @return 
	 * @return Object
	 * @throws Exception
	 */
	public Object getObjectData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return null;
		}
		Object object = mapper.readValue(jedis.get(key),Object.class);
		if(object==null||object.equals("")) {
			log.info("KEY에 해당되는 데이터 없음");
			return null;
		}
		redisConnect.close();
		return object;
	}
	
	/**
	 * List<Object> 데이터 SET
	 * @param key
	 * @param object
	 * @return 0:success, 1:fail
	 * @throws Exception
	 */
	public int setObjectListData(String key, List<Object> object) throws Exception{
		String setString = mapper.writeValueAsString(object);
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return 1;
		}
		jedis.set(key, setString);
		redisConnect.close();
		return 0;
	}
	
	/**
	 * List<Object> 데이터 GET
	 * @param key
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getObjectListData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return null;
		}
		List<Object> object = mapper.readValue(jedis.get(key),new TypeReference<List<Object>>(){});
		if(object==null||object.equals("")) {
			log.info("KEY에 해당되는 데이터 없음");
			return null;
		}
		redisConnect.close();
		return object;
	}
	
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
