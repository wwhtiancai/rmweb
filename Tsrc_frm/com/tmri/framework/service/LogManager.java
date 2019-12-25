package com.tmri.framework.service;

import java.util.List;

import com.tmri.framework.bean.FrmLoginlog;
import com.tmri.framework.bean.Log;
import com.tmri.share.frm.util.PageController;

/**
 * 定义日志管理应该具有的功能
 * 
 * @author Administrator
 * 
 */
public interface LogManager{
	
	//public List getLogsByPagesize(UserSession userSession,Log log, PageController controller) throws Exception;
    public List getLogList(Log obj)throws Exception;
    public List getLogList(Log obj, PageController controller)throws Exception;
    public List getBaslogtrackList(Log obj)throws Exception;
    public List getFrmlogtrackList(Log obj,PageController controller)throws Exception;
    public Log getLog(Log obj)throws Exception;	
    public String getJkmc(String jkid);
    public FrmLoginlog getUserLogin(String yhdh) throws Exception;
    
}
