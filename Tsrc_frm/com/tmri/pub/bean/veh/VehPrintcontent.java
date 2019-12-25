package com.tmri.pub.bean.veh;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>
 * Title:VEH_PRINTCONTENTµÄ³Ö¾ÃÀà
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
public class VehPrintcontent implements Serializable {
	private String lsh;
	private String xh;
	private String hpzl;
	private String hphm;
	private String dylx;
	private String ywlx;
	private String ywyy;
	private String dysx1;
	private String dysx2;
	private String dyrq;
	private String jbr;
	private String fzjg;

	public String getLsh() {
		return StringUtil.transBlank(lsh);
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

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

	public String getDylx() {
		return StringUtil.transBlank(dylx);
	}

	public void setDylx(String dylx) {
		this.dylx = dylx;
	}

	public String getYwlx() {
		return StringUtil.transBlank(ywlx);
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getYwyy() {
		return StringUtil.transBlank(ywyy);
	}

	public void setYwyy(String ywyy) {
		this.ywyy = ywyy;
	}

	public String getDysx1() {
		return StringUtil.transBlank(dysx1);
	}

	public void setDysx1(String dysx1) {
		this.dysx1 = dysx1;
	}

	public String getDysx2() {
		return StringUtil.transBlank(dysx2);
	}

	public void setDysx2(String dysx2) {
		this.dysx2 = dysx2;
	}

	public String getDyrq() {
		return StringUtil.transBlank(dyrq);
	}

	public void setDyrq(String dyrq) {
		this.dyrq = dyrq;
	}

	public String getJbr() {
		return StringUtil.transBlank(jbr);
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}

	public String getFzjg() {
		return StringUtil.transBlank(fzjg);
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
}
