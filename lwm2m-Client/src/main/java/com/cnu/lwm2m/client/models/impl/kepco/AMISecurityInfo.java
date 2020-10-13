package com.cnu.lwm2m.client.models.impl.kepco;

import java.util.Date;

public interface AMISecurityInfo {
	public int getEncryptModuleCompany();

	public String getEncryptModuleModelNumber();

	public String getEncryptModuleSerialNumber();

	public int getEncryptModuleType();

	public String getEncryptModuleVersion();

	public int getSecurityModuleCompatible();

	public byte[] getSecurityModuleTestVector();

	public byte[] getSecurityModuleCompatibleResult();

	public int getFWImageIntegrityCheckPeriod();

	public int getIntegrityCheckStatus();

	public byte[] getIntegrityCheckToken();

	public byte[] getFWImageIntegrityCheckResult();

	public byte[] getFWImageIntegrityCheckKey();

	public boolean isSecureBootSuccess();

	public Date getSecureBootSuccessDate();
}