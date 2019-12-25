package com.tmri.rfid.common;
/**
 * 
 * @author stone
 * @date 2016-3-24 ÏÂÎç3:08:43
 */
public enum ClassTableName {

	ERIEQUIPMENTBEAN( "com.tmri.rfid.bean.EriEquipmentBean","RFID_ERI_EQUIPMENT_APPLY","xh"),
	REQUEST( "com.tmri.rfid.bean.EriCustomizeRequest","RFID_ERI_CUSTOMIZE_REQUEST","xh"),
	EXTERNAL_REQUEST("com.tmri.rfid.bean.ExternalRequest", "RFID_EXTERNAL_REQUEST", "xh"),
	CUSTOMIZE_DATA("com.tmri.rfid.bean.EriCustomizeData", "RFID_ERI_CUSTOMIZE_DATA", "xh");

	private String className;
    private String tableName;
    private String keyName;

    ClassTableName(String className, String tableName, String keyName) {
        this.className = className;
        this.tableName = tableName;
        this.keyName = keyName;
    }
    public String getClassName() {
        return className;
    }

    public String getTableName() {
        return tableName;
    }
    
    public String getkeyName() {
        return keyName;
    }
    
    public static ClassTableName getByClassName(String className){
    	ClassTableName[] classNames = ClassTableName.values();
    	ClassTableName classTableName = null;
    	for(int i = 0; i < classNames.length; i++){
    		if(className.equals(classNames[i].getClassName())){
    			classTableName = classNames[i];
    		} 
    	}
    	return classTableName; 
    }
    
    public static ClassTableName getByTableName(String tableName){
    	ClassTableName[] classNames = ClassTableName.values();
    	ClassTableName classTableName = null;
    	for(int i = 0; i < classNames.length; i++){
    		if(tableName.equals(classNames[i].getTableName())){
    			classTableName = classNames[i];
    		} 
    	}
    	return classTableName; 
    }
    
    public static void main(String[] args){
    	ClassTableName classTableName = getByClassName("com.tmri.rfid.bean.Inventory");
    	System.out.println(classTableName.getTableName()+"===="+classTableName.getkeyName());
    	
    }
}
