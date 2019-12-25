package com.tmri.rfid.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author wuweihong
 *
 */
public class SecurityModel{
	private String xh; //序列号
	private String lx; //类型（手持式、固定式终端、桌面式等）
	private String qulx; //权限类型代码
	private String xp1gy; //芯片1公钥
	private String xp2gy; //芯片2公钥
	private String cagysyh; //CA公钥索引号
	private String cagy;  //CA公钥
	private String xp1mybb; //芯片1认证加密密钥版本
	private String xp1yhcxbb; //芯片1用户程序版本号
	private String xp2yhcxbb; //芯片2用户程序版本号
	private String stm32gjbb; //STM32固件版本
	private Date ccrq; //出厂日期
	private String dlbbb; //电路板版本号
	private Date cshrq;  //初始化日期
	private String czr;  //操作人
	private Date scsjrq;  //上次升级日期
	private String scsjczr;  //上次升级操作人
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
	public Date getCshrq() {
		return cshrq;
	}
	public void setCshrq(Date cshrq) {
		this.cshrq = cshrq;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	
	public Date getScsjrq() {
		return scsjrq;
	}
	public void setScsjrq(Date scsjrq) {
		this.scsjrq = scsjrq;
	}
	public String getScsjczr() {
		return scsjczr;
	}
	public void setScsjczr(String scsjczr) {
		this.scsjczr = scsjczr;
	}

}
