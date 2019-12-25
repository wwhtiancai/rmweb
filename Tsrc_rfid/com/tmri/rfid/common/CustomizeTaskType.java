package com.tmri.rfid.common;
/*
 *wuweihong
 *2015-11-19
 */
public enum CustomizeTaskType {
	
	NEW(1, "�״�����"),
	SUPPLY(2,"����"),
	CHANGE(3,"����"),
    MODIFY(4, "���"),
    PRE_CUSTOMIZE(5, "Ԥ���Ի�"),
    ANNUAL_INSPECTION(6, "���"),
    TRANSFER(7, "����"),
    MODIFY_PASSWORD(8, "�޸Ŀ���");
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
