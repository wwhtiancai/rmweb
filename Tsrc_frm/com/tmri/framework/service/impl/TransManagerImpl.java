package com.tmri.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.framework.bean.FrmLogTask;
import com.tmri.framework.bean.FrmTaskTj;
import com.tmri.framework.dao.TransDao;
import com.tmri.framework.service.TransManager;
import com.tmri.share.frm.util.PageController;
@Service

public class TransManagerImpl implements TransManager{
	@Autowired
	private TransDao transDao;

//	public void setTransDao(TransDao transDao) {
//		this.transDao = transDao;
//	}
	public List getLogtask(FrmLogTask frmlogtask, PageController controller) {
		return this.transDao.getLogtask(frmlogtask, controller);
	}
	/**
	 * 获取logrow日志
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getLogrow(FrmLogTask frmlogtask, PageController controller) {
		return this.transDao.getLogrow(frmlogtask, controller);
	}
	/**
	 * 获取upqueue日志
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getUpqueue(FrmLogTask frmlogtask, PageController controller) {
		return this.transDao.getUpqueue(frmlogtask, controller);
	}
	/**
	 * 获取upqueue日志
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public List getTransTj(FrmLogTask frmlogtask){
		HashMap hm=new HashMap();
		FrmTaskTj tj;
		List list=this.transDao.getTransTj(frmlogtask);
		if (list!=null)
		{
			for(int i=0;i<list.size();i++)
			{
				hm.put(((FrmTaskTj)list.get(i)).getBzid(), list.get(i));
			}
		}
		List rtn=new ArrayList();
		tj=(FrmTaskTj)hm.get("Z01");
		if (tj!=null){
			tj.setSjmc("传输记录总量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z01");
			tj.setSjmc("传输记录总量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z02");
		if (tj!=null){
			tj.setSjmc("传输记录成功量");
			rtn.add(tj);
		}else
		{
			tj.setBzid("Z02");
			tj=new FrmTaskTj();
			tj.setSjmc("传输记录成功量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z03");
		if (tj!=null){
			tj.setSjmc("传输记录失败量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z03");
			tj.setSjmc("传输记录失败量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z04");
		if (tj!=null){
			tj.setSjmc("传输任务总量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z04");
			tj.setSjmc("传输任务总量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z05");
		if (tj!=null){
			tj.setSjmc("传输任务成功量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z05");
			tj.setSjmc("传输任务成功量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z06");
		if (tj!=null){
			tj.setSjmc("传输任务失败量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z06");
			tj.setSjmc("传输任务失败量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z07");
		if (tj!=null){
			tj.setSjmc("队列表积压数据总量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z07");
			tj.setSjmc("队列表积压数据总量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z08");
		if (tj!=null){
			tj.setSjmc("队列表未传数据量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z08");
			tj.setSjmc("队列表未传数据量");
			rtn.add(tj);
		}
		tj=(FrmTaskTj)hm.get("Z09");
		if (tj!=null){
			tj.setSjmc("队列表传输失败数据量");
			rtn.add(tj);
		}else
		{
			tj=new FrmTaskTj();
			tj.setBzid("Z09");
			tj.setSjmc("队列表传输失败数据量");
			rtn.add(tj);
		}
		return rtn;
	}


}
