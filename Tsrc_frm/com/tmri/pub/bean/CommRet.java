package com.tmri.pub.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Hashtable;
/**
 * ͨ�ò�ѯ����������Ϣ
 * @author Administrator
 * 
 *
 */
public class CommRet extends Hashtable implements Serializable {
	// ���ؼ�¼��,ȱʡδ����,-2��ʾ��֤δͨ��
	private int rownum = 0;         
	//���صĴ�����Ϣ
	private String message="";      
	// ��������Լ��ӱ���Ϣ����ŵ�hashmap��
	
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

