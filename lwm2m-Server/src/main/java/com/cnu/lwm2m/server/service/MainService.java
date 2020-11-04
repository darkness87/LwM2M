package com.cnu.lwm2m.server.service;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import com.cnu.lwm2m.server.vo.MainUsageVO;
import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
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
		mainUsageVO.setOsMemory(String.format("%.3f", (osBean.getFreePhysicalMemorySize() / gb) / (osBean.getTotalPhysicalMemorySize() / gb) * 100));
		mainUsageVO.setJvmUsed(String.format("%.3f", (double) heapUseSize / gb));
		mainUsageVO.setJvmFree(String.format("%.3f", (double) heapFreeSize / gb));
		mainUsageVO.setJvmTotal(String.format("%.3f", (double) heapSize / gb));
		mainUsageVO.setJvmMax(String.format("%.3f", (double) heapMaxSize / gb));

		return mainUsageVO;
	}
}
