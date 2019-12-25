package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/23.
 */
public class Product extends BaseEntity{

    private Long id;
    private String cpmc;    //产品名称
    private int cplb;       //产品类别，见ProductCategory
    private String cpdm;    //产品代码
    private String gysmc;   //供应名称
    private int zt;         //状态
    private String tzz;   //特征值
    
    private String cpbm;  //产品编码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public int getCplb() {
        return cplb;
    }

    public void setCplb(int cplb) {
        this.cplb = cplb;
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

	public String getGysmc() {
		return gysmc;
	}

	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
	}

	public String getTzz() {
		return tzz;
	}

	public void setTzz(String tzz) {
		this.tzz = tzz;
	}

	public String getCpbm() {
		return cpbm;
	}

	public void setCpbm(String cpbm) {
		this.cpbm = cpbm;
	}
    
}
