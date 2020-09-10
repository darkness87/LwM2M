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
import com.cnu.lwm2m.client.models.impl.AccessControlInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CNUAccessControl extends BaseInstanceEnabler {

	ObjectExcuteTask objectTask;
	private final static List<Integer> supportedResources = Arrays.asList(0, 1, 2, 3);

	private int objectID;
	private int objectInstanceID;
	private long accessControl;
	private long accessControlOwner;

	AccessControlInfo access;

	public CNUAccessControl() {
		// should only be used at bootstrap time
	}

	public CNUAccessControl(AccessControlInfo access, ObjectExcuteTask task) {
		// should only be used at bootstrap time
		this.access = access;
		objectID = access.getObjectID();
		objectInstanceID = access.getObjectInstanceID();
		accessControl = access.getAccessControl();
		accessControlOwner = access.getAccessControlOwner();
		this.objectTask = task;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, objectID);

		case 1:
			return ReadResponse.success(resourceid, objectInstanceID);

		case 2:
			return ReadResponse.success(resourceid, accessControl);

		case 3:
			return ReadResponse.success(resourceid, accessControlOwner);

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
		case 2:
			accessControl = (long) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 3:
			accessControlOwner = (long) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		log.debug("Execute on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);

		log.info("Object Execute is not supported");
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
