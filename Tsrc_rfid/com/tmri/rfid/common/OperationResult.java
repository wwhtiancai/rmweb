package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-25.
 */
public enum OperationResult {

    NO_RESULT(0, "无结果"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败"),
    PENDING(3, "等待");

    private int result;
    private String desc;

    OperationResult(int result, String desc) {
        this.result = result;
        this.desc = desc;
    }

    public int getResult() {
        return result;
    }

    public String getDesc() {
        return desc;
    }
}
