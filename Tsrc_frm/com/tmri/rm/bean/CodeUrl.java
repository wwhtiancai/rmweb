package com.tmri.rm.bean;
/**
 *
 * <p>Title: DC</p>
 *
 * <p>Description: 各支队URL表</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2010-10-29 09:52:27</p>
 *
 */
public class CodeUrl {
	private String dwdm;	//单位代码
	private String url;	//URI路径
	private String port;	//端口
	private String context;	//上下文
	private String jb;	//级别:1-部局,2-总队,3-支队,4-大队,5-中队
	private String jdmc;	//节点名称
	private String sn;	//sn序列号
	private String sjjd;	//上级节点
	private String bz;	//备注
	//非数据库字段的JavaBean属性，请使用Public方法。

	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getJb() {
		return jb;
	}
	public void setJb(String jb) {
		this.jb = jb;
	}
	public String getJdmc() {
		return jdmc;
	}
	public void setJdmc(String jdmc) {
		this.jdmc = jdmc;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSjjd() {
		return sjjd;
	}
	public void setSjjd(String sjjd) {
		this.sjjd = sjjd;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}