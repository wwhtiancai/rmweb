package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-04-12.
 */
public enum DataExchangeType {

    DB("数据库"),
    FILE("文件"),
    FTP("FTP");

    private String desc;

    DataExchangeType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
