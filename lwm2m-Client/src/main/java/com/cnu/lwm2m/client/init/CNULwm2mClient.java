package com.cnu.lwm2m.client.init;

import java.io.File;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.LwM2mId;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.CNUAccessControl;
import com.cnu.lwm2m.client.models.CNUConnectivityMonitoring;
import com.cnu.lwm2m.client.models.CNUConnectivityStatistics;
import com.cnu.lwm2m.client.models.CNUDevice;
import com.cnu.lwm2m.client.models.CNUFirmwareUpdate;
import com.cnu.lwm2m.client.models.CNULocation;
import com.cnu.lwm2m.client.models.CNUSecurity;
import com.cnu.lwm2m.client.models.CNUServer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CNULwm2mClient extends AbsCNUModelSettings implements DisposableBean {

	@Autowired
	public ObjectExcuteTask task;

	private LeshanClient client;

	public void run() {
		// TODO: endpoint는 DCU_ID가 적정해보인다... 조율필요
		String endpoint = "CNU0492010001";
		LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);

		ObjectsInitializer init = new ObjectsInitializer();
		init.setInstancesForObject(LwM2mId.SECURITY, CNUSecurity.kepcoKcmvp("coap://leshan.eclipseprojects.io:5683", this));
		init.setInstancesForObject(LwM2mId.SERVER, new CNUServer(this, task));
		init.setInstancesForObject(LwM2mId.DEVICE, new CNUDevice(this, task));
		init.setInstancesForObject(LwM2mId.ACCESS_CONTROL, new CNUAccessControl(this, task));
		init.setInstancesForObject(LwM2mId.CONNECTIVITY_MONITORING, new CNUConnectivityMonitoring(this, task));
		init.setInstancesForObject(LwM2mId.FIRMWARE, new CNUFirmwareUpdate(this, task));
		init.setInstancesForObject(LwM2mId.LOCATION, new CNULocation(this, task));
		init.setInstancesForObject(LwM2mId.CONNECTIVITY_STATISTICS, new CNUConnectivityStatistics(this, task));

		builder.setObjects(init.createAll());
		builder.setCoapConfig(createConfig());	// Create CoAP Config
		client = builder.build();
		client.start();
	}

	private NetworkConfig createConfig() {
		NetworkConfig coapConfig;
		File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
;
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
