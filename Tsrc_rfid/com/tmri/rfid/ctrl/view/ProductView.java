package com.tmri.rfid.ctrl.view;

import com.tmri.rfid.bean.Product;

/**
 * Created by Joey on 2015/10/13.
 */
public class ProductView extends Product{

    private String cplbmc;

    public String getCplbmc() {
        return cplbmc;
    }

    public void setCplbmc(String cplbmc) {
        this.cplbmc = cplbmc;
    }
}
