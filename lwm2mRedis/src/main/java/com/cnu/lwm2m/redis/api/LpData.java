package com.cnu.lwm2m.redis.api;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import com.cnu.lwm2m.redis.vo.LpDataVo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class LpData {
	
	RedisConnect redisConnect = new RedisConnect();
	ObjectMapper mapper = new ObjectMapper();

	/**
	 * LP데이터 key 값 String 조회
	 * @param jedis
	 * @param key
	 * @return String
	 * @throws Exception
	 */
	public String getLpDataString(Jedis jedis,String key) throws Exception{
		
		String valueString = jedis.get(key);
		
		return valueString; // 오류시 리턴값 변경 전달
	}

	/**
	 * LP데이터 String 저장
	 * @param jedis
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public int setLpData(Jedis jedis,String key,String data) throws Exception{
		
		jedis.set(key, data);
		
		return 0; // set 오류시 return 값 변경하여 전달해야함
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getTestDataString(String key) throws Exception{
		
		Jedis jedis = redisConnect.connect();
		
		String valueString = jedis.get(key);
		
		return valueString;
	}
	
	/**
	 * vo,object에 set 된 데이터를 json string으로 저장
	 * @param key
	 * @param lpDataVO
	 * @return
	 * @throws Exception
	 */
	public int setLPHash(String key, Object object) throws Exception {
		
		String setString = mapper.writeValueAsString(object);
		
		Jedis jedis = redisConnect.connect();
		
		jedis.set(key, setString);
		
		redisConnect.close();
		
		return 0;
		
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public LpDataVo getLPHash(String key) throws Exception {
		
		Jedis jedis = redisConnect.connect();
		
		jedis.get(key);
		
		LpDataVo LpDataVo = mapper.readValue(jedis.get(key),LpDataVo.class);
		
		//또는
		//Object object = mapper.readValue(jedis.get(key),Object.class); // Object 로 넘길수도 있음
				
		redisConnect.close();
		
		return LpDataVo;
		
	}
	
	/**
	 * List vo,object에 set 된 데이터를 json string으로 저장
	 * @param key
	 * @param lpDataVO
	 * @return
	 * @throws Exception
	 */
	public int setLpList(String key, List<LpDataVo> list) throws Exception {
		
		String setString = mapper.writeValueAsString(list);
		
		Jedis jedis = redisConnect.connect();
		
		jedis.set(key, setString);
		
		redisConnect.close();
		
		return 0;
		
	}
	
}
