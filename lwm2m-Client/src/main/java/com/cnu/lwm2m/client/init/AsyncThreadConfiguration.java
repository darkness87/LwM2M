package com.cnu.lwm2m.client.init;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@Slf4j
public class AsyncThreadConfiguration {
	@Bean(name="asyncServerDisableExecutor")
	public Executor asyncServerDisableThreadTaskExecutor() {
		log.debug("Initialized AsyncThreadConfiguration ObjectServerDisable");
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(10);
		threadPoolTaskExecutor.setThreadNamePrefix("Disable-Execute-#");
		threadPoolTaskExecutor.initialize();

		return threadPoolTaskExecutor;
	}
}