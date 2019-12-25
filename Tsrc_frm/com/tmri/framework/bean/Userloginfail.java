package com.tmri.framework.bean;

import java.util.*;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 ** zhoujn 自动生成
 **/
public class Userloginfail implements Serializable {
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

	private String ipdz;

	public String getIpdz() {
		return StringUtil.transBlank(ipdz);
	}

	public void setIpdz(String ipdz) {
		this.ipdz = ipdz;
	}

	private String yhmm;

	public String getYhmm() {
		return StringUtil.transBlank(yhmm);
	}

	public void setYhmm(String yhmm) {
		this.yhmm = yhmm;
	}

	private String bz;

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}