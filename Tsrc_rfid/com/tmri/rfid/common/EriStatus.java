package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/8.
 */
public enum EriStatus {

    NEW(0, "出厂"),
    AVAILABLE(1, "生效"),
    DISABLE(2, "失效");

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
