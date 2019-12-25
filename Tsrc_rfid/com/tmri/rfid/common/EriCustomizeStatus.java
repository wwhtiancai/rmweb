package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/16.
 */
public enum  EriCustomizeStatus {

    IN_PROGRESS(0, "进行中"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败"),
    CANCEL(3, "取消");

    private int status;
    private String desc;

    private EriCustomizeStatus(int status, String desc) {
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
