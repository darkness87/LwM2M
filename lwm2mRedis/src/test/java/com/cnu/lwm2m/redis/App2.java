package com.cnu.lwm2m.redis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.cnu.lwm2m.redis.api.RedisObjectData;
import com.cnu.lwm2m.redis.api.RedisConnect;
import com.cnu.lwm2m.redis.vo.LpLoadProfileVo;
import com.cnu.lwm2m.redis.vo.MeterVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */

public class App2 
{
    public static void main( String[] args ) throws Exception
    {

		RedisConnect redisConnect = new RedisConnect();
		ObjectMapper mapper = new ObjectMapper();
		Jedis jedis = redisConnect.connect();
		
		if(jedis==null) {
			System.out.println("Fail");
		}
		
		
		
		List<Object> list = new ArrayList<Object>();
		MeterVo meterVo = new MeterVo();
		
		for(int i=100;i<130;i++) {
			meterVo = new MeterVo();
			meterVo.setMeterId("11190000"+i);
			list.add(meterVo);
		}
		
		String setString1 = mapper.writeValueAsString(list);
		jedis.set("meterid", setString1);
		
		
		
		List<MeterVo> meterObject = mapper.readValue(jedis.get("meterid"),new TypeReference<List<MeterVo>>(){});
		
		
		
		LpLoadProfileVo lp = new LpLoadProfileVo();
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "";
		String key = "";
		Random rnd = new Random();
		
		for(int i=0;i<meterObject.size();i++) {
			lp = new LpLoadProfileVo();
			
			meterid = meterObject.get(i).getMeterId();
			key = "LoadProfile:"+meterid+":"+fdate;
			
			rnd = new Random();
			
			lp.setAid("agent_cnu_13493"); // Agent ID
			lp.setMid(meterid); // Meter ID
			lp.setcDv("0"); // 검침구분 (0:정규, 1:재검침, 2:자율검침)
			lp.setbDt(baseFormat.format(date)); // Base Date 수집기준일시
			lp.setfPa(String.valueOf(rnd.nextInt(10))); // 순방향 유효전력량
			lp.setfGa(String.valueOf(rnd.nextInt(10))); // 순방향 지상무효전력량
			lp.setfRa(String.valueOf(rnd.nextInt(10))); // 순방향 진상무효전력량
			lp.setfAa(String.valueOf(rnd.nextInt(10))); // 순방향 피상전력량
			lp.setmDt(baseFormat.format(date)); // Date // 검침일시 (계기기록시간)
			lp.setmSt("11"); // 계기 상태정보
			lp.setrPa(String.valueOf(rnd.nextInt(10))); // 역방향 유효전력량
			lp.setrRa(String.valueOf(rnd.nextInt(10))); // 역방향 진상무효전력량
			lp.setrGa(String.valueOf(rnd.nextInt(10))); // 역방향 지상무효전력량
			lp.setrSt("2"); // 결과 (0:대기, 1:전달완료, 2:성공, 101:실패, 102:미지원)
			lp.setcDt(baseFormat.format(date)); // Date // Master 요청에 대한 상세정보
	
			jedis.set(key, mapper.writeValueAsString(lp));
			jedis.expire(key, 86400); // 1일 
			
		}
		
		redisConnect.close();
		
		System.out.println("sample ===== "+fdate);
    }
}
