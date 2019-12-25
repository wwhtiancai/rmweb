package com.tmri.rfid.common;
/*
 *st
 *2015-11-6
 */
public enum MaterialWarehouseStatus {

	UNCOMPLETE(0, "δ���"),
    COMPLETE(1, "���"),
    CANCEL(2, "ȡ��");

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
