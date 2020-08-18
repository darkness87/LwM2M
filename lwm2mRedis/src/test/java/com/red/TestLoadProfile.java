package com.red;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red.api.ObjectData;
import com.red.vo.LpLoadProfileVo;
import com.red.vo.LpDataVo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class TestLoadProfile {
	
	static final Logger log = LoggerFactory.getLogger(TestLoadProfile.class);
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("=== start test ===");
		
		String meterid = "02190000005";
		int date = 1597217281;
		
		String key = "key_"+meterid;
		
		ObjectData objectData = new ObjectData();
		
		
//		List<LoadProfileVo> list = new ArrayList<LoadProfileVo>();
		
		List<Object> list = new ArrayList<Object>();
		
		LpLoadProfileVo LoadProfileVo = new LpLoadProfileVo();
		
		for(int i=0;i<96;i++) {
		
			LoadProfileVo.setAid("1234567890");
			LoadProfileVo.setbDt("1234567890");
			LoadProfileVo.setcDt("1234567890");
			LoadProfileVo.setcDv("1234567890");
			LoadProfileVo.setfAa("1234567890");
			LoadProfileVo.setfGa("1234567890");
			LoadProfileVo.setfPa("1234567890");
			LoadProfileVo.setfRa("1234567890");
			LoadProfileVo.setmDt("1234567890");
			LoadProfileVo.setMid("1234567890");
			LoadProfileVo.setmSt("1234567890");
			LoadProfileVo.setrGa("1234567890");
			LoadProfileVo.setrPa("1234567890");
			LoadProfileVo.setrRa("1234567890");
			LoadProfileVo.setrSt("1234567890");
			
			list.add(LoadProfileVo);
			
		}
		
		objectData.setObjectListData(key, list);
		
//		Object data = objectData.getObjectData(key);
		
		System.out.println("=== end test ===");

	}
}
