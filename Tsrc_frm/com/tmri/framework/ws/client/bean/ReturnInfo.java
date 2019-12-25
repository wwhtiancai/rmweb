package com.tmri.framework.ws.client.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/1/9.
 */
public class ReturnInfo {

    private String code = "";
    private String message = "";
    private String retCode = "";
    private int rowNum = 0;
    private List resultList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }
}
