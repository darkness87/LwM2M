package com.cnu.lwm2m.client.models.impl.kepco;

import java.util.Date;

public interface AMISoftwareInfo {
	public int getDownloadUpdateStatus();

	public int getCheckDownloadPeriod();

	public Date getDownloadStartDate();

	public Date getDownloadCompleteDate();

	public Date getUpdateCompleteDate();

	public Date getUpdateReservationDate();

	public String getFwFactoryVersion();

	public String getFwLastVersion();

	public String getFwCurrentVersion();

	public String getCommonControlOSName();

	public String getCommonControlOSVersion();

	public String getMwSystem();

	public String getMwVersion();

	public String getSecurityAgentVersion();

	public String getSecurityProxyVersion();

	public String getLwm2mAgentVersion();

	public String getLwm2mProxyVersion();

	public String getDlmsAgentVersion();

	public String getDlmsManagerVersion();
}