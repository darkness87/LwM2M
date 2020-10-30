package com.cnu.lwm2m.server.scheduler;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class batch {

	@SuppressWarnings("restriction")
	@Scheduled(fixedDelay = 10000)
	public void task() {
		// JVM memory
		float gb = 1024*1024*1024;
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long heapUseSize = heapSize-heapFreeSize;
		log.info("========== JVM 사용량(GB) = Used:{},Free:{},Total:{},Max:{}",String.format("%.3f", (double)heapUseSize/gb),String.format("%.3f", (double)heapFreeSize/gb),String.format("%.3f", (double)heapSize/gb),String.format("%.3f", (double)heapMaxSize/gb));
		
		// OS
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		log.info("========== CPU Usage : " + String.format("%.3f", osBean.getSystemCpuLoad() * 100));
		log.info("========== Memory Free Space : " + String.format("%.3f", (double)osBean.getFreePhysicalMemorySize()/1024/1024/1024  ));
		log.info("========== Memory Total Space : " + String.format("%.3f", (double)osBean.getTotalPhysicalMemorySize()/1024/1024/1024  ));

	}
}
