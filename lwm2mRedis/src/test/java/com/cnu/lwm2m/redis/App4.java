package com.cnu.lwm2m.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnu.lwm2m.redis.api.RedisObjectData;
import com.cnu.lwm2m.redis.vo.MeterVo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class App4 {
	
	static final Logger log = LoggerFactory.getLogger(App4.class);
	
	public static void main(String[] args) throws Exception {
		
		log.info("=== start test ===");

		RedisObjectData redisObjectData = new RedisObjectData();

		/*
		 * MeterVo meterVo = new MeterVo(); meterVo.setMeterId("123456789");
		 * meterVo.setMeterType("AE");
		 */
		
		List<Object> list = new ArrayList<Object>();
		MeterVo meterVo = new MeterVo();
		
		for(int i=0;i<5;i++) {
			meterVo = new MeterVo();
			meterVo.setMeterId("123456789"+i);
			meterVo.setMeterType("AE");
			list.add(meterVo);
		}
		
//		int returnData = redisObjectData.setRedisObjectDataConfirm("key", meterVo, MeterVo.class);
		
		int returnData = redisObjectData.setRedisObjectListConfirm("key", list);
		
		log.info("result : "+returnData);
		
		log.info("=== end test ===");

	}
}
