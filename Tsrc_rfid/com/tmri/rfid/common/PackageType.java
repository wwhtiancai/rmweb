package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/10/13.
 */
public enum  PackageType {

    CHEST(1, "Ïä"),
    BOX(2, "ºÐ"),
    PIECE(3, "µ¥¸ö");

    private int type;
    private String desc;

    PackageType(int type, String desc) {
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
