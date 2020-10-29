package com.cnu.lwm2m.client.init;

import java.io.File;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.cnu.lwm2m.client.models.KepcoServer;
import com.cnu.lwm2m.client.models.KepcoSoftware;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource("classpath:lwm2m.properties")
public class CNULwm2mClient extends AbsCNUModelSettings implements DisposableBean {
	private final String[] KEPCO_MODELS = { "LWM2M_Server_1_Custom.xml", "LWM2M_Device_3_Custom.xml",
			"LWM2M_ConnectivityMonitoring_4_Custom.xml", "LWM2M_Location_6_Custom.xml",
			"LWM2M_ConnectivityStatistics_7_Custom.xml", "26241.xml", "26243.xml", "26245.xml", "26247.xml",
			"26249.xml" };

	@Autowired
	public ObjectExcuteTask task;

	@Autowired
	ResourceLoader resourceLoader;

	private LeshanClient client;

	@Value("${lwm2m.server.ip:leshan.eclipseprojects.io}")
	private String serverIp;

	@Value("${lwm2m.server.port:5683}")
	private String serverPort;

	public void run() {
		// TODO: endpoint는 DCU_ID가 적정해보인다... 조율필요
		String endpoint = "CNU0492010001";
		LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
		List<ObjectModel> models = null;
		models = ObjectLoader.loadDefault();
		models.addAll(ObjectLoader.loadDdfResources("/models", KEPCO_MODELS));

		// Initialize model
		ObjectsInitializer init = new ObjectsInitializer(new StaticModel(models));
		init.setInstancesForObject(LwM2mID.SECURITY, CNUSecurity.kepcoKcmvp("coap://" + serverIp + ":" + serverPort, this));
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
		init.setInstancesForObject(LwM2mID.KEPCO_SERVER, new KepcoServer(this, task));
		init.setInstancesForObject(LwM2mID.KEPCO_SOFTWARE, new KepcoSoftware(this, task));

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
