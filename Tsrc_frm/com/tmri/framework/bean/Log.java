package com.tmri.framework.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_LOGµÄ³Ö¾ÃÀà </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class Log implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String glbm;
  private String yhdh;
  private String czsj;
  private String czlx;
  private String czgn;
  private String cznr;
  private String ip;
  private String bz;
  private String isxls;
  private String jsdh;
  private String cdbh;
    
  public String getCdbh() {
		return StringUtil.transBlank(cdbh);
	}
	public void setCdbh(String cdbh) {
		this.cdbh = cdbh;
	}
	public String getJsdh() {
		return StringUtil.transBlank(jsdh);
	}
	public void setJsdh(String jsdh) {
		this.jsdh = jsdh;
	}
	public String getIsxls() {
		return StringUtil.transBlank(isxls);
	}
	public void setIsxls(String isxls) {
		this.isxls = isxls;
	}
	public Log(String glbm,String yhdh,String czsj,String czlx,String czgn,String cznr,String ip,String bz){
  		this.glbm = glbm;
  		this.yhdh = yhdh;
  		this.czsj = czsj;
  		this.czlx = czlx;
  		this.cznr = cznr;
  		this.ip = ip;
  		this.bz = bz;
  		this.czgn = czgn;
  	}    
  	public Log(){
  		
  	}
    public String getGlbm(){
        return StringUtil.transBlank(this.glbm);
    }
    public void setGlbm(String glbm1) {
        this.glbm =glbm1;
    }
    public String getYhdh(){
        return StringUtil.transBlank(this.yhdh);
    }
    public void setYhdh(String yhdh1) {
        this.yhdh =yhdh1;
    }
    public String getCzsj(){
        return this.czsj;
    }
    public void setCzsj(String czsj1) {
        this.czsj =czsj1;
    }
    public String getCzlx(){
        return StringUtil.transBlank(this.czlx);
    }
    public void setCzlx(String czlx1) {
        this.czlx =czlx1;
    }
    public String getCzgn(){
        return this.czgn;
    }
    public void setCzgn(String czgn1) {
        this.czgn =czgn1;
    }
    public String getCznr(){
        return StringUtil.transBlank(this.cznr);
    }
    public void setCznr(String cznr1) {
      if(cznr1.length()>3072)
      	this.cznr =cznr1.substring(0,3072);	
    	this.cznr =cznr1;
    }
    public String getIp(){
        return StringUtil.transBlank(this.ip);
    }
    public void setIp(String ip1) {
        this.ip =ip1;
    }
    public String getBz(){
        return this.bz;
    }
    public void setBz(String bz1) {
        this.bz =bz1;
    }
    
    private String rzlx;
    private String ksrq;
    private String jsrq;
	public String getRzlx() {
		return StringUtil.transBlank(rzlx);
	}
	public void setRzlx(String rzlx) {
		this.rzlx = rzlx;
	}
	public String getKsrq() {
		return StringUtil.transBlank(ksrq);
	}
	public void setKsrq(String ksrq) {
		this.ksrq = ksrq;
	}
	public String getJsrq() {
		return StringUtil.transBlank(jsrq);
	}
	public void setJsrq(String jsrq) {
		this.jsrq = jsrq;
	}
    
	private String bhxj;
	public String getBhxj() {
		return StringUtil.transBlank(bhxj);
	}
	public void setBhxj(String bhxj) {
		this.bhxj = bhxj;
	}
	
	private String ywlx;
	public String getYwlx() {
		return StringUtil.transBlank(ywlx);
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	
	private String ywyy;
	public String getYwyy() {
		return StringUtil.transBlank(ywyy);
	}
	public void setYwyy(String ywyy) {
		this.ywyy = ywyy;
	}
	
	private String lsh;
	private String hpzl;
	private String hphm;
	public String getLsh() {
		return StringUtil.transBlank(lsh);
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
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
	
	private String ywgw;
	private String sfzmhm;
	public String getYwgw() {
		return StringUtil.transBlank(ywgw);
	}
	public void setYwgw(String ywgw) {
		this.ywgw = ywgw;
	}
	public String getSfzmhm() {
		return StringUtil.transBlank(sfzmhm);
	}
	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}
	
	private String sgbh;
	private String jar1;
	private String jar2;
	public String getSgbh() {
		return StringUtil.transBlank(sgbh);
	}
	public void setSgbh(String sgbh) {
		this.sgbh = sgbh;
	}
	public String getJar1() {
		return StringUtil.transBlank(jar1);
	}
	public void setJar1(String jar1) {
		this.jar1 = jar1;
	}
	public String getJar2() {
		return StringUtil.transBlank(jar2);
	}
	public void setJar2(String jar2) {
		this.jar2 = jar2;
	}
	
	
	private String ywmc;
	public String getYwmc() {
		return StringUtil.transBlank(ywmc);
	}
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	private String fwcs;
	public String getFwcs() {
		return StringUtil.transBlank(fwcs);
	}
	public void setFwcs(String fwcs) {
		this.fwcs = fwcs;
	}
	private String dlms;
	public String getDlms() {
		return StringUtil.transBlank(dlms);
	}
	public void setDlms(String dlms) {
		this.dlms = dlms;
	}
	
	private String cwxx;
	public String getCwxx() {
		return StringUtil.transBlank(cwxx);
	}
	public void setCwxx(String cwxx) {
		this.cwxx = cwxx;
	}
	
	private String fwbj;
	public String getFwbj() {
		return StringUtil.transBlank(fwbj);
	}
	public void setFwbj(String fwbj) {
		this.fwbj = fwbj;
	}
	
    
}
