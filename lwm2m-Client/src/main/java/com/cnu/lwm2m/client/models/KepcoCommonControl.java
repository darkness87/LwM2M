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
import com.cnu.lwm2m.client.models.impl.kepco.AMICommonControlInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KepcoCommonControl extends BaseInstanceEnabler {

	ObjectExcuteTask objectTask;
	private final static List<Integer> supportedResources = Arrays.asList(0, 1, 2, 3, 4
																		, 7, 8, 10, 11, 12
																		, 13, 14, 15, 17, 18
																		, 19, 20, 21, 22, 25
																		, 26, 30, 31, 35, 36
																		, 37, 40, 41, 42, 43
																		, 44, 47);

	private String cpuUsageRateObserveNotify;
	private String ramUsageRateObserveNotify;
	private String powerVoltageObserveNotify;
	private String CurrentConsumptionObserveNotify;
	private Date blackout;
	private Date recovery;
	private long selfResetPeriod;
	private long selfResetTime;
	private long rs485DLBPeriod;
	@SuppressWarnings("unused") private String rs485DLBInputData;
	private String rs485DLBOutputData;

	AMICommonControlInfo commonControl;

	public KepcoCommonControl() {
		// should only be used at bootstrap time
	}

	public KepcoCommonControl(AMICommonControlInfo commonControl, ObjectExcuteTask task) {
		// should only be used at bootstrap time
		this.commonControl = commonControl;
		this.cpuUsageRateObserveNotify = commonControl.getCpuUsageRateObserveNotify();
		this.ramUsageRateObserveNotify = commonControl.getRamUsageRateObserveNotify();
		this.powerVoltageObserveNotify = commonControl.getPowerVoltageObserveNotify();
		this.CurrentConsumptionObserveNotify = commonControl.getCurrentConsumptionObserveNotify();
		this.blackout = commonControl.getBlackout();
		this.recovery = commonControl.getRecovery();
		this.selfResetPeriod = commonControl.getSelfResetPeriod();
		this.selfResetTime = commonControl.getSelfResetTime();
		this.rs485DLBPeriod = commonControl.getRs485DLBPeriod();
		this.rs485DLBOutputData = commonControl.getRs485DLBOutputData();
		this.objectTask = task;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, commonControl.getSystemTitle());

		case 1:
			return ReadResponse.success(resourceid, commonControl.getAmiBussinessName());

		case 2:
			return ReadResponse.success(resourceid, commonControl.getAmiModemType());

		case 3:
			return ReadResponse.success(resourceid, commonControl.getAmiDcuType());

		case 4:
			return ReadResponse.success(resourceid, commonControl.getManufacturingDate());

		case 7:
			return ReadResponse.success(resourceid, commonControl.getCpuManufacturer());

		case 8:
			return ReadResponse.success(resourceid, commonControl.getCpuModelNumber());

		case 10:
			return ReadResponse.success(resourceid, commonControl.getCpuUsageRate());

		case 11:
			return ReadResponse.success(resourceid, cpuUsageRateObserveNotify);

		case 13:
			return ReadResponse.success(resourceid, commonControl.getRamUsageRate());

		case 14:
			return ReadResponse.success(resourceid, ramUsageRateObserveNotify);

		case 17:
			return ReadResponse.success(resourceid, commonControl.getPowerVoltage());

		case 18:
			return ReadResponse.success(resourceid, powerVoltageObserveNotify);

		case 20:
			return ReadResponse.success(resourceid, commonControl.getCurrentConsumption());

		case 21:
			return ReadResponse.success(resourceid, CurrentConsumptionObserveNotify);

		case 30:
			return ReadResponse.success(resourceid, blackout);

		case 31:
			return ReadResponse.success(resourceid, recovery);

		case 36:
			return ReadResponse.success(resourceid, selfResetPeriod);

		case 37:
			return ReadResponse.success(resourceid, selfResetTime);

		case 40:
			return ReadResponse.success(resourceid, rs485DLBPeriod);

		case 44:
			return ReadResponse.success(resourceid, rs485DLBOutputData);

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
		case 11:
			cpuUsageRateObserveNotify = (String) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 14:
			ramUsageRateObserveNotify = (String) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 18:
			powerVoltageObserveNotify = (String) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 21:
			CurrentConsumptionObserveNotify = (String) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 36:
			selfResetPeriod = (long) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 37:
			selfResetTime = (long) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 40:
			rs485DLBPeriod = (long) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 43:
			rs485DLBInputData = (String) value.getValue();
			fireResourcesChange(resourceid);
			return WriteResponse.success();

		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		log.debug("Execute on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);

		if (resourceid == 12) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 15) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 19) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 22) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 25) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 26) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 35) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 41) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 42) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 47) {
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
