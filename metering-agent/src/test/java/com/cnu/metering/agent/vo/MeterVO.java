package com.cnu.metering.agent.vo;

public class MeterVO {

	private String meterId; // 계량기번호
	private String meterType; // 계량기타입
	private String rDt; // 등록일시
	private String info; // 기타정보
	
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getMeterType() {
		return meterType;
	}
	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}
	public String getrDt() {
		return rDt;
	}
	public void setrDt(String rDt) {
		this.rDt = rDt;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}
