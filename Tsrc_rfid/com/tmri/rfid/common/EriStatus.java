package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/8.
 */
public enum EriStatus {

    NEW(0, "����"),
    AVAILABLE(1, "��Ч"),
    DISABLE(2, "ʧЧ");

    int status;
    String desc;

    EriStatus(int status, String desc) {
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
