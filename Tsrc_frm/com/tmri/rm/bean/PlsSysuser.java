package com.tmri.rm.bean;

import com.tmri.share.frm.util.StringUtil;
/**
*
* <p>Title: RM</p>
*
* <p>Description: �����û���Ϣ��</p>
*
* <p>Copyright: Copyright (c) 2012</p>
*
* <p>Company: TMRI.HT</p>
*
* <p>Author: Administrator</p>
*
* <p>Date: 2012-09-12 10:26:45</p>
*
*/
public class PlsSysuser {
	private String yhdh;	//�û�����
	private String xm;	//����
	private String mm;	//����
	private String glbm;	//������
	private String sfzmhm;	//���֤������
	private String ipks;	//IP��ַ��ʼ
	private String ipjs;	//IP��ַ����
	private String zhxyq;	//�ʺ���Ч��
	private String mmyxq;	//������Ч��
	private String sfmj;	//�Ƿ���
	private String rybh;	//��Ա���
	private String qxms;	//Ȩ��ģʽ
	private String zt;	//״̬
	private String zjdlsj;	//����Ǽ�ʱ��
	private String mac;	//MAC��ַ
	private String gdip;	//�̶�IP
	private String gdip1;	//�̶�IP
	private String gdip2;	//�̶�IP
	private String gxsj;	//����ʱ��
	private String bz;	//��ע
	//[�ض�����]<!--�������ڿ����û��ɼ����ض����ԣ��ض����Կ��Ա���������Զ�������ʱ������-->
	public String ztmc;
	//[/�ض�����]��

	public String getYhdh() {
		return yhdh;
	}

	public void setYhdh(String yhdh) {
		this.yhdh = yhdh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getGlbm() {
		return glbm;
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	public String getIpks() {
		return ipks;
	}

	public void setIpks(String ipks) {
		this.ipks = ipks;
	}

	public String getIpjs() {
		return ipjs;
	}

	public void setIpjs(String ipjs) {
		this.ipjs = ipjs;
	}

	public String getZhxyq() {
		return StringUtil.transBlank(zhxyq);
	}

	public void setZhxyq(String zhxyq) {
		this.zhxyq = zhxyq;
	}

	public String getMmyxq() {
		return StringUtil.transBlank(mmyxq);
	}

	public void setMmyxq(String mmyxq) {
		this.mmyxq = mmyxq;
	}

	public String getSfmj() {
		return sfmj;
	}

	public void setSfmj(String sfmj) {
		this.sfmj = sfmj;
	}

	public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
	}

	public String getQxms() {
		return qxms;
	}

	public void setQxms(String qxms) {
		this.qxms = qxms;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getZjdlsj() {
		return StringUtil.transBlank(zjdlsj);
	}

	public void setZjdlsj(String zjdlsj) {
		this.zjdlsj = zjdlsj;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getGdip() {
		return gdip;
	}

	public void setGdip(String gdip) {
		this.gdip = gdip;
	}

	public String getGdip1() {
		return gdip1;
	}

	public void setGdip1(String gdip1) {
		this.gdip1 = gdip1;
	}

	public String getGdip2() {
		return gdip2;
	}

	public void setGdip2(String gdip2) {
		this.gdip2 = gdip2;
	}

	public String getGxsj() {
		return StringUtil.transBlank(gxsj);
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getZtmc(){
		return ztmc;
	}

	public void setZtmc(String ztmc){
		this.ztmc=ztmc;
	}

}