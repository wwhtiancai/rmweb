package com.tmri.rfid.bean;

import java.util.Date;

/**
 * 
 * @author stone
 * @date 2016-5-18 ����3:15:08
 */
public class WarehouseCount {
	
	private Long xh; //���
    private Long count; //�������
    private String czr; //������
    private String jfdw; //������λ
    private Date rkrq; //�������
    private String bz; //��ע

    private String czrxm; //����������
    
	public Long getXh() {
		return xh;
	}
	public void setXh(Long xh) {
		this.xh = xh;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getJfdw() {
		return jfdw;
	}
	public void setJfdw(String jfdw) {
		this.jfdw = jfdw;
	}
	public Date getRkrq() {
		return rkrq;
	}
	public void setRkrq(Date rkrq) {
		this.rkrq = rkrq;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getCzrxm() {
		return czrxm;
	}
	public void setCzrxm(String czrxm) {
		this.czrxm = czrxm;
	}
    
}
