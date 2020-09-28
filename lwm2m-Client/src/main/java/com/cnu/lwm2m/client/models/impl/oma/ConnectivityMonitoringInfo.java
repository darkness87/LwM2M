package com.cnu.lwm2m.client.models.impl.oma;

public interface ConnectivityMonitoringInfo {
	public int getRadioSignalStrength();
	public int getLinkQuality();
	public String getIpAddress();
	public String getRouterIpAddress();
	public int getCellID();
	public int getSMNC();
	public int getSMCC();
	public int getSignalSNR();
}
