package com.cnu.lwm2m.redis.vo;

public class LpMeteringNormalRviVo {

	private String c; // Captured Obis Code
	private Integer t; // Captured Obis	Type
	private Integer i; // Captured Attribute Index
	private String v; // Captured Obis Value

	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public Integer getT() {
		return t;
	}
	public void setT(Integer t) {
		this.t = t;
	}
	public Integer getI() {
		return i;
	}
	public void setI(Integer i) {
		this.i = i;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	
}
