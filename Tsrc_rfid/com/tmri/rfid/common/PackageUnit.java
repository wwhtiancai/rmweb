package com.tmri.rfid.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by Joey on 2015/9/22.
 */
public enum  PackageUnit {

    BY_XIANG(1, "œ‰"),
    BY_HE(2, "∫–");

    private int code;
    private String name;

    PackageUnit(int code, String name) {
        this.code = code;
        this.name = name;
    }

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static String toJSONString(){
		PackageUnit[] units = PackageUnit.values();
		JSONObject joUnit = new JSONObject();
		for(int i = 0;i < units.length;i++){
			PackageUnit pu =units[i];
			joUnit.put(pu.getCode(), pu.getName());
		}
		
		return joUnit.toString();
	}
}
