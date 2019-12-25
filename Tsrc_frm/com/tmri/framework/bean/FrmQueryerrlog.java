package com.tmri.framework.bean;

import java.util.*;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;


/**
 ** zhoujn 自动生成
 **/
public class FrmQueryerrlog implements Serializable {
	private String gnbh;
	
	public String getGnbh() {
		return gnbh;
	}

	public void setGnbh(String gnbh) {
		this.gnbh = gnbh;
	}

	private String yhdh;

	public String getYhdh() {
		return StringUtil.transBlank(yhdh);
	}

	public void setYhdh(String yhdh) {
		this.yhdh = yhdh;
	}

	private String rq;

	public String getRq() {
		return StringUtil.transBlank(rq);
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	private String fwcs;

	public String getFwcs() {
		return StringUtil.transBlank(fwcs);
	}

	public void setFwcs(String fwcs) {
		this.fwcs = fwcs;
	}

	private String ipdz;

	public String getIpdz() {
		return StringUtil.transBlank(ipdz);
	}

	public void setIpdz(String ipdz) {
		this.ipdz = ipdz;
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
	
	private String scbj;

	public String getScbj() {
		return StringUtil.transBlank(scbj);
	}

	public void setScbj(String scbj) {
		this.scbj = scbj;
	}

}