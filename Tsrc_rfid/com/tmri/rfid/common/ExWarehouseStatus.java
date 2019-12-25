package com.tmri.rfid.common;
/**
 * 
 * @author stone
 * @date 2015-12-21 ����11:47:18
 */
public enum ExWarehouseStatus {
	
	SUBMIT(1, "����"),
	APPROVED(2,"���ͨ��"),
	UNAPPROVED(3,"��˲�ͨ��");
	private int status;
    private String desc;


    ExWarehouseStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
    
    public static String getName(int index) {
        for (ExWarehouseStatus c : ExWarehouseStatus.values()) {
            if (c.getStatus() == index) {
                return c.desc;
            }
        }
        return null;
    }

}
