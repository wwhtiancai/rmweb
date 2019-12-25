package com.tmri.share.frm.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>
 * Title:FRM_ROADITEM的持久类
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
 * @author chenxh
 * @version 1.0
 */
public class Roaditem implements Serializable{
	private String glbm;
	private String dldm;
	private String xzqh;
	private String dlmc;
	private String dllx;
	private String glxzdj;
	private String dx;
	private String dlxx;
	private String lkldlx;
	private String dlwlgl;
	private String lmjg;
	private String fhsslx;
	private String qs;
	private String js;
	private String xqlc;
	private String cjr;
	private String cjsj;
	private String gxsj;
	private String sgxzqh;
	private String sgdldm;
	private String wfglbm;
	private String wfdldm;
	private String jlzt;
	private String qsmc;
	private String jsmc;
	private String zyglss;
	private String xzqhxxlc;
	private String xh;
	private String bmjb;
	private String sgdlmc;
	private String wfdlmc;
	private String bmdm;
	private String csbj;
	// 交通代码
	private String jtdm;
	private String ssglbm;
	private String bz;
	private String lsh;
	public Roaditem(){}
	public Roaditem(String glbm,String dldm,String dlmc,String xzqh,String glxzdj){
		this.glbm=glbm;
		this.dldm=dldm;
		this.dlmc=dlmc;
		this.xzqh=xzqh;
		this.glxzdj=glxzdj;
	}
	public String getJtdm(){
		return StringUtil.transBlank(jtdm);
	}
	public void setJtdm(String jtdm){
		this.jtdm=jtdm;
	}
	public String getGlbm(){
		return StringUtil.transBlank(glbm);
	}
	public void setGlbm(String glbm){
		this.glbm=glbm;
	}
	public String getDldm(){
		return StringUtil.transBlank(dldm);
	}
	public void setDldm(String dldm){
		this.dldm=dldm;
	}
	public String getXzqh(){
		return StringUtil.transBlank(xzqh);
	}
	public void setXzqh(String xzqh){
		this.xzqh=xzqh;
	}
	public String getDlmc(){
		return StringUtil.transBlank(dlmc);
	}
	public void setDlmc(String dlmc){
		this.dlmc=dlmc;
	}
	public String getDllx(){
		return StringUtil.transBlank(dllx);
	}
	public void setDllx(String dllx){
		this.dllx=dllx;
	}
	public String getGlxzdj(){
		return StringUtil.transBlank(glxzdj);
	}
	public void setGlxzdj(String glxzdj){
		this.glxzdj=glxzdj;
	}
	public String getDx(){
		return StringUtil.transBlank(dx);
	}
	public void setDx(String dx){
		this.dx=dx;
	}
	public String getDlxx(){
		return StringUtil.transBlank(dlxx);
	}
	public void setDlxx(String dlxx){
		this.dlxx=dlxx;
	}
	public String getLkldlx(){
		return StringUtil.transBlank(lkldlx);
	}
	public void setLkldlx(String lkldlx){
		this.lkldlx=lkldlx;
	}
	public String getDlwlgl(){
		return StringUtil.transBlank(dlwlgl);
	}
	public void setDlwlgl(String dlwlgl){
		this.dlwlgl=dlwlgl;
	}
	public String getLmjg(){
		return StringUtil.transBlank(lmjg);
	}
	public void setLmjg(String lmjg){
		this.lmjg=lmjg;
	}
	public String getFhsslx(){
		return StringUtil.transBlank(fhsslx);
	}
	public void setFhsslx(String fhsslx){
		this.fhsslx=fhsslx;
	}
	public String getQs(){
		return StringUtil.transBlank(qs);
	}
	public void setQs(String qs){
		this.qs=qs;
	}
	public String getJs(){
		return StringUtil.transBlank(js);
	}
	public void setJs(String js){
		this.js=js;
	}
	public String getXqlc(){
		return StringUtil.transBlank(xqlc);
	}
	public void setXqlc(String xqlc){
		this.xqlc=xqlc;
	}
	public String getCjr(){
		return StringUtil.transBlank(cjr);
	}
	public void setCjr(String cjr){
		this.cjr=cjr;
	}
	public String getCjsj(){
		return StringUtil.transBlank(cjsj);
	}
	public void setCjsj(String cjsj){
		this.cjsj=cjsj;
	}
	public String getGxsj(){
		return StringUtil.transBlank(gxsj);
	}
	public void setGxsj(String gxsj){
		this.gxsj=gxsj;
	}
	public String getSgxzqh(){
		return sgxzqh;
	}
	public void setSgxzqh(String sgxzqh){
		this.sgxzqh=sgxzqh;
	}
	public String getSgdldm(){
		return sgdldm;
	}
	public void setSgdldm(String sgdldm){
		this.sgdldm=sgdldm;
	}
	public String getWfglbm(){
		return wfglbm;
	}
	public void setWfglbm(String wfglbm){
		this.wfglbm=wfglbm;
	}
	public String getWfdldm(){
		return wfdldm;
	}
	public void setWfdldm(String wfdldm){
		this.wfdldm=wfdldm;
	}
	public String getJlzt(){
		return jlzt;
	}
	public void setJlzt(String jlzt){
		this.jlzt=jlzt;
	}
	public String getQsmc(){
		return qsmc;
	}
	public void setQsmc(String qsmc){
		this.qsmc=qsmc;
	}
	public String getJsmc(){
		return jsmc;
	}
	public void setJsmc(String jsmc){
		this.jsmc=jsmc;
	}
	public String getZyglss(){
		return zyglss;
	}
	public void setZyglss(String zyglss){
		this.zyglss=zyglss;
	}
	public String getXzqhxxlc(){
		return xzqhxxlc;
	}
	public void setXzqhxxlc(String xzqhxxlc){
		this.xzqhxxlc=xzqhxxlc;
	}
	public String getXh(){
		return xh;
	}
	public void setXh(String xh){
		this.xh=xh;
	}
	public String getBmjb(){
		return bmjb;
	}
	public void setBmjb(String bmjb){
		this.bmjb=bmjb;
	}
	public String getSgdlmc(){
		return sgdlmc;
	}
	public void setSgdlmc(String sgdlmc){
		this.sgdlmc=sgdlmc;
	}
	public String getWfdlmc(){
		return wfdlmc;
	}
	public void setWfdlmc(String wfdlmc){
		this.wfdlmc=wfdlmc;
	}
	public String getBmdm(){
		return bmdm;
	}
	public void setBmdm(String bmdm){
		this.bmdm=bmdm;
	}
	public String getSsglbm(){
		return ssglbm;
	}
	public void setSsglbm(String ssglbm){
		this.ssglbm=ssglbm;
	}
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz=bz;
	}
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public String getCsbj() {
		return csbj;
	}
	public void setCsbj(String csbj) {
		this.csbj = csbj;
	}
}
