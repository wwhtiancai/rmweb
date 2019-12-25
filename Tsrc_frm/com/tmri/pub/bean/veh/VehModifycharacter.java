package com.tmri.pub.bean.veh;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>
 * Title:VEH_MODIFY_CHARACTERµÄ³Ö¾ÃÀà
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
public class VehModifycharacter implements Serializable {
	private String xh;
	private String syxz;
	private String qsrq;
	private String zzrq;
	private String syns;
	private String bfnx;
	private String lsh;
	private String ywlx;
	private String fzjg;
	private String zt;

	public String getZt() {
		return StringUtil.transBlank(zt);
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getFzjg() {
		return StringUtil.transBlank(fzjg);
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getXh() {
		return StringUtil.transBlank(xh);
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getSyxz() {
		return StringUtil.transBlank(syxz);
	}

	public void setSyxz(String syxz) {
		this.syxz = syxz;
	}

	public String getQsrq() {
		return StringUtil.transBlank(qsrq);
	}

	public void setQsrq(String qsrq) {
		this.qsrq = qsrq;
	}

	public String getZzrq() {
		return StringUtil.transBlank(zzrq);
	}

	public void setZzrq(String zzrq) {
		this.zzrq = zzrq;
	}

	public String getSyns() {
		return StringUtil.transBlank(syns);
	}

	public void setSyns(String syns) {
		this.syns = syns;
	}

	public String getBfnx() {
		return StringUtil.transBlank(bfnx);
	}

	public void setBfnx(String bfnx) {
		this.bfnx = bfnx;
	}

	public String getLsh() {
		return StringUtil.transBlank(lsh);
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getYwlx() {
		return StringUtil.transBlank(ywlx);
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
}
