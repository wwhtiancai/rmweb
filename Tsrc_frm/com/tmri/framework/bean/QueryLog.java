package com.tmri.framework.bean;
/**
 *
 * <p>Title: RM</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Weng.YF</p>
 *
 * <p>Date: 2012-08-01 15:31:49</p>
 *
 */
public class QueryLog {
	private String czsj;	//操作时间
	private String czlx;	//系统类别
	private String cdbh;	//菜单编号
	private String gnid;	//功能ID
	private String cznr;	//操作内容
	private String ip;	//IP地址
	private String xm;	//用户姓名
	private String bmdm;	//部门代码
	private String bz;	//备注
	//[特定属性]<!--本区域内开发用户可加入特定属性，特定属性可以被工具软件自动化生成时保留。-->
	
	//[/特定属性]。

	public String getCzsj() {
		return czsj;
	}

	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getCdbh() {
		return cdbh;
	}

	public void setCdbh(String cdbh) {
		this.cdbh = cdbh;
	}

	public String getGnid() {
		return gnid;
	}

	public void setGnid(String gnid) {
		this.gnid = gnid;
	}

	public String getCznr() {
		return cznr;
	}

	public void setCznr(String cznr) {
		this.cznr = cznr;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getBmdm() {
		return bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}