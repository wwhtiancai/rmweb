package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/22.
 */
public enum EriInitializeStatus {

    IN_PROGRESS(0, "������"),
    SUCCESS(1, "�ɹ�"),
    FAIL(2, "ʧ��");

    private int status;
    private String desc;

    EriInitializeStatus(int status, String desc) {
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
