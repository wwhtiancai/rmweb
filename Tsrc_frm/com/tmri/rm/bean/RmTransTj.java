package com.tmri.rm.bean;
import java.math.BigDecimal;

import com.tmri.share.frm.util.StringUtil;
/**
 *
 * <p>Title: RMS</p>
 *
 * <p>Description: ���ݴ������</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: TMRI.HT</p>
 *
 * <p>Author: Administrator</p>
 *
 * <p>Date: 2013-06-26 14:30:07</p>
 *
 */
public class RmTransTj {
	private String xh;	//���
	private String fzjg;	//��֤����
	private String sjlx;	//��������
	private String jcsj;	//���ʱ��
	private String csrq;	//��������
	private BigDecimal cgsl;	//����ɹ�����
	private BigDecimal sbsl;	//����ʧ������
	private BigDecimal jysl;	//��ѹ����
	//[�ض�����]<!--�������ڿ����û��ɼ����ض����ԣ��ض����Կ��Ա���������Զ�������ʱ������-->
	
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
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public String getJcsj() {
		return StringUtil.transBlank(jcsj);
	}
	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}
	public String getCsrq() {
		return StringUtil.transBlank(csrq);
	}
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	public BigDecimal getCgsl() {
		return cgsl;
	}
	public void setCgsl(BigDecimal cgsl) {
		this.cgsl = cgsl;
	}
	public BigDecimal getSbsl() {
		return sbsl;
	}
	public void setSbsl(BigDecimal sbsl) {
		this.sbsl = sbsl;
	}
	public BigDecimal getJysl() {
		return jysl;
	}
	public void setJysl(BigDecimal jysl) {
		this.jysl = jysl;
	}
}