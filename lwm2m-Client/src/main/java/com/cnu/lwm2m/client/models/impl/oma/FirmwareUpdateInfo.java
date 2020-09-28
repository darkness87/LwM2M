package com.cnu.lwm2m.client.models.impl.oma;

public interface FirmwareUpdateInfo {
	public byte[] getFirmwarePackage();

	public String getFirmwarePackageURI();

	public int getFirmwareUpdateState();

	public int getFirmwareUpdateResult();

	public String getFirmwarePackageName();

	public String getFirmwarePackageVersion();

	public int getFirmwareUpdateProtocolSupport();

	public int getFirmwareUpdateDeliveryMethod();
}