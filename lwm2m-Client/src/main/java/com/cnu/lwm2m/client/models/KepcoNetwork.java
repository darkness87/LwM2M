package com.cnu.lwm2m.client.models;

import java.util.Arrays;
import java.util.List;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.impl.kepco.AMINetworkInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KepcoNetwork extends BaseInstanceEnabler {

	ObjectExcuteTask objectTask;
	private final static List<Integer> supportedResources = Arrays.asList(0, 1, 2, 3, 4, 5, 8, 9, 10, 15, 17, 20, 21);

	private int wanCode;
	private int commTypeCode;
	private int teleCompany;
	private String phoneNumber;
	private String commModuleCompany;
	private String commModuleChipCompany;

	AMINetworkInfo network;

	public KepcoNetwork() {
		// should only be used at bootstrap time
	}

	public KepcoNetwork(AMINetworkInfo network, ObjectExcuteTask task) {
		// should only be used at bootstrap time
		this.network = network;
		this.wanCode = network.getWanCode();
		this.commTypeCode = network.getCommTypeCode();
		this.teleCompany = network.getTeleCompany();
		this.phoneNumber = network.getPhoneNumber();
		this.commModuleCompany = network.getCommModuleCompany();
		this.commModuleChipCompany = network.getCommModuleChipCompany();
		this.objectTask = task;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, wanCode);

		case 1:
			return ReadResponse.success(resourceid, commTypeCode);

		case 2:
			return ReadResponse.success(resourceid, teleCompany);

		case 3:
			return ReadResponse.success(resourceid, phoneNumber);

		case 8:
			return ReadResponse.success(resourceid, commModuleCompany);

		case 9:
			return ReadResponse.success(resourceid, commModuleChipCompany);

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		if (!identity.isSystem()) {
			log.debug("Write on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		log.debug("Execute on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);

		if (resourceid == 15) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 20) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		}

		return super.execute(identity, resourceid, params);
	}

	@Override
	public void reset(int resourceid) {
		switch (resourceid) {
		default:
			super.reset(resourceid);
		}
	}

	@Override
	public List<Integer> getAvailableResourceIds(ObjectModel model) {
		return supportedResources;
	}
}
