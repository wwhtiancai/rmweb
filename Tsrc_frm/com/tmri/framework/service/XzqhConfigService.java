package com.tmri.framework.service;

import java.util.List;
import java.util.Map;

import com.tmri.framework.bean.XzqhTransBean;
import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.bean.RmLog;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.ora.bean.DbResult;

public interface XzqhConfigService {

	List<FrmXzqhLocal> getFrmXzqhLocal(FrmXzqhLocal bean,
			PageController controller) throws Exception;

	List<BasAllxzqh> getBasAllXzqhList(FrmXzqhLocal bean) throws Exception;
	
	Map<XzqhTransBean, List<BasAllxzqh>>  getBasAllXzqhMap(FrmXzqhLocal bean) throws Exception;

	DbResult saveXzqhList(List<FrmXzqhLocal> xzqhList, RmLog log) throws Exception;

}
