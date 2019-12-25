package com.tmri.rfid.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * Created by st on 2015/9/24.
 */
public class Inventory implements RowMapper {

	private String bzhh; // 包装盒号
    private String qskh; // 起始卡号
    private String zzkh; // 终止卡号
    private String ssbm; // 所属部门
    private String cpdm; // 产品代码
    private int zt; //状态
    private String bzxh;    //包装箱号
    private String rwh; //计划号

    private String cplb; //产品类别
    private String cplbmc; //产品类别名称
    private String cpmc; //产品名称
    private String ssbmmc; // 所属部门名称
    
    private int sjsl;//用于出入库详情的字段
    
	public String getBzhh() {
		return bzhh;
	}
	public void setBzhh(String bzhh) {
		this.bzhh = bzhh;
	}
	public String getQskh() {
		return qskh;
	}
	public void setQskh(String qskh) {
		this.qskh = qskh;
	}
	public String getZzkh() {
		return zzkh;
	}
	public void setZzkh(String zzkh) {
		this.zzkh = zzkh;
	}
	public String getSsbm() {
		return ssbm;
	}
	public void setSsbm(String ssbm) {
		this.ssbm = ssbm;
	}
	public String getCpdm() {
		return cpdm;
	}
	public void setCpdm(String cpdm) {
		this.cpdm = cpdm;
	}

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public String getBzxh() {
        return bzxh;
    }

    public void setBzxh(String bzxh) {
        this.bzxh = bzxh;
    }

    public String getRwh() {
        return rwh;
    }

    public void setRwh(String rwh) {
        this.rwh = rwh;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getSsbmmc() {
        return ssbmmc;
    }

    public void setSsbmmc(String ssbmmc) {
        this.ssbmmc = ssbmmc;
    }

    public String getCplb() {
		return cplb;
	}
	public void setCplb(String cplb) {
		this.cplb = cplb;
	}
	public String getCplbmc() {
        return cplbmc;
    }

    public void setCplbmc(String cplbmc) {
        this.cplbmc = cplbmc;
    }
    
    public int getSjsl() {
		return sjsl;
	}
	public void setSjsl(int sjsl) {
		this.sjsl = sjsl;
	}
	
	@Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
		Inventory inventory = new Inventory();
		
		inventory.setBzhh(resultSet.getString("bzhh"));
		inventory.setQskh(resultSet.getString("qskh"));
		inventory.setZzkh(resultSet.getString("zzkh"));
		inventory.setSsbm(resultSet.getString("ssbm"));
		inventory.setCpdm(resultSet.getString("cpdm"));
		
        return inventory;
    }
}
