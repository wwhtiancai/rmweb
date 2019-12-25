package com.tmri.rfid.service;

import java.util.List;

import com.tmri.rfid.bean.MyDataEx;

/**
 * 
 * @author stone
 * @date 2016-2-5 обнГ2:13:44
 */
public interface ZWService {

    void saveData(String sj, String sjlx, String clfs) throws Exception;
    
    List<MyDataEx> getData(String sjlx) throws Exception;

	void updateFlag(String bhs,String clbj) throws Exception;

	List<MyDataEx> getDataWithTableInfo(String sjlx) throws Exception;
}
