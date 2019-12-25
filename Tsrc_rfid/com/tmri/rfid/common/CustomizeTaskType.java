package com.tmri.rfid.common;
/*
 *wuweihong
 *2015-11-19
 */
public enum CustomizeTaskType {
	
	NEW(1, "首次申领"),
	SUPPLY(2,"补领"),
	CHANGE(3,"换领"),
    MODIFY(4, "变更"),
    PRE_CUSTOMIZE(5, "预个性化"),
    ANNUAL_INSPECTION(6, "年检"),
    TRANSFER(7, "过户"),
    MODIFY_PASSWORD(8, "修改口令");
	private int type;
    private String desc;

    CustomizeTaskType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getName(int index) {
        for (CustomizeTaskType c : CustomizeTaskType.values()) {
            if (c.getType() == index) {
                return c.desc;
            }
        }
        return null;
    }

    public static CustomizeTaskType getByType(int type) {
        for (CustomizeTaskType customizeTaskType : CustomizeTaskType.values()) {
            if (customizeTaskType.getType() == type) return customizeTaskType;
        }
        return null;
    }
}
