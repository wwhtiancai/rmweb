package com.tmri.share.frm.bean;
import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

/**
 * <p>Title:FRM_SYSUSER的持久类 </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 * @author long
 * @version 1.0
 */
public class SysUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String yhdh;
    private String xm;
    private String mm;
    private String glbm;
    private String sfzmhm;
    private String ipks;
    private String ipjs;
    private String zhyxq;
    private String mmyxq;
    private String spjb;
    private String spglbm;
    private String sfmj;
    private String rybh;
    private String zt;
    private String bz;
    private String csbj;
    private String dlms;
    private String ip;
    private String qxms;
    private String roles;
    private String zdsp;
    private String zwtz;
    private String kgywyhlx ;
    private String yhssyw;
    
    private String mac;
    private String yzm;
    private String czqx;
    
    private String gdip;
    private String gdip1;
    private String gdip2;
    
    private String xtglymc;
    
    public String getGdip1() {
    	return StringUtil.transBlank(gdip1);
	}
	public void setGdip1(String gdip1) {
		this.gdip1 = gdip1;
	}
	public String getGdip2() {
		return StringUtil.transBlank(gdip2);
	}
	public void setGdip2(String gdip2) {
		this.gdip2 = gdip2;
	}
	public String getGdip(){
		return StringUtil.transBlank(gdip);
	}
	public void setGdip(String gdip){
		this.gdip=gdip;
	}
	public String getCzqx() {
		return StringUtil.transBlank(czqx);
	}
	public void setCzqx(String czqx) {
		this.czqx = czqx;
	}
	public String getYzm(){
			return yzm;
	}
	public void setYzm(String yzm){
			this.yzm=yzm;
	}
	
	public String getMac() {
		return StringUtil.transBlank(mac);
	}
	public void setMac(String mac) {
		this.mac = mac;
	}

	private String txtlb;
    private String tcdbh;
    private String tgnid;
    
    public String getTxtlb() {
		return StringUtil.transBlank(txtlb);
	}
	public void setTxtlb(String txtlb) {
		this.txtlb = txtlb;
	}

	private String tjsdh;
    private String sqms;
    
    private String proper;
    private String order;
    
    
    public String getProper() {
		return StringUtil.transBlank(proper);
	}
	public void setProper(String proper) {
		this.proper = proper;
	}
	public String getOrder() {
		return StringUtil.transBlank(order);
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSqms() {
		return StringUtil.transBlank(sqms);
	}
	public String getTjsdh() {
		return StringUtil.transBlank(tjsdh);
	}
	public void setTjsdh(String tjsdh) {
		this.tjsdh = tjsdh;
	}
	public void setSqms(String sqms) {
		this.sqms = sqms;
	}
	public String getTcdbh() {
		return StringUtil.transBlank(tcdbh);
	}
	public void setTcdbh(String tcdbh) {
		this.tcdbh = tcdbh;
	}
	public String getTgnid() {
		return StringUtil.transBlank(tgnid);
	}
	public void setTgnid(String tgnid) {
		this.tgnid = tgnid;
	}
	public String getKgywyhlx() {
		return StringUtil.transBlank(kgywyhlx);
	}
	public void setKgywyhlx(String kgywyhlx) {
		this.kgywyhlx = kgywyhlx;
	}
	public String getYhssyw() {
		return StringUtil.transBlank(yhssyw);
	}
	public void setYhssyw(String yhssyw) {
		this.yhssyw = yhssyw;
	}

	//可授予权限
    private String grantroles;
    public String getGrantroles() {
		return StringUtil.transBlank(grantroles);
	}
	public void setGrantroles(String grantroles) {
		this.grantroles = grantroles;
	}

	private String cdbhs;
    //zhoujn 20100520
    
    private String jyd;
    private String xtgly;
    private String zjdlsj;
    private String bmmc;
    private String ztmc;
    
    
    public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getYhdh(){
        return StringUtil.transBlank(this.yhdh);
    }
    public String getJyd() {
		return StringUtil.transBlank(jyd);
	}
	public void setJyd(String jyd) {
		this.jyd = jyd;
	}
	public String getXtgly() {
		return StringUtil.transBlank(xtgly);
	}
	public void setXtgly(String xtgly) {
		this.xtgly = xtgly;
	}
	public String getZjdlsj() {
		return StringUtil.transBlank(zjdlsj);
	}
	public void setZjdlsj(String zjdlsj) {
		this.zjdlsj = zjdlsj;
	}
	public void setYhdh(String yhdh1) {
        this.yhdh =yhdh1;
    }
    public String getXm(){
        return StringUtil.transBlank(this.xm);
    }
    public void setXm(String xm1) {
        this.xm =xm1;
    }
    public String getMm(){
        return StringUtil.transBlank(this.mm);
    }
    public void setMm(String mm1) {
        this.mm =mm1;
    }
    public String getGlbm(){
        return StringUtil.transBlank(this.glbm);
    }
    public void setGlbm(String glbm1) {
        this.glbm =glbm1;
    }
    public String getSfzmhm(){
        return StringUtil.transBlank(this.sfzmhm);
    }
    public void setSfzmhm(String sfzmhm1) {
        this.sfzmhm =sfzmhm1;
    }
    public String getIpks(){
        return StringUtil.transBlank(this.ipks);
    }
    public void setIpks(String ipks1) {
        this.ipks =ipks1;
    }
    public String getIpjs(){
        return StringUtil.transBlank(this.ipjs);
    }
    public void setIpjs(String ipjs1) {
        this.ipjs =ipjs1;
    }
    public String getZhyxq(){
        return StringUtil.transBlank(this.zhyxq);
    }
    public void setZhyxq(String zhyxq1) {
        this.zhyxq =zhyxq1;
    }
    public String getMmyxq(){
        return StringUtil.transBlank(this.mmyxq);
    }
    public void setMmyxq(String mmyxq1) {
        this.mmyxq =mmyxq1;
    }
    public String getSpjb(){
        return StringUtil.transBlank(this.spjb);
    }
    public void setSpjb(String spjb1) {
        this.spjb =spjb1;
    }
    public String getSpglbm(){
        return StringUtil.transBlank(this.spglbm);
    }
    public void setSpglbm(String spglbm1) {
        this.spglbm =spglbm1;
    }
    public String getSfmj(){
        return StringUtil.transBlank(this.sfmj);
    }
    public void setSfmj(String sfmj1) {
        this.sfmj =sfmj1;
    }
    public String getRybh(){
        return StringUtil.transBlank(this.rybh);
    }
    public void setRybh(String rybh1) {
        this.rybh =rybh1;
    }
    public String getZt(){
        return StringUtil.transBlank(this.zt);
    }
    public void setZt(String zt1) {
        this.zt =zt1;
    }
    public String getBz(){
        return StringUtil.transBlank(this.bz);
    }  
	public void setBz(String bz1) {
        this.bz =bz1;
    }
    public String getCsbj(){
        return StringUtil.transBlank(this.csbj);
    }
    public void setCsbj(String csbj1) {
        this.csbj =csbj1;
    }
	public String getIp() {
		return StringUtil.transBlank(ip);
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRoles(){
		return roles;
	}
	public void setRoles(String roles){
		this.roles=roles;
	}
	public String getQxms(){
		return StringUtil.transBlank(qxms);
	}
	public void setQxms(String qxms){
		this.qxms=qxms;
	}
	public String getCdbhs(){
		return StringUtil.transBlank(cdbhs);
	}
	public void setCdbhs(String cdbhs){
		this.cdbhs=cdbhs;
	}
	public String getDlms(){
		return dlms;
	}
	public void setDlms(String dlms){
		this.dlms=dlms;
	}
	
	private String bhxj;
	public String getBhxj() {
		return StringUtil.transBlank(bhxj);
	}
	public void setBhxj(String bhxj) {
		this.bhxj = bhxj;
	}
	
	public String getZdsp() {
		return StringUtil.transBlank(zdsp);
	}
	public void setZdsp(String zdsp) {
		this.zdsp = zdsp;
	}
	public String getZwtz(){
		return zwtz;
	}
	public void setZwtz(String zwtz){
		this.zwtz=zwtz;
	}
	public String getZtmc() {
		return ztmc;
	}
	public void setZtmc(String ztmc) {
		this.ztmc = ztmc;
	}
	public String getXtglymc() {
		return xtglymc;
	}
	public void setXtglymc(String xtglymc) {
		this.xtglymc = xtglymc;
	}

}
