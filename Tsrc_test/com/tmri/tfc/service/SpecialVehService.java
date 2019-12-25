package com.tmri.tfc.service;

import java.util.List;

import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;
import com.tmri.tfc.bean.TfcSpecial;

/**
@author Yangzm
@data: 2014-06-04  time: обнГ04:42:56

 */
public interface SpecialVehService {
	
	public List<TfcSpecial> getSpecialVehList(TfcSpecial sv,RmLog log,PageController controller) throws Exception;
	
	public TfcSpecial getSpecial(String xh) throws Exception;
	
	public DbResult saveSpecial(TfcSpecial sv,RmLog log) throws Exception;
	
	public DbResult delSpecial(String xh,RmLog log) throws Exception;

}
