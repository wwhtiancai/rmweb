package com.tmri.rfid.common;

import net.sf.json.JSONObject;

/**
 * Created by Joey on 2015/10/21.
 */
public enum InventoryStatus {

    PLANNED(1, "待生产"),
    PRODUCING(2, "生产中"),
    PRODUCED(3, "生产完成"),
    STORED(4, "已入库"),
    SENDOUT(5, "已出库"),
    STOREDCORPS(9, "已入库总队"),
    CANCEL(6, "取消");

    private int status;
    private String desc;

    InventoryStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
    
    public static String toJSONString(){
    	InventoryStatus[] units = InventoryStatus.values();
		JSONObject joUnit = new JSONObject();
		for(int i = 0;i < units.length;i++){
			InventoryStatus pu =units[i];
			joUnit.put(pu.getStatus(), pu.getDesc());
		}
		
		return joUnit.toString();
	}
}
