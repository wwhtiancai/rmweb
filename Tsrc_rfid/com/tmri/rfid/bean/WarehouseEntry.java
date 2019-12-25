package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2015/9/22.
 */
public class WarehouseEntry {

    private String rkdh;
    private String jbr;
    private Date rkrq;
    private String ssbm;
    private String cplb;
    private String cpdm;
    private String bz;
    
    private String bmmc;
    private String jbrxm;

    public String getRkdh() {
        return rkdh;
    }

    public void setRkdh(String rkdh) {
        this.rkdh = rkdh;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public Date getRkrq() {
        return rkrq;
    }

    public void setRkrq(Date rkrq) {
        this.rkrq = rkrq;
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

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getJbrxm() {
		return jbrxm;
	}

	public void setJbrxm(String jbrxm) {
		this.jbrxm = jbrxm;
	}
    
}
