package com.cnu.lwm2m.redis.vo;

public class LpAvgVoltVo {

	private String aId; // Agent ID Agent의 고유 ID
	private String mId; // Meter ID 계기 ID
	private String cDv; // Capture Division 검침 구분 (0: 정규, 1: 재검침, 2: 자율검침)
	private String aAv; // A-B Side Average Voltage A-B상 평균전압
	private String aIv; // A Side Inspect Voltage A상 순시전압THD
	private String aAa; // A Side Average Ampere A상 평균전류
	private String bAv; // B-C Side Average Voltage B-C상 평균전압
	private String bIv; // B Side Inspect Voltage B상 순시전압THD
	private String bAa; // B Side Average Ampere B상 평균전류
	private String cAv; // C-A Side Average Voltage C-A상 평균전압
	private String cIv; // C Side Inspect Voltage C상 순시전압THD
	private String cAa; // C Side Average Ampere C상 평균전류
	private String rSt; // Requested Work Result 결과 (0:대기, 1:전달완료, 2:성공, 101:무응답, 102:미지원)
	private String bDt; // Base Date 수집기준일시
	private String cDt; // Captured Date 검침일시 (에이전트 저장시간)
	
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
	public String getcDv() {
		return cDv;
	}
	public void setcDv(String cDv) {
		this.cDv = cDv;
	}
	public String getaAv() {
		return aAv;
	}
	public void setaAv(String aAv) {
		this.aAv = aAv;
	}
	public String getaIv() {
		return aIv;
	}
	public void setaIv(String aIv) {
		this.aIv = aIv;
	}
	public String getaAa() {
		return aAa;
	}
	public void setaAa(String aAa) {
		this.aAa = aAa;
	}
	public String getbAv() {
		return bAv;
	}
	public void setbAv(String bAv) {
		this.bAv = bAv;
	}
	public String getbIv() {
		return bIv;
	}
	public void setbIv(String bIv) {
		this.bIv = bIv;
	}
	public String getbAa() {
		return bAa;
	}
	public void setbAa(String bAa) {
		this.bAa = bAa;
	}
	public String getcAv() {
		return cAv;
	}
	public void setcAv(String cAv) {
		this.cAv = cAv;
	}
	public String getcIv() {
		return cIv;
	}
	public void setcIv(String cIv) {
		this.cIv = cIv;
	}
	public String getcAa() {
		return cAa;
	}
	public void setcAa(String cAa) {
		this.cAa = cAa;
	}
	public String getrSt() {
		return rSt;
	}
	public void setrSt(String rSt) {
		this.rSt = rSt;
	}
	public String getbDt() {
		return bDt;
	}
	public void setbDt(String bDt) {
		this.bDt = bDt;
	}
	public String getcDt() {
		return cDt;
	}
	public void setcDt(String cDt) {
		this.cDt = cDt;
	}
	
}
