package com.tmri.rfid.util;

import java.util.List;

import com.tmri.rfid.bean.DataExchangeMapping;

public class MyConstantUtils {
	 
    public static MyConstantUtils obj;
    
    public int environmentType;
    public String outPath = "out";//数据交换输出地址
    public String inPath = "in";//数据交换获取地址
    
    
    public MyConstantUtils (String type) {
    	obj = this;        
    	environmentType = Integer.parseInt(type);
    }
    
    /**
     * Spring启动的时候注入
     */
    public List<DataExchangeMapping> dataExchangeMappings;
    public List<DataExchangeMapping> getDataExchangeMappings() {
    	return dataExchangeMappings;
    }
    public void setDataExchangeMappings(List<DataExchangeMapping> dataExchangeMappings) {
    	System.out.println("===="+ dataExchangeMappings);
    	this.dataExchangeMappings = dataExchangeMappings;
    }
    //其他常量……

    public static DataExchangeMapping getByClassName(String className){
    	List<DataExchangeMapping> classNames = obj.dataExchangeMappings;
    	DataExchangeMapping dataExchangeMapping = null;
    	for(int i = 0; i < classNames.size(); i++){
    		if(classNames.get(i).getClassName().equals(className)){
    			dataExchangeMapping = classNames.get(i);
    		} 
    	}
    	return dataExchangeMapping;
    }
    
    public static DataExchangeMapping getByTableName(String tableName){
    	List<DataExchangeMapping> classNames = obj.dataExchangeMappings;
    	DataExchangeMapping dataExchangeMapping = null;
    	for(int i = 0; i < classNames.size(); i++){
    		if(classNames.get(i).getTableName().equals(tableName)){
    			dataExchangeMapping = classNames.get(i);
    		} 
    	}
    	return dataExchangeMapping;
    }
}