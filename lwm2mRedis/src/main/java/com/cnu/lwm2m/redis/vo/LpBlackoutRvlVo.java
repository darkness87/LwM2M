package com.cnu.lwm2m.redis.vo;

public class LpBlackoutRvlVo {

	private String bDt; // Base Date 수집기준일시
	private String bCt; // Blackout Count 정복전 횟수
	
	public String getbDt() {
		return bDt;
	}
	public void setbDt(String bDt) {
		this.bDt = bDt;
	}
	public String getbCt() {
		return bCt;
	}
	public void setbCt(String bCt) {
		this.bCt = bCt;
	}
	
}
