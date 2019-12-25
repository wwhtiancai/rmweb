package com.tmri.framework.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.bean.FrmInformsetup;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.SmsContent;
import com.tmri.framework.bean.SmsSetup;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.CodeDao;
import com.tmri.framework.dao.CodetypeDao;
import com.tmri.framework.dao.SysuserDao;
import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
import com.tmri.framework.service.CodeManager;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Codetype;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.GSysparaCodeDao;
import com.tmri.share.frm.util.PageController;
@Service

public class CodeManagerImpl implements CodeManager {
	@Autowired
	CodeDao codeDao = null;
	@Autowired
	CodetypeDao codetypeDao = null;
	@Autowired
	FrmDataObjDaoJdbc frmDataObjDao;
	@Autowired
	SysuserDao sysuserDao = null;
	@Autowired
	GSysparaCodeDao gSysparaCodeDao;

	public List getCodetypes(Codetype codetype) throws Exception {
		return this.codetypeDao.getCodetypes(codetype);
	}

	public Codetype getCodetype(String xtlb,String dmlb) throws Exception {
		return this.gSysparaCodeDao.getCodetype(xtlb,dmlb);
	}

	public SysResult removeCode(Code frmCode,Log log) throws Exception {
		SysResult result=null;
		result=frmDataObjDao.setOracleData(frmCode,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=codeDao.removeCode();
		return result;
	}

	public SysResult saveCode(Code code,Log log,String strModal) throws Exception {
		SysResult result=null;
		result=frmDataObjDao.setOracleData(code,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=codeDao.saveCode(strModal);
		return result;
	}

	public List getCodetypesByPageSize(Codetype codetype, PageController controller) throws Exception {
		return this.codetypeDao.getCodetypesByPageSize(codetype, controller);
	}
	public void freshCodebyDmlb(Code code)
	{
		this.codeDao.freshCodebyDmlb(code);
	}
	public List getXzqhList(String dmz,String dmsm1,String xzqh, PageController controller) throws Exception{
		return this.codeDao.getXzqhList(dmz, dmsm1, xzqh,controller);
	}
	//获取一个消息接收设置用户
	public List getOneSmsSetup(String xxdm) throws Exception{
		return this.codeDao.getSmsSetupContent(xxdm);
	}
	//消息设置时，获取用户
	public String getUsersToXml(String xtgly,String kgywyhlx,String xm) throws Exception{
		List list=this.codeDao.getUsers(xtgly,kgywyhlx,xm);
		StringBuffer resultbuffer=new StringBuffer();
		if(list.size()>0){
			Iterator iterator=list.iterator();
			resultbuffer.append("<root>");
			while(iterator.hasNext()){
				SysUser user=(SysUser)iterator.next();
				resultbuffer.append("<user>");
				resultbuffer.append("<yhdh>"+user.getYhdh()+"</yhdh>");
				resultbuffer.append("<xm>"+user.getXm()+"</xm>");
				resultbuffer.append("</user>");
			}
			resultbuffer.append("</root>");
		}else{
			resultbuffer.append("<root>");
			resultbuffer.append("</root>");
		}
		return resultbuffer.toString();
	}
	//消息设置保存
	public SysResult savesmssetup(SmsSetup smsSetup,Log log) throws Exception{
		SysResult result=null;
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=codeDao.savesmssetup(smsSetup);
		return result;
	}
	//获取用户消息列表
	public List getUserSmsList(SmsContent smsContent,PageController controller) throws Exception{
		return this.codeDao.getUserSmsList(smsContent,controller);
	}
	//获取单个用户消息
	public SmsContent saveReadOneUserSms(SmsContent smsContent) throws Exception{
		return this.codeDao.saveReadOneUserSms(smsContent);
	}
	//获取提醒内容设置代码
	public Code getInformCode(String xxdm){
		return this.gSysparaCodeDao.getInformCode(xxdm);
	}
	//获取提醒内容设置代码列表
	public List getInformCodes(String dqyhbmjb,String djbmjb){
		return this.codeDao.getInformCodes(dqyhbmjb,djbmjb);
	}
  //获取提醒内容设置内容列表
	public List getInformSetups(FrmInformsetup command,PageController controller){
		return this.codeDao.getInformSetups(command,controller);
	}
	//获取用户提醒内容设置
	public FrmInformsetup getInformSetup(FrmInformsetup command){
		return this.codeDao.getInformSetup(command);
	}
	//获取提醒内容设置用户选择列表
	public List getInformSetupUsers(FrmInformsetup command){
		List sysusersList =  this.sysuserDao.getSysusers(command.getGlbm());
		SysUser sysUser = null;
		for(int i=0;i<sysusersList.size();i++){
			sysUser = (SysUser)sysusersList.get(i);
			if(command.getYhdh().indexOf(sysUser.getYhdh())>=0){
				sysUser.setBz("1");
			}else{
				sysUser.setBz("0");
			}
		}
		return sysusersList;
	}
	//工作提醒消息设置保存
	public SysResult savefrmInformsetup(FrmInformsetup frmInformsetup,Log log) throws Exception{
		SysResult result=null;
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(frmInformsetup,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.execCommProcedure("frm_sys_pkg.Saveinformsetup");
		return result;
	}
}
