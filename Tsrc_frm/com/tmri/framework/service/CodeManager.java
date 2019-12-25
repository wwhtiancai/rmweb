package com.tmri.framework.service;

import java.util.List;

import com.tmri.framework.bean.FrmInformsetup;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SmsContent;
import com.tmri.framework.bean.SmsSetup;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Codetype;
import com.tmri.share.frm.util.PageController;


public interface CodeManager {
	public List getCodetypes(Codetype codetype) throws Exception;
	
	public List getCodetypesByPageSize(Codetype codetype,PageController controller) throws Exception;
	
	public Codetype getCodetype(String xtlb,String dmlb) throws Exception;

	/**
	 * 保存代码
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SysResult saveCode(Code code,Log log,String strModal) throws Exception;
	
	/**
	 * 删除代码
	 * @param frmCode
	 * @return
	 * @throws Exception
	 */
	public SysResult removeCode(Code frmCode,Log log) throws Exception;
	
	public void freshCodebyDmlb(Code code);
	public List getXzqhList(String dmz,String dmsm1,String xzqh, PageController controller) throws Exception;
	//获取一个消息接收设置用户
	public List getOneSmsSetup(String xxdm) throws Exception;
	//消息设置时，获取用户
	public String getUsersToXml(String xtgly,String kgywyhlx,String xm) throws Exception;
	//消息设置保存
	public SysResult savesmssetup(SmsSetup smsSetup,Log log) throws Exception;
	//获取用户消息列表
	public List getUserSmsList(SmsContent smsContent,PageController controller) throws Exception;
	//获取单个用户消息
	public SmsContent saveReadOneUserSms(SmsContent smsContent) throws Exception;
	//获取提醒内容设置代码
	public Code getInformCode(String xxdm);
	//获取提醒内容设置代码列表
	public List getInformCodes(String dqyhbmjb,String djbmjb);
	//获取提醒内容设置内容列表
	public List getInformSetups(FrmInformsetup command,PageController controller);
	//获取用户提醒内容设置
	public FrmInformsetup getInformSetup(FrmInformsetup command);	
	//获取提醒内容设置用户选择列表
	public List getInformSetupUsers(FrmInformsetup command);
	//工作提醒消息设置保存
	public SysResult savefrmInformsetup(FrmInformsetup frmInformsetup,Log log) throws Exception;

}
