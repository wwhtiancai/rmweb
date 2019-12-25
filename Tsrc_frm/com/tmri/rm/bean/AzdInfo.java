package com.tmri.rm.bean;

public class AzdInfo {
	private String azdmc; // 安装点名称
	private String azdm;  // 安装代码
	private String fzjg;  // 发证机关
	private String zxscsj;// 最新上传时间
	private double sjc;   // 时间差
	private String status;// 运行状态
	private int errNum;   // 错误数
	private String sjcStr;// 时间差(字符串)
	private String wjs;   // 文件数
	private String jls;   // 记录数
	
	public String getAzdmc() {
		return azdmc;
	}
	public void setAzdmc(String azdmc) {
		this.azdmc = azdmc;
	}
	public String getAzdm() {
		return azdm;
	}
	public void setAzdm(String azdm) {
		this.azdm = azdm;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public String getZxscsj() {
		return zxscsj;
	}
	public void setZxscsj(String zxscsj) {
		this.zxscsj = zxscsj;
	}
	public double getSjc() {
		return sjc;
	}
	public void setSjc(double sjc) {
		this.sjc = sjc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getErrNum() {
		return errNum;
	}
	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}
	public String getSjcStr() {
		return sjcStr;
	}
	public void setSjcStr(String sjcStr) {
		this.sjcStr = sjcStr;
	}
	public String getWjs() {
		return wjs;
	}
	public void setWjs(String wjs) {
		this.wjs = wjs;
	}
	public String getJls() {
		return jls;
	}
	public void setJls(String jls) {
		this.jls = jls;
	}
}
