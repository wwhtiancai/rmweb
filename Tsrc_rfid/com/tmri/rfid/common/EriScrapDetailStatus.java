package com.tmri.rfid.common;

/**
 * Created by Joey on 2016/8/8.
 */
public enum  EriScrapDetailStatus {

    SUBMIT(1, "�ύ"),
    CANCEL(2, "ȡ��");

    private int status;
    private String desc;


    EriScrapDetailStatus(int status, String desc) {
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
