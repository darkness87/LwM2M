package com.cnu.lwm2m.client.models.impl.oma;

import java.util.Date;

public interface DeviceInfo {
	public String getManufacturer();
	public String getModelNumber();
	public String getSerialNumber();
	public String getFirmwareVersion();
	public int getAvailablePowerSources();
	public int getPowerSourceVoltage();
	public String getPowerSourceCurrent();
	public int getMemoryFree();
	public int getErrorCode();
	public void setCurrentTime(Date date);
	public Date getCurrentTime();
	public String getTimezone();
	public int getDeviceType();
	public String getHardwareVersion();
	public int getMemoryTotal();
}
