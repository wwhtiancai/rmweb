package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-04-12.
 */
public enum DataExchangeType {

    DB("���ݿ�"),
    FILE("�ļ�"),
    FTP("FTP");

    private String desc;

    DataExchangeType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
