package com.cnu.lwm2m.client.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.impl.oma.DeviceInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CNUDevice extends BaseInstanceEnabler {

	private static final List<Integer> supportedResources = Arrays.asList(0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16);

	DeviceInfo device;
	private String manufacturer;
	private String modelNumber;
	private String serialNumber;
	private String firmwareVersion;
	private int availablePowerSources;
	private int powerSourceVoltage;
	String deviceType;

	private String timezone;

	public CNUDevice() {
		// should never be used
	}

	public CNUDevice(DeviceInfo device, ObjectExcuteTask task) {
		this.manufacturer = device.getManufacturer();
		this.modelNumber = device.getModelNumber();
		this.serialNumber = device.getSerialNumber();
		this.firmwareVersion = device.getFirmwareVersion();
		this.availablePowerSources = device.getAvailablePowerSources();
		this.powerSourceVoltage = device.getPowerSourceVoltage();
		this.deviceType = device.getDeviceType();
		this.timezone = device.getTimezone();
		this.device = device;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0: // manufacturer
			return ReadResponse.success(resourceid, manufacturer);

		case 1: // model number
			return ReadResponse.success(resourceid, modelNumber);

		case 2: // serial number
			return ReadResponse.success(resourceid, serialNumber);

		case 3: // firmware version
			return ReadResponse.success(resourceid, firmwareVersion);

		case 6: // Available Power Sources
			return ReadResponse.success(resourceid, availablePowerSources);

		case 7: // Power Source Voltage
			return ReadResponse.success(resourceid, powerSourceVoltage);

		case 8: // Power Source Current
			return ReadResponse.success(resourceid, device.getPowerSourceCurrent());

		case 10: // Memory Free (KB)
			return ReadResponse.success(resourceid, device.getMemoryFree());

		case 11: // error codes
//			return ReadResponse.success(resourceid, new HashMap<Integer, Integer>(), Type.INTEGER);
			Map<Integer, Long> errorCodes = new HashMap<>();
			errorCodes.put(0, (long) device.getErrorCode());
			return ReadResponse.success(resourceid, errorCodes, Type.INTEGER);

		case 13: // Current Time (초)
			return ReadResponse.success(resourceid, device.getCurrentTime());

		case 15: // timezone
			return ReadResponse.success(resourceid, timezone);

		case 17: // Device Type
			return ReadResponse.success(resourceid, deviceType);

		case 18: // Hardware Version
			return ReadResponse.success(resourceid, device.getHardwareVersion());

		case 21: // Memory Total (KB)
			return ReadResponse.success(resourceid, device.getMemoryTotal());

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {

		switch (resourceid) {

		case 13: // Current Time (초)
			WriteResponse.internalServerError("not implements");

		case 15: // timezone
			timezone = (String) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {

		if (resourceid == 4) { // reboot
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 5) { // remote factory reset
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 10) { // memory free
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 12) { // remote factory reset
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else {
			return super.execute(identity, resourceid, params);
		}
	}

	@Override
	public void reset(int resourceid) {
		switch (resourceid) {
		case 15:
			timezone = TimeZone.getDefault().getID();
			break;
		default:
			super.reset(resourceid);
		}
	}

	@Override
	public List<Integer> getAvailableResourceIds(ObjectModel model) {
		return supportedResources;
	}
}
