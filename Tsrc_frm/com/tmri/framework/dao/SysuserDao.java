package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.PassWord;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.SysUserFinger;
import com.tmri.framework.bean.SysUserSeal;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.bean.UserRole;
import com.tmri.pub.bean.AtmAuthUser;
import com.tmri.pub.bean.BasPolice;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.ora.bean.DbResult;

public interface SysuserDao{
	public SysUser getSysuser(String yhdh) throws DataAccessException;
	
	public SysUser getSysuser(String glbm,String xm);
	
	public SysUser getAtmSysUserByIpdz(String ipdz);
	
	public List getAtmSysUserMenus(String yhdh);
	
	public List getSysusers(SysUser user) throws DataAccessException;

	public List getSysusersByPagesize(SysUser user,PageController controller) throws DataAccessException;;

	public SysResult saveSysuser(String modal) throws SQLException;

	public SysResult saveUserMenu(UserMenu userMenu);

	public SysResult saveUserRole(UserRole userRole);

	public SysResult removeSysuser() throws SQLException;

	public SysResult saveSetuppassword() throws SQLException;

	public List getUserRole(String yhdh) throws DataAccessException;

	public List getUserMenu(String yhdh) throws DataAccessException;

	public List getLoginFail(String yhdh);

	public SysResult saveUnlockUser();

	public SysResult savapassword(PassWord passWord) throws SQLException;
//�����汾	
	public SysResult savaPlspassword(PassWord passWord) throws SQLException;
	
	public SysResult savagdip(PassWord passWord) throws SQLException;

	public SysUserFinger getSysUserFinger(String yhdh);
	
	public SysResult savaFinger(SysUserFinger sysUserFinger) throws SQLException;
	
	//��ȡ��ǰ�û���¼��Ϣ
	public void getLoginInfo(UserSession userSession) throws Exception;
	// zhoujn ��֤�û�
	public int validateSysuser(SysUser sysuser,String strRemoteAddr) throws Exception;
	// ��֤ATM��Ȩ���û�
	public DbResult validateAtmSysuser(AtmAuthUser atmAuthUser);
//�����汾����ȡ�����û�������
	public int getTrafficPolice(String yhdh) throws Exception;
//�����汾��У�鹫���û�
	public int validatePoliceman(SysUser sysuser,String strRemoteAddr) throws Exception;
//�����汾����ȡ�����û�
	public SysUser getPoliceman(String yhdh) throws DataAccessException;
//�����汾�����湫���û�����־
	public void getPolicemanInfo(UserSession userSession) throws Exception;
	
	// ��ȡ�û�Ȩ�޳���
	public List getSysuserMenuList(String strYhdh) throws Exception;

	// ������������
	public int Savedesk(UserDesk userDesk) throws SQLException;

	// zhoujn 20100524
	public java.sql.Blob getQmtp(String yhdh) throws Exception;

	public SysResult saveQmtp(String yhdh,SysUserSeal obj,final byte[] qmtp) throws Exception;

	public SysResult delQmtp(String yhdh) throws Exception;

	public void updateQmtp(final SysUserSeal obj) throws Exception;

	public int getQmtpNum(String yhdh);
	
	public List getUserGrantRole(String yhdh) throws DataAccessException;
	//��ȨȨ��
	public SysResult saveUserGrantrole(UserRole userRole);
	
	//��ȡ�û���Ա��Ϣ
	public SysUser getUserinfo(String rylb,String sfzmhm) throws Exception;
	//��ȡ�û��б�
	public List getUserinfoList(SysUser obj) throws Exception;
	
	//��ȡĳ����������ҵ���ϵͳ�û��б�
	public List getDepSsywusers(String glbm,String yhssyw);
	//��ȡ�û���ɫlist
	public List getUserRoleList(String yhdh) throws DataAccessException;
	
	//��ȡ���������������û�����Ϣ
	public List getPoliceUserList(String glbm);
	
	//��ȡ��������ָ���������û�����Ϣ
	public BasPolice getPolice(String glbm,String jybh);
	
	//���ڴ���л�ȡ�û���Ϣ
	public SysUser getSysuserFromMem(String yhdh);
	//��ȡ�����������е���
	public List getDepPoliceusers_Like(String glbm,String yhssyw);
	//��ȡ������yhssywָ����
	public List getDepPoliceusers(String glbm,String yhssyw);
	
	//�����û�Ȩ����Ϣ
	public SysResult saveSysuserRole(String modal) throws SQLException;
	//�����û�������Ϣ
	public void recZxyhxx(String flag);
	//�����¼ʧ����Ϣ
	public void saveUserLoginFail();
	//��ȡĳ�������û����б�
	List getSysusers(String glbm);
	
	public int getTrafficPoliceSfzmhm(String sfzmhm) throws Exception;

	public String getFwzbh(String yhdh);
}