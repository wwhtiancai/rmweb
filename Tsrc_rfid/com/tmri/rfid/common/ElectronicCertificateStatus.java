package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/11/13.
 */
public enum ElectronicCertificateStatus {

    INVALID(0, "无效"),
    VALID(1, "有效");

    private int status;
    private String desc;

    ElectronicCertificateStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
