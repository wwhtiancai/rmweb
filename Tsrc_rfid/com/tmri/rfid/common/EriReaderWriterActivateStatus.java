package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-01.
 */
public enum  EriReaderWriterActivateStatus {

    IN_PROGRESS(0, "进行中"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败"),
    RESET(3, "重置");

    private int status;
    private String desc;

    EriReaderWriterActivateStatus(int status, String desc) {
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
