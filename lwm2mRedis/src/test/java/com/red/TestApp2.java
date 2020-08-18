package com.red;

import com.red.api.LpData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class TestApp2 {
	
	static final Logger log = LoggerFactory.getLogger(TestApp2.class);
	
	public static void main(String[] args) throws Exception {
		
//		log.info("=== start ===");
		System.out.println("=== start test ===");

		// 데이터 조회
		String key = "connect";

		LpData lpData = new LpData();
		String testData = lpData.getTestDataString(key);
		System.out.println(testData);
		
		System.out.println("=== end test ===");

	}
}
