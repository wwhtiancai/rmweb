package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/25.
 */
public class WarehouseDetail {

    private String rkdh;
    private int dw;
    private String bzhm;
    private String bzxh;
    private String bzhh;
    private int zt; //状态，1-待生产，2-生产中，3-生产完成，4-已入库，5-已出库，6-取消
    private String bz;

    private String qskh;
    private String zzkh;
    
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

	public String getRkdh() {
        return rkdh;
    }

    public void setRkdh(String rkdh) {
        this.rkdh = rkdh;
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
}
