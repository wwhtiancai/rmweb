package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2015-10-27
 */
public class DeviceStation {
	private Long xh; //��� ͨ��SEQ_RFID_WORK_STATION����
	 private String gwmc; //��λ����
	 private String gwxh; 	//��λ���
	 private Long sbxh; //�����豸 RFID_DEVICE.XH
	 private Integer zt;	//״̬(0-δ������1-��Ч��2-ʧЧ)

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
