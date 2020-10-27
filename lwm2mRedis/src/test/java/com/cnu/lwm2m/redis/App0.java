package com.cnu.lwm2m.redis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cnu.lwm2m.redis.api.RedisObjectData;
import com.cnu.lwm2m.redis.vo.MeterVo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App0 {

	public static void main(String[] args) throws Exception {

		System.out.println("===== Meter 정보 생성");
		String key = "Meter:Info:List";
		
		RedisObjectData objectData = new RedisObjectData();
		ObjectMapper mapper = new ObjectMapper();
		
		List<Object> list = new ArrayList<Object>();
		MeterVo meterVo = new MeterVo();
		
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String rdate = baseFormat.format(date);
		
		for(int i=100;i<130;i++) {
			meterVo = new MeterVo();
			meterVo.setMeterId("11190000"+i);
			meterVo.setMeterType("Advance E-type");
			meterVo.setrDt(rdate);
			meterVo.setInfo("기타정보");
			list.add(meterVo);
		}
		// meter 정보 set
		objectData.setRedisObjectData(key, list);
		
		
		// meter 정보 get 1 (list)
		List<MeterVo> meterlist = objectData.getRedisListObjectData(key);
		
		for(int i=0;meterlist.size()>i;i++) {
//			 System.out.println(meterlist.get(i).getMeterId());
		};
		
		// Json 정렬
		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meterlist);
		System.out.println(data);
		
		
		// meter 정보 get 2
		String key2 = "Meter:Info:One";
		MeterVo meterVo2 = new MeterVo();
		meterVo2.setMeterId("11190000999");
		meterVo2.setMeterType("Advance E-type");
		meterVo2.setrDt(rdate);
		meterVo2.setInfo("기타정보");
		objectData.setRedisObjectData(key2, meterVo2);
		
		MeterVo meterlist2 = objectData.getRedisObjectData(key2,MeterVo.class);
		String data2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meterlist2);
		System.out.println(data2);


		System.out.println("===== 종료");

	}

}
