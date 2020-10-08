package com.cnu.lwm2m.client.models.impl.oma;

import java.util.Date;

public interface DeviceInfo {
	public String getManufacturer();
	public String getModelNumber();
	public String getSerialNumber();
	public String getFirmwareVersion();
	public int getAvailablePowerSources();
	public int getPowerSourceVoltage();
	public int getPowerSourceCurrent();
	public int getMemoryFree();
	public int getErrorCode();
	public Date getCurrentTime();
	public String getTimezone();
	public String getDeviceType();
	public String getHardwareVersion();
	public int getMemoryTotal();
}
