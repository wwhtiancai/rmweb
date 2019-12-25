package com.tmri.rm.bean;

public class AzdTimeDiff {
	private String azdm;  // 安装代码
	private double sjc;   // 时间差（最新数据与当前时间之差）
	
	public String getAzdm() {
		return azdm;
	}
	public void setAzdm(String azdm) {
		this.azdm = azdm;
	}
	public double getSjc() {
		return sjc;
	}
	public void setSjc(double sjc) {
		this.sjc = sjc;
	}
}
