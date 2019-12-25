package com.tmri.rfid.service.impl;

import com.tmri.rfid.service.PlateNumberHandler;

/**
 * Created by Joey on 2015/9/25.
 */
public class PolicePlateNumberHandler implements PlateNumberHandler {

    private String identification = "警";

    @Override
    public String compose(String fzjg, String hphm) throws Exception{
        String cphm = hphm;
        if (!cphm.endsWith(identification)) {
            cphm = cphm + identification;
        }
        if (cphm.length() > 7 || cphm.length() < 5) {
            throw new Exception("号牌号码不正确");
        } else if (cphm.length() == 5) {
            return fzjg + cphm;
        } else if (cphm.length() == 6){
            return fzjg + cphm.substring(1);
        } else {
            return cphm;
        }
    }

    @Override
    public String getHpxh(String cphm) throws Exception{
        String hpxh = cphm;
        if (hpxh.endsWith(identification)) {
            hpxh = hpxh.substring(0, hpxh.length() - 1);
        }
        if (hpxh.length() > 6 || hpxh.length() < 5) {
            throw new Exception("车牌号码不正确");
        } else if (hpxh.length() == 6) {
            return hpxh.substring(1, 5);
        } else {
            return hpxh;
        }
    }
}
