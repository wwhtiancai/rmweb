package com.tmri.pub.bean.veh;
/***********************************************************************
* Module:  Veh_exchange.java
* From Table:  VEH_EXCHANGE
* 源自:  补/换领牌证信息表
* Author:  Shi Jianrong
***********************************************************************/
public class VehExchange {
	private String xh = ""; //机动车序号
	private String sqrq = ""; //申请日期
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getSqrq() {
		return sqrq;
	}
	public void setSqrq(String sqrq) {
		this.sqrq = sqrq;
	}
	public String getSqyy() {
		return sqyy;
	}
	public void setSqyy(String sqyy) {
		this.sqyy = sqyy;
	}
	public String getSqsx() {
		return sqsx;
	}
	public void setSqsx(String sqsx) {
		this.sqsx = sqsx;
	}
	public String getYdjzsbh() {
		return ydjzsbh;
	}
	public void setYdjzsbh(String ydjzsbh) {
		this.ydjzsbh = ydjzsbh;
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
	public String getXgzl() {
		return xgzl;
	}
	public void setXgzl(String xgzl) {
		this.xgzl = xgzl;
	}
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public String getRowcounter() {
		return rowcounter;
	}
	public void setRowcounter(String rowcounter) {
		this.rowcounter = rowcounter;
	}
	private String sqyy = ""; //申请原因
	private String sqsx = ""; //申请事项
	private String ydjzsbh = ""; //原登记证书编号
	private String jbr = ""; //经办人
	private String fzjg = ""; //发证机关
	private String xgzl = ""; //相关资料
	private String lsh = ""; //流水号
	private String rowcounter="";
}
