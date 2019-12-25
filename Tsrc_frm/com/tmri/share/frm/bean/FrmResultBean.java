package com.tmri.share.frm.bean;
import java.util.ArrayList;
public class FrmResultBean {
    String code;
    int rownum;
    String message;
    ArrayList objectItems;
    public ArrayList getObjectItems() {
	return objectItems;
    }

    public String getMessage() {
	return message;
    }

    public String getCode() {
	return code;
    }

    public void setRownum(int rownum) {
	this.rownum = rownum;
    }

    public void setObjectItems(ArrayList objectItems) {
	this.objectItems = objectItems;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public int getRownum() {
	return rownum;
    }

    public FrmResultBean() {
	objectItems=new ArrayList();
    }
    public void addObject(Object item)
    {
	objectItems.add(item);
    }
}
