package com.tmri.rm.bean;
import java.math.BigDecimal;

import com.tmri.share.frm.util.StringUtil;
/**
 *
 * <p>Title: RMS</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2013-06-28 14:58:54</p>
 *
 */
public class RmTransTasklog {
	private String xh;	//���
	private String fzjg;	//��֤����
	private String azdm;	//��װ����
	private String rwid;	//����ID
	private String rwmc;	//��������
	private String jqmc;	//��������
	private String sjlx;	//��������
	private String sjmc;    //��������
	private String jcsj;	//���ʱ��
	private String kssj;	//��ʼʱ��
	private String jssj;	//����ʱ��
	private String flag;	//0-ʧ��;1-�ɹ�
	private String cwdm;	//�������
	private String fhxx;	//������Ϣ
	private BigDecimal jysl;	//��ѹ����
	//[�ض�����]<!--�������ڿ����û��ɼ����ض����ԣ��ض����Կ��Ա���������Զ�������ʱ������-->
	private String azdmc;   //��װ������
	private BigDecimal jy1;	//��ѹ1
	private BigDecimal jy2;	//��ѹ2
	//[/�ض�����]��

	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public String getAzdm() {
		return azdm;
	}
	public void setAzdm(String azdm) {
		this.azdm = azdm;
	}
	public String getRwid() {
		return rwid;
	}
	public void setRwid(String rwid) {
		this.rwid = rwid;
	}
	public String getRwmc() {
		return rwmc;
	}
	public void setRwmc(String rwmc) {
		this.rwmc = rwmc;
	}
	public String getJqmc() {
		return jqmc;
	}
	public void setJqmc(String jqmc) {
		this.jqmc = jqmc;
	}
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public String getSjmc() {
		return sjmc;
	}
	public void setSjmc(String sjmc) {
		this.sjmc = sjmc;
	}
	public String getJcsj() {
		return StringUtil.transBlank(jcsj);
	}
	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}
	public String getKssj() {
		return StringUtil.transBlank(kssj);
	}
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	public String getJssj() {
		return StringUtil.transBlank(jssj);
	}
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCwdm() {
		return cwdm;
	}
	public void setCwdm(String cwdm) {
		this.cwdm = cwdm;
	}
	public String getFhxx() {
		return StringUtil.transScriptStr(fhxx);
	}
	public void setFhxx(String fhxx) {
		this.fhxx = fhxx;
	}
	public BigDecimal getJysl() {
		return jysl;
	}
	public void setJysl(BigDecimal jysl) {
		this.jysl = jysl;
	}
	
	public String getAzdmc() {
		return azdmc;
	}
	public void setAzdmc(String azdmc) {
		this.azdmc = azdmc;
	}
	
	public BigDecimal getJy1() {
		return jy1;
	}
	public void setJy1(BigDecimal jy1) {
		this.jy1 = jy1;
	}
	public BigDecimal getJy2() {
		return jy2;
	}
	public void setJy2(BigDecimal jy2) {
		this.jy2 = jy2;
	}
}