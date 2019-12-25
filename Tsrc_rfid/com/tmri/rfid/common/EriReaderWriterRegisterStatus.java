package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-22.
 */
public enum EriReaderWriterRegisterStatus {
    UNREGISTERED(0, "未启用"),
    REGISTERED(1, "已启用"),
    DISABLED(2, "已禁用");

    private int status;
    private String desc;

    EriReaderWriterRegisterStatus(int status, String desc) {
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
