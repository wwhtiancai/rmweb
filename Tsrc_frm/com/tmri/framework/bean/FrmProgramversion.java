package com.tmri.framework.bean;

import java.util.*;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;


/**
 ** zhoujn 自动生成
 **/
public class FrmProgramversion implements Serializable {
	private String xh;
	private String mkm;
	private String xgsm;
	private String zxbb;
	private String sxl;
	private String bz;
	
	private String gnm;
	private String yybb;
	

	public String getGnm() {
		return StringUtil.transBlank(gnm);
	}

	public void setGnm(String gnm) {
		this.gnm = gnm;
	}

	public String getYybb() {
		return StringUtil.transBlank(yybb);
	}

	public void setYybb(String yybb) {
		this.yybb = yybb;
	}

	public String getXh() {
		return StringUtil.transBlank(xh);
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getMkm() {
		return StringUtil.transBlank(mkm);
	}

	public void setMkm(String mkm) {
		this.mkm = mkm;
	}

	public String getXgsm() {
		return StringUtil.transBlank(xgsm);
	}

	public void setXgsm(String xgsm) {
		this.xgsm = xgsm;
	}

	public String getZxbb() {
		return StringUtil.transBlank(zxbb);
	}

	public void setZxbb(String zxbb) {
		this.zxbb = zxbb;
	}

	public String getSxl() {
		return StringUtil.transBlank(sxl);
	}

	public void setSxl(String sxl) {
		this.sxl = sxl;
	}

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
}
