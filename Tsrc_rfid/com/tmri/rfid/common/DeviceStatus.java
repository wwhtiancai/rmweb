package com.tmri.rfid.common;
/*
 *wuweihong
 *2015-10-24
 */
public enum DeviceStatus {
	NOT_READY(0, "未就绪"),
	IS_OK(1,"生效"),
	IS_LOSE(2,"失效");
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
