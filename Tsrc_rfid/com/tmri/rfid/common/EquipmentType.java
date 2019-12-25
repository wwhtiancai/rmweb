package com.tmri.rfid.common;
/*
 *wuweihong
 *2016-2-15
 */
public enum EquipmentType {
	OK(0,"ÆôÓÃ"),
	STOP(1,"½ûÓÃ");
	private int status;
    private String desc;


    EquipmentType(int status, String desc) {
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
