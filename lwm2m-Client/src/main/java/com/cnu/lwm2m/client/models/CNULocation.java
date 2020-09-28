package com.cnu.lwm2m.client.models;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.impl.oma.LocationInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CNULocation extends BaseInstanceEnabler {

	private static final List<Integer> supportedResources = Arrays.asList(0, 1, 5);

	private float latitude;
	private float longitude;
	private Date timestamp;

	public CNULocation() {
		// should never be used
	}

	public CNULocation(LocationInfo location, ObjectExcuteTask task) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
		this.timestamp = location.getTimestamp();
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, latitude);

		case 1:
			return ReadResponse.success(resourceid, longitude);

		case 5:
			return ReadResponse.success(resourceid, timestamp);

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		switch (resourceid) {
		case 0:
			latitude = (float) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 1:
			longitude = (float) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		if (resourceid == 2) {
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
