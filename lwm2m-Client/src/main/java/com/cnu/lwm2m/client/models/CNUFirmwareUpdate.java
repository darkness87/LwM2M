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
import com.cnu.lwm2m.client.models.impl.FirmwareUpdateInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CNUFirmwareUpdate extends BaseInstanceEnabler {

	private static final List<Integer> supportedResources = Arrays.asList(0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16);

	FirmwareUpdateInfo firmwareUpdate;
	private byte[] firmwarePackage;
	private String firmwarePackageURI;
	private String firmwarePackageName;
	private String firmwarePackageVersion;

	public CNUFirmwareUpdate() {
		// should never be used
	}

	public CNUFirmwareUpdate(FirmwareUpdateInfo firmwareUpdate, ObjectExcuteTask task) {
		this.firmwarePackageURI = firmwareUpdate.getFirmwarePackageURI();
		this.firmwarePackageName = firmwareUpdate.getFirmwarePackageName();
		this.firmwarePackageVersion = firmwareUpdate.getFirmwarePackageVersion();
		this.firmwareUpdate = firmwareUpdate;
	}

	public byte[] getFirmwarePacakge() {
		return firmwarePackage;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 1:
			return ReadResponse.success(resourceid, firmwarePackageURI);

		case 3:
			return ReadResponse.success(resourceid, firmwareUpdate.getFirmwareUpdateState());

		case 5:
			return ReadResponse.success(resourceid, firmwareUpdate.getFirmwareUpdateResult());

		case 6:
			return ReadResponse.success(resourceid, firmwarePackageName);

		case 7:
			return ReadResponse.success(resourceid, firmwarePackageVersion);

		case 8:
			return ReadResponse.success(resourceid, firmwareUpdate.getFirmwareUpdateProtocolSupport());

		case 9:
			return ReadResponse.success(resourceid, firmwareUpdate.getFirmwareUpdateDeliveryMethod());

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		switch (resourceid) {
		case 0:
			firmwarePackage = (byte[]) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();
		case 1:
			firmwarePackageURI = (String) value.getValue();
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
