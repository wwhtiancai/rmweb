package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/9/25.
 */
public class ExWarehouseEntry {

    private String ckdh;
    private String jbr;
    private Date ckrq;
    private String ssbm;
    private String cplb;
    private String cpdm;
    private String bz;
    private int zt;
    
    private String sqdh;
    
    private String bmmc;//部门名称
    private String jbrxm;
    
    private int cksl;//出库数量
    
    public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public int getZt() {
		return zt;
	}

	public void setZt(int zt) {
		this.zt = zt;
	}

	public String getCkdh() {
        return ckdh;
    }

    public void setCkdh(String ckdh) {
        this.ckdh = ckdh;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public Date getCkrq() {
        return ckrq;
    }

    public void setCkrq(Date ckrq) {
        this.ckrq = ckrq;
    }

    public String getSsbm() {
        return ssbm;
    }

    public void setSsbm(String ssbm) {
        this.ssbm = ssbm;
    }

    public String getCplb() {
		return cplb;
	}

	public void setCplb(String cplb) {
		this.cplb = cplb;
	}

	public String getCpdm() {
		return cpdm;
	}

	public void setCpdm(String cpdm) {
		this.cpdm = cpdm;
	}

	public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
    
	public String getSqdh() {
		return sqdh;
	}
	public void setSqdh(String sqdh) {
		this.sqdh = sqdh;
	}
	public String getJbrxm() {
		return jbrxm;
	}
	public void setJbrxm(String jbrxm) {
		this.jbrxm = jbrxm;
	}
	public int getCksl() {
		return cksl;
	}
	public void setCksl(int cksl) {
		this.cksl = cksl;
	}
    
}
