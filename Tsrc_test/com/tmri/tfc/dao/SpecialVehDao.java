package com.tmri.tfc.dao;

import java.util.List;

import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;
import com.tmri.tfc.bean.TfcSpecial;

/**
@author Yangzm
@data: 2014-06-04  time: ÏÂÎç04:45:32

 */
public interface SpecialVehDao extends FrmDao{
	
	public List<TfcSpecial> getSpecialVehList(TfcSpecial sv,PageController controller) throws Exception;
	
	public TfcSpecial getSpecial(String xh) throws Exception;
	
	public DbResult setSpecial(TfcSpecial bean) throws Exception;
	
	public DbResult saveSpecialVeh(String hpxx) throws Exception;
	
	public DbResult delSpecial() throws Exception;
	
	
}
