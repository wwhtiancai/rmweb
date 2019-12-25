package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/10/22.
 */
public enum ProductionTaskStatus {

    PLANNED(0, "������"),
    PRODUCING(1, "������"),
    PRODUCED(2, "������"),
    CANCEL(3, "��ȡ��");

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
