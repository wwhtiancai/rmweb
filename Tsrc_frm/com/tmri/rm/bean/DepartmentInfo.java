package com.tmri.rm.bean;

import java.io.Serializable;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.util.StringUtil;

public class DepartmentInfo extends Department implements Serializable {
	private String glbm;
	private String fzr;
	private String lxr;
	private String lxdh;
	private String czhm;
	private String lxdz;
	private String xzqh;
	private String dllx;
	private String glxzdj;
	private String dldm;
	private String lkh;
	private String dlms;
	private String jd;
	private String wd;
	private String csbj;
	private String bjcsbj;
	private String dlmc;
    private String sjxzqh;
	
	private String glbmmc;
    private String sjxzqhmc;
	private String lkmc;
	private String dllxmc;
	private String glxzdjmc;
	
	public String getGlbmmc() {
		return glbmmc;
	}

	public void setGlbmmc(String glbmmc) {
		this.glbmmc = glbmmc;
	}

	public String getLkmc() {
		return lkmc;
	}

	public void setLkmc(String lkmc) {
		this.lkmc = lkmc;
	}

	public String getDllxmc() {
		return dllxmc;
	}

	public void setDllxmc(String dllxmc) {
		this.dllxmc = dllxmc;
	}

	public String getGlxzdjmc() {
		return glxzdjmc;
	}

	public void setGlxzdjmc(String glxzdjmc) {
		this.glxzdjmc = glxzdjmc;
	}

	public String getDlmc() {
		return dlmc;
	}

	public void setDlmc(String dlmc) {
		this.dlmc = dlmc;
	}

	private String xzqhmc;
	

	public String getXzqhmc() {
		return xzqhmc;
	}

	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}

	public String getGlbm() {
		return StringUtil.transBlank(glbm);
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public String getFzr() {
		return StringUtil.transBlank(fzr);
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public String getLxr() {
		return StringUtil.transBlank(lxr);
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getLxdh() {
		return StringUtil.transBlank(lxdh);
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getCzhm() {
		return StringUtil.transBlank(czhm);
	}

	public void setCzhm(String czhm) {
		this.czhm = czhm;
	}

	public String getLxdz() {
		return StringUtil.transBlank(lxdz);
	}

	public void setLxdz(String lxdz) {
		this.lxdz = lxdz;
	}

	public String getXzqh() {
		return StringUtil.transBlank(xzqh);
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getDllx() {
		return StringUtil.transBlank(dllx);
	}

	public void setDllx(String dllx) {
		this.dllx = dllx;
	}

	public String getGlxzdj() {
		return StringUtil.transBlank(glxzdj);
	}

	public void setGlxzdj(String glxzdj) {
		this.glxzdj = glxzdj;
	}

	public String getDldm() {
		return StringUtil.transBlank(dldm);
	}

	public void setDldm(String dldm) {
		this.dldm = dldm;
	}

	public String getLkh() {
		return StringUtil.transBlank(lkh);
	}

	public void setLkh(String lkh) {
		this.lkh = lkh;
	}

	public String getDlms() {
		return StringUtil.transBlank(dlms);
	}

	public void setDlms(String dlms) {
		this.dlms = dlms;
	}

	public String getJd() {
		return StringUtil.transBlank(jd);
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return StringUtil.transBlank(wd);
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getCsbj() {
		return StringUtil.transBlank(csbj);
	}

	public void setCsbj(String csbj) {
		this.csbj = csbj;
	}

	public String getBjcsbj() {
		return StringUtil.transBlank(bjcsbj);
	}

	public void setBjcsbj(String bjcsbj) {
		this.bjcsbj = bjcsbj;
	}

    public String getSjxzqh() {
        return sjxzqh;
    }

    public void setSjxzqh(String sjxzqh) {
        this.sjxzqh = sjxzqh;
    }

    public String getSjxzqhmc() {
        return sjxzqhmc;
    }

    public void setSjxzqhmc(String sjxzqhmc) {
        this.sjxzqhmc = sjxzqhmc;
    }
}
