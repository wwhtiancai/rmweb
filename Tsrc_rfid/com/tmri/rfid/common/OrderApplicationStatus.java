package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/23.
 */
public enum OrderApplicationStatus {

    SUBMIT(1, "待审核"),
    APPROVE(3, "审核通过"),
    REJECT(2, "拒绝"),
    PICK(5, "拣货"),
    TRANSPORT(6, "运输"),
    COMPLETE(7, "完成"),
    CANCEL(8, "取消");

    private int status;
    private String desc;

    OrderApplicationStatus(int status, String desc) {
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
