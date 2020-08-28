package com.cnu.metering.agent.vo;

import java.util.ArrayList;
import java.util.List;

public class LpMeteringNormalVo {

	private String rId; // Request ID 요청 ID
	private String aId; // Agent ID Agent의 고유 ID
	private String mId; // Meter ID 계기 ID
	private String mTp; // Meter Type 계기 타입
	private String aTP; // Access Type 엑세스 유형 (0: 읽기 / 1: 쓰기)
	private String oCd; // Obis Code OBIS Code
	private String aIx; // Attribute Index 검침 대상 Index는 “1”, 미 대상 Index는 “0” 표시 ex) 0100000000 
	private String rSt; // Requested Work 결과 (0:대기, 1:전달완료, 2:성공, 101:무응답, 102:미지원) 
	private Object rVI; // Object / Array Requested Value Info 검침 결과에 대한 상세내용
	
	List<LpMeteringNormalRviVo> rVl = new ArrayList<LpMeteringNormalRviVo>(); // Object / Array Requested Value Info 검침 결과에 대한 상세내용
	
	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

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

	public String getmTp() {
		return mTp;
	}

	public void setmTp(String mTp) {
		this.mTp = mTp;
	}

	public String getaTP() {
		return aTP;
	}

	public void setaTP(String aTP) {
		this.aTP = aTP;
	}

	public String getoCd() {
		return oCd;
	}

	public void setoCd(String oCd) {
		this.oCd = oCd;
	}

	public String getaIx() {
		return aIx;
	}

	public void setaIx(String aIx) {
		this.aIx = aIx;
	}

	public String getrSt() {
		return rSt;
	}

	public void setrSt(String rSt) {
		this.rSt = rSt;
	}

	public Object getrVI() {
		return rVI;
	}

	public void setrVI(Object rVI) {
		this.rVI = rVI;
	}

	public List<LpMeteringNormalRviVo> getrVl() {
		return rVl;
	}

	public void setrVl(List<LpMeteringNormalRviVo> rVl) {
		this.rVl = rVl;
	}
	
}
