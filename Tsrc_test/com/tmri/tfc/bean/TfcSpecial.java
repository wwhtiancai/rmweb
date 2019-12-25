package com.tmri.tfc.bean;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>
 * Title:TFC_SPECIALµÄ³Ö¾ÃÀà
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:tmri
 * </p>
 * 
 * @author zhoujn
 * @version 1.0
 */
public class TfcSpecial implements Serializable {
	private String xh;
	private String hpzl;
	private String hphm;
	private String gxsj;
	private String sjzt;
	private String bzsm;
	private String csbj;
	private String bjcsbj;
	private String sjjb;
	private String cjr;
	private String glbm;
	private String jyw;
	
	private String hpzlmc;
	private String glbmmc;
	private String bmjb;

	public String getXh() {
		return StringUtil.transBlank(xh);
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getHpzl() {
		return StringUtil.transBlank(hpzl);
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getHphm() {
		return StringUtil.transBlank(hphm);
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getGxsj() {
		return StringUtil.transBlank(gxsj);
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getSjzt() {
		return StringUtil.transBlank(sjzt);
	}

	public void setSjzt(String sjzt) {
		this.sjzt = sjzt;
	}

	public String getBzsm() {
		return StringUtil.transBlank(bzsm);
	}

	public void setBzsm(String bzsm) {
		this.bzsm = bzsm;
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

	public String getSjjb() {
		return StringUtil.transBlank(sjjb);
	}

	public void setSjjb(String sjjb) {
		this.sjjb = sjjb;
	}

	public String getCjr() {
		return StringUtil.transBlank(cjr);
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getGlbm() {
		return StringUtil.transBlank(glbm);
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public String getJyw() {
		return StringUtil.transBlank(jyw);
	}

	public void setJyw(String jyw) {
		this.jyw = jyw;
	}

	public String getHpzlmc() {
		return hpzlmc;
	}

	public void setHpzlmc(String hpzlmc) {
		this.hpzlmc = hpzlmc;
	}

	public String getGlbmmc() {
		return glbmmc;
	}

	public void setGlbmmc(String glbmmc) {
		this.glbmmc = glbmmc;
	}

	public String getBmjb() {
		return bmjb;
	}

	public void setBmjb(String bmjb) {
		this.bmjb = bmjb;
	}
}
