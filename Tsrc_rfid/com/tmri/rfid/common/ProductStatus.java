package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/28.
 */
public enum ProductStatus {

    VALID(1, "生效"),
    INVALID(0, "无效");

    private int status;
    private String desc;

    ProductStatus(int status, String desc) {
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
