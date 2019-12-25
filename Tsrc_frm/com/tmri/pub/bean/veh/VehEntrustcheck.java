/***********************************************************************
 * Module:  Veh_entrustcheck.java
 * From Table:  VEH_ENTRUSTCHECK
 * 源自:  委托检验信息表
 * Author:  Shi Jianrong
 ***********************************************************************/
package com.tmri.pub.bean.veh;

import java.io.Serializable;

//import java.util.*;

public class VehEntrustcheck implements Serializable {

	public String getJyrq() {
		return jyrq;
	}

	public void setJyrq(String jyrq) {
		this.jyrq = jyrq;
	}

	public String getCjdw() {
		return cjdw;
	}

	public void setCjdw(String cjdw) {
		this.cjdw = cjdw;
	}

	private String jyrq;
	private String cjdw;

	private String xh = ""; // 机动车序号
	private String wtrq = ""; // 委托日期
	private String hpzl = ""; // 号牌种类
	private String hphm = ""; // 号牌号码
	private String yxqz = ""; // 检验有效期止
	private String stjg = ""; // 受托机关
	private String jbr = ""; // 经办人
	private String fzjg = ""; // 发证机关
	private String bz = ""; // 备注
	private String sybj = ""; // 检验标记
	private String lsh = "";// 流水号
	private String clpp1 = "";
	private String clxh = "";
	private String syr = "";
	private String wtksrq = "";
	private String wtjsrq = "";

	public String getWtksrq() {
		return wtksrq;
	}

	public void setWtksrq(String wtksrq) {
		this.wtksrq = wtksrq;
	}

	public String getWtjsrq() {
		return wtjsrq;
	}

	public void setWtjsrq(String wtjsrq) {
		this.wtjsrq = wtjsrq;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getWtrq() {
		return wtrq;
	}

	public void setWtrq(String wtrq) {
		this.wtrq = wtrq;
	}

	public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getHphm() {
		return hphm;
	}

	public void setHphm(String hphm) {
		this.hphm = hphm;
	}

	public String getYxqz() {
		return yxqz;
	}

	public void setYxqz(String yxqz) {
		this.yxqz = yxqz;
	}

	public String getStjg() {
		return stjg;
	}

	public void setStjg(String stjg) {
		this.stjg = stjg;
	}

	public String getJbr() {
		return jbr;
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}

	public String getFzjg() {
		return fzjg;
	}

	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getSybj() {
		return sybj;
	}

	public void setSybj(String sybj) {
		this.sybj = sybj;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public String getClpp1() {
		return clpp1;
	}

	public void setClpp1(String clpp1) {
		this.clpp1 = clpp1;
	}

	public String getClxh() {
		return clxh;
	}

	public void setClxh(String clxh) {
		this.clxh = clxh;
	}

	public String getSyr() {
		return syr;
	}

	public void setSyr(String syr) {
		this.syr = syr;
	}

}
