package com.cnu.lwm2m.client.init.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.leshan.client.LwM2mClient;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@EnableAsync
@Component
@Slf4j
public class ObjectExcuteTask {

	@Async("asyncServerDisableExecutor")
	public void asyncServerDisableExecutor(LwM2mClient client, int timeout) {
		try {
			client.stop(true);
			sleep("LwM2M Client is Disabled", timeout);
			List<LwM2mObjectEnabler> list = new ArrayList<LwM2mObjectEnabler>(client.getObjectTree().getObjectEnablers().values());
			log.info("Object check : {}ea", list.size());

			for (LwM2mObjectEnabler o : list) {
				log.info("{} / {} / {}", o.getId(), o.getObjectModel().id, o.getObjectModel().urn);
			}

			client.start();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void sleep(String txt, int sec) {
		try {
			long milSec = sec * 1000L;
			log.info("{} sleep : {} sec", txt, sec);
			TimeUnit.MILLISECONDS.sleep(milSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}