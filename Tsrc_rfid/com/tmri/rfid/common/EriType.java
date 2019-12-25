package com.tmri.rfid.common;

/**
 * Created by Joey on 2015/9/22.
 */
public enum  EriType {

    BY_TMRI(0, "ͨ����ͨ������ط��п�"),
    BY_AUTOMAKER(1, "�³�����Ԥװ��");

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
