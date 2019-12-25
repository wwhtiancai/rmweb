package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-23.
 */
public enum EriCustomizeRequestStatus {

    NEW(0, "新生成"),
    FETCHING(1, "获取数据中"),
    FETCHED(2, "数据已获取"),
    WRITTEN(3, "已完成写入"),
    FAIL(4, "请求失败"),
    CANCELLED(5, "取消");

    private int status;
    private String desc;

    EriCustomizeRequestStatus(int status, String desc) {
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
