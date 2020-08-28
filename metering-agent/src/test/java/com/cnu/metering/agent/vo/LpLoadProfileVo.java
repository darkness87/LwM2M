package com.cnu.metering.agent.vo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.13
 */
public class LpLoadProfileVo {

	private String aid; // Agent ID Agent의 고유 ID
	private String mid; // Meter ID 계기 ID
	private String cDv; // Capture Division 검침 구분	(0: 정규, 1: 재검침, 2: 자율검침)
	private String bDt; // Base Date 수집기준일시
	private String fPa; // Forward Active power Amount 순방향 유효전력량
	private String fGa; // Forward ground reactive power Amount 순방향 지상무효전력량
	private String fRa; // Forward true reactive power Amount 순방향 진상무효전력량
	private String fAa; // Forward Apparent power Amount 순방향 피상전력량
	private String mDt; // Meter Date 검침일시 (계기기록시간)
	private String mSt; // Meter Status	Information 계기 상태정보
	private String rPa; // Reverse active power	Amount 역방향 유효전력량
	private String rRa; // Reverse true reactive power Amount 역방향 진상무효전력량
	private String rGa; // Reverse ground reactive power Amount 역방향 지상무효전력량
	private String rAa; // Reverse Apparent power Amount 역방향 피상전력량
	private String rSt; // Requested Work Result 결과 (0:대기, 1:전달완료, 2:성공, 101:무응답, 102:미지원)
	private String cDt; // Captured Date 작업수행일시
	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getcDv() {
		return cDv;
	}
	public void setcDv(String cDv) {
		this.cDv = cDv;
	}
	public String getbDt() {
		return bDt;
	}
	public void setbDt(String bDt) {
		this.bDt = bDt;
	}
	public String getfPa() {
		return fPa;
	}
	public void setfPa(String fPa) {
		this.fPa = fPa;
	}
	public String getfGa() {
		return fGa;
	}
	public void setfGa(String fGa) {
		this.fGa = fGa;
	}
	public String getfRa() {
		return fRa;
	}
	public void setfRa(String fRa) {
		this.fRa = fRa;
	}
	public String getfAa() {
		return fAa;
	}
	public void setfAa(String fAa) {
		this.fAa = fAa;
	}
	public String getmDt() {
		return mDt;
	}
	public void setmDt(String mDt) {
		this.mDt = mDt;
	}
	public String getmSt() {
		return mSt;
	}
	public void setmSt(String mSt) {
		this.mSt = mSt;
	}
	public String getrPa() {
		return rPa;
	}
	public void setrPa(String rPa) {
		this.rPa = rPa;
	}
	public String getrRa() {
		return rRa;
	}
	public void setrRa(String rRa) {
		this.rRa = rRa;
	}
	public String getrGa() {
		return rGa;
	}
	public void setrGa(String rGa) {
		this.rGa = rGa;
	}
	public String getrAa() {
		return rAa;
	}
	public void setrAa(String rAa) {
		this.rAa = rAa;
	}
	public String getrSt() {
		return rSt;
	}
	public void setrSt(String rSt) {
		this.rSt = rSt;
	}
	public String getcDt() {
		return cDt;
	}
	public void setcDt(String cDt) {
		this.cDt = cDt;
	}
	
}
