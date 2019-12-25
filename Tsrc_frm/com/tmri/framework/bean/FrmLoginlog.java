package com.tmri.framework.bean;

import java.util.*;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;


/**
 ** zhoujn 自动生成
 **/
public class FrmLoginlog implements Serializable {
	private String yhdh;

	public String getYhdh() {
		return StringUtil.transBlank(yhdh);
	}

	public void setYhdh(String yhdh) {
		this.yhdh = yhdh;
	}

	private String dlsj;

	public String getDlsj() {
		return StringUtil.transBlank(dlsj);
	}

	public void setDlsj(String dlsj) {
		this.dlsj = dlsj;
	}

	private String ip;

	public String getIp() {
		return StringUtil.transBlank(ip);
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private String dlms;

	public String getDlms() {
		return StringUtil.transBlank(dlms);
	}

	public void setDlms(String dlms) {
		this.dlms = dlms;
	}

	private String bz;

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	private String glbm;

	public String getGlbm() {
		return StringUtil.transBlank(glbm);
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}
}