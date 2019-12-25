package com.tmri.pub.bean;

public class QueryRoad{
	private String zdglbm;//支队管理部门
	private String glbm;//当前用户管理部门
	private String dldm;//道路代码
	private String dlmc;//道路名称
	private String xzqh;//违法、事故行政区划
	private String glqt;
	
	public String getXzqh(){
		return xzqh;
	}
	public void setXzqh(String xzqh){
		this.xzqh=xzqh;
	}
	public String getGlbm(){
		return glbm;
	}
	public void setGlbm(String glbm){
		this.glbm=glbm;
	}
	public String getDldm(){
		return dldm;
	}
	public void setDldm(String dldm){
		this.dldm=dldm;
	}
	public String getDlmc(){
		return dlmc;
	}
	public void setDlmc(String dlmc){
		this.dlmc=dlmc;
	}
	public String getGlqt() {
		return glqt;
	}
	public void setGlqt(String glqt) {
		this.glqt = glqt;
	}
	public String getZdglbm(){
		return zdglbm;
	}
	public void setZdglbm(String zdglbm){
		this.zdglbm=zdglbm;
	}
}
