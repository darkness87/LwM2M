package com.cnu.lwm2m.redis.api;

import java.util.List;
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
public class ObjectData {

	/*
	 * Class 생성시 DbConnect, ObjectMapper 선언하여 사용
	 * 레디스 연동을 위한 Jedis 라이브러리 사용
	 * DbConnect 사용 후 종료 시 항상 close();
	 */
	
	RedisConnect redisConnect = new RedisConnect();
	ObjectMapper mapper = new ObjectMapper();
	
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
			return 1;
		}
		
		jedis.set(key, setString);
		redisConnect.close();
		
		return 0; // return 처리 오류에 따른 변경 필요
	}
	
	/**
	 * Object 데이터 GET
	 * @param key
	 * @return 
	 * @return Object
	 * @throws Exception
	 */
	public <T> Object getObjectData(String key) throws Exception {
		
		Jedis jedis = redisConnect.connect();
		
		if(jedis==null) {
			return null;
		}
		
		Object object = mapper.readValue(jedis.get(key),Object.class);
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
			return 1;
		}
		
		jedis.set(key, setString);
		redisConnect.close();
		
		return 0; // return 처리 오류에 따른 변경 필요
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
			return null;
		}
		
		List<Object> object = mapper.readValue(jedis.get(key),new TypeReference<List<Object>>(){});
		redisConnect.close();
		
		return object;
	}
	
}
