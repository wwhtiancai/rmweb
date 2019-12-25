package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/16.
 */
public enum  EriCustomizeStatus {

    IN_PROGRESS(0, "������"),
    SUCCESS(1, "�ɹ�"),
    FAIL(2, "ʧ��"),
    CANCEL(3, "ȡ��");

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
