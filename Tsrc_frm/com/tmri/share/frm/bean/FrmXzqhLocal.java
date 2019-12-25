package com.tmri.share.frm.bean;

import java.io.Serializable;

import com.tmri.share.frm.util.StringUtil;

public class FrmXzqhLocal implements Serializable {

	private static final long serialVersionUID = 1992430203882243152L;
	private String xzqh;
	private String qhmc;
	private String qhsm;
	// �����ֶ�,�Ƿ�������������
	private String sfxnqh;
	// ʵ�ʶ�Ӧ��������
	private String sjxzqh;
	private String sjxzqhmc;

	private String gxsj;
	private String bz;
	private String csbj;
	private String bjcsbj;

	// �����ֶΣ����ڲ�ѯ, ά����ǣ�0��δά����1��ά��
	private String whbj;
	
	private String xzqhHead;

	public String getXzqh() {
		return StringUtil.transBlank(xzqh);
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getQhmc() {
		return StringUtil.transBlank(qhmc);
	}

	public void setQhmc(String qhmc) {
		this.qhmc = qhmc;
	}

	public String getQhsm() {
		return StringUtil.transBlank(qhsm);
	}

	public void setQhsm(String qhsm) {
		this.qhsm = qhsm;
	}

	public String getGxsj() {
		return StringUtil.transBlank(gxsj);
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getBz() {
		return StringUtil.transBlank(bz);
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCsbj() {
		return StringUtil.transBlank(csbj);
	}

	public void setCsbj(String csbj) {
		this.csbj = csbj;
	}

	public String getBjcsbj() {
		return StringUtil.transBlank(bjcsbj);
	}

	public void setBjcsbj(String bjcsbj) {
		this.bjcsbj = bjcsbj;
	}

	public String getSfxnqh() {
		return sfxnqh;
	}

	public void setSfxnqh(String sfxnqh) {		
		this.sfxnqh = sfxnqh;
	}

	public String getSjxzqh() {
		return sjxzqh;
	}

	public void setSjxzqh(String sjxzqh) {
		this.sjxzqh = sjxzqh;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWhbj() {
		return whbj;
	}

	public void setWhbj(String whbj) {
		this.whbj = whbj;
	}

	public String getWhbjmc() {
		if ("1".equals(whbj)) {
			return "��ά��";
		}
		return "δά��";
	}

	public String getXzqhHead() {
		return xzqhHead;
	}

	public void setXzqhHead(String xzqhHead) {
		this.xzqhHead = xzqhHead;
	}

	public String getSjxzqhmc() {
		return sjxzqhmc;
	}

	public void setSjxzqhmc(String sjxzqhmc) {
		this.sjxzqhmc = sjxzqhmc;
	}
}
