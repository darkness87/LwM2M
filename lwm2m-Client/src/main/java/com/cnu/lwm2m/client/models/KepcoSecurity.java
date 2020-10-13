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
import com.cnu.lwm2m.client.models.impl.kepco.AMISecurityInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KepcoSecurity extends BaseInstanceEnabler {

	ObjectExcuteTask objectTask;
	private final static List<Integer> supportedResources = Arrays.asList(0, 1, 2, 3, 5, 10, 11, 12, 13, 14, 20, 21, 22, 25, 26, 27, 28, 30, 31);

	private int encryptModuleCompany;
	private String encryptModuleModelNumber;
	private String encryptModuleSerialNumber;
	private int encryptModuleType;
	private String encryptModuleVersion;
	private int securityModuleCompatible;
	private byte[] securityModuleTestVector;
	private byte[] securityModuleCompatibleResult;
	private int FWImageIntegrityCheckPeriod;
	private int integrityCheckStatus;
	private byte[] integrityCheckToken;
	private byte[] FWImageIntegrityCheckResult;
	private byte[] FWImageIntegrityCheckKey;
	private boolean secureBootSuccess;
	private Date secureBootSuccessDate;

	AMISecurityInfo security;

	public KepcoSecurity() {
		// should only be used at bootstrap time
	}

	public KepcoSecurity(AMISecurityInfo security, ObjectExcuteTask task) {
		// should only be used at bootstrap time
		this.security = security;
		this.encryptModuleCompany = security.getEncryptModuleCompany();
		this.encryptModuleModelNumber = security.getEncryptModuleModelNumber();
		this.encryptModuleSerialNumber = security.getEncryptModuleSerialNumber();
		this.encryptModuleType = security.getEncryptModuleType();
		this.encryptModuleVersion = security.getEncryptModuleVersion();
		this.securityModuleCompatible = security.getSecurityModuleCompatible();
		this.securityModuleTestVector = security.getSecurityModuleTestVector();
		this.securityModuleCompatibleResult = security.getSecurityModuleCompatibleResult();
		this.FWImageIntegrityCheckPeriod = security.getFWImageIntegrityCheckPeriod();
		this.integrityCheckStatus = security.getIntegrityCheckStatus();
		this.integrityCheckToken = security.getIntegrityCheckToken();
		this.FWImageIntegrityCheckResult = security.getFWImageIntegrityCheckResult();
		this.FWImageIntegrityCheckKey = security.getFWImageIntegrityCheckKey();
		this.secureBootSuccess = security.isSecureBootSuccess();
		this.secureBootSuccessDate = security.getSecureBootSuccessDate();
		this.objectTask = task;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, encryptModuleCompany);

		case 1:
			log.debug("resourceid : 1  !!!!");
			return ReadResponse.success(resourceid, encryptModuleModelNumber);

		case 2:
			return ReadResponse.success(resourceid, encryptModuleSerialNumber);

		case 3:
			return ReadResponse.success(resourceid, encryptModuleType);

		case 5:
			return ReadResponse.success(resourceid, encryptModuleVersion);

		case 10:
			return ReadResponse.success(resourceid, securityModuleCompatible);

		case 11:
			return ReadResponse.success(resourceid, securityModuleTestVector);

		case 14:
			return ReadResponse.success(resourceid, securityModuleCompatibleResult);

		case 20:
			return ReadResponse.success(resourceid, FWImageIntegrityCheckPeriod);

		case 21:
			return ReadResponse.success(resourceid, integrityCheckStatus);

		case 22:
			return ReadResponse.success(resourceid, integrityCheckToken);

		case 27:
			return ReadResponse.success(resourceid, FWImageIntegrityCheckResult);

		case 28:
			return ReadResponse.success(resourceid, FWImageIntegrityCheckKey);

		case 30:
			return ReadResponse.success(resourceid, secureBootSuccess);

		case 31:
			return ReadResponse.success(resourceid, secureBootSuccessDate);

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
		case 10:
			securityModuleCompatible = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 11:
			securityModuleTestVector = (byte[]) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 20:
			FWImageIntegrityCheckPeriod = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 21:
			integrityCheckStatus = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 22:
			integrityCheckToken = (byte[]) value.getValue();
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
		} else if (resourceid == 13) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 25) {
			return ExecuteResponse.internalServerError("구현체가 없습니다.");
		} else if (resourceid == 26) {
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
