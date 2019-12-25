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
//公安版本	
	public SysResult savaPlspassword(PassWord passWord) throws SQLException;
	
	public SysResult savagdip(PassWord passWord) throws SQLException;

	public SysUserFinger getSysUserFinger(String yhdh);
	
	public SysResult savaFinger(SysUserFinger sysUserFinger) throws SQLException;
	
	//获取当前用户登录信息
	public void getLoginInfo(UserSession userSession) throws Exception;
	// zhoujn 验证用户
	public int validateSysuser(SysUser sysuser,String strRemoteAddr) throws Exception;
	// 验证ATM及权限用户
	public DbResult validateAtmSysuser(AtmAuthUser atmAuthUser);
//公安版本：获取交警用户的数量
	public int getTrafficPolice(String yhdh) throws Exception;
//公安版本：校验公安用户
	public int validatePoliceman(SysUser sysuser,String strRemoteAddr) throws Exception;
//公安版本：获取公安用户
	public SysUser getPoliceman(String yhdh) throws DataAccessException;
//公安版本：保存公安用户的日志
	public void getPolicemanInfo(UserSession userSession) throws Exception;
	
	// 获取用户权限程序
	public List getSysuserMenuList(String strYhdh) throws Exception;

	// 保存桌面设置
	public int Savedesk(UserDesk userDesk) throws SQLException;

	// zhoujn 20100524
	public java.sql.Blob getQmtp(String yhdh) throws Exception;

	public SysResult saveQmtp(String yhdh,SysUserSeal obj,final byte[] qmtp) throws Exception;

	public SysResult delQmtp(String yhdh) throws Exception;

	public void updateQmtp(final SysUserSeal obj) throws Exception;

	public int getQmtpNum(String yhdh);
	
	public List getUserGrantRole(String yhdh) throws DataAccessException;
	//授权权限
	public SysResult saveUserGrantrole(UserRole userRole);
	
	//获取用户人员信息
	public SysUser getUserinfo(String rylb,String sfzmhm) throws Exception;
	//获取用户列表
	public List getUserinfoList(SysUser obj) throws Exception;
	
	//获取某管理部门所属业务的系统用户列表
	public List getDepSsywusers(String glbm,String yhssyw);
	//获取用户角色list
	public List getUserRoleList(String yhdh) throws DataAccessException;
	
	//获取管理部门下所有民警用户的信息
	public List getPoliceUserList(String glbm);
	
	//获取管理部门下指定警号民警用户的信息
	public BasPolice getPolice(String glbm,String jybh);
	
	//从内存表中获取用户信息
	public SysUser getSysuserFromMem(String yhdh);
	//获取部门下属所有的民警
	public List getDepPoliceusers_Like(String glbm,String yhssyw);
	//获取部门下yhssyw指定民警
	public List getDepPoliceusers(String glbm,String yhssyw);
	
	//保存用户权限信息
	public SysResult saveSysuserRole(String modal) throws SQLException;
	//保存用户在线信息
	public void recZxyhxx(String flag);
	//保存登录失败信息
	public void saveUserLoginFail();
	//获取某管理部门用户的列表
	List getSysusers(String glbm);
	
	public int getTrafficPoliceSfzmhm(String sfzmhm) throws Exception;

	public String getFwzbh(String yhdh);
}