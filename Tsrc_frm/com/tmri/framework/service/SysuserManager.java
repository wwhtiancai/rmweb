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
//公安版本：获取交警用户的数量
	public int getTrafficPolice(String yhdh) throws Exception;
//公安版本：校验公安用户
	public int validatePoliceman(SysUser sysuser,UserSession userSession,String strRemoteAddr) throws Exception;
//公安版本：获取公安用户
	public SysUser getPoliceman(String yhdh);
	
	public SysResult savapassword(PassWord passWord,Log log) throws Exception;
//公安版本：修改公安用户密码	
	public SysResult savaPlspassword(PassWord passWord,Log log) throws Exception;
	
	public SysResult savagdip(PassWord passWord,Log log) throws Exception;

	public SysUserFinger getSysUserFinger(String yhdh) throws Exception;
	
	public SysResult savaFinger(SysUserFinger sysUserFinger,Log log) throws Exception;
	// 保存桌面设置
	public int Savedesk(UserDesk userDesk) throws Exception;
	
	//zhoujn 
	public String getBmmc(String bmdm)  throws Exception;
	
	//处理ip地址
	//处理ip地址
	//bj 1开始 2结束
	public String getIpdz(String ipdz,String bj,int idx) throws Exception;
	//zhoujn 20100524
	public Blob getQmtp(String yhdh)throws Exception;	
	public SysResult saveQmtp(SysUserSeal obj,final byte[] qmtp)  throws Exception;
	public SysResult delQmtp(SysUserSeal obj)  throws Exception ;
	// 取随机数
	public int getRand();
	public int getQmtpNum(String yhdh);
	//获取状态
	public String getZtmc(String zt);
	//获取授权角色
	public String getRolelistHtml(String ckname,int colnum,Hashtable rolelist);
//设置grantrole
	
	public void setSysuserGrantRoles(SysUser sysuser);
	//获取用户人员信息
	public SysUser getUserinfo(String rylb,String sfzmhm) throws Exception;
	
	public String getRylxOptionHtml(String defval);
	public String getRylxHtml(String defval);
	
	//获取用户列表
	public List getUserinfoList(SysUser obj) throws Exception;
	
	//检测是否同一授权
	public boolean checkSameGrant(List rolelist,SysUser sysuser);
	
	//获取用户角色list
	public List getUserRoleList(String yhdh) throws DataAccessException;
	
	//获取用户已授权的可授权权限
	public List getUserGrantRoleList(String yhdh) throws Exception;
	
	//合并两类权限
	public Hashtable combinRoleList(List xtglyrolelist,List userrolelist);
	
	// 构造授权角色
	public String getGrantRolelistHtml(String ckname,int column,List rolelist);
	//在线用户数记录
	public void recZxyhxx(String flag);
	//保存登录失败信息
	public void saveUserLoginFail(FrmUserloginfail frmUserloginfail);
	//获取调用KEY
	public String getOracleKey(); 
	
	public int getTrafficPoliceSfzmhm(String sfzmhm) throws Exception ;

	public String getFwzbh(String yhdh);

	int create(SysUser sysUser);
}