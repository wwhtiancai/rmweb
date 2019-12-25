package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/23.
 */
public class ProductCategory {

    private int cplb; // 1 - 机动车电子标识， 2 - 读/写卡器， 其它等添加
    private String lbmc;

    public int getCplb() {
        return cplb;
    }

    public void setCplb(int cplb) {
        this.cplb = cplb;
    }

    public String getLbmc() {
        return lbmc;
    }

    public void setLbmc(String lbmc) {
        this.lbmc = lbmc;
    }
}
