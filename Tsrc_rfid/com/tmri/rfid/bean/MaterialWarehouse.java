package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by st on 2015/11/6.
 */
public class MaterialWarehouse {

    private String rkdh;    //��ⵥ�� YYYYMMDD+3λ˳���
    private String jbr;     //������
    private Date rkrq;      //�������
    private String jfdw;      //������λ
    private int zt;         //״̬��0-δ��ɣ��ϴ��������������ɵļ�¼Ϊ��״̬����1-��ɣ��˶���ɣ����ϴ�ֽ�ʽ�������Ϊ��״����2-ȡ�����˶Թ����������⣬����ȡ������Ϊ��״̬��
    private String bz;      //��ע
    private String dgdh;  //��������
    
    private String jbrxm;    //����������

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

	public String getJfdw() {
		return jfdw;
	}

	public void setJfdw(String jfdw) {
		this.jfdw = jfdw;
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
    
}
