package com.cnu.lwm2m.redis.vo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.13
 */
public class LpLoadProfileVo {

	private String aid; // Agent ID
	private String mid; // Meter ID
	private String cDv; // 검침구분 (0:정규, 1:재검침, 2:자율검침)
	private String bDt; // Base Date 수집기준일시 // 10자리 숫자 또는 Date형식?
	private String fPa; // 순방향 유효전력량
	private String fGa; // 순방향 지상무효전력량
	private String fRa; // 순방향 진상무효전력량
	private String fAa; // 순방향 피상전력량
	private String mDt; // Date // 검침일시 (계기기록시간)
	private String mSt; // 계기 상태정보
	private String rPa; // 역방향 유효전력량
	private String rRa; // 역방향 진상무효전력량
	private String rGa; // 역방향 지상무효전력량
	private String rSt; // 결과 (0:대기, 1:전달완료, 2:성공, 101:실패, 102:미지원)
	private String cDt; // Date // Master 요청에 대한 상세정보
	
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
