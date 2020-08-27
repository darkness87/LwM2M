package com.cnu.lwm2m.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cnu.lwm2m.redis.api.RedisObjectData;
import com.cnu.lwm2m.redis.api.RedisConnect;
import com.cnu.lwm2m.redis.vo.LpBlackoutRviVo;
import com.cnu.lwm2m.redis.vo.LpBlackoutVo;

import redis.clients.jedis.Jedis;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class App1 {
	
	static final Logger log = LoggerFactory.getLogger(App1.class);
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("=== start test ===");

//		RedisConnect redisConnect = new RedisConnect();
//		Jedis jedis = redisConnect.connect();
		
		ObjectMapper mapper = new ObjectMapper();
		
		RedisObjectData objectData = new RedisObjectData();
		LpBlackoutVo lpBlackoutVo = new LpBlackoutVo();
		
		lpBlackoutVo.setaId("1");
		lpBlackoutVo.setcDt("2");
		lpBlackoutVo.setmId("3");
		lpBlackoutVo.setoCd("4");
		lpBlackoutVo.setrSt("5");
		
		List<LpBlackoutRviVo> list = new ArrayList<LpBlackoutRviVo>();
		LpBlackoutRviVo lpBlackoutRviVo = new LpBlackoutRviVo();
		
		for(int i=0;i<10;i++) {
			lpBlackoutRviVo = new LpBlackoutRviVo();
			lpBlackoutRviVo.setbCt("50");
			lpBlackoutRviVo.setbDt("70");
			list.add(lpBlackoutRviVo);
		}
		
		lpBlackoutVo.setrVl(list);
		
		objectData.setObjectData("testkey", lpBlackoutVo);
		
		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectData.getObjectData("testkey"));
        System.out.println(data);
		
//		redisConnect.close();
		
		System.out.println("=== end test ===");

	}
}
