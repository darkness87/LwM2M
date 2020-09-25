package com.cnu.lwm2m.client.models.impl;

import java.util.Date;

public interface AMICommonControlInfo {
	public byte[] getSystemTitle();

	public String getAmiBussinessName();

	public int getAmiModemType();

	public int getAmiDcuType();

	public String getManufacturingDate();

	public String getCpuManufacturer();

	public String getCpuModelNumber();

	public int getCpuUsageRate();

	public String getCpuUsageRateObserveNotify();
//	public void setCpuUsageRateObserveNotify(String cpuUsageRateObserveNotify);

	public int getRamUsageRate();

	public String getRamUsageRateObserveNotify();
//	public void setRamUsageRateObserveNotify(String ramUsageRateObserveNotify);

	public int getPowerVoltage();

	public String getPowerVoltageObserveNotify();
//	public void setPowerVoltageObserveNotify(int powerVoltageObserveNotify);

	public int getCurrentConsumption();

	public String getCurrentConsumptionObserveNotify();
//	public void setCurrentConsumptionObserveNotify(String currentConsumptionObserveNotify);

	public Date getBlackout();

	public Date getRecovery();

	public int getSelfResetPeriod();
//	public void setSelfResetPeriod(int selfResetPeriod);

	public int getSelfResetTime();
//	public void setSelfResetTime(int selfResetTime);

	public int getRs485DLBPeriod();
//	public void setRs485DLBPeriod(int rs485DLBPeriod);

	public String getRs485DLBInputData();
	public void setRs485DLBInputData(String rs485DLBInputData);

	public String getRs485DLBOutputData();
}