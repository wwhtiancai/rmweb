package com.tmri.pub.bean.veh;

import java.util.ArrayList;

public class VehStole {
	private String retCode;
	private String rowCount;
	private String error;
	private ArrayList vehStoleItems = new ArrayList();

	public VehStole() {
	}

	public void addVehStoleItem(VehStoleItem item) {
		vehStoleItems.add(item);
	}

	public ArrayList getVehStoleItems() {
		return vehStoleItems;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
