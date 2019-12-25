package com.tmri.share.frm.bean;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

public class FrmGispara implements Serializable {
	private String gjz;
	private String csmc;
	private String cssm;
	private String mrz;
	private String sfxs;
	private String xssx;
	private String dmlb;
	private String sjgf;
	private String bz;
	private String jyw;
	private String xsxs;
	private String fzmc;

	public String getGjz() {
		return StringUtil.transBlank(gjz);
	}

	public void setGjz(String gjz) {
		this.gjz = gjz;
	}

	public String getCsmc() {
		return StringUtil.transBlank(csmc);
	}

	public void setCsmc(String csmc) {
		this.csmc = csmc;
	}

	public String getCssm() {
		return StringUtil.transBlank(cssm);
	}

	public void setCssm(String cssm) {
		this.cssm = cssm;
	}

	public String getMrz() {
		return StringUtil.transBlank(mrz);
	}

	public void setMrz(String mrz) {
		this.mrz = mrz;
	}

	public String getSfxs() {
		return StringUtil.transBlank(sfxs);
	}

	public void setSfxs(String sfxs) {
		this.sfxs = sfxs;
	}

	public String getXssx() {
		return StringUtil.transBlank(xssx);
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getDmlb() {
		return StringUtil.transBlank(dmlb);
	}

	public void setDmlb(String dmlb) {
		this.dmlb = dmlb;
	}

	public String getSjgf() {
		return StringUtil.transBlank(sjgf);
	}

	public void setSjgf(String sjgf) {
		this.sjgf = sjgf;
	}

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getJyw() {
		return StringUtil.transBlank(jyw);
	}

	public void setJyw(String jyw) {
		this.jyw = jyw;
	}

	public String getXsxs() {
		return StringUtil.transBlank(xsxs);
	}

	public void setXsxs(String xsxs) {
		this.xsxs = xsxs;
	}

	public String getFzmc() {
		return StringUtil.transBlank(fzmc);
	}

	public void setFzmc(String fzmc) {
		this.fzmc = fzmc;
	}
}
