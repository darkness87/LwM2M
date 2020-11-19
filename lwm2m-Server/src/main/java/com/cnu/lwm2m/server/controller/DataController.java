package com.cnu.lwm2m.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.RedisService;
import com.cnu.lwm2m.server.vo.ChartVO;
import com.cnu.lwm2m.server.vo.LpAvgVoltVo;
import com.cnu.lwm2m.server.vo.LpDataVo;
import com.cnu.lwm2m.server.vo.LpMaxDemandVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DataController {

	@Autowired
	RedisService redisService;

	@RequestMapping("/getSearchData.do")
	public @ResponseBody List<ChartVO> getSearchData(String typeData, String dateData) throws Exception {
		// TODO
		log.info("=== getChart  ===");
		String key = typeData + ":" + dateData.replaceAll("-", "");
		log.info("{}", key);

		List<String> val = redisService.getSearchKey(key);
		Collections.sort(val); // 정렬 !!

		List<ChartVO> list = new ArrayList<ChartVO>();
		ChartVO chartVO = new ChartVO();

		for (int i = 0; i < val.size(); i++) {
			log.info("{}", val.get(i));

			chartVO = new ChartVO();
			String[] array = val.get(i).split(":");
			String label = array[2];
			chartVO.setLabel(label);

			int value = 0;

			if (typeData.equals("LPDATA")) {

				List<LpDataVo> data = redisService.hgetRedisHashesAllDataList(val.get(i), LpDataVo.class);

				for (int d = 0; d < data.size(); d++) {
					value = Integer.valueOf(data.get(d).getpA0()) + value;
				}
			} else if (typeData.equals("MAXDEMAND")) {

				List<LpMaxDemandVo> data = redisService.hgetRedisHashesAllDataList(val.get(i), LpMaxDemandVo.class);

				for (int d = 0; d < data.size(); d++) {
					value = Integer.valueOf(data.get(d).getpA0()) + value;
				}
			} else if (typeData.equals("AVGVOLT")) {

				List<LpAvgVoltVo> data = redisService.hgetRedisHashesAllDataList(val.get(i), LpAvgVoltVo.class);

				for (int d = 0; d < data.size(); d++) {
					value = Integer.valueOf(data.get(d).getaAv()) + value;
				}
			}

			chartVO.setValue(value);

			list.add(chartVO);
		}

		log.info("{}", list);

		return list;
	}

}
