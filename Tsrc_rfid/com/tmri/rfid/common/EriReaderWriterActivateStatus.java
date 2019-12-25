package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-01.
 */
public enum  EriReaderWriterActivateStatus {

    IN_PROGRESS(0, "������"),
    SUCCESS(1, "�ɹ�"),
    FAIL(2, "ʧ��"),
    RESET(3, "����");

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
