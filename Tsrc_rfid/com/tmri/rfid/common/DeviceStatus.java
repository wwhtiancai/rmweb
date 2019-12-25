package com.tmri.rfid.common;
/*
 *wuweihong
 *2015-10-24
 */
public enum DeviceStatus {
	NOT_READY(0, "δ����"),
	IS_OK(1,"��Ч"),
	IS_LOSE(2,"ʧЧ");
	private int status;
    private String desc;


    DeviceStatus(int status, String desc) {
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
