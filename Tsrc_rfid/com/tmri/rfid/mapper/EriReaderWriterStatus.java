package com.tmri.rfid.mapper;

/**
 * Created by Joey on 2016-03-01.
 */
public enum EriReaderWriterStatus {

    NEW(0, "未激活"),
    ACTIVATED(1, "已激活"),
    INACTIVATED(2, "已注销");

    private int status;
    private String desc;

    EriReaderWriterStatus(int status, String desc) {
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
