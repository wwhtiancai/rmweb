package com.tmri.rfid.ctrl.view;

import com.tmri.rfid.bean.OrderApplication;

import java.util.Date;

/**
 * Created by Joey on 2015/9/29.
 */
public class OrderApplicationView extends OrderApplication {

    private String bmmc;    //��������
    private String lbmc;    //�������
    private String cpmc;    //��Ʒ����
    private String ztms;    //״̬����

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getLbmc() {
        return lbmc;
    }

    public void setLbmc(String lbmc) {
        this.lbmc = lbmc;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getZtms() {
        return ztms;
    }

    public void setZtms(String ztms) {
        this.ztms = ztms;
    }
}
