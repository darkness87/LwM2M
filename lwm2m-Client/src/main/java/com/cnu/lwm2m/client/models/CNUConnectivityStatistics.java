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
import com.cnu.lwm2m.client.models.impl.oma.ConnectivityStatisticsInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CNUConnectivityStatistics extends BaseInstanceEnabler {

	private static final List<Integer> supportedResources = Arrays.asList(2, 3, 4, 5, 6, 7, 8);

	ConnectivityStatisticsInfo connectivityStatistics;
	private long collectionPeriod;

	public CNUConnectivityStatistics() {
		// should never be used
	}

	public CNUConnectivityStatistics(ConnectivityStatisticsInfo connectivityStatistics, ObjectExcuteTask task) {
		this.collectionPeriod = connectivityStatistics.getCollectionPeriod();
		this.connectivityStatistics = connectivityStatistics;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 2:
			return ReadResponse.success(resourceid, connectivityStatistics.getTxData());

		case 3:
			return ReadResponse.success(resourceid, connectivityStatistics.getRxData());

		case 4:
			return ReadResponse.success(resourceid, connectivityStatistics.getMaxMessageSize());

		case 5:
			return ReadResponse.success(resourceid, connectivityStatistics.getAverageMessageSize());

		case 8:
			return ReadResponse.success(resourceid, collectionPeriod);

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		switch (resourceid) {
		case 8:
			collectionPeriod = (long) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();
		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		if (resourceid == 6) {
			// TODO: 어떻게 실행하지?ㅋㅋㅋ
			return super.execute(identity, resourceid, params);
		} else if (resourceid == 7) {
			// TODO: 어떻게 실행하지?ㅋㅋㅋ
			return super.execute(identity, resourceid, params);
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
