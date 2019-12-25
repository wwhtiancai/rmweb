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
	//��ȡһ����Ϣ���������û�
	public List getOneSmsSetup(String xxdm) throws Exception{
		return this.codeDao.getSmsSetupContent(xxdm);
	}
	//��Ϣ����ʱ����ȡ�û�
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
	//��Ϣ���ñ���
	public SysResult savesmssetup(SmsSetup smsSetup,Log log) throws Exception{
		SysResult result=null;
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=codeDao.savesmssetup(smsSetup);
		return result;
	}
	//��ȡ�û���Ϣ�б�
	public List getUserSmsList(SmsContent smsContent,PageController controller) throws Exception{
		return this.codeDao.getUserSmsList(smsContent,controller);
	}
	//��ȡ�����û���Ϣ
	public SmsContent saveReadOneUserSms(SmsContent smsContent) throws Exception{
		return this.codeDao.saveReadOneUserSms(smsContent);
	}
	//��ȡ�����������ô���
	public Code getInformCode(String xxdm){
		return this.gSysparaCodeDao.getInformCode(xxdm);
	}
	//��ȡ�����������ô����б�
	public List getInformCodes(String dqyhbmjb,String djbmjb){
		return this.codeDao.getInformCodes(dqyhbmjb,djbmjb);
	}
  //��ȡ�����������������б�
	public List getInformSetups(FrmInformsetup command,PageController controller){
		return this.codeDao.getInformSetups(command,controller);
	}
	//��ȡ�û�������������
	public FrmInformsetup getInformSetup(FrmInformsetup command){
		return this.codeDao.getInformSetup(command);
	}
	//��ȡ�������������û�ѡ���б�
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
	//����������Ϣ���ñ���
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
