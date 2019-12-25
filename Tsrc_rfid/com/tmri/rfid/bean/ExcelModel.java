package com.tmri.rfid.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author stone
 * @date 2016-4-5 ����11:19:38
 */

public class ExcelModel {
	/**
	 * �ļ�·���������ǰ����ļ�����·��
	 */
	protected String path;
	/**
	 * ��������
	 */
	protected String sheetName;
	/**
	 * ��������,�����ڶ�ά��ArrayList������
	 */
	//protected ArrayList data;
	/**
	 * ���ݱ�ı�������
	 */
	//protected ArrayList header;
	/**
	 * 
	 */
	protected HashMap dataMap;
	
	/**
	 * ���������п���������� ��������ڳ�������δ�õ� �����ڹ̶������ı��
	 */
	protected int[] width;

	public ExcelModel() {
		path = "report.xls";
	}

	public ExcelModel(String path) {
		this.path = path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return this.sheetName;
	}

	/*public void setData(ArrayList data) {
		this.data = data;
	}

	public ArrayList getData() {
		return this.data;
	}

	
	public void setHeader(ArrayList header) {
		this.header = header;
	}

	public ArrayList getHeader() {
		return this.header;
	}*/

	public HashMap getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap dataMap) {
		this.dataMap = dataMap;
	}

	public void setWidth(int[] width) {
		this.width = width;
	}

	public int[] getWidth() {
		return this.width;
	}
}