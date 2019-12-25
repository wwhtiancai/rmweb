package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/10/22.
 */
public enum ProductionTaskStatus {

    PLANNED(0, "待生产"),
    PRODUCING(1, "生产中"),
    PRODUCED(2, "已生产"),
    CANCEL(3, "已取消");

    private int status;
    private String desc;

    ProductionTaskStatus(int status, String desc) {
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
