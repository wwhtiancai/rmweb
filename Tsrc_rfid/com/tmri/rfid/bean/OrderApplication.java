package com.tmri.rfid.bean;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Joey on 2015/9/23.
 */
public class OrderApplication {

    private String sqdh;    //申请单号
    private String sqbm;    //申请部门
    private int cplb;       //产品类别
    private String cpdm;    //产品代码
    private String jbr;     //经办人
    private Date sqrq;      //申请日期
    private int sl;         //申请数量
    private int zt;         //状态，见OrderStatus
    private Date wcrq;      //完成日期
    private String bz;      //备注
    private String lxr;     //联系人
    private String lxdh;    //联系电话
    private String cz;      //传真
    private String shyj;    //审核意见
    
    private int ycksl;      //已出库数量

    public String getSqdh() {
        return sqdh;
    }

    public void setSqdh(String sqdh) {
        this.sqdh = sqdh;
    }

    public String getSqbm() {
        return sqbm;
    }

    public void setSqbm(String sqbm) {
        this.sqbm = sqbm;
    }

    public String getCpdm() {
        return cpdm;
    }

    public void setCpdm(String cpdm) {
        this.cpdm = cpdm;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public Date getSqrq() {
        return sqrq;
    }

    public void setSqrq(Date sqrq) {
        this.sqrq = sqrq;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public int getCplb() {
        return cplb;
    }

    public void setCplb(int cplb) {
        this.cplb = cplb;
    }

	public int getYcksl() {
		return ycksl;
	}

	public void setYcksl(int ycksl) {
		this.ycksl = ycksl;
	}

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }
}
