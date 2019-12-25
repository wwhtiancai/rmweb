package com.tmri.rfid.service;

import java.io.IOException;
import java.util.List;

import com.tmri.rfid.bean.MyDataEx;

/**
 * 
 * @author stone
 * @date 2016-3-23 обнГ1:53:01
 */
public interface ExchangeService {

	void updateFlag(String bhs) throws Exception;
	void saveData(Object object) throws Exception;
	List<MyDataEx> fetchData() throws Exception;
	List<MyDataEx> fetchData(String sjlx) throws Exception;

}
