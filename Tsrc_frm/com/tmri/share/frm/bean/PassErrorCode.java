package com.tmri.share.frm.bean;

import com.tmri.share.frm.util.StringUtil;

public class PassErrorCode {

	private String cwdm;
	private String cwms;
	private String jjfs;

	public String getCwdm() {
		return StringUtil.transBlank(cwdm);
	}

	public void setCwdm(String cwdm) {
		this.cwdm = cwdm;
	}

	public String getCwms() {
		return StringUtil.transBlank(cwms);
	}

	public void setCwms(String cwms) {
		this.cwms = cwms;
	}

	public String getJjfs() {
		return StringUtil.transBlank(jjfs);
	}

	public void setJjfs(String jjfs) {
		this.jjfs = jjfs;
	}
}
