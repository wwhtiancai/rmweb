package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by st on 2015/11/2.
 */
public class MaterialApply {

    private String dgdh;    //订购单号 产品代码+YYYYMMDD+3位顺序号
    private String cpdm;    //产品代码
    private int sl;       //数量
    private String jbr;     //经办人
    private Date dgrq;      //订购日期
    private int zt;         //状态，1-提交，2-交付，3-取消
    private String bz;      //备注
    
    private int yrksl;   //已入库数量
    
    private String cplb;    //产品类别
    private String lbmc;    //类别名称
    private String cpmc;    //产品名称
    private String jbrxm;    //经办人姓名
    private String bmmc;    //部门名称
    
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getLbmc() {
		return lbmc;
	}
	public void setLbmc(String lbmc) {
		this.lbmc = lbmc;
	}
	public String getCplb() {
		return cplb;
	}
	public void setCplb(String cplb) {
		this.cplb = cplb;
	}
	public String getCpmc() {
		return cpmc;
	}
	public void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	
	public String getJbrxm() {
		return jbrxm;
	}
	public void setJbrxm(String jbrxm) {
		this.jbrxm = jbrxm;
	}
	public String getDgdh() {
		return dgdh;
	}
	public void setDgdh(String dgdh) {
		this.dgdh = dgdh;
	}
	public String getCpdm() {
		return cpdm;
	}
	public void setCpdm(String cpdm) {
		this.cpdm = cpdm;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public String getJbr() {
		return jbr;
	}
	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
	public Date getDgrq() {
		return dgrq;
	}
	public void setDgrq(Date dgrq) {
		this.dgrq = dgrq;
	}
	public int getZt() {
		return zt;
	}
	public void setZt(int zt) {
		this.zt = zt;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getYrksl() {
		return yrksl;
	}
	public void setYrksl(int yrksl) {
		this.yrksl = yrksl;
	}

    
}
