package com.tmri.rfid.ctrl.view;

import com.tmri.rfid.bean.ProductApply;

/**
 * Created by Joey on 2015/10/9.
 */
public class ProductApplyView extends ProductApply {

    private String lybmmc;
    private String glbmmc;

    public String getLybmmc() {
        return lybmmc;
    }

    public void setLybmmc(String lybmmc) {
        this.lybmmc = lybmmc;
    }

    public String getGlbmmc() {
        return glbmmc;
    }

    public void setGlbmmc(String glbmmc) {
        this.glbmmc = glbmmc;
    }
}
