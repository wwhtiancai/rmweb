package com.tmri.framework.dao.jdbc;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import oracle.sql.BLOB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import com.tmri.framework.bean.FrmUserloginfail;
import com.tmri.framework.bean.PassWord;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.RoleMenu;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.SysUserFinger;
import com.tmri.framework.bean.SysUserSeal;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.bean.UserRole;
import com.tmri.framework.dao.SysuserDao;
import com.tmri.pub.bean.AtmAuthUser;
import com.tmri.pub.bean.BasEmployee;
import com.tmri.pub.bean.BasPolice;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.FuncUtil;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbParaInfo;
import com.tmri.share.ora.bean.DbResult;
@Repository
public class SysuserDaoJdbc extends FrmDaoJdbc implements SysuserDao{
	/**
	 * 获取管理部门查询条件
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public static String getGlbmQueryCondition(UserSession userSession,String Colname){
		String sql="";
		String glbm="";
		try{
			glbm=userSession.getDepartment().getGlbm();
			// 总队用户
			if(userSession.getDepartment().getBmjb().equals("1")){
				sql=" "+Colname+" like '"+glbm.substring(0,2)+"%' ";
			}

			// 支队用户
			if(userSession.getDepartment().getBmjb().equals("2")){
				sql=" "+Colname+" like '"+glbm.substring(0,4)+"%' ";
			}

			// 大队用户
			if(userSession.getDepartment().getBmjb().equals("3")){
				sql=" "+Colname+" like '"+glbm.substring(0,8)+"%' ";
			}

			// 中队用户
			if(userSession.getDepartment().getBmjb().equals("4")){
				sql=" "+Colname+" = '"+glbm+"' ";
			}

			if(sql.equals("")){
				sql=" 1=1 ";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return sql;
	}

	public SysUser getSysuser(String yhdh) throws DataAccessException{
		String sql="select * from frm_sysuser where yhdh=?";
		Object[] paraObjects=new Object[]{yhdh};
		SysUser sysUser=(SysUser)jdbcTemplate.queryForBean(sql,paraObjects,SysUser.class);
		return sysUser;
	};
	public SysUser getSysuser(String glbm,String xm){
		String sql="select * from frm_sysuser where glbm=? and xm=?";
		Object[] paraObjects=new Object[]{glbm,xm};
		SysUser sysUser=(SysUser)jdbcTemplate.queryForBean(sql,paraObjects,SysUser.class);
		return sysUser;
	}
	public SysUser getAtmSysUserByIpdz(String ipdz){
		String sql="select * from frm_sysuser where ipks=?";
		Object[] paraObjects=new Object[]{ipdz};
		SysUser sysUser=(SysUser)jdbcTemplate.queryForBean(sql,paraObjects,SysUser.class);
		return sysUser;
	}
	public List getAtmSysUserMenus(String yhdh){
		String sql="select distinct b.* from frm_userrole a,frm_rolemenu b Where a.jsdh=b.jsdh and a.yhdh=?";
		Object[] paraObjects=new Object[]{yhdh};
		return jdbcTemplate.queryForList(sql,paraObjects,RoleMenu.class);
	}
	public List getSysusers(SysUser user) throws DataAccessException{
		String tmpSql="";

		if(user.getGlbm()!=null&&!user.getGlbm().equals("")){
			tmpSql=" where glbm='"+user.getGlbm()+"'";
		}

		if(user.getYhdh()!=null&&!user.getYhdh().equals("")){
			if(tmpSql!=""){
				tmpSql=tmpSql+" and yhdh like '"+user.getYhdh()+"%'";
			}else{
				tmpSql=" where yhdh like '"+user.getYhdh()+"%'";
			}
		}

		tmpSql="select * from frm_sysuser "+tmpSql+" order by glbm";
		List list=jdbcTemplate.queryForList(tmpSql,SysUser.class);
		return list;
	};

	/**
	 * 保存用户
	 */
	//zhoujn 20100524
	public SysResult saveSysuser(String modal) throws SQLException{
		String callString="{call Frm_Sys_Pkg.saveFRM_SYSUSER(?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				String modal =(String)getParameterObject();
				cstmt.setString(1,modal);
				cstmt.registerOutParameter(2,Types.NUMERIC);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(2));
				result.setDesc1(cstmt.getString(3));
				result.setDesc(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(modal);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	
	//保存用户权限信息
	public SysResult saveSysuserRole(String modal) throws SQLException{
		String callString="{call Frm_Sys_Pkg.saveFRM_SYSUSERROLE(?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				String modal =(String)getParameterObject();
				cstmt.setString(1,modal);
				cstmt.registerOutParameter(2,Types.NUMERIC);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(2));
				result.setDesc1(cstmt.getString(3));
				result.setDesc(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(modal);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}	

	public SysResult saveUserMenu(UserMenu userMenu){
		SysResult sysResult = null;
		String callString="{call Frm_Sys_Pkg.saveFrm_UserMENU(?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				UserMenu userMenu =(UserMenu)getParameterObject();
				cstmt.setString(1,userMenu.getYhdh());
				cstmt.setString(2,userMenu.getXtlb());
				cstmt.setString(3,userMenu.getCdbh());
				cstmt.setString(4,userMenu.getGnlb());
				cstmt.registerOutParameter(5,Types.NUMERIC);
				cstmt.registerOutParameter(6,Types.VARCHAR);
				cstmt.registerOutParameter(7,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(5));
				result.setDesc(cstmt.getString(6));
				result.setDesc1(cstmt.getString(7));
				return result;
			}
		};
		// 插入用户菜单表中的记录
		String[] arrCdbh = splitString(userMenu.getCdbh(), "#");
		UserMenu userMenu1 = null;
		if (arrCdbh != null) {
			for (int i = 0; i < arrCdbh.length; i++) {
				if (arrCdbh[i] != null && arrCdbh[i] != "") {
					String[] menu = arrCdbh[i].split("-");
					userMenu1 = new UserMenu();
					userMenu1.setXtlb(menu[0]);
					userMenu1.setYhdh(userMenu.getYhdh());
					userMenu1.setCdbh(menu[1]);
					if(menu.length>2)
						userMenu1.setGnlb(menu[2]);
					else {
						userMenu1.setGnlb("");
					}
					callBack.setParameterObject(userMenu1);
					sysResult = (SysResult)jdbcTemplate.execute(callString, callBack);
					if(sysResult.getFlag()==0){
						break;
					}
				}
			}
		}
		return sysResult;
	}
	public SysResult saveUserRole(UserRole userRole){
		SysResult sysResult = null;
		String callString="{call Frm_Sys_Pkg.saveFrm_UserRole(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				UserRole userRole =(UserRole)getParameterObject();
				cstmt.setString(1,userRole.getYhdh());
				cstmt.setString(2,userRole.getJsdh());				
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.registerOutParameter(5,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(3));
				result.setDesc(cstmt.getString(4));
				result.setDesc1(cstmt.getString(5));
				return result;
			}
		};
		// 插入用户菜单表中的记录
		String[] arrJsdh = splitString(userRole.getJsdh(), "#");
		UserRole userRole1 = null;
		if (arrJsdh != null) {
			for (int i = 0; i < arrJsdh.length; i++) {
					userRole1 = new UserRole();
					userRole1.setYhdh(userRole.getYhdh());
					userRole1.setJsdh(arrJsdh[i]);
					callBack.setParameterObject(userRole1);
					sysResult = (SysResult)jdbcTemplate.execute(callString, callBack);
					if(sysResult.getFlag()==0){
						break;
					}
			}
		}
		return sysResult;
	}

	//授权权限
	public SysResult saveUserGrantrole(UserRole userRole){
		SysResult sysResult = null;
		String callString="{call Frm_Sys_Pkg.saveFrm_UserGrantrole(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				UserRole userRole =(UserRole)getParameterObject();
				cstmt.setString(1,userRole.getYhdh());
				cstmt.setString(2,userRole.getJsdh());				
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.registerOutParameter(5,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(3));
				result.setDesc(cstmt.getString(4));
				result.setDesc1(cstmt.getString(5));
				return result;
			}
		};
		// 插入用户菜单表中的记录
		String[] arrJsdh = splitString(userRole.getJsdh(), "#");
		UserRole userRole1 = null;
		if (arrJsdh != null) {
			for (int i = 0; i < arrJsdh.length; i++) {
					userRole1 = new UserRole();
					userRole1.setYhdh(userRole.getYhdh());
					userRole1.setJsdh(arrJsdh[i]);
					callBack.setParameterObject(userRole1);
					sysResult = (SysResult)jdbcTemplate.execute(callString, callBack);
					if(sysResult.getFlag()==0){
						break;
					}
			}
		}
		return sysResult;
	}	
	
	
	public SysResult removeSysuser() throws SQLException{
		String callString="{call Frm_Sys_Pkg.Deletefrm_Sysuser(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc1(cstmt.getString(2));
				result.setDesc(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	};

	public SysResult saveSetuppassword() throws SQLException{
		String callString="{call Frm_Sys_Pkg.Setupfrm_Password(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc1(cstmt.getString(2));
				result.setDesc(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	
	//解锁
	public SysResult saveUnlockUser(){
		String callString="{call Frm_Sys_Pkg.saveUnlockUser(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc1(cstmt.getString(2));
				result.setDesc(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	};
	
	// 将字符串分割为字符数组
	private String[] splitString(String str,String split){
		String[] result;
		String tmpStr;
		tmpStr=str.trim();
		if(tmpStr.equals("")){
			result=null;
		}else{
			result=tmpStr.split(split);
		}
		return result;
	}

	public List getUserRole(String yhdh) throws DataAccessException {
		String strSql = "Select * From frm_userrole Where yhdh=?";
		Object[] paraObjects=new Object[]{yhdh};
		List list = jdbcTemplate.queryForList(strSql,paraObjects, Role.class);
		return list;
	};
	
	//获取用户角色list
	public List getUserRoleList(String yhdh) throws DataAccessException {
		String strSql = "Select v2.* From frm_userrole v1,frm_role v2 where v1.jsdh=v2.jsdh and v1.yhdh=? order by v2.jsmc";
		Object[] paraObjects=new Object[]{yhdh};
		List list = jdbcTemplate.queryForList(strSql,paraObjects, Role.class);
		return list;
	}	

	

	public List getUserGrantRole(String yhdh) throws DataAccessException {
		String strSql = "Select v2.* From frm_user_grantrole v1,frm_role v2 Where v1.jsdh=v2.jsdh and v1.yhdh=? order by v2.jsmc";
		Object[] paraObjects=new Object[]{yhdh};
		List list = jdbcTemplate.queryForList(strSql,paraObjects, Role.class);
		return list;
	};
	
	public List getUserMenu(String yhdh) throws DataAccessException {
		String strSql = "Select * From frm_usermenu Where yhdh=?";
		Object[] paraObjects=new Object[]{yhdh};
		List list = jdbcTemplate.queryForList(strSql,paraObjects, UserMenu.class);
		return list;
	};
	public List getLoginFail(String yhdh){
		String strSql = "Select * From frm_userloginfail Where yhdh=?";
		Object[] paraObjects=new Object[]{yhdh};
		List list = jdbcTemplate.queryForList(strSql,paraObjects, FrmUserloginfail.class);
		return list;		
	}
	/**
	 * 获取当前用户登录信息
	 */
	public void getLoginInfo(UserSession userSession) throws Exception{
		String callString="{call FRM_SYS_PKG.getLoginInfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				UserSession userSession=(UserSession)getParameterObject();
				cstmt.setString(1,userSession.getYhdh());
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.registerOutParameter(5,Types.VARCHAR);
				cstmt.registerOutParameter(6,Types.VARCHAR);
				cstmt.registerOutParameter(7,Types.VARCHAR);
				cstmt.registerOutParameter(8,Types.VARCHAR);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.registerOutParameter(10,Types.VARCHAR);
				cstmt.registerOutParameter(11,Types.VARCHAR);
				cstmt.registerOutParameter(12,Types.VARCHAR);
				cstmt.registerOutParameter(13,Types.VARCHAR);
				cstmt.registerOutParameter(14,Types.VARCHAR);
				cstmt.registerOutParameter(15,Types.VARCHAR);
				cstmt.execute();
				userSession.setDlsj(cstmt.getString(2));
				userSession.setDlms(cstmt.getString(3));
				userSession.setDlip(cstmt.getString(4));
				
				userSession.setScdlsj(cstmt.getString(5));
				userSession.setScdlms(cstmt.getString(6));
				userSession.setScdlip(cstmt.getString(7));
				
				userSession.setMmyxq(cstmt.getString(8));
				userSession.setZhyxq(cstmt.getString(9));
				userSession.setDlcs(cstmt.getString(10));
				userSession.setBydlcs(cstmt.getString(11));
				userSession.setZxrs(cstmt.getString(12));
				userSession.setZdzxrs(cstmt.getString(13));
				userSession.setDrfwrs(cstmt.getString(14));
				userSession.setZfwrs(cstmt.getString(15));
				return null;
			}
		};
		callBack.setParameterObject(userSession);
		jdbcTemplate.execute(callString,callBack);
	}
	/**
	 * 验证登录用户
	 */
	public int validateSysuser(SysUser sysuser,String strRemoteAddr) throws Exception{
		sysuser.setIp(strRemoteAddr);
		String callString="{call FRM_SYS_PKG.checkfrm_sysuser(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysUser sysuser=(SysUser)getParameterObject();
				cstmt.setString(1,sysuser.getYhdh());
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.setString(2,sysuser.getMm());
				cstmt.setString(3,sysuser.getIp());
				cstmt.setString(4,sysuser.getSfzmhm());
				cstmt.setString(5,sysuser.getDlms());
				cstmt.registerOutParameter(6,Types.NUMERIC); // result
				cstmt.execute();
				int iResult=cstmt.getInt(6);
				sysuser.setYhdh(cstmt.getString(1));
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(sysuser);
		Integer i=(Integer)jdbcTemplate.execute(callString,callBack);
		return i.intValue();
	}
	// 验证ATM及权限用户
	public DbResult validateAtmSysuser(AtmAuthUser atmAuthUser){
		String callString="{call FRM_SYS_PKG.checkAtm_sysuser(?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				AtmAuthUser atmAuthUser=(AtmAuthUser)getParameterObject();
				cstmt.setString(1,atmAuthUser.getYhdh());
				cstmt.setString(2,atmAuthUser.getIpdz());
				cstmt.setString(3,atmAuthUser.getMac());
				cstmt.setString(4,atmAuthUser.getXtlb());
				cstmt.setString(5,atmAuthUser.getCdbh());
				cstmt.setString(6,atmAuthUser.getGnid());
				cstmt.registerOutParameter(7,Types.NUMERIC); // result
				cstmt.registerOutParameter(8,Types.VARCHAR); // result
				cstmt.execute();
				int iResult=cstmt.getInt(7);
				DbResult dbReturnInfo = new DbResult();
				dbReturnInfo.setCode(cstmt.getLong(7));
				dbReturnInfo.setMsg(cstmt.getString(8));
				return dbReturnInfo;
			}
		};
		callBack.setParameterObject(atmAuthUser);
		DbResult i=(DbResult)jdbcTemplate.execute(callString,callBack);
		return i;
	}
//公安版本：获取交警用户的数量
	public int getTrafficPolice(String yhdh) throws Exception{
		String sql="select count(*) c from frm_sysuser where yhdh='"+yhdh+"'";
		return jdbcTemplate.queryForInt(sql);
	}
	
	public int getTrafficPoliceSfzmhm(String sfzmhm) throws Exception{
		String sql="select count(*) c from frm_sysuser where sfzmhm='"+sfzmhm+"'";
		return jdbcTemplate.queryForInt(sql);
	}	
	
//公安版本：校验公安用户
	public int validatePoliceman(SysUser sysuser,String strRemoteAddr) throws Exception{
		sysuser.setIp(strRemoteAddr);
		String callString="{call FRM_SYS_PKG.checkfrm_policeman(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysUser sysuser=(SysUser)getParameterObject();
				cstmt.setString(1,sysuser.getYhdh());
				cstmt.registerOutParameter(1,Types.VARCHAR);
				cstmt.setString(2,sysuser.getMm());
				cstmt.setString(3,sysuser.getIp());
				cstmt.setString(4,sysuser.getSfzmhm());
				cstmt.setString(5,sysuser.getDlms());
				cstmt.registerOutParameter(6,Types.NUMERIC);
				cstmt.execute();
				int iResult=cstmt.getInt(6);
				sysuser.setYhdh(cstmt.getString(1));
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(sysuser);
		Integer i=(Integer)jdbcTemplate.execute(callString,callBack);
		return i.intValue();
	}
//公安版本：获取公安用户
	public SysUser getPoliceman(String yhdh) throws DataAccessException{
		String sql="select * from pls_sysuser where yhdh=?";
		Object[] paraObjects=new Object[]{yhdh};
		SysUser sysUser=(SysUser)jdbcTemplate.queryForBean(sql,paraObjects,SysUser.class);
		return sysUser;
	}
//公安版本：保存公安用户的日志
	public void getPolicemanInfo(UserSession userSession) throws Exception{
		String callString="{call FRM_SYS_PKG.getPolicemanInfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				UserSession userSession=(UserSession)getParameterObject();
				cstmt.setString(1,userSession.getYhdh());
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.registerOutParameter(5,Types.VARCHAR);
				cstmt.registerOutParameter(6,Types.VARCHAR);
				cstmt.registerOutParameter(7,Types.VARCHAR);
				cstmt.registerOutParameter(8,Types.VARCHAR);
				cstmt.registerOutParameter(9,Types.VARCHAR);
				cstmt.registerOutParameter(10,Types.VARCHAR);
				cstmt.registerOutParameter(11,Types.VARCHAR);
				cstmt.registerOutParameter(12,Types.VARCHAR);
				cstmt.registerOutParameter(13,Types.VARCHAR);
				cstmt.registerOutParameter(14,Types.VARCHAR);
				cstmt.registerOutParameter(15,Types.VARCHAR);
				cstmt.execute();
				userSession.setDlsj(cstmt.getString(2));
				userSession.setDlms(cstmt.getString(3));
				userSession.setDlip(cstmt.getString(4));
				userSession.setScdlsj(cstmt.getString(5));
				userSession.setScdlms(cstmt.getString(6));
				userSession.setScdlip(cstmt.getString(7));
				userSession.setMmyxq(cstmt.getString(8));
				userSession.setZhyxq(cstmt.getString(9));
				userSession.setDlcs(cstmt.getString(10));
				userSession.setBydlcs(cstmt.getString(11));
				userSession.setZxrs(cstmt.getString(12));
				userSession.setZdzxrs(cstmt.getString(13));
				userSession.setDrfwrs(cstmt.getString(14));
				userSession.setZfwrs(cstmt.getString(15));
				return null;
			}
		};
		callBack.setParameterObject(userSession);
		jdbcTemplate.execute(callString,callBack);
	}
	
	/**
	 * 获取用户权限程序
	 */
	public List getSysuserMenuList(String strYhdh) throws Exception{
		String sql=" select v2.cxdh,v2.mldh"+" from frm_userrole v1,frm_rolemenu v2"+" where v1.jsdh=v2.jsdh"+" and   v1.yhdh='"+strYhdh+"'"+" union"+" select v1.cxdh,v1.mldh"+" from frm_usermenu v1"+" where v1.yhdh='"+strYhdh+"'";
		List menuList=jdbcTemplate.queryForList(sql,Program.class);
		return menuList;
	}

	/**
	 * 保存密码
	 */
	public SysResult savapassword(PassWord passWord) throws SQLException{
		String callString="{call FRM_SYS_PKG.saveFRM_PASSWORD(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				PassWord passWord=(PassWord)getParameterObject();
				cstmt.setString(1,passWord.getYhdh());
				cstmt.setString(2,passWord.getJmm());
				cstmt.setString(3,passWord.getXmm());
				cstmt.registerOutParameter(4,Types.NUMERIC); // result
				cstmt.registerOutParameter(5,Types.VARCHAR);
				cstmt.execute();
				SysResult result = new SysResult();
				result.setFlag(cstmt.getInt(4));
				result.setDesc(cstmt.getString(5));
				return result;
			}
		};
		callBack.setParameterObject(passWord);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	//公安版本：
	public SysResult savaPlspassword(PassWord passWord) throws SQLException{
		String callString="{call RM_PKG.savePLS_PASSWORD(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				PassWord passWord=(PassWord)getParameterObject();
				cstmt.setString(1,passWord.getYhdh());
				cstmt.setString(2,passWord.getJmm());
				cstmt.setString(3,passWord.getXmm());
				cstmt.registerOutParameter(4,Types.NUMERIC); // result
				cstmt.registerOutParameter(5,Types.VARCHAR);
				cstmt.execute();
				SysResult result = new SysResult();
				result.setFlag(cstmt.getInt(4));
				result.setDesc(cstmt.getString(5));
				return result;
			}
		};
		callBack.setParameterObject(passWord);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	
	/**
	 * 保存密码
	 */
	public SysResult savagdip(PassWord passWord) throws SQLException{
		String callString="{call FRM_SYS_PKG.saveFRM_GDIP(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				PassWord passWord=(PassWord)getParameterObject();
				cstmt.setString(1,passWord.getYhdh());
				cstmt.setString(2,passWord.getGdip());
				cstmt.setString(3,passWord.getGdip1());
				cstmt.setString(4,passWord.getGdip2());
				cstmt.registerOutParameter(5,Types.NUMERIC); // result
				cstmt.registerOutParameter(6,Types.VARCHAR);
				cstmt.execute();
				SysResult result = new SysResult();
				result.setFlag(cstmt.getInt(5));
				result.setDesc(cstmt.getString(6));
				return result;
			}
		};
		callBack.setParameterObject(passWord);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	public SysUserFinger getSysUserFinger(String yhdh){
		String sql="select * from frm_sysuser_finger where yhdh=?";
		Object[] paraObjects=new Object[]{yhdh};
		SysUserFinger sysUserFinger=(SysUserFinger)jdbcTemplate.queryForBean(sql,paraObjects,SysUserFinger.class);
		return sysUserFinger;
	}
	public SysResult savaFinger(SysUserFinger sysUserFinger) throws SQLException {
		String callString="{call FRM_SYS_PKG.saveFRM_SysUserFinger(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysUserFinger sysUserFinger=(SysUserFinger)getParameterObject();
				cstmt.setString(1,sysUserFinger.getYhdh());
				cstmt.setString(2,sysUserFinger.getZwtz1());
				cstmt.setString(3,sysUserFinger.getZwtz2());
				cstmt.setString(4,sysUserFinger.getZwtz3());
				cstmt.registerOutParameter(5,Types.NUMERIC); // result
				cstmt.registerOutParameter(6,Types.VARCHAR);
				cstmt.execute();
				SysResult result = new SysResult();
				result.setFlag(cstmt.getInt(5));
				result.setDesc(cstmt.getString(6));
				return result;
			}
		};
		callBack.setParameterObject(sysUserFinger);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	/**
	 * 获取用户列表
	 */
	public List getSysusersByPagesize(SysUser user,PageController controller) throws DataAccessException{
		HashMap map=new HashMap();
		List queryList  = null;
		String sql ="select v1.yhdh,v1.xm,v1.mm,v1.glbm,v1.sfzmhm,v1.ipks,"
			+"v1.ipjs,to_char(v1.zhyxq,'yyyy-mm-dd') zhyxq,"
			+"to_char(v1.mmyxq,'yyyy-mm-dd') mmyxq,v1.jyd,v1.spjb,v1.spglbm,"
			+"v1.sfmj,v1.rybh,v1.qxms,v1.zt,v1.xtgly,"
			+"to_char(v1.zjdlsj,'yyyy-mm-dd') zjdlsj,v1.bz,v1.kgywyhlx,v1.yhssyw,v1.mac,v1.gdip "
			+"from frm_sysuser v1 where 1=1 ";
		
		if (StringUtil.checkBN(user.getGlbm())) {
			sql += " and v1.glbm like '"+user.getGlbm()+"'";
		}		

		if(user.getYhdh()!=null&&!user.getYhdh().equals("")){
			sql+=" and yhdh like :yhdh ";
			map.put("yhdh","%"+user.getYhdh()+"%");
		}
		if(user.getXm()!=null&&!user.getXm().equals("")){
			sql+=" and xm like :xm ";
			map.put("xm","%"+user.getXm()+"%");
		}
		if(user.getXtgly()!=null&&!user.getXtgly().equals("")){
			sql+=" and xtgly = :xtgly ";
			map.put("xtgly",user.getXtgly());
		}
		//增加用户所属业务判断
		/*
		if(user.getYhssyw().equals("")){
			//该用户可管辖的全部
			String kgywyhlx=user.getKgywyhlx();
			if(kgywyhlx.equals("")){
				kgywyhlx="1000000000";
			}
			if(kgywyhlx.charAt(0)!='1'){
				sql+=getKgywyhlx(kgywyhlx);
			}
		}else{
			String yhssyw=user.getYhssyw();
			String kgywyhlx=user.getKgywyhlx();
			if(kgywyhlx.equals("")){
				kgywyhlx="1000000000";
			}
			int index=Integer.valueOf(yhssyw).intValue();
			sql+=" and (v1.yhssyw is null or v1.yhssyw like '"+getZwf(index-1,"_")+"1"+getZwf(kgywyhlx.length()-index,"_")+"')";
			
		}*/
		
		//增加权限查询条件，是操作权限还是管理权限
		if(user.getQxms().equals("1")){
			//操作权限
			if(user.getSqms().equals("1")){
				if(!user.getTjsdh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.* " 
						+ " from frm_userrole w1"
						+ " where w1.yhdh=v1.yhdh "
						+ " and   w1.jsdh='"+user.getTjsdh()+"'"
						+ ")";
				}				
			}else if(user.getSqms().equals("2")){
				if(!user.getTgnid().equals("")){
					sql = sql + " and exists ("
						+ " select w1.xtlb,w1.cdbh,w1.gnlb" 
						+ " from frm_usermenu w1"
						+ " where w1.yhdh=v1.yhdh"
						+ " and   w1.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w1.xtlb||w1.cdbh = '"+user.getTxtlb()+"'"
						+ " union"
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_userrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w2.xtlb||w2.cdbh = '"+user.getTxtlb()+"'"
						+ ")";				
				}else if(!user.getTcdbh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.xtlb,w1.cdbh,w1.gnlb" 
						+ " from frm_usermenu w1"
						+ " where w1.yhdh=v1.yhdh"
						+ " and   w1.cdbh='"+user.getTcdbh()+"'"
						+ " and   w1.xtlb = '"+user.getTxtlb()+"'"
						+ " union"
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_userrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.cdbh='"+user.getTcdbh()+"'"
						+ " and   w2.xtlb = '"+user.getTxtlb()+"'"
						+ ")";
				}
			}
			
		}else{
			//管理权限
			if(user.getSqms().equals("1")){
				if(!user.getTjsdh().equals("")){
					sql = sql + " and exists ("
						+ " select w1.* " 
						+ " from frm_user_grantrole w1"
						+ " where w1.yhdh=v1.yhdh "
						+ " and   w1.jsdh='"+user.getTjsdh()+"'"
						+ ")";
				}			
				
			}else if(user.getSqms().equals("2")){			
				if(!user.getTgnid().equals("")){
					sql = sql + " and exists ("
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_user_grantrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.gnlb like '%"+user.getTgnid()+"%'"
						+ " and   w2.xtlb||w2.cdbh = '"+user.getTxtlb()+"'"
						+ ")";				
				}else if(!user.getTcdbh().equals("")){
					sql = sql + " and exists ("
						+ " select w2.xtlb,w2.cdbh,w2.gnlb" 
						+ " from frm_user_grantrole w1,frm_rolemenu w2"
						+ " where w1.jsdh=w2.jsdh"
						+ " and   w1.yhdh=v1.yhdh "
						+ " and   w2.cdbh='"+user.getTcdbh()+"'"
						+ " and   w2.xtlb = '"+user.getTxtlb()+"'"
						+ ")";
				}
			}
		}
		
		//增加操作权限是否授权查询
		if (StringUtil.checkBN(user.getCzqx())) {
			//已授权和未授权
			if(user.getCzqx().equals("1")){
				sql = sql + " and exists ("
					+ " select w1.yhdh" 
					+ " from frm_userrole w1 "
					+ " where w1.yhdh=v1.yhdh "
					+ " union "
					+ " select w2.yhdh" 
					+ " from frm_usermenu w2 "
					+ " where w2.yhdh=v1.yhdh "
					+ ")";
			}else if(user.getCzqx().equals("2")){
				sql = sql + " and not exists ("
					+ " select w1.yhdh" 
					+ " from frm_userrole w1 "
					+ " where w1.yhdh=v1.yhdh "
					+ " union "
					+ " select w2.yhdh" 
					+ " from frm_usermenu w2 "
					+ " where w2.yhdh=v1.yhdh "
					+ ")";
			}
		}
		
		// 排序
		if (StringUtil.checkBN(user.getOrder())) {
			sql += " order by v1." + user.getOrder()+" "+user.getProper();
		} else {
			sql += " order by v1.yhdh";
		}
		queryList=controller.getWarpedList(sql,map,SysUser.class,jdbcTemplate);
		return queryList;
	}

	
	private String getKgywyhlx(String kgywyhlx){
		String result="v1.yhssyw is null";
		boolean bk=true;
		for(int i=1;i<7;i++){
			if(bk&& kgywyhlx.charAt(i)=='1'){
				result+=" or v1.yhssyw like '"+getZwf(i,"_")+"1" + getZwf(kgywyhlx.length()-i-1,"_")+"'";
				bk=false;
			}else if(kgywyhlx.charAt(i)=='1'){
				result+=" or v1.yhssyw like '"+getZwf(i,"_")+"1" + getZwf(kgywyhlx.length()-i-1,"_")+"'";
			}
		}
		if(!result.equals("")){
			result=" and ("+result+")";
		}
		
		return result;
	}
	
	private String getZwf(int num,String zwf){
		String result="";
		for(int i=0;i<num;i++){
			result+=zwf;
		}
		return result;
	}


	// 保存桌面设置
	public int Savedesk(UserDesk userDesk) throws SQLException{
		String callString="{call FRM_SYS_PKG.Savedesk(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				UserDesk userDesk=(UserDesk)getParameterObject();

				if(userDesk.getYhdh()==null) cstmt.setNull(1,Types.VARCHAR);
				else cstmt.setString(1,userDesk.getYhdh());

				if(userDesk.getCdbh()==null) cstmt.setNull(2,Types.VARCHAR);
				else cstmt.setString(2,userDesk.getCdbh());
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.execute();
				int iResult=cstmt.getInt(3);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(userDesk);
		Integer i=(Integer)jdbcTemplate.execute(callString,callBack);
		return i.intValue();
	}
	
	//zhoujn 20100524
	public java.sql.Blob getQmtp(String yhdh)throws Exception {
		String sql=" select qmtp from FRM_SYSUSER_SEAL where yhdh='"+yhdh+"'";
		java.sql.Blob zp = null;
		zp=(java.sql.Blob) jdbcTemplate.queryForObject(sql, Blob.class);
		return zp;
	}	
	
	public int getQmtpNum(String yhdh){
		String sql=" select count(*) from FRM_SYSUSER_SEAL where yhdh='"+yhdh+"'";
		int result= jdbcTemplate.queryForInt(sql);
		return result;
	}	
	
	//签名图片管理
	public SysResult saveQmtp(String yhdh,SysUserSeal obj,final byte[] qmtp) throws SQLException{
		SysResult result = new SysResult();
		result.setFlag(0);
		
		String callString="{call Frm_Sys_Pkg.Savefrm_Sysuser_Seal(?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				String yhdh = (String) getParameterObject();
				SysUserSeal obj = new SysUserSeal();
				obj.setXh(yhdh);
				cstmt.setString(1, yhdh);
				cstmt.registerOutParameter(2,Types.NUMERIC);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.registerOutParameter(5,Types.BLOB);
				
				cstmt.execute();
				obj.setCode(cstmt.getLong(2));
				obj.setMsg(cstmt.getString(4));
				BLOB blob=(oracle.sql.BLOB)cstmt.getBlob(5);
				OutputStream out=blob.getBinaryOutputStream();
				try{
					out.write(qmtp);
					out.flush();
					out.close();
				}catch(IOException e){
					obj.setCode(0);
					obj.setMsg(e.getMessage());
					e.printStackTrace();
				}
				obj.setZp(blob);
				return obj;
			}
		};
		callBack.setParameterObject(yhdh);
		SysUserSeal objtemp=(SysUserSeal)jdbcTemplate.execute(callString,callBack);
		
		
		if(objtemp.getCode()==1){
			String callSql="{call Frm_Sys_Pkg.Updfrm_Sysuser_Seal(?,?,?,?,?)}";
			CallableStatementCallbackImpl callBack1=new CallableStatementCallbackImpl(){
				public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
					DbParaInfo para1 = (DbParaInfo) getParameterObject();
					SysUserSeal obj = (SysUserSeal) para1.getObject();
					
					SysResult result=new SysResult();
					FuncUtil.bean2cstmt(obj, "xh", cstmt, 1);
					cstmt.setBlob(2,obj.getZp());
					cstmt.registerOutParameter(3,Types.NUMERIC);
					cstmt.registerOutParameter(4,Types.VARCHAR);
					cstmt.registerOutParameter(5,Types.VARCHAR);
					cstmt.execute();
					result.setFlag(cstmt.getLong(3));
					result.setDesc1(cstmt.getString(4));
					result.setDesc(cstmt.getString(5));
					return result;
				}
			};
			DbParaInfo para1 = new DbParaInfo();
			para1.setObject(objtemp);
			callBack1.setParameterObject( para1);		
			result=(SysResult)jdbcTemplate.execute(callSql,callBack1);
		}else{
			result.setFlag(objtemp.getCode());
			result.setDesc1(objtemp.getMsg());
		}
		
		return result;
	}
	
	
	public SysResult delQmtp(String yhdh) throws SQLException{
		String callString="{call Frm_Sys_Pkg.Delfrm_Sysuser_Seal(?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				String yhdh=(String)getParameterObject();
				SysResult result=new SysResult();
				cstmt.setString(1, yhdh);
				cstmt.registerOutParameter(2,Types.NUMERIC);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(2));
				result.setDesc1(cstmt.getString(3));
				result.setDesc(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(yhdh);
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}	
	
	
	//zhoujn 由于oracle 版本问题没法处理，采用原始方式
	//修改，改为存储过程方式
	public void updateQmtp(final SysUserSeal obj) throws Exception {
		oracle.sql.BLOB blob = null;
		Connection conn = null;
		try {
			conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			conn.setAutoCommit(false);
			Statement stmt = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT qmtp FROM FRM_SYSUSER_SEAL WHERE yhdh='" + obj.getYhdh()
					+ "' FOR UPDATE ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				blob = (oracle.sql.BLOB) rs.getBlob(1);
				blob.putBytes(1, obj.getQmtp().getBytes());
				sql = "update FRM_SYSUSER_SEAL set qmtp=? WHERE yhdh='" + obj.getYhdh()
						+ "' ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setBlob(1, blob);
				pstmt.executeUpdate();
				conn.commit();
			}
			rs.close();
			stmt.close();
			pstmt.close();
		} catch (Exception ex) {
			DataSourceUtils.releaseConnection(conn, jdbcTemplate
					.getDataSource());
			throw new Exception("执行时出错!" + ex.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(conn, jdbcTemplate
					.getDataSource());
		}
	}	
	
	
	//获取用户人员信息
	public SysUser getUserinfo(String rylb,String sfzmhm) throws Exception {
		HashMap map=new HashMap();
		String sql="";
		if(rylb.equals("1")){
			//民警
			sql="select v1.jybh rybh,v1.xm from bas_police v1 where jlzt in ('1','3') and sfzmhm=:sfzmhm";
		}else{
			//协警和工作人员
			sql="select v1.rybh,v1.xm from bas_employee v1 where jlzt in ('1','3') and sfzmhm=:sfzmhm";
		}
		map.put("sfzmhm",sfzmhm);
		SysUser sysuser=(SysUser)jdbcTemplate.queryForSingleObject(sql, map, SysUser.class);
		return sysuser;
	}
	
	//获取用户列表
	public List getUserinfoList(SysUser obj) throws Exception {
		List resultlist=new ArrayList();
		HashMap map=new HashMap();
		String sql="";
		if(obj.getSfmj().equals("1")){
			//民警
			sql=" select v1.xh,v1.jylx,v1.jybh,v1.xm,v1.sfzmhm,v1.bmdm,"
		    	+"v1.bzlx,v1.ywgw,to_char(v1.csrq,'yyyy-mm-dd') csrq,v1.xb,v1.jx,v1.jb,v1.jg,"
		    	+"to_char(v1.rdtsj,'yyyy-mm-dd') rdtsj,v1.zzmm,v1.mz,v1.xl,v1.zy,v1.zw,"
		    	+"to_char(v1.rdsj,'yyyy-mm-dd') rdsj,to_char(v1.cgsj,'yyyy-mm-dd') cgsj,"
		    	+"to_char(v1.rxzsj,'yyyy-mm-dd') rxzsj,v1.jtzz,v1.sj,v1.bgdh,v1.zd,v1.jlzt,"
		    	+"to_char(v1.gxsj,'yyyy-mm-dd') gxsj,to_char(v1.cjsj,'yyyy-mm-dd') cjsj,v1.sgcldj,v1.ldjb "
		    	+"from bas_police v1 ";
			sql+="where jlzt in('1','3') and bmdm=:glbm ";
			map.put("glbm",obj.getGlbm());
			if(!obj.getXm().equals("")){
				sql+=" and xm like :xm";
				map.put("xm","%"+obj.getXm()+"%");
			}
			resultlist=jdbcTemplate.queryForList(sql, map, BasPolice.class);			
		}else{
			//协警和工作人员
			sql = " select v1.xh,v1.xm,v1.bmdm,v1.xb,"
				+"v1.rylx,v1.rybh,v1.sfzmhm,to_char(v1.csrq,'yyyy-mm-dd') csrq,"
				+"v1.jg,v1.zzmm,v1.mz,v1.xl,v1.zy,v1.zw,"
				+"to_char(v1.rdsj,'yyyy-mm-dd') rdsj,v1.jtzz,v1.sj,v1.bgdh,"
				+"v1.zd,to_char(v1.cjsj,'yyyy-mm-dd') cjsj,"
				+"to_char(v1.gxsj,'yyyy-mm-dd') gxsj,v1.jlzt "
				+"from bas_employee v1 ";
			sql+="where jlzt in ('1','3') and bmdm=:glbm ";
			map.put("glbm",obj.getGlbm());
			if(!obj.getXm().equals("")){
				sql+=" and xm like :xm";
				map.put("xm","%"+obj.getXm()+"%");
			}
			resultlist=jdbcTemplate.queryForList(sql, map, BasEmployee.class);			
		}
		return resultlist;
	}	
	public List getDepSsywusers(String glbm,String yhssyw){
		String sqlString = "select * from frm_sysuser where glbm=:glbm and substr(yhssyw,:yhssyw,1)='1' order by xm";
		HashMap map = new HashMap();
		map.put("glbm",glbm);
		map.put("yhssyw",yhssyw);
		List resultList = this.jdbcTemplate.queryForList(sqlString,map,SysUser.class);
		return resultList;
	}
	//获取管理部门下所有民警用户的信息
	public List getPoliceUserList(String glbm){
		List resultlist=new ArrayList();
		HashMap map=new HashMap();
		String sql="select xh,jylx,jybh,xm,sfzmhm,bmdm from bas_police where jlzt in ('1','3') and bmdm=:glbm order by xh";
		map.put("glbm",glbm);
		resultlist=jdbcTemplate.queryForList(sql, map, BasPolice.class);			
		return resultlist;
	}	
	//获取管理部门下指定警号民警用户的信息
	public BasPolice getPolice(String glbm,String jybh){
		List resultlist=new ArrayList();
		HashMap map=new HashMap();
		String sql=null;
		int x=0;
        if(glbm==null)
        {glbm="";}
		sql="select frm_comm_pkg.Getglbmt('"+glbm+"') glbmt,xh,jylx,jybh,xm,sfzmhm,bmdm from bas_police where  jlzt in ('1','3') and jybh=:jybh order by bmdm";
		map.put("jybh",jybh);
		resultlist=jdbcTemplate.queryForList(sql, map, BasPolice.class);
		if(resultlist.size()==0)
		{return null;}
		for(int i=0;i<resultlist.size();i++)
		{
			BasPolice bas=(BasPolice)resultlist.get(i);
			if(glbm.equals(""))
			{
				bas.setZt("1");
				return bas;
			}
			if(bas.getGlbmt().equals(bas.getBmdm().substring(0,bas.getGlbmt().length())))
			{bas.setZt("1");
				return bas;}
		}
		BasPolice bas= (BasPolice) resultlist.get(0);
		bas.setZt("0");
		return bas;
	}
	public SysUser getSysuserFromMem(String yhdh){
		Hashtable tab=(Hashtable)SystemCache.getInstance().getValue("sysuserhash");
		if(tab==null){
			tab = new Hashtable();
			SystemCache.getInstance().reg("sysuserhash",tab);			
		}
		SysUser sysUser = (SysUser)tab.get(yhdh);
		if(sysUser==null){
			String sql="select * from frm_sysuser where yhdh=?";
			Object[] paraObjects=new Object[]{yhdh};
			sysUser=(SysUser)jdbcTemplate.queryForBean(sql,paraObjects,SysUser.class);
			tab.put(yhdh,sysUser);
		}
		return sysUser;
	}
	//获取部门下属所有的民警
	public List getDepPoliceusers_Like(String glbm,String yhssyw){
		String sqlString = "select a.* from frm_sysuser a,bas_police b where a.sfzmhm=b.sfzmhm and a.glbm like :glbm and substr(a.yhssyw,:yhssyw,1)='1' and a.zt='1' and b.jlzt in ('1','3') order by a.xm";
		HashMap map = new HashMap();
		map.put("glbm",glbm + "%");
		map.put("yhssyw",yhssyw);
		List resultList = this.jdbcTemplate.queryForList(sqlString,map,SysUser.class);
		return resultList;
	}
	//获取部门下yhssyw指定民警
	public List getDepPoliceusers(String glbm,String yhssyw){
		String sqlString = "select a.* from frm_sysuser a,bas_police b where a.sfzmhm=b.sfzmhm and a.glbm=:glbm and substr(a.yhssyw,:yhssyw,1)='1' and a.zt='1' and b.jlzt in ('1','3') order by a.xm";
		HashMap map = new HashMap();
		map.put("glbm",glbm);
		map.put("yhssyw",yhssyw);
		List resultList = this.jdbcTemplate.queryForList(sqlString,map,SysUser.class);
		return resultList;
	}
	//保存用户在线信息
	public void recZxyhxx(String flag){
		String callString="{call FRM_SYS_PKG.saveZxyhxx(?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				String flag=(String)getParameterObject();
				cstmt.setString(1,flag);
				cstmt.execute();
				return null;
			}
		};
		callBack.setParameterObject(flag);
		jdbcTemplate.execute(callString,callBack);
	}
	//保存登录失败信息
	public void saveUserLoginFail(){
		String callString="{call FRM_SYS_PKG.saveFrmUserloginfail()}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				cstmt.execute();
				return null;
			}
		};
		jdbcTemplate.execute(callString,callBack);
	}
	//获取某管理部门用户的列表
	public List getSysusers(String glbm){
		String sqlString = "select a.* from frm_sysuser a where a.glbm = :glbm and a.zt='1' and a.sfmj in ('1','2','3') order by a.xm";
		HashMap map = new HashMap();
		map.put("glbm",glbm);
		List resultList = this.jdbcTemplate.queryForList(sqlString,map,SysUser.class);
		return resultList;
	}

	public String getFwzbh(String yhdh) {
		String result = null;
		try {
			String sql = "select fwzbh from (select * from SERV_STATION_PERSON t where rybh = '" + yhdh + "' and jlzt = '1' order by cjsj desc) where rownum = 1";
			result = jdbcTemplate.queryForObject(sql, String.class);
		} catch (Exception e) {
			//System.out.println("当前用户未配置到交警执法站");
		}
		
		return result;
	}
}
