package com.tmri.framework.dao;

import java.util.List;

import com.tmri.framework.bean.FrmLoginlog;
import com.tmri.framework.bean.Log;
import com.tmri.share.frm.util.PageController;

public interface LogDao {
    public List getLogList(Log obj)throws Exception;
    public List getLogList(Log obj, PageController controller)throws Exception;
    public Log getLog(Log obj)throws Exception;
    public List getQueryerrList(Log obj,String gpfwfz)throws Exception;
	//public List getLogsByPagesize(UserSession userSession,Log log, PageController controller) throws SQLException;
    public String getJkmc(String jkid) throws Exception;
    public FrmLoginlog getUserLogin(String yhdh) throws Exception;
    public List getBaslogtrackList(Log obj);
  //²Ù×÷ÈÕÖ¾
    public List getFrmlogtrackList(Log obj,PageController controller);
}
