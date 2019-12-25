package com.tmri.rfid.common;
/*
 *st
 *2015-11-6
 */
public enum MaterialWarehouseStatus {

	UNCOMPLETE(0, "未完成"),
    COMPLETE(1, "完成"),
    CANCEL(2, "取消");

	private int status;
    private String desc;


    MaterialWarehouseStatus(int status, String desc) {
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
