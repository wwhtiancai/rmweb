package com.tmri.rfid.common;
/*
 *wuweihong
 *2016-1-6
 */
public enum EriScrapStatus {
	SUBMIT(1, "未审核"),
	APPROVED(2,"审核通过"),
	REJECT(3,"审核不通过"),
    CANCEL(4, "取消"),
    DONE(5, "完成");

	private int status;
    private String desc;


    EriScrapStatus(int status, String desc) {
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
