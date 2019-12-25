package com.tmri.rfid.exception;

import java.util.Map;

/**
 * Created by Joey on 2017/4/10.
 */
public class KMSException extends Exception {

    private String resultCode;
    private String operation;

    public KMSException(String operation, Map resultMap) {
        super();
        this.operation = operation;
        resultCode = String.valueOf(((Map)resultMap.get("Result")).get("ErrorCode"));
    }

    @Override
    public String getMessage() {
        return operation + ":" + resultCode;
    }

}
