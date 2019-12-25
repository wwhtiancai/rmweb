package com.tmri.rfid.common;

import com.tmri.rfid.bean.ExternalRequest;

/**
 * Created by Joey on 2016-03-23.
 */
public enum ExternalRequestStatus {

    NEW(0, "δ����"),
    DONE(1, "�Ѵ���");

    private int status;
    private String desc;

    ExternalRequestStatus(int status, String desc) {
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
