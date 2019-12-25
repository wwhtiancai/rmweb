package com.tmri.pub.bean.veh;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>
 * Title:VEH_SCHOOLBUS_PERMIT_DRIVERµÄ³Ö¾ÃÀà
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
public class VehSchoolbuspermitdriver implements Serializable {
	private String xh;
	private String sfzmmc;
	private String sfzmhm;
	private String djrq;
	private String zt;
	private String gxrq;
	private String csbj;
	private String bz;
	private String xczjcx;
	private String xkzbh;
	private String xm;
	private String fzjg;
	private String xczg;
	public String getXh() {
		return StringUtil.transBlank(xh);
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getSfzmmc() {
		return StringUtil.transBlank(sfzmmc);
	}

	public void setSfzmmc(String sfzmmc) {
		this.sfzmmc = sfzmmc;
	}

	public String getSfzmhm() {
		return StringUtil.transBlank(sfzmhm);
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	public String getDjrq() {
		return StringUtil.transBlank(djrq);
	}

	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	public String getZt() {
		return StringUtil.transBlank(zt);
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getGxrq() {
		return StringUtil.transBlank(gxrq);
	}

	public void setGxrq(String gxrq) {
		this.gxrq = gxrq;
	}

	public String getCsbj() {
		return StringUtil.transBlank(csbj);
	}

	public void setCsbj(String csbj) {
		this.csbj = csbj;
	}

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getXczjcx() {
		return StringUtil.transBlank(xczjcx);
	}

	public void setXczjcx(String xczjcx) {
		this.xczjcx = xczjcx;
	}

	public String getXkzbh() {
		return StringUtil.transBlank(xkzbh);
	}

	public void setXkzbh(String xkzbh) {
		this.xkzbh = xkzbh;
	}

	public String getXm() {
		return StringUtil.transBlank(xm);
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getFzjg() {
		return StringUtil.transBlank(fzjg);
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public void setXczg(String xczg) {
		this.xczg = xczg;
	}

	public String getXczg() {
		return xczg;
	}
}