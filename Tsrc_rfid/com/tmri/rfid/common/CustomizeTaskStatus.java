package com.tmri.rfid.common;
/*
 *wuweihong
 *2015-11-17
 */
public enum CustomizeTaskStatus {

	SUBMIT(1, "����"),
	DONE(2,"���"),
	CANCEL(3,"ȡ��"),
    WAITING_DATA(4, "�ȴ�����"),
    PENDING(0, "�����");

	private int status;
    private String desc;


    CustomizeTaskStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
    
    public static String getName(int index) {
        for (CustomizeTaskStatus c : CustomizeTaskStatus.values()) {
            if (c.getStatus() == index) {
                return c.desc;
            }
        }
        return null;
    }

}
