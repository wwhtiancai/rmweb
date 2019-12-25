package com.tmri.framework.service;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.tmri.framework.bean.FrmTask;
import com.tmri.framework.bean.FrmWsAppForm;
import com.tmri.framework.bean.FrmWsContent;
import com.tmri.framework.bean.FrmWsControl;
import com.tmri.framework.bean.FrmWsIpxz;
import com.tmri.framework.bean.FrmWsLog;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.TaskDao;
import com.tmri.share.frm.util.PageController;

public interface WsManager{
	public SysResult saveFrmWsContent(FrmWsContent wsContent)throws Exception;
	public SysResult saveFrmWsControl(FrmWsControl wsControl)throws Exception;
	public SysResult saveFrmWsIpxz(FrmWsIpxz wsIpxz,Log log)throws Exception;
	public SysResult saveFrmWsAppForm(FrmWsAppForm wsAppForm,Log log)throws Exception;
	public SysResult delFrmWsAppForm(FrmWsAppForm wsAppForm,Log log)throws Exception;
	public SysResult delFrmWsIpxz(FrmWsIpxz wsIpxz,Log log)throws Exception;
	public List getFrmWsContentList(String  xtlb,String jksx) throws Exception;
	public List getWsContentsByKfwjk(String kfwjk) throws Exception;
	public FrmWsContent getFrmWsContent(String jkid)throws Exception;	
	public List getFrmWsControlList(String jkid)throws Exception;
	public List getFrmWsControlList(String xtlb,String azdm,String sft,String fzjg,String dyrjmc) throws Exception;
	public FrmWsControl getFrmWsControl(String jkxlh)throws Exception;
	public FrmWsAppForm getFrmAppForm(String xtlb,String azdm,String dyrjmc)throws Exception;
	public List getFrmWsLogList(FrmWsLog wsLog,PageController controller)throws Exception;
	public List getWsIpxzList(String jkid)throws Exception;	
	public String getJkOptions(String xtlb,String jksx)throws Exception;	
	public String getJkOptions(String kfwjk)throws Exception;
	public String getDivContents(FrmWsControl wsControl) throws Exception;
	public String getDivContents(FrmWsAppForm wsAppForm) throws Exception;
	public List getWsAppFormList(String xtlb,String azdm,String scbj,String sft,String fzjg,String dyrjmc,String dyrjkfdw) throws Exception;
	public String getWsTotalVisit(FrmWsLog wsLog )throws Exception;//总量统计
	public String getWsLinearAnalysis(FrmWsLog wsLog )throws Exception;//线性分析
	
	//和统计监管系统的信息交换
	public SysResult syncFrmWsContents() throws Exception;
	public SysResult syncFrmWsControls(String xzbj) throws Exception;
	public void syncFrmWsContents(ApplicationContext ctx,TaskDao taskdao,FrmTask task) throws Exception;
	public void syncFrmWsControls(ApplicationContext ctx,TaskDao taskdao,FrmTask task) throws Exception;
	//写接口访问统计信息上传队列
	public void saveWsStatAlign(ApplicationContext ctx,TaskDao taskdao,FrmTask task) throws Exception;	
	public SysResult uploadFrmWsAppForm(String xtlb,String azdm,String dyrjmc) throws Exception;
}
