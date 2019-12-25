/***********************************************************************
 * Module:  Veh_handicapped.java
 * From Table:  VEH_HANDICAPPED
 * 源自:  校车备案表
 * Author:  Shi Jianrong
 ***********************************************************************/
package com.tmri.pub.bean.veh;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

public class VehHandicapped implements Serializable {
	private String xh;
	private String cjrfzzzxh;
	private String cjrfzzzbh;
	private String djrq;
	private String jbr1;
	private String lsh1;
	private String cxrq;
	private String jbr2;
	private String lsh2;
	private String cxbj;
	private String hpzl;
	private String hphm;
	private String fzjg;
	private String clpp1;
	private String clxh;
	private String cllx;
	private String csbj;

	public String getXh() {
		return StringUtil.transBlank(xh);
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getCjrfzzzxh() {
		return StringUtil.transBlank(cjrfzzzxh);
	}

	public void setCjrfzzzxh(String cjrfzzzxh) {
		this.cjrfzzzxh = cjrfzzzxh;
	}

	public String getCjrfzzzbh() {
		return StringUtil.transBlank(cjrfzzzbh);
	}

	public void setCjrfzzzbh(String cjrfzzzbh) {
		this.cjrfzzzbh = cjrfzzzbh;
	}

	public String getDjrq() {
		return StringUtil.transBlank(djrq);
	}

	public void setDjrq(String djrq) {
		this.djrq = djrq;
	}

	public String getJbr1() {
		return StringUtil.transBlank(jbr1);
	}

	public void setJbr1(String jbr1) {
		this.jbr1 = jbr1;
	}

	public String getLsh1() {
		return StringUtil.transBlank(lsh1);
	}

	public void setLsh1(String lsh1) {
		this.lsh1 = lsh1;
	}

	public String getCxrq() {
		return StringUtil.transBlank(cxrq);
	}

	public void setCxrq(String cxrq) {
		this.cxrq = cxrq;
	}

	public String getJbr2() {
		return StringUtil.transBlank(jbr2);
	}

	public void setJbr2(String jbr2) {
		this.jbr2 = jbr2;
	}

	public String getLsh2() {
		return StringUtil.transBlank(lsh2);
	}

	public void setLsh2(String lsh2) {
		this.lsh2 = lsh2;
	}

	public String getCxbj() {
		return StringUtil.transBlank(cxbj);
	}

	public void setCxbj(String cxbj) {
		this.cxbj = cxbj;
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

	public String getFzjg() {
		return StringUtil.transBlank(fzjg);
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getClpp1() {
		return StringUtil.transBlank(clpp1);
	}

	public void setClpp1(String clpp1) {
		this.clpp1 = clpp1;
	}

	public String getClxh() {
		return StringUtil.transBlank(clxh);
	}

	public void setClxh(String clxh) {
		this.clxh = clxh;
	}

	public String getCllx() {
		return StringUtil.transBlank(cllx);
	}

	public void setCllx(String cllx) {
		this.cllx = cllx;
	}

	public String getCsbj() {
		return StringUtil.transBlank(csbj);
	}

	public void setCsbj(String csbj) {
		this.csbj = csbj;
	}

}
