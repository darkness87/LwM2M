package com.cnu.lwm2m.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnu.lwm2m.redis.api.RedisConnect;
import com.cnu.lwm2m.redis.api.RedisInfoData;
import com.cnu.lwm2m.redis.api.RedisObjectData;
import com.cnu.lwm2m.redis.vo.LpDataVo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.10.28
 */
public class App7 {

	static final Logger log = LoggerFactory.getLogger(App7.class);

	public static void main(String[] args) throws Exception {

		log.info("=== start test ===");

		RedisConnect redisConnect = new RedisConnect();
		RedisObjectData objectData = new RedisObjectData();
		RedisInfoData infoData = new RedisInfoData();

		Set<String> val = infoData.getSearchKey("LPDATA:20201111");
		List<String> targetList = new ArrayList<String>(val);
		Collections.sort(targetList); // 정렬 !!

		for (int i = 0; i < targetList.size(); i++) {
			log.info("{}", targetList.get(i));
			List<LpDataVo> data = objectData.hgetRedisHashesAllDataList(targetList.get(i),LpDataVo.class);
//			log.info("{}", data);
			
			log.info("{}", data.get(0).getaA0());
		}

		redisConnect.close();
		log.info("=== end test ===");
	}
}
