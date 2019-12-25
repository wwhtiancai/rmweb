package com.tmri.rfid.common;

import net.sf.json.JSONObject;

/**
 * Created by Joey on 2015/10/21.
 */
public enum InventoryStatus {

    PLANNED(1, "������"),
    PRODUCING(2, "������"),
    PRODUCED(3, "�������"),
    STORED(4, "�����"),
    SENDOUT(5, "�ѳ���"),
    STOREDCORPS(9, "������ܶ�"),
    CANCEL(6, "ȡ��");

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
