package com.tmri.rm.bean;
/**
 *
 * <p>Title: DC</p>
 *
 * <p>Description: ��֧��URL��</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2010-10-29 09:52:27</p>
 *
 */
public class CodeUrl {
	private String dwdm;	//��λ����
	private String url;	//URI·��
	private String port;	//�˿�
	private String context;	//������
	private String jb;	//����:1-����,2-�ܶ�,3-֧��,4-���,5-�ж�
	private String jdmc;	//�ڵ�����
	private String sn;	//sn���к�
	private String sjjd;	//�ϼ��ڵ�
	private String bz;	//��ע
	//�����ݿ��ֶε�JavaBean���ԣ���ʹ��Public������

	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getJb() {
		return jb;
	}
	public void setJb(String jb) {
		this.jb = jb;
	}
	public String getJdmc() {
		return jdmc;
	}
	public void setJdmc(String jdmc) {
		this.jdmc = jdmc;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSjjd() {
		return sjjd;
	}
	public void setSjjd(String sjjd) {
		this.sjjd = sjjd;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}