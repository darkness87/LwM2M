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
import com.cnu.lwm2m.client.models.impl.ConnectivityMonitoringInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CNUConnectivityMonitoring extends BaseInstanceEnabler {

	private static final List<Integer> supportedResources = Arrays.asList(0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16);

	ConnectivityMonitoringInfo connectivityMonitoring;
	private int SMCC;

	public CNUConnectivityMonitoring() {
		// should never be used
	}

	public CNUConnectivityMonitoring(ConnectivityMonitoringInfo connectivityMonitoring, ObjectExcuteTask task) {
		this.SMCC = connectivityMonitoring.getSMCC();
		this.connectivityMonitoring = connectivityMonitoring;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 2:
			return ReadResponse.success(resourceid, connectivityMonitoring.getRadioSignalStrength());

		case 3:
			return ReadResponse.success(resourceid, connectivityMonitoring.getLinkQuality());

		case 4:
			return ReadResponse.success(resourceid, connectivityMonitoring.getIpAddress());

		case 5:
			return ReadResponse.success(resourceid, connectivityMonitoring.getRouterIpAddress());

		case 8:
			return ReadResponse.success(resourceid, connectivityMonitoring.getCellID());

		case 9:
			return ReadResponse.success(resourceid, connectivityMonitoring.getSMNC());

		case 10:
			return ReadResponse.success(resourceid, SMCC);

		case 11:
			return ReadResponse.success(resourceid, connectivityMonitoring.getSignalSNR());

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		switch (resourceid) {
		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
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
