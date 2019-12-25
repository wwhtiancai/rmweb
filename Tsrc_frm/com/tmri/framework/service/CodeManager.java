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
	 * �������
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SysResult saveCode(Code code,Log log,String strModal) throws Exception;
	
	/**
	 * ɾ������
	 * @param frmCode
	 * @return
	 * @throws Exception
	 */
	public SysResult removeCode(Code frmCode,Log log) throws Exception;
	
	public void freshCodebyDmlb(Code code);
	public List getXzqhList(String dmz,String dmsm1,String xzqh, PageController controller) throws Exception;
	//��ȡһ����Ϣ���������û�
	public List getOneSmsSetup(String xxdm) throws Exception;
	//��Ϣ����ʱ����ȡ�û�
	public String getUsersToXml(String xtgly,String kgywyhlx,String xm) throws Exception;
	//��Ϣ���ñ���
	public SysResult savesmssetup(SmsSetup smsSetup,Log log) throws Exception;
	//��ȡ�û���Ϣ�б�
	public List getUserSmsList(SmsContent smsContent,PageController controller) throws Exception;
	//��ȡ�����û���Ϣ
	public SmsContent saveReadOneUserSms(SmsContent smsContent) throws Exception;
	//��ȡ�����������ô���
	public Code getInformCode(String xxdm);
	//��ȡ�����������ô����б�
	public List getInformCodes(String dqyhbmjb,String djbmjb);
	//��ȡ�����������������б�
	public List getInformSetups(FrmInformsetup command,PageController controller);
	//��ȡ�û�������������
	public FrmInformsetup getInformSetup(FrmInformsetup command);	
	//��ȡ�������������û�ѡ���б�
	public List getInformSetupUsers(FrmInformsetup command);
	//����������Ϣ���ñ���
	public SysResult savefrmInformsetup(FrmInformsetup frmInformsetup,Log log) throws Exception;

}
