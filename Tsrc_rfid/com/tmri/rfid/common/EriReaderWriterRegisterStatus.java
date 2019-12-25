package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-22.
 */
public enum EriReaderWriterRegisterStatus {
    UNREGISTERED(0, "δ����"),
    REGISTERED(1, "������"),
    DISABLED(2, "�ѽ���");

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
