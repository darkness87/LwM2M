package com.red;

import com.red.api.LpData;
import com.red.vo.LpDataVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class App {
	
	static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) throws Exception {
		
//		log.info("=== start ===");
		System.out.println("=== start test ===");
		
		// DB Connect 발생
//		DbConnect dbConnect = new DbConnect();
//		Jedis jedis = dbConnect.redisConnect();
		
		// 데이터 조회
		String key = "connect";
		/*
		 * if(jedis!=null) { LpData lpData = new LpData();
		 * 
		 * System.out.println(lpData.getLpDataString(jedis,key)); }else if(jedis==null)
		 * { System.out.println("Connect Fail"); }
		 */
		
		
		LpDataVo e = new LpDataVo();
		
		/*
		 * e.setDate(0); e.setEtc(0); e.setId("string"); e.setKey("key"); e.setLp1(0);
		 * e.setLp2(0); e.setMeterId(123456789); e.setProtocal("protocal");
		 * e.setType("type");
		 */
			
		LpData lpData = new LpData();
		lpData.setLPHash(key, e);
		
		LpDataVo LpDataVo = lpData.getLPHash(key);
		
		System.out.println(LpDataVo);
		
		
		
		
		  List<LpDataVo> list = new ArrayList<LpDataVo>();
		  
		  LpDataVo le = new LpDataVo();
		  
		  for(int i=0;i<96;i++) {
			  le = new LpDataVo();
			  
			/*
			 * le.setDate(20200812); le.setEtc(9); le.setId("string"); le.setKey("key");
			 * le.setLp1(1234); le.setLp2(1111); le.setMeterId(123456789);
			 * le.setProtocal("protocal"); le.setType("type");
			 */
			  
			  list.add(le);
		  
		  }
		  
		  lpData.setLpList(key, list);
		  
		 		
		
		// DB Connect 종료
//		dbConnect.redisClose();
		
		System.out.println("=== end test ===");

	}
}
