package com.tmri.rfid.common;
/*
 *st
 *2015-11-3
 */
public enum MaterialApplyStatus {

	SUBMIT(1, "正在生产"),
    COMPLETE(2, "交付"),
    CANCEL(3, "撤销");

	private int status;
    private String desc;


    MaterialApplyStatus(int status, String desc) {
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
