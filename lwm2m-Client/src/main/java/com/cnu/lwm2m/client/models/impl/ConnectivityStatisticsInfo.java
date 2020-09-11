package com.cnu.lwm2m.client.models.impl;

public interface ConnectivityStatisticsInfo {
	public int getTxData();
	public int getRxData();
	public int getMaxMessageSize();
	public int getAverageMessageSize();
	public int getCollectionPeriod();
}