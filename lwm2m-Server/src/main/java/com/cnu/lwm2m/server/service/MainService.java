package com.cnu.lwm2m.server.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.cnu.lwm2m.server.vo.MainUsageVO;
import com.google.gson.Gson;
import com.sun.management.OperatingSystemMXBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MainService {

	public MainUsageVO getUsage() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		MainUsageVO mainUsageVO = new MainUsageVO();

		// JVM memory
		float gb = 1024 * 1024 * 1024;
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long heapUseSize = heapSize - heapFreeSize;

		// OS
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

		mainUsageVO.setDate(dateFormat.format(date));
		mainUsageVO.setOsCpu(String.format("%.3f", osBean.getSystemCpuLoad() * 100));
		mainUsageVO.setOsMemory(String.format("%.3f",
				((osBean.getTotalPhysicalMemorySize()-osBean.getFreePhysicalMemorySize()) / gb) / (osBean.getTotalPhysicalMemorySize() / gb) * 100));
		mainUsageVO.setJvmUsed(String.format("%.3f", (double) heapUseSize / gb));
		mainUsageVO.setJvmFree(String.format("%.3f", (double) heapFreeSize / gb));
		mainUsageVO.setJvmTotal(String.format("%.3f", (double) heapSize / gb));
		mainUsageVO.setJvmMax(String.format("%.3f", (double) heapMaxSize / gb));

		return mainUsageVO;
	}

	public String getExternalIP() {
		try {
			URL url = new URL("http://myexternalip.com/raw");
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			String externalIP = IOUtils.toString(in, "UTF-8");

			return externalIP;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public String getLocationInfo(String ip) {
		float lat = 0;
		float lng = 0;
		Gson gson = new Gson();
		HashMap<String, Object> resultMap = null;
		try {
			URL url = new URL("http://ip-api.com/json/" + ip);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			int responseCode = con.getResponseCode();
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			if (200 == responseCode) {
				resultMap = gson.fromJson(responseBuffer.toString(), HashMap.class);
				if ("success".equals(resultMap.get("status"))) {
					double latDouble = (double) resultMap.get("lat");
					lat = (float) latDouble;

					double lngDouble = (double) resultMap.get("lon");
					lng = (float) lngDouble;
				} else {
					lat = (float) 0.0;
					lng = (float) 0.0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("{}", resultMap);
		log.info("{},{}", lat, lng);

		return resultMap.toString();
	}
}
