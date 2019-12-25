package com.tmri.rm.bean;
import com.tmri.share.frm.util.StringUtil;
/**
 *
 * <p>Title: RMS</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2013-07-02 19:35:18</p>
 *
 */
public class RmVersion {
	private String fzjg;	//发证机关
	private String azdm;	//安装代码
	private String azdmc;   //安装点名称
	private String ip;	//IP地址
	private String gjz;	//关键字
	private String mc;	//名称
	private String version;	//版本号
	private String jcsj;	//检查时间
	//[特定属性]<!--本区域内开发用户可加入特定属性，特定属性可以被工具软件自动化生成时保留。-->
	private String sm;      //版本说明
	//[/特定属性]。
	private String sf;      //省份
	private String webzt;   //应用服务状态
	private String pkgzt;   //存储过程状态
	
	private String sfdm; // 省份代码
	private int azds; // 安装点数
	private int newWebCount; // 已更新应用程序数量
	private int newPkgCount; // 已更新存储过程数量
	private int normalCount; // 运行正常的数量
	private String ds; //地市
	
	private String web; // 应用程序更新
	private String pkg; // 存储过程更新
	private String status; // 状态
	
	private int webSc; // web是否上传 0 未上传 1 已上传
	private int pkgSc; // pkg是否上传 0 未上传 1 已上传
	
	private String webClass; // web样式
	private String pkgClass; // pkg样式
	private String statusClass; // status样式
	
	public String getWebClass() {
		return webClass;
	}
	public void setWebClass(String webClass) {
		this.webClass = webClass;
	}
	public String getPkgClass() {
		return pkgClass;
	}
	public void setPkgClass(String pkgClass) {
		this.pkgClass = pkgClass;
	}
	public String getStatusClass() {
		return statusClass;
	}
	public void setStatusClass(String statusClass) {
		this.statusClass = statusClass;
	}
	public int getWebSc() {
		return webSc;
	}
	public void setWebSc(int webSc) {
		this.webSc = webSc;
	}
	public int getPkgSc() {
		return pkgSc;
	}
	public void setPkgSc(int pkgSc) {
		this.pkgSc = pkgSc;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDs() {
		return ds;
	}
	public void setDs(String ds) {
		this.ds = ds;
	}
	public int getNormalCount() {
		return normalCount;
	}
	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}
	public String getSfdm() {
		return sfdm;
	}
	public void setSfdm(String sfdm) {
		this.sfdm = sfdm;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public String getAzdm() {
		return azdm;
	}
	public void setAzdm(String azdm) {
		this.azdm = azdm;
	}
	public String getAzdmc() {
		return azdmc;
	}
	public void setAzdmc(String azdmc) {
		this.azdmc = azdmc;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGjz() {
		return gjz;
	}
	public void setGjz(String gjz) {
		this.gjz = gjz;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getJcsj() {
		return StringUtil.transBlank(jcsj);
	}
	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getSf() {
		return sf;
	}
	public void setSf(String sf) {
		this.sf = sf;
	}
	public String getWebzt() {
		return webzt;
	}
	public void setWebzt(String webzt) {
		this.webzt = webzt;
	}
	public String getPkgzt() {
		return pkgzt;
	}
	public void setPkgzt(String pkgzt) {
		this.pkgzt = pkgzt;
	}
	public int getNewWebCount() {
		return newWebCount;
	}
	public void setNewWebCount(int newWebCount) {
		this.newWebCount = newWebCount;
	}
	public int getNewPkgCount() {
		return newPkgCount;
	}
	public void setNewPkgCount(int newPkgCount) {
		this.newPkgCount = newPkgCount;
	}
	public int getAzds() {
		return azds;
	}
	public void setAzds(int azds) {
		this.azds = azds;
	}
}