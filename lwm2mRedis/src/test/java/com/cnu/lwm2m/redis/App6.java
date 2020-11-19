package com.cnu.lwm2m.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnu.lwm2m.redis.api.RedisConnect;
import com.cnu.lwm2m.redis.api.RedisObjectData;
import com.cnu.lwm2m.redis.vo.LpDataVo;

import redis.clients.jedis.Jedis;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.10.28
 */
public class App6 {

	static final Logger log = LoggerFactory.getLogger(App6.class);

	public static void main(String[] args) throws Exception {

		log.info("=== start test ===");

		RedisConnect redisConnect = new RedisConnect();
		Jedis jedis = redisConnect.connect();
		RedisObjectData objectData = new RedisObjectData();

		Map<String, String> fields = jedis.hgetAll("LPDATA:20201111:2015");
//		System.out.println("fields : " + fields);

		// System.out.println(fields.get("cnumeter10"));
		Set<String> keyset = fields.keySet();

		List<String> targetList = new ArrayList<String>(keyset);

		Collections.sort(targetList);
		System.out.println(targetList);

		for (int i = 0; i < targetList.size(); i++) {
			LpDataVo lpData = objectData.hgetRedisHashesData("LPDATA:20201111:2015", targetList.get(i), LpDataVo.class);
			System.out.println(targetList.get(i) + " : " + lpData.getaA0());
		}

		// String jsondata =
		// mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fields);
		// //.replaceAll("\\\\\"", "\"");

//		System.out.println("jsondata : " + jsondata);

		redisConnect.close();

		log.info("=== end test ===");

	}
}
