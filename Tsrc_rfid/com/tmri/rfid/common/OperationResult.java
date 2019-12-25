package com.tmri.rfid.common;

/**
 * Created by Joey on 2016-03-25.
 */
public enum OperationResult {

    NO_RESULT(0, "�޽��"),
    SUCCESS(1, "�ɹ�"),
    FAIL(2, "ʧ��"),
    PENDING(3, "�ȴ�");

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
