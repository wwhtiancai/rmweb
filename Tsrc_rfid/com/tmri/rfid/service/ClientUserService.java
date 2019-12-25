package com.tmri.rfid.service;

import com.tmri.rfid.bean.ClientUser;

/**
 * 
 * @author stone
 * @date 2015-12-4 обнГ3:49:46
 */
public interface ClientUserService {
	
	int validateSysuser(String yhdh,String mm, String strRemoteAddr) throws Exception;

}
