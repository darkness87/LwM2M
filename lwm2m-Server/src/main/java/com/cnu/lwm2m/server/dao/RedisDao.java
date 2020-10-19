package com.cnu.lwm2m.server.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.cnu.lwm2m.redis.api.RedisInfoData;
import com.cnu.lwm2m.redis.api.RedisObjectData;

@Repository
public class RedisDao {

	RedisObjectData redisObjectData = new RedisObjectData();
	RedisInfoData redisInfoData = new RedisInfoData();

	public int setRedisData(String key, Object object) throws Exception {
		int returnValue = redisObjectData.setRedisObjectData(key, object);
		return returnValue;
	}

	public int setRedisStringData(String key, String setString) throws Exception {
		int returnValue = redisObjectData.setRedisStringData(key, setString);
		return returnValue;
	}

	public <T> T getRedisData(String key, Class<T> T) throws Exception {
		T object = redisObjectData.getRedisObjectData(key, T);
		return object;
	}

	public <T> List<T> getRedisData(String key) throws Exception {
		List<T> object = redisObjectData.getRedisListObjectData(key);
		return object;
	}

	public String getRedisStringData(String key) throws Exception {
		String data = redisObjectData.getRedisStringData(key);
		return data;
	}

	public int setExpireKey(String key, int sec) throws Exception {
		int returnValue = redisInfoData.setExpireKey(key, sec);
		return returnValue;
	}

	public int setPersistKey(String key) throws Exception {
		int returnValue = redisInfoData.setPersistKey(key);
		return returnValue;
	}

	public Set<String> getAllKey() throws Exception {
		Set<String> keys = redisInfoData.getAllKey();
		return keys;
	}

	public Set<String> getSearchKey(String key) throws Exception {
		Set<String> keys = redisInfoData.getSearchKey(key);
		return keys;
	}

	public Boolean getBooleanKey(String key) throws Exception {
		Boolean ex = redisInfoData.getBooleanKey(key);
		return ex;
	}

	public int setDelKey(String key) throws Exception {
		int returnValue = redisInfoData.setDelKey(key);
		return returnValue;
	}

	public String getClientList() throws Exception {
		String clientInfo = redisInfoData.getClientList();
		return clientInfo;
	}

	public String getRedisInfo() throws Exception {
		String redisInfo = redisInfoData.getRedisInfo();
		return redisInfo;
	}

}