package com.cnu.metering.agent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.metering.agent.dao.RedisDao;
import com.cnu.metering.agent.vo.LpBlackoutRviVO;
import com.cnu.metering.agent.vo.LpBlackoutVO;
import com.cnu.metering.agent.vo.LpLoadProfileVO;
import com.cnu.metering.agent.vo.MeterVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis Set / Get 에 대한 Meter데이터 Sample Code
 * 
 * @author sookwon
 * @since 2020.08.26
 */
@Slf4j
@Service
public class RedisSampleService {

	@Autowired
	RedisDao redisDao;
	@Autowired
	ObjectMapper mapper;

	public void setMeterList(String key) throws Exception {
		List<Object> list = new ArrayList<Object>();
		MeterVO meterVO = new MeterVO();

		for (int i = 100; i < 130; i++) {
			meterVO = new MeterVO();
			meterVO.setMeterId("11190000" + i);
			list.add(meterVO);
		}

		redisDao.setRedisData(key, list); // 데이터 set, (Key, Value) 값으로 저장
		MeterVO redisData = redisDao.getRedisData(key,MeterVO.class);
		log.info("{} => Test OK : {}", key, redisData);
	}

	public String getMeterList(String key) throws Exception {
		key = "Meter:Info:List";
		List<MeterVO> redisData = redisDao.getRedisData(key); // 데이터 get, Object를 List<MeterVo> 타입으로

		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(redisData);
		log.info(data); // Json 정렬

		return data;
	}

	public void setLpData(String key, String data) throws Exception {
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		Random rnd = new Random();

		key = "LoadProfile:" + meterid + ":" + fdate; // KEY명 규칙 필요

		LpLoadProfileVO lp = new LpLoadProfileVO();

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

		redisDao.setRedisData(key, lp); // int 0 값 : 성공, 그외 실패
		redisDao.setExpireKey(key, 86400); // 키값 expire 설정 * 필수 * => 7일 : 604,800 초 , 1일 : 86400 초

		log.info(key + " => Test OK");
	}

	public String getLpData(String key) throws Exception {

		String meterid = "11190000999";
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		key = "LoadProfile:" + meterid + ":" + fdate;

		LpLoadProfileVO redisData = redisDao.getRedisData(key,LpLoadProfileVO.class);

		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(redisData);
		log.info(data); // Json 정렬

		return data;
	}

	public void setLpBlackout(String key, String data) throws Exception {

		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		Random rnd = new Random();

		key = "BlackOut:" + meterid + ":" + fdate; // KEY명 규칙 필요

		LpBlackoutVO lpBlackoutVo = new LpBlackoutVO();

		lpBlackoutVo.setaId("agent_cnu_13493");
		lpBlackoutVo.setcDt(fdate);
		lpBlackoutVo.setmId(meterid);
		lpBlackoutVo.setoCd("111");
		lpBlackoutVo.setrSt("222");

		List<LpBlackoutRviVO> list = new ArrayList<LpBlackoutRviVO>();
		LpBlackoutRviVO lpBlackoutRviVO = new LpBlackoutRviVO();

		for (int i = 0; i < 10; i++) {
			lpBlackoutRviVO = new LpBlackoutRviVO();
			lpBlackoutRviVO.setbCt(String.valueOf(rnd.nextInt(100)));
			lpBlackoutRviVO.setbDt(fdate);
			list.add(lpBlackoutRviVO);
		}

		lpBlackoutVo.setrVl(list);

		redisDao.setRedisData(key, lpBlackoutVo);

		log.info(key + " => Test OK");

	}

	public String getLpBlackout(String key) throws Exception {

		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";

		key = "BlackOut:" + meterid + ":" + fdate; // KEY명 규칙 필요

		LpBlackoutVO blackOutData = redisDao.getRedisData(key,LpBlackoutVO.class);

		for (int i = 0; blackOutData.getrVl().size() > i; i++) {
			blackOutData.getrVl().get(i).getbCt();
			blackOutData.getrVl().get(i).getbDt();
		}

		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(blackOutData);
		log.info(data);

		return data;
	}

}
