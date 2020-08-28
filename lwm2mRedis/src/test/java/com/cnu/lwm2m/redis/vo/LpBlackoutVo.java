package com.cnu.lwm2m.redis.vo;

import java.util.ArrayList;
import java.util.List;

public class LpBlackoutVo {

	private String aId; // Agent ID Agent의 고유 ID
	private String mId; // Meter ID 계기 ID
	private String oCd; // Obis Code OBIS Code
	private String cDt; // Captured Date 검침 일시
	private String rSt; // Requested Work Result 결과 (0:대기, 1:전달완료, 2:성공, 101:무응답, 102:미지원)

	List<LpBlackoutRviVo> rVl = new ArrayList<LpBlackoutRviVo>(); // Object / Array Requested Value Info 검침 결과에 대한 상세내용

	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getoCd() {
		return oCd;
	}

	public void setoCd(String oCd) {
		this.oCd = oCd;
	}

	public String getcDt() {
		return cDt;
	}

	public void setcDt(String cDt) {
		this.cDt = cDt;
	}

	public String getrSt() {
		return rSt;
	}

	public void setrSt(String rSt) {
		this.rSt = rSt;
	}

	public List<LpBlackoutRviVo> getrVl() {
		return rVl;
	}

	public void setrVl(List<LpBlackoutRviVo> rVl) {
		this.rVl = rVl;
	}
	
	
}
