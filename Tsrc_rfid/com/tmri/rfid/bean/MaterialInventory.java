package com.tmri.rfid.bean;

import java.util.Date;

public class MaterialInventory {

	private String xh;//������������SEQ_RFID_RAW_ERI_INVENTORY����
    private String bzxh;//��װ���  �ɳ����ṩ��ֻ��¼�����Ʒ��װ��Ų�ͬ
    private String bzhh;//��װ�к�  �ɳ����ṩ��ֻ��¼�����Ʒ��װ�кŲ�ͬ
    private String rkdh;//RFID_RAW_ERI_INVENTORY_IN.RKDH
    private int zt;//״̬ 1-��⣬2-����
    private String lyr;//������
    private Date lyrq;//��������
    private String gwh;//��λ�� RFID_WORK_STATION.XH
    private String rwh;//����� RFID_PRODUCTION_TASK.RWH
    
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
