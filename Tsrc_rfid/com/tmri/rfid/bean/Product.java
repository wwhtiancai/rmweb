package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/23.
 */
public class Product extends BaseEntity{

    private Long id;
    private String cpmc;    //��Ʒ����
    private int cplb;       //��Ʒ��𣬼�ProductCategory
    private String cpdm;    //��Ʒ����
    private String gysmc;   //��Ӧ����
    private int zt;         //״̬
    private String tzz;   //����ֵ
    
    private String cpbm;  //��Ʒ����

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
