package com.cnu.metering.agent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cnu.metering.agent.vo.MeterVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeterService extends AbsService {
	public List<MeterVO> getMeterList() {
		List<MeterVO> meterList = new ArrayList<MeterVO>();

		MeterVO meterVO1 = new MeterVO();
		meterVO1.setMeterId("0421100001");
		meterVO1.setType("G");
		MeterVO meterVO2 = new MeterVO("0421100001", "E");

		meterList.add(meterVO1);
		meterList.add(meterVO2);

		return meterList;
	}

	public void addMeter(String meterId, String type) {
		
	}

	public void setMeter(String meterId, String type) {
		
	}

	public void sendMeter(MeterVO meterVO) {
		log.debug("meterVO: {}", meterVO);
		send("coap://127.0.0.1:15683/cmd", meterVO);
	}
}