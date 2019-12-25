package com.tmri.rfid.common;

import com.tmri.rfid.bean.ElectronicCertificate;

/**
 * Created by Joey on 2015/11/13.
 */
public enum  ElectronicCertificateSubjectType {

    READER_WRITER(1, "¶ÁÐ´Æ÷"),
    SECURITY_MODEL(2, "°²È«Ä£¿é");

    private int type;
    private String desc;

    ElectronicCertificateSubjectType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
