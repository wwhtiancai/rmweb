package com.tmri.rm.bean;

public class RmSysRunStatus {
	private String bmmc;  // 部门名称
	private int azds;  // 安装点数
	private int webs;  // 应用程序已更新安装点数
	private int pkgs;  // 存储过程已更新安装点数
	private int zcs;   // 正常数(24小时内有数据上传)
	private int stns;  // 3天内有数据上传安装点数
	private int yxqns; // 一星期内有数据上传安装点数
	private int cgyxqs;// 超过1星期无数据上传安装点数
	private int cscws; // 传输有错误安装点数
	private int sjjys; // 有数据积压安装点数
	private int wyxs;  // 后台任务未运行安装点数
	private int yxcws; // 后台任务运行有错误安装点数
	private int gcsjs; // 过车数据文件有积压的安装点数
	
	private String sf;    // 省份代码前两位

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public int getAzds() {
		return azds;
	}

	public void setAzds(int azds) {
		this.azds = azds;
	}

	public int getWebs() {
		return webs;
	}

	public void setWebs(int webs) {
		this.webs = webs;
	}

	public int getPkgs() {
		return pkgs;
	}

	public void setPkgs(int pkgs) {
		this.pkgs = pkgs;
	}

	public int getZcs() {
		return zcs;
	}

	public void setZcs(int zcs) {
		this.zcs = zcs;
	}

	public int getStns() {
		return stns;
	}

	public void setStns(int stns) {
		this.stns = stns;
	}

	public int getYxqns() {
		return yxqns;
	}

	public void setYxqns(int yxqns) {
		this.yxqns = yxqns;
	}

	public int getCgyxqs() {
		return cgyxqs;
	}

	public void setCgyxqs(int cgyxqs) {
		this.cgyxqs = cgyxqs;
	}

	public int getCscws() {
		return cscws;
	}

	public void setCscws(int cscws) {
		this.cscws = cscws;
	}

	public int getSjjys() {
		return sjjys;
	}

	public void setSjjys(int sjjys) {
		this.sjjys = sjjys;
	}

	public int getWyxs() {
		return wyxs;
	}

	public void setWyxs(int wyxs) {
		this.wyxs = wyxs;
	}

	public int getYxcws() {
		return yxcws;
	}

	public void setYxcws(int yxcws) {
		this.yxcws = yxcws;
	}

	public String getSf() {
		return sf;
	}

	public void setSf(String sf) {
		this.sf = sf;
	}

	public int getGcsjs() {
		return gcsjs;
	}

	public void setGcsjs(int gcsjs) {
		this.gcsjs = gcsjs;
	}
}
