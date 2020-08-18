package com.red;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red.api.ObjectData;
import com.red.vo.LpDataVo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class TestObject {
	
	static final Logger log = LoggerFactory.getLogger(TestObject.class);
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("=== start test ===");
		
		String key = "objectKey";
		
		ObjectData objectData = new ObjectData();
		
		LpDataVo object = new LpDataVo();
		
		/*
		 * object.setDate(20200813); object.setEtc(9); object.setId("string");
		 * object.setKey("key"); object.setLp1(1234); object.setLp2(1111);
		 * object.setMeterId(123456789); object.setProtocal("protocal");
		 * object.setType("type");
		 */
		
		objectData.setObjectData(key, object);
		
		Object data = objectData.getObjectData(key);
		
		System.out.println(data);
		
		System.out.println("=== end test ===");

	}
}
