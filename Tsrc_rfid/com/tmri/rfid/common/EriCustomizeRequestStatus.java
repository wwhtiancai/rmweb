package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-23.
 */
public enum EriCustomizeRequestStatus {

    NEW(0, "������"),
    FETCHING(1, "��ȡ������"),
    FETCHED(2, "�����ѻ�ȡ"),
    WRITTEN(3, "�����д��"),
    FAIL(4, "����ʧ��"),
    CANCELLED(5, "ȡ��");

    private int status;
    private String desc;

    EriCustomizeRequestStatus(int status, String desc) {
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
