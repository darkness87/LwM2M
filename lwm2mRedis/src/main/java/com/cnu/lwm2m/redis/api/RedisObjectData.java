package com.cnu.lwm2m.redis.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

/**
 * Object 데이터를 Redis에 저장 List Object 포함
 * 
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.13
 */
public class RedisObjectData {

	static final Logger log = LoggerFactory.getLogger(RedisObjectData.class);

	/*
	 * Class 생성시 RedisConnect, ObjectMapper 선언하여 사용 레디스 연동을 위한 Jedis 라이브러리 사용
	 * DbConnect 사용 후 종료 시 항상 close();
	 */

	RedisConnect redisConnect = new RedisConnect();
	ObjectMapper mapper = new ObjectMapper();

	RedisInfoData redisInfoData = new RedisInfoData();

	/**
	 * Object 데이터 값 체크 후 SET
	 * 
	 * @param <T>
	 * @param key
	 * @param object
	 * @return 0:success, 1:fail, 2:중복키(Object 형태 같음), 3:중복키(Object 형태 다름)
	 * @throws Exception
	 */
	public <T> int setRedisObjectDataConfirm(String key, Object object, Class<T> T) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		int returnValue = redisInfoData.getRedisKeyClassCheck(jedis, key, T);

		if (returnValue == 0) {
			String setString = mapper.writeValueAsString(object);
			jedis.set(key, setString);
		}
		redisConnect.close();
		return returnValue;
	}

	/**
	 * List<Object> 데이터 값 체크 후 SET
	 * 
	 * @param <T>
	 * @param key
	 * @param T
	 * @return 0:success, 1:fail, 2:중복키(Object 형태 같음), 3:중복키(Object 형태 다름)
	 * @throws Exception
	 */
	public <T> int setRedisObjectListConfirm(String key, List<T> T) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		int returnValue = redisInfoData.getRedisKeyListCheck(jedis, key, T);

		if (returnValue == 0) {
			String setString = mapper.writeValueAsString(T);
			jedis.set(key, setString);
		}
		redisConnect.close();
		return returnValue;
	}

	/**
	 * Object 데이터 SET
	 * 
	 * @param key
	 * @param object
	 * @return 0:success, 1:fail
	 * @throws Exception
	 */
	public int setRedisObjectData(String key, Object object) throws Exception {
		String setString = mapper.writeValueAsString(object);
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		jedis.set(key, setString);
		redisConnect.close();
		return 0;
	}

	/**
	 * String 데이터 SET
	 * 
	 * @param key
	 * @param setString
	 * @return 0:success, 1:fail
	 * @throws Exception
	 */
	public int setRedisStringData(String key, String setString) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		jedis.set(key, setString);
		redisConnect.close();
		return 0;
	}

	/**
	 * String, Object String 데이터 GET
	 * 
	 * @param key
	 * @return
	 * @return String
	 * @throws Exception
	 */
	public String getRedisStringData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		String data = jedis.get(key);
		if (data == null || data.equals("")) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		redisConnect.close();
		return data;
	}

	/**
	 * Object 데이터 GET
	 * 
	 * @param <T>
	 * @param key
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T> T getRedisObjectData(String key, Class<T> T) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		T object = (T) mapper.readValue(jedis.get(key), T);
		if (object == null) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		redisConnect.close();
		return object;
	}

	/**
	 * List<Object> 데이터 GET
	 * 
	 * @param <T>
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getRedisListObjectData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		List<T> object = mapper.readValue(jedis.get(key), new TypeReference<List<T>>() {
		});
		if (object == null) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		redisConnect.close();
		return object;
	}

	/**
	 * Hashes 형태 hget (Object)
	 * 
	 * @param <T>
	 * @param key
	 * @param field
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T> T hgetRedisHashesData(String key, String field, Class<T> T) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		T object = (T) mapper.readValue(jedis.hget(key, field), T);
		if (object == null) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		redisConnect.close();
		return object;
	}

	/**
	 * Hashes 형태 hget (String)
	 * 
	 * @param key
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public String hgetRedisHashesData(String key, String field) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		String data = jedis.hget(key, field);
		if (data == null) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		redisConnect.close();
		return data;
	}

	/**
	 * Hashes 형태 hgetAll (전체값 String)
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String hgetRedisHashesAllData(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		Map<String, String> fields = jedis.hgetAll(key);

		if (fields == null) {
			log.info("=== Redis Data Null");
			redisConnect.close();
			return null;
		}
		String jsondata = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fields); //.replaceAll("\\\\\"", "\"");
		redisConnect.close();
		return jsondata;
	}

	/**
	 * Hashes 형태 hset (Object)
	 * 
	 * @param <T>
	 * @param key
	 * @param field
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T> int hsetRedisHashesData(String key, String field, Class<T> T) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		String setString = mapper.writeValueAsString(T);
		jedis.hset(key, field, setString);
		redisConnect.close();
		return 0;
	}

	/**
	 * Hashes 형태 hset (String)
	 * 
	 * @param <T>
	 * @param key
	 * @param field
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public <T> int hsetRedisHashesData(String key, String field, String data) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		jedis.hset(key, field, data);
		redisConnect.close();
		return 0;
	}

}
