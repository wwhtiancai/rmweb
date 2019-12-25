package com.tmri.rfid.common;

/**
 * Created by Joey on 2016/10/11.
 */
public enum ReviewStatus {

    PENDING(0, "�����"),
    APPROVED(1, "ͬ��"),
    REJECT(2, "�ܾ�");

    private int status;
    private String desc;

    ReviewStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
