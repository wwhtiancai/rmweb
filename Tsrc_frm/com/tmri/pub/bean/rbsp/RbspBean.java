package com.tmri.pub.bean.rbsp;

public class RbspBean {
	private String sfzmhm;
	private String xm;
	private int timeout;
	
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	private String userglbm;
	private String usersfzmhm;
	private String userxm;

	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	public String getUserglbm() {
		return userglbm;
	}

	public void setUserglbm(String userglbm) {
		this.userglbm = userglbm;
	}

	public String getUsersfzmhm() {
		return usersfzmhm;
	}

	public void setUsersfzmhm(String usersfzmhm) {
		this.usersfzmhm = usersfzmhm;
	}

	public String getUserxm() {
		return userxm;
	}

	public void setUserxm(String userxm) {
		this.userxm = userxm;
	}

}
