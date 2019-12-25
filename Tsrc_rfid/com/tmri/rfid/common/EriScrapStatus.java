package com.tmri.rfid.common;
/*
 *wuweihong
 *2016-1-6
 */
public enum EriScrapStatus {
	SUBMIT(1, "δ���"),
	APPROVED(2,"���ͨ��"),
	REJECT(3,"��˲�ͨ��"),
    CANCEL(4, "ȡ��"),
    DONE(5, "���");

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
