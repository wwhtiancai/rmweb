package com.tmri.rfid.bean;

import java.util.Date;

public class MaterialInventory {

	private String xh;//主键，由序列SEQ_RFID_RAW_ERI_INVENTORY生成
    private String bzxh;//包装箱号  由厂商提供，只记录，与成品包装箱号不同
    private String bzhh;//包装盒号  由厂商提供，只记录，与成品包装盒号不同
    private String rkdh;//RFID_RAW_ERI_INVENTORY_IN.RKDH
    private int zt;//状态 1-入库，2-领用
    private String lyr;//领用人
    private Date lyrq;//领用日期
    private String gwh;//工位号 RFID_WORK_STATION.XH
    private String rwh;//任务号 RFID_PRODUCTION_TASK.RWH
    
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
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
	public String getRkdh() {
		return rkdh;
	}
	public void setRkdh(String rkdh) {
		this.rkdh = rkdh;
	}
	public int getZt() {
		return zt;
	}
	public void setZt(int zt) {
		this.zt = zt;
	}
	public String getLyr() {
		return lyr;
	}
	public void setLyr(String lyr) {
		this.lyr = lyr;
	}
	public Date getLyrq() {
		return lyrq;
	}
	public void setLyrq(Date lyrq) {
		this.lyrq = lyrq;
	}
	public String getGwh() {
		return gwh;
	}
	public void setGwh(String gwh) {
		this.gwh = gwh;
	}
	public String getRwh() {
		return rwh;
	}
	public void setRwh(String rwh) {
		this.rwh = rwh;
	}
    

}
