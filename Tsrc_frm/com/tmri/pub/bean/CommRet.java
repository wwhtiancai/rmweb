package com.tmri.pub.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Hashtable;
/**
 * 通用查询公共返回信息
 * @author Administrator
 * 
 *
 */
public class CommRet extends Hashtable implements Serializable {
	// 返回记录数,缺省未返回,-2表示验证未通过
	private int rownum = 0;         
	//返回的错误信息
	private String message="";      
	// 存放主表以及子表信息（存放的hashmap）
	
	private List itemlist;
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List getItemlist() {
		return itemlist;
	}
	public void setItemlist(List itemlist) {
		this.itemlist = itemlist;
	}

	
	
}

