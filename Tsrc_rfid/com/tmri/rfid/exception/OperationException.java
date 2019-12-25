package com.tmri.rfid.exception;

/**
 * Created by Joey on 2016-05-20.
 */
public class OperationException extends Exception {

    private String operation;
    private String code;

    public OperationException(String code) {
        super();
        this.code = code;
    }

    public OperationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public OperationException(String code, String operation, String message) {
        super(message);
        this.operation = operation;
        this.code = code;
    }

    public String getOperation() {
        return operation;
    }

    public String getCode() {
        return code;
    }
}
