package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by st on 2015/11/6.
 */
public class MaterialWarehouse {

    private String rkdh;    //入库单号 YYYYMMDD+3位顺序号
    private String jbr;     //经办人
    private Date rkrq;      //入库日期
    private String jfdw;      //交付单位
    private int zt;         //状态，0-未完成（上传交货单后新生成的记录为此状态），1-完成（核对完成，并上传纸质交货单后为此状），2-取消（核对过程中有问题，决定取消交付为此状态）
    private String bz;      //备注
    private String dgdh;  //订购单号
    
    private String jbrxm;    //经办人姓名

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
