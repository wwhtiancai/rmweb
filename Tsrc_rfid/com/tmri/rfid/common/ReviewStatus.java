package com.tmri.rfid.common;

/**
 * Created by Joey on 2016/10/11.
 */
public enum ReviewStatus {

    PENDING(0, "ÉóºËÖĞ"),
    APPROVED(1, "Í¬Òâ"),
    REJECT(2, "¾Ü¾ø");

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
