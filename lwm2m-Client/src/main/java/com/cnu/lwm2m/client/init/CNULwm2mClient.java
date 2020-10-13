package com.cnu.lwm2m.client.init;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.StaticModel;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.cnu.lwm2m.client.consts.LwM2mID;
import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.CNUAccessControl;
import com.cnu.lwm2m.client.models.CNUConnectivityMonitoring;
import com.cnu.lwm2m.client.models.CNUConnectivityStatistics;
import com.cnu.lwm2m.client.models.CNUDevice;
import com.cnu.lwm2m.client.models.CNUFirmwareUpdate;
import com.cnu.lwm2m.client.models.CNULocation;
import com.cnu.lwm2m.client.models.CNUSecurity;
import com.cnu.lwm2m.client.models.CNUServer;
import com.cnu.lwm2m.client.models.KepcoCommonControl;
import com.cnu.lwm2m.client.models.KepcoNetwork;
import com.cnu.lwm2m.client.models.KepcoSecurity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CNULwm2mClient extends AbsCNUModelSettings implements DisposableBean {

	@Autowired
	public ObjectExcuteTask task;

	@Autowired
	ResourceLoader resourceLoader;

	private LeshanClient client;

	public void run() {
		// TODO: endpoint는 DCU_ID가 적정해보인다... 조율필요
		String endpoint = "CNU0492010001";
		LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
		List<ObjectModel> models = null;

		Resource resource = resourceLoader.getResource("classpath:models");
		try {
			File directory = resource.getFile();
			log.debug(directory.getAbsolutePath());
			log.debug("is directory : {}", directory.isDirectory());

			if (directory.isDirectory()) {
				for (String fileName : directory.list()) {
					log.debug(fileName);
				}

				models = ObjectLoader.loadDefault();
				models.addAll(ObjectLoader.loadDdfResources("/models", directory.list()));
			} else {
				log.error("is directory : {}", directory.isDirectory());
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		// Initialize model
		ObjectsInitializer init = new ObjectsInitializer(new StaticModel(models));
		init.setInstancesForObject(LwM2mID.SECURITY, CNUSecurity.kepcoKcmvp("coap://leshan.eclipseprojects.io:5683", this));
//		init.setInstancesForObject(LwM2mID.SECURITY, CNUSecurity.kepcoKcmvp("coap://localhost:5683", this));
		init.setInstancesForObject(LwM2mID.SERVER, new CNUServer(this, task));
		init.setInstancesForObject(LwM2mID.DEVICE, new CNUDevice(this, task));
		init.setInstancesForObject(LwM2mID.ACCESS_CONTROL, new CNUAccessControl(this, task));
		init.setInstancesForObject(LwM2mID.CONNECTIVITY_MONITORING, new CNUConnectivityMonitoring(this, task));
		init.setInstancesForObject(LwM2mID.FIRMWARE, new CNUFirmwareUpdate(this, task));
		init.setInstancesForObject(LwM2mID.LOCATION, new CNULocation(this, task));
		init.setInstancesForObject(LwM2mID.CONNECTIVITY_STATISTICS, new CNUConnectivityStatistics(this, task));
		init.setInstancesForObject(LwM2mID.KEPCO_COMMON_CONTROL, new KepcoCommonControl(this, task));
		init.setInstancesForObject(LwM2mID.KEPCO_NETWORK, new KepcoNetwork(this, task));
		init.setInstancesForObject(LwM2mID.KEPCO_SECURITY, new KepcoSecurity(this, task));

		builder.setObjects(init.createAll());
		builder.setCoapConfig(createConfig());	// Create CoAP Config
		client = builder.build();
		client.start();
	}

	private NetworkConfig createConfig() {
		NetworkConfig coapConfig;
		File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);

		if (configFile.isFile()) {
			coapConfig = new NetworkConfig();
			coapConfig.load(configFile);
		} else {
			coapConfig = LeshanClientBuilder.createDefaultNetworkConfig();
			coapConfig.store(configFile);
		}

		return coapConfig;
	}

	@Override
	public void destroy() throws Exception {
		log.info("destroy!!!");

		if (client != null) {
			client.stop(true);
			client.destroy(false);
			log.debug("LwM2M client destroyed!!!");
		}
	}
}
