package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2015-10-27
 */
public class DeviceStation {
	private Long xh; //序号 通过SEQ_RFID_WORK_STATION生成
	 private String gwmc; //工位名称
	 private String gwxh; 	//工位序号
	 private Long sbxh; //所属设备 RFID_DEVICE.XH
	 private Integer zt;	//状态(0-未就绪，1-生效，2-失效)

	public String getGwmc() {
		return gwmc;
	}
	public void setGwmc(String gwmc) {
		this.gwmc = gwmc;
	}
	public String getGwxh() {
		return gwxh;
	}
	public void setGwxh(String gwxh) {
		this.gwxh = gwxh;
	}
	public Integer getZt() {
		return zt;
	}
	public void setZt(Integer zt) {
		this.zt = zt;
	}

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public Long getSbxh() {
        return sbxh;
    }

    public void setSbxh(Long sbxh) {
        this.sbxh = sbxh;
    }
}
