package com.tmri.framework.service;

import java.sql.Blob;
import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.FrmUserloginfail;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.PassWord;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.SysUserFinger;
import com.tmri.framework.bean.SysUserSeal;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.bean.UserRole;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;

public interface SysuserManager{
	public SysUser getSysuser(String yhdh);

	public SysUser getAtmSysUserByIpdz(String ipdz);
	
	public List getAtmSysUserMenus(String yhdh);
	
	public List getSysusers(SysUser user);

	public List getSysusersByPagesize(SysUser user,PageController controller);

	public SysResult saveSysuser(SysUser user,Log log,String modal) throws Exception;
	public SysResult saveSysuserrole(SysUser user,UserRole userRole,UserMenu userMenu,UserRole userGrantrole,Log log,String modal) throws Exception;

	public SysResult removeSysuser(SysUser user,Log log) throws Exception;

	public SysResult saveSetuppassword(SysUser user,Log log) throws Exception;
	
	public void setSysuserRoles(SysUser sysuser);
	
	public List getLoginFail(String yhdh);
	
	public SysResult saveUnlockUser(SysUser user,Log log);
	
	public int validateSysuser(SysUser sysuser,UserSession userSession,String strRemoteAddr) throws Exception;
//�����汾����ȡ�����û�������
	public int getTrafficPolice(String yhdh) throws Exception;
//�����汾��У�鹫���û�
	public int validatePoliceman(SysUser sysuser,UserSession userSession,String strRemoteAddr) throws Exception;
//�����汾����ȡ�����û�
	public SysUser getPoliceman(String yhdh);
	
	public SysResult savapassword(PassWord passWord,Log log) throws Exception;
//�����汾���޸Ĺ����û�����	
	public SysResult savaPlspassword(PassWord passWord,Log log) throws Exception;
	
	public SysResult savagdip(PassWord passWord,Log log) throws Exception;

	public SysUserFinger getSysUserFinger(String yhdh) throws Exception;
	
	public SysResult savaFinger(SysUserFinger sysUserFinger,Log log) throws Exception;
	// ������������
	public int Savedesk(UserDesk userDesk) throws Exception;
	
	//zhoujn 
	public String getBmmc(String bmdm)  throws Exception;
	
	//����ip��ַ
	//����ip��ַ
	//bj 1��ʼ 2����
	public String getIpdz(String ipdz,String bj,int idx) throws Exception;
	//zhoujn 20100524
	public Blob getQmtp(String yhdh)throws Exception;	
	public SysResult saveQmtp(SysUserSeal obj,final byte[] qmtp)  throws Exception;
	public SysResult delQmtp(SysUserSeal obj)  throws Exception ;
	// ȡ�����
	public int getRand();
	public int getQmtpNum(String yhdh);
	//��ȡ״̬
	public String getZtmc(String zt);
	//��ȡ��Ȩ��ɫ
	public String getRolelistHtml(String ckname,int colnum,Hashtable rolelist);
//����grantrole
	
	public void setSysuserGrantRoles(SysUser sysuser);
	//��ȡ�û���Ա��Ϣ
	public SysUser getUserinfo(String rylb,String sfzmhm) throws Exception;
	
	public String getRylxOptionHtml(String defval);
	public String getRylxHtml(String defval);
	
	//��ȡ�û��б�
	public List getUserinfoList(SysUser obj) throws Exception;
	
	//����Ƿ�ͬһ��Ȩ
	public boolean checkSameGrant(List rolelist,SysUser sysuser);
	
	//��ȡ�û���ɫlist
	public List getUserRoleList(String yhdh) throws DataAccessException;
	
	//��ȡ�û�����Ȩ�Ŀ���ȨȨ��
	public List getUserGrantRoleList(String yhdh) throws Exception;
	
	//�ϲ�����Ȩ��
	public Hashtable combinRoleList(List xtglyrolelist,List userrolelist);
	
	// ������Ȩ��ɫ
	public String getGrantRolelistHtml(String ckname,int column,List rolelist);
	//�����û�����¼
	public void recZxyhxx(String flag);
	//�����¼ʧ����Ϣ
	public void saveUserLoginFail(FrmUserloginfail frmUserloginfail);
	//��ȡ����KEY
	public String getOracleKey(); 
	
	public int getTrafficPoliceSfzmhm(String sfzmhm) throws Exception ;

	public String getFwzbh(String yhdh);

	int create(SysUser sysUser);
}