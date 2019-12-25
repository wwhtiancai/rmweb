package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/25.
 */
public class ExWarehouseDetail {

    private String ckdh;
    private int dw;
    private String bzhm;
    private String bzxh;
    private String bzhh;
    private int zt;
    private String bz;
    
    private String qskh;
    private String zzkh;
    
    private int sjsl;//用于出入库详情的字段

    public String getQskh() {
		return qskh;
	}

	public void setQskh(String qskh) {
		this.qskh = qskh;
	}

	public String getZzkh() {
		return zzkh;
	}

	public void setZzkh(String zzkh) {
		this.zzkh = zzkh;
	}

	public String getCkdh() {
        return ckdh;
    }

    public void setCkdh(String ckdh) {
        this.ckdh = ckdh;
    }

    public int getDw() {
		return dw;
	}

	public void setDw(int dw) {
		this.dw = dw;
	}

	public String getBzhm() {
		return bzhm;
	}

	public void setBzhm(String bzhm) {
		this.bzhm = bzhm;
	}

	public String getBzxh() {
        return bzxh;
    }

    public void setBzxh(String bzxh) {
        this.bzxh = bzxh;
    }

    public String getBzhh() {
        return bzhh;
    }

    public void setBzhh(String bzhh) {
        this.bzhh = bzhh;
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
	public int getSjsl() {
		return sjsl;
	}
	public void setSjsl(int sjsl) {
		this.sjsl = sjsl;
	}
}
