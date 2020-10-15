package com.cnu.lwm2m.client.models;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.impl.kepco.AMISoftwareInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KepcoSoftware extends BaseInstanceEnabler {

	ObjectExcuteTask objectTask;
	private final static List<Integer> supportedResources = Arrays.asList(0, 1, 2, 3, 5, 6, 10, 11, 12, 13, 15, 16, 17
																			, 20, 21, 25, 26, 30, 31, 35, 36, 40, 41);
	private Map<Integer, Type> typeMap;

	private AMISoftwareInfo software;

	private int downloadUpdateStatus;
	private int checkDownloadPeriod;
	private Date downloadStartDate;
	private Date downloadCompleteDate;
	private Date updateCompleteDate;
	private Date updateReservationDate;
	private String fwFactoryVersion;
	private String fwCurrentVersion;
	private String commonControlOSName;
	private String commonControlOSVersion;
	private String mwSystem;
	private String mwVersion;
	private String securityAgentVersion;
	private String securityProxyVersion;
	private String lwm2mAgentVersion;
	private String lwm2mProxyVersion;
	private String dlmsAgentVersion;
	private String dlmsManagerVersion;

	public KepcoSoftware() {
		// should only be used at bootstrap time
		typeMap = new HashMap<Integer, Type>();
		typeMap.put(0, Type.INTEGER);
		typeMap.put(1, Type.INTEGER);
		typeMap.put(2, Type.INTEGER);
		typeMap.put(3, Type.INTEGER);
		typeMap.put(5, Type.INTEGER);
		typeMap.put(6, Type.BOOLEAN);
		typeMap.put(7, Type.STRING);
	}

	public KepcoSoftware(AMISoftwareInfo software, ObjectExcuteTask task) {
		this();
		// should only be used at bootstrap time
		this.software = software;
		this.downloadUpdateStatus = software.getDownloadUpdateStatus();
		this.checkDownloadPeriod = software.getCheckDownloadPeriod();
		this.downloadStartDate = software.getDownloadStartDate();
		this.downloadCompleteDate = software.getDownloadCompleteDate();
		this.updateCompleteDate = software.getUpdateCompleteDate();
		this.updateReservationDate = software.getUpdateReservationDate();
		this.fwFactoryVersion = software.getFwFactoryVersion();
		this.fwCurrentVersion = software.getFwCurrentVersion();
		this.commonControlOSName = software.getCommonControlOSName();
		this.commonControlOSVersion = software.getCommonControlOSVersion();
		this.mwSystem = software.getMwSystem();
		this.mwVersion = software.getMwVersion();
		this.securityAgentVersion = software.getSecurityAgentVersion();
		this.securityProxyVersion = software.getSecurityProxyVersion();
		this.lwm2mAgentVersion = software.getLwm2mAgentVersion();
		this.lwm2mProxyVersion = software.getLwm2mProxyVersion();
		this.dlmsAgentVersion = software.getDlmsAgentVersion();
		this.dlmsManagerVersion = software.getDlmsManagerVersion();
		this.objectTask = task;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, downloadUpdateStatus);
		case 3:
			return ReadResponse.success(resourceid, checkDownloadPeriod);
		case 5:
			return ReadResponse.success(resourceid, downloadStartDate);
		case 6:
			return ReadResponse.success(resourceid, downloadCompleteDate);
		case 12:
			return ReadResponse.success(resourceid, updateCompleteDate);
		case 13:
			return ReadResponse.success(resourceid, updateReservationDate);
		case 15:
			return ReadResponse.success(resourceid, fwFactoryVersion);
		case 16:
			return ReadResponse.success(resourceid, software.getFwLastVersion());
		case 17:
			return ReadResponse.success(resourceid, fwCurrentVersion);
		case 20:
			return ReadResponse.success(resourceid, commonControlOSName);
		case 21:
			return ReadResponse.success(resourceid, commonControlOSVersion);
		case 25:
			return ReadResponse.success(resourceid, mwSystem);
		case 26:
			return ReadResponse.success(resourceid, mwVersion);
		case 30:
			return ReadResponse.success(resourceid, securityAgentVersion);
		case 31:
			return ReadResponse.success(resourceid, securityProxyVersion);
		case 35:
			return ReadResponse.success(resourceid, lwm2mAgentVersion);
		case 36:
			return ReadResponse.success(resourceid, lwm2mProxyVersion);
		case 40:
			return ReadResponse.success(resourceid, dlmsAgentVersion);
		case 41:
			return ReadResponse.success(resourceid, dlmsManagerVersion);

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		if (!identity.isSystem()) {
			log.debug("Write on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		if (value.getType() != typeMap.get(resourceid)) {
			return WriteResponse.badRequest("invalid type : " + value.getType());
		}

		switch (resourceid) {
		case 1:
//			long previousLifetime = lifeTime;
//			lifeTime = (Long) value.getValue();

//			if (previousLifetime != lifeTime) {
//				fireResourcesChange(resourceid);
//			}
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 2:
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 3:
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 12:
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 13:
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		log.debug("Execute on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);

		if (resourceid == 10) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 11) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else {
			return super.execute(identity, resourceid, params);
		}
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
