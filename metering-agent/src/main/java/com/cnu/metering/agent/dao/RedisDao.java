package com.cnu.metering.agent.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.cnu.lwm2m.redis.api.RedisInfoData;
import com.cnu.lwm2m.redis.api.RedisObjectData;

@Repository
public class RedisDao {

	RedisObjectData redisObjectData = new RedisObjectData();
	RedisInfoData redisInfoData = new RedisInfoData();
	
	public int setRedisData(String key,Object object) throws Exception {
		int returnValue = redisObjectData.setObjectData(key, object);
		return returnValue;
	}
	
	public String getRedisData(String key) throws Exception {
		String data = redisObjectData.getObjectStringData(key);
		return data;
	}
	
	public int setExpireKey(String key, int sec) throws Exception{
		int returnValue = redisInfoData.setExpireKey(key, sec);
		return returnValue;
	}
	
	public int setPersistKey(String key) throws Exception{
		int returnValue = redisInfoData.setPersistKey(key);
		return returnValue;
	}
	
	public Set<String> getAllKey() throws Exception{
		Set<String> keys = redisInfoData.getAllKey();
		return keys;
	}
	
	public Set<String> getSearchKey(String key) throws Exception{
		Set<String> keys = redisInfoData.getSearchKey(key);
		return keys;
	}
	
	public Boolean getBooleanKey(String key) throws Exception{
		Boolean ex = redisInfoData.getBooleanKey(key);
		return ex;
	}
	
	public int setDelKey(String key) throws Exception{
		int returnValue = redisInfoData.setDelKey(key);
		return returnValue;
	}
	
	public String getClientList() throws Exception{
		String clientInfo = redisInfoData.getClientList();
		return clientInfo;
	}
	
	public String getRedisInfo() throws Exception{
		String redisInfo = redisInfoData.getRedisInfo();
		return redisInfo;
	}
	
}
