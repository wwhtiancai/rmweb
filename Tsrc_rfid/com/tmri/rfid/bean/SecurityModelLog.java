package com.tmri.rfid.bean;

import java.util.Date;

/*
 *wuweihong
 *2015-11-4
 */
public class SecurityModelLog {
	private Integer id; //��������SEQ_RFID_SECURITY_MODEL_OPERATION����
	private String xh; //���к�
	private String lx; //���ͣ��ֳ�ʽ���̶�ʽ�նˡ�����ʽ�ȣ�
	private String qulx; //Ȩ�����ʹ���
	private String xp1gy; //оƬ1��Կ
	private String xp2gy; //оƬ2��Կ
	private String cagysyh; //CA��Կ������
	private String cagy;  //CA��Կ
	private String xp1mybb; //оƬ1��֤������Կ�汾
	private String xp1yhcxbb; //оƬ1�û�����汾��
	private String xp2yhcxbb; //оƬ2�û�����汾��
	private String stm32gjbb; //STM32�̼��汾
	private Date ccrq; //��������
	private String dlbbb; //��·��汾��
	private Integer czlx;  //�������� 1-��ʼ����2-����
	private String czr;  //������
	private Date czrq;  //��������
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getQulx() {
		return qulx;
	}
	public void setQulx(String qulx) {
		this.qulx = qulx;
	}
	public String getXp1gy() {
		return xp1gy;
	}
	public void setXp1gy(String xp1gy) {
		this.xp1gy = xp1gy;
	}
	public String getXp2gy() {
		return xp2gy;
	}
	public void setXp2gy(String xp2gy) {
		this.xp2gy = xp2gy;
	}
	public String getCagysyh() {
		return cagysyh;
	}
	public void setCagysyh(String cagysyh) {
		this.cagysyh = cagysyh;
	}
	public String getCagy() {
		return cagy;
	}
	public void setCagy(String cagy) {
		this.cagy = cagy;
	}
	public String getXp1mybb() {
		return xp1mybb;
	}
	public void setXp1mybb(String xp1mybb) {
		this.xp1mybb = xp1mybb;
	}
	public String getXp1yhcxbb() {
		return xp1yhcxbb;
	}
	public void setXp1yhcxbb(String xp1yhcxbb) {
		this.xp1yhcxbb = xp1yhcxbb;
	}
	public String getXp2yhcxbb() {
		return xp2yhcxbb;
	}
	public void setXp2yhcxbb(String xp2yhcxbb) {
		this.xp2yhcxbb = xp2yhcxbb;
	}
	public String getStm32gjbb() {
		return stm32gjbb;
	}
	public void setStm32gjbb(String stm32gjbb) {
		this.stm32gjbb = stm32gjbb;
	}
	public Date getCcrq() {
		return ccrq;
	}
	public void setCcrq(Date ccrq) {
		this.ccrq = ccrq;
	}
	public String getDlbbb() {
		return dlbbb;
	}
	public void setDlbbb(String dlbbb) {
		this.dlbbb = dlbbb;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCzlx() {
		return czlx;
	}
	public void setCzlx(Integer czlx) {
		this.czlx = czlx;
	}
	public Date getCzrq() {
		return czrq;
	}
	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}
	

}