package com.tmri.rfid.bean;

/**
 * Created by Joey on 2015/9/23.
 */
public class ProductCategory {

    private int cplb; // 1 - ���������ӱ�ʶ�� 2 - ��/д������ ���������
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
