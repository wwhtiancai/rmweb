package com.tmri.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.framework.bean.FrmLoginlog;
import com.tmri.framework.bean.Log;
import com.tmri.framework.dao.LogDao;
import com.tmri.framework.service.LogManager;
import com.tmri.share.frm.util.PageController;
@Service

public class LogManagerImpl extends FrmManagerImpl implements LogManager {
	@Autowired
    private LogDao logDao;

//    public LogDao getLogDao() {
//        return logDao;
//    }
//
//    public void setLogDao(LogDao logDao) {
//        this.logDao = logDao;
//    }
    
    public Log getLog(Log obj)throws Exception{
        Log log=this.logDao.getLog(obj);
        return log;
    }
    
    public List getLogList(Log obj)throws Exception{
        List loglist=this.logDao.getLogList(obj);
        return loglist;
    }
    
    
    public List getLogList(Log obj, PageController controller)throws Exception{
        List loglist=this.logDao.getLogList(obj,controller);
        return loglist;
    }
    
    public List getBaslogtrackList(Log obj)throws Exception{
        List loglist=this.logDao.getBaslogtrackList(obj);
        return loglist;
    }   
    
    public List getFrmlogtrackList(Log obj, PageController controller)throws Exception{
        List loglist=this.logDao.getFrmlogtrackList(obj,controller);
        return loglist;
    }
    /*
    public List getLogtypeList()throws Exception{
        List loglist=this.logDao.getLogtypeList();
        return loglist;
    }

	public List getLogsByPagesize(UserSession userSession,Log log, PageController controller) throws Exception{
		return this.dao.getLogsByPagesize(userSession,log,controller);
	}
	
	public Log setLog(HttpServletRequest request,String czlx,String cznr,String bz) throws Exception{
		Log log=new Log();
		SysUser sysuser=((UserSession)request.getSession().getAttribute("userSession")).getSysuser();
		log.setGlbm(sysuser.getGlbm());
		log.setYhdh(sysuser.getYhdh());
		log.setCzlx(czlx);
		log.setCznr(cznr);
		log.setIp(sysService.getRemoteIpdz(request));
		log.setBz(bz);
		return log;
	}
	*/
    
    public String getJkmc(String jkid) {
    	String result="";
    	try{
    		result=this.logDao.getJkmc(jkid);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return result;
    }
    
    public FrmLoginlog getUserLogin(String yhdh) throws Exception{
    	return this.logDao.getUserLogin(yhdh);
    }
}
