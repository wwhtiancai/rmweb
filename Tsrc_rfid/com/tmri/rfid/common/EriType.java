package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/22.
 */
public enum  EriType {

    BY_TMRI(0, "通过交通管理机关发行卡"),
    BY_AUTOMAKER(1, "新车出厂预装卡");

    private int type;
    private String desc;

    EriType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
