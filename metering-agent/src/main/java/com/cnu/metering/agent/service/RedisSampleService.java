package com.cnu.metering.agent.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.lwm2m.redis.api.InfoData;
import com.cnu.lwm2m.redis.api.ObjectData;
import com.cnu.lwm2m.redis.vo.LpBlackoutRviVo;
import com.cnu.lwm2m.redis.vo.LpBlackoutVo;
import com.cnu.lwm2m.redis.vo.LpLoadProfileVo;
import com.cnu.lwm2m.redis.vo.MeterVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis Set / Get 에 대한 Meter데이터 Sample Code
 * @author sookwon
 * @since 2020.08.26
 */
@Slf4j
@Service
public class RedisSampleService {

	@Autowired
	ObjectData objectData;
	@Autowired
	InfoData infoData;
	@Autowired
	ObjectMapper mapper;
	
	/**
	 * METER 리스트 정보 저장
	 * @param key
	 * @param data
	 * @throws Exception
	 */
	public void setMeter(String key,String data) throws Exception {
		
		// 테스트 코드
		key = "meter:info";
		
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
		objectData.setObjectData(key, list); // 데이터 set, (Key, Value) 값으로 저장
		
		log.info(key+" => Test OK");
		
	}
	
	/**
	 * METER 리스트 정보 가져오기
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getMeterList(String key) throws Exception{
		
		// 테스트 코드
		key = "meter:info";
		
		// meter 정보 get (list)
		List<MeterVo> meterlist = mapper.readValue(objectData.getObjectStringData(key),new TypeReference<List<MeterVo>>(){}); // 데이터 get, Object를 List<MeterVo> 타입으로
		
//		for(int i=0;meterlist.size()>i;i++) {
//			 System.out.println(meterlist.get(i).getMeterId());
//		};
		
		// Json 정렬
		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meterlist);
		log.info(data);
		
		return data; // Json 타입이 아닌 객체로 넘길 경우 List<MeterVo> 으로 return
	}
	
	/**
	 * MeteringData LoadProfile 저장
	 * @param key
	 * @param data
	 * @throws Exception
	 */
	public void setLpData(String key,String data) throws Exception{
		
		// 테스트 코드
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		Random rnd = new Random();
		
		key = "LoadProfile:"+meterid+":"+fdate; // KEY명 규칙 필요

		LpLoadProfileVo lp = new LpLoadProfileVo();
		
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
		lp.setrAa(String.valueOf(rnd.nextInt(10))); // 역방향 피상전력량
		lp.setrSt("2"); // 결과 (0:대기, 1:전달완료, 2:성공, 101:실패, 102:미지원)
		lp.setcDt(baseFormat.format(date)); // Date
		
		objectData.setObjectData(key, lp); // int 0 값 : 성공, 그외 실패
		infoData.setExpireKey(key, 86400); // 키값 expire 설정 * 필수 * => 7일 : 604,800 초 , 1일 : 86400 초
		
		log.info(key+" => Test OK");
	}
	
	/**
	 * MeteringData LoadProfile 가져오기
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getLpData(String key) throws Exception{
		
		// 테스트 코드
		String meterid = "11190000999";
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		key = "LoadProfile:"+meterid+":"+fdate;
		
		LpLoadProfileVo loadProfileData = mapper.readValue(objectData.getObjectStringData(key),LpLoadProfileVo.class);

		// Json 정렬
		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(loadProfileData);
		log.info(data);
				
		return data;
	}
	
	/**
	 * 정전/복전 데이터 저장
	 * @param key
	 * @param data
	 * @throws Exception
	 */
	public void setLpBlackout(String key,String data) throws Exception{
		
		// 테스트 코드
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		Random rnd = new Random();
		
		key = "BlackOut:"+meterid+":"+fdate; // KEY명 규칙 필요
		
		LpBlackoutVo lpBlackoutVo = new LpBlackoutVo();
		
		lpBlackoutVo.setaId("agent_cnu_13493");
		lpBlackoutVo.setcDt(fdate);
		lpBlackoutVo.setmId(meterid);
		lpBlackoutVo.setoCd("111");
		lpBlackoutVo.setrSt("222");
		
		List<LpBlackoutRviVo> list = new ArrayList<LpBlackoutRviVo>();
		LpBlackoutRviVo lpBlackoutRviVo = new LpBlackoutRviVo();
		
		for(int i=0;i<10;i++) {
			lpBlackoutRviVo = new LpBlackoutRviVo();
			lpBlackoutRviVo.setbCt(String.valueOf(rnd.nextInt(100)));
			lpBlackoutRviVo.setbDt(fdate);
			list.add(lpBlackoutRviVo);
		}
		
		lpBlackoutVo.setrVl(list);
		
		objectData.setObjectData(key, lpBlackoutVo);
		
		log.info(key+" => Test OK");
		
	}
	
	/**
	 * 정전/복전 데이터 가져오기
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getLpBlackout(String key) throws Exception{
		
		// 테스트 코드
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		
		key = "BlackOut:"+meterid+":"+fdate; // KEY명 규칙 필요
		
		LpBlackoutVo blackOutData = mapper.readValue(objectData.getObjectStringData(key),LpBlackoutVo.class);

		String aId = blackOutData.getaId();
		for(int i =0;blackOutData.getrVl().size()>i;i++){
			blackOutData.getrVl().get(i).getbCt();
			blackOutData.getrVl().get(i).getbDt();
		}
		
		// Json 정렬
		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(blackOutData);
		log.info(data);
				
		return data;
	}
	
}
