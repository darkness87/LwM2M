package com.cnu.lwm2m.redis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cnu.lwm2m.redis.api.RedisConnect;
import com.cnu.lwm2m.redis.vo.LpDataVo;
import com.cnu.lwm2m.redis.vo.MeterVo;

import redis.clients.jedis.Jedis;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.10.28
 */
public class App5 {

	static final Logger log = LoggerFactory.getLogger(App5.class);

	public static void main(String[] args) throws Exception {

		log.info("=== start test ===");

		RedisConnect redisConnect = new RedisConnect();
		ObjectMapper mapper = new ObjectMapper();
		Jedis jedis = redisConnect.connect();

		MeterVo meterVo = new MeterVo();
		meterVo.setMeterId("01011234567");
		meterVo.setMeterType("AE-type");
		meterVo.setInfo("data");
		meterVo.setrDt("2020-10-28");
		
		LpDataVo lpDataVo = new LpDataVo();
		lpDataVo.setmId("id123");
		
		String setString = mapper.writeValueAsString(meterVo);
		String setLPString = mapper.writeValueAsString(lpDataVo);
		
		/* Hashes 형태 입출력 */
		System.out.println("<< redis Hashes start >>");
		
		jedis.hset("meter:001", "lpdata", setLPString);
		jedis.hset("meter:001", "reginfo", setString);
		jedis.hset("meter:001", "monthdata", "data");
		jedis.hset("meter:001", "testfield", "data");

		System.out.println(jedis.hget("meter:001", "lpdata"));

		Map<String, String> fields = jedis.hgetAll("meter:001");
		System.out.println("Hashes size : " + jedis.hgetAll("user").size());

		System.out.println("fields : " + fields);
		System.out.println(fields.get("monthdata"));
		
		String jsondata = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fields).replaceAll("\\\\\"", "\"");
		
		System.out.println("jsondata : "+jsondata);
		
		System.out.println(jedis.hdel("meter:001", "lpdata"));
		
		System.out.println("<< redis Hashes end >>");

		redisConnect.close();

		log.info("=== end test ===");

	}
}
