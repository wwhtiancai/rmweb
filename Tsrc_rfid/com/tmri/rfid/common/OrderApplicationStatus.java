package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/23.
 */
public enum OrderApplicationStatus {

    SUBMIT(1, "�����"),
    APPROVE(3, "���ͨ��"),
    REJECT(2, "�ܾ�"),
    PICK(5, "���"),
    TRANSPORT(6, "����"),
    COMPLETE(7, "���"),
    CANCEL(8, "ȡ��");

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
