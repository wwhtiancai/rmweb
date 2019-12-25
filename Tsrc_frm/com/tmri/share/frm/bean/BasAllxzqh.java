package com.tmri.share.frm.bean;

import com.tmri.share.frm.util.StringUtil;

public class BasAllxzqh {
	private String azdm;
	private String xzqh;
	private String qhmc;
	private String qhsm;
	private String sfxn;
	private String jlzt;
	private String gxsj;
	private String bz;

	public String getAzdm() {
		return StringUtil.transBlank(azdm);
	}

	public void setAzdm(String azdm) {
		this.azdm = azdm;
	}

	public String getXzqh() {
		String qh = StringUtil.transBlank(xzqh);
		return qh.trim();
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getQhmc() {
		return StringUtil.transBlank(qhmc);
	}

	public void setQhmc(String qhmc) {
		this.qhmc = qhmc;
	}

	public String getQhsm() {
		return StringUtil.transBlank(qhsm);
	}

	public void setQhsm(String qhsm) {
		this.qhsm = qhsm;
	}

	public String getSfxn() {
		return StringUtil.transBlank(sfxn);
	}

	public void setSfxn(String sfxn) {
		this.sfxn = sfxn;
	}

	public String getJlzt() {
		return StringUtil.transBlank(jlzt);
	}

	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}

	public String getGxsj() {
		return StringUtil.transBlank(gxsj);
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
}
