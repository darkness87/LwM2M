package com.cnu.lwm2m.client.init;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.LwM2mId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.model.CNUServer;

@Component
public class CNULwm2mClient extends AbsCNUModelSettings {

	@Autowired
	public ObjectExcuteTask task;

	public void run() {
		// endpoint는 DCU_ID가 적정해보인다...
		String endpoint = "CNU0492010001";
		LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);

		ObjectsInitializer init = new ObjectsInitializer();
		init.setInstancesForObject(LwM2mId.SECURITY, Security.noSec("coap://leshan.eclipseprojects.io:5683", getServerID()));
		init.setInstancesForObject(LwM2mId.SERVER, new CNUServer(this, task));
		init.setInstancesForObject(LwM2mId.DEVICE, new Device("test", "test1", "test2", "U"));

		builder.setObjects(init.createAll());
		LeshanClient client = builder.build();
		client.start();
	}
}
