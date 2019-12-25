package com.tmri.rfid.ctrl.view;

import com.tmri.rfid.bean.ProductionTask;

import java.util.Date;

/**
 * Created by Joey on 2016/2/15.
 */
public class ProductionTaskView extends ProductionTask {

    private String rwmc;
    private String ztms;
    private String sf;
    private String qskh;
    private String zzkh;
    private String cpdm;

    public String getRwmc() {
        return rwmc;
    }

    public void setRwmc(String rwmc) {
        this.rwmc = rwmc;
    }

    public String getZtms() {
        return ztms;
    }

    public void setZtms(String ztms) {
        this.ztms = ztms;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
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

    public String getCpdm() {
        return cpdm;
    }

    public void setCpdm(String cpdm) {
        this.cpdm = cpdm;
    }
}
