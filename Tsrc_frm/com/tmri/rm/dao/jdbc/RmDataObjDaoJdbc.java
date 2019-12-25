package com.tmri.rm.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.bean.UserRole;
import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.rm.bean.RmTransTasklog;
import com.tmri.rm.dao.RmDataObjDao;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;
@Repository
public class RmDataObjDaoJdbc extends FrmDaoJdbc implements RmDataObjDao{
	
	public DbResult setOracleData(Object obj) throws Exception {
		if(null == obj) {
			throw new Exception("对象不能为空");
		}
		DbResult result = null;
		String className = obj.getClass().getName();
		if("com.tmri.share.frm.bean.Department".equals(className)){
			result=this.setDepartment((Department)obj);
		}else if("com.tmri.rm.bean.RmTransTasklog".equals(className)){
			result=this.setRmTransTasklog((RmTransTasklog)obj);
		}else{
			throw new Exception("未知的对象类型");
		}
		
		return result;
	}
	
	public DbResult setSysuser(SysUser bean) throws Exception {
		String callString="{call RM_SETDATA_PKG.setFrm_Sysuser(?,?,?,?,?, ?,?,?,?,?, ?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysUser bean=(SysUser)getParameterObject();
				DbResult result = new DbResult();
				if(bean.getYhdh()==null) cstmt.setNull(1, Types.NULL); else cstmt.setString(1,bean.getYhdh());
				if(bean.getIpks()==null) cstmt.setNull(2, Types.NULL); else cstmt.setString(2,bean.getIpks());
				if(bean.getIpjs()==null) cstmt.setNull(3, Types.NULL); else cstmt.setString(3,bean.getIpjs());
				if(bean.getGdip()==null) cstmt.setNull(4, Types.NULL); else cstmt.setString(4,bean.getGdip());
				if(bean.getGdip1()==null) cstmt.setNull(5, Types.NULL); else cstmt.setString(5,bean.getGdip1());
				if(bean.getGdip2()==null) cstmt.setNull(6, Types.NULL); else cstmt.setString(6,bean.getGdip2());
				if(bean.getXtgly() ==  null) cstmt.setNull(7, Types.NULL); else cstmt.setString(7,bean.getXtgly());
				if(bean.getQxms() ==  null) cstmt.setNull(8, Types.NULL); else cstmt.setString(8,bean.getQxms());
				if(bean.getZhyxq() ==  null) cstmt.setNull(9, Types.NULL); else cstmt.setString(9,bean.getZhyxq());
				if(bean.getMmyxq() ==  null) cstmt.setNull(10, Types.NULL); else cstmt.setString(10,bean.getMmyxq());
				cstmt.registerOutParameter(11,Types.NUMERIC);
				cstmt.registerOutParameter(12,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(11));
				result.setMsg(cstmt.getString(12));
				return result;
			}
		};
		callBack.setParameterObject(bean);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
	public DbResult setUserRole(UserRole userRole) throws Exception{
		String callString="{call RM_SETDATA_PKG.setFrm_Userrole(?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				UserRole bean = (UserRole)getParameterObject();
				DbResult result = new DbResult();
				cstmt.setString(1,bean.getYhdh());
				cstmt.setString(2,bean.getJsdh());
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(3));
				result.setMsg(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(userRole);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
	public DbResult setUserGrantrole(UserRole userRole) throws Exception{
		String callString="{call RM_SETDATA_PKG.setFrm_User_Grantrole(?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				UserRole bean = (UserRole)getParameterObject();
				DbResult result = new DbResult();
				cstmt.setString(1,bean.getYhdh());
				cstmt.setString(2,bean.getJsdh());
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(3));
				result.setMsg(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(userRole);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
	/** 设置公安部门信息表（表名为：PLS_DEPARTMENT）对象 */
	private DbResult setDepartment(Department bean) throws Exception{
		String callString="{call RM_SETDATA_PKG.setPLS_DEPARTMENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				Department bean=(Department)getParameterObject();
				DbResult result = new DbResult();
				if(bean.getGlbm()==null) cstmt.setNull(1, Types.NULL); else cstmt.setString(1,bean.getGlbm());
				if(bean.getJzlx()==null) cstmt.setNull(2, Types.NULL); else cstmt.setString(2,bean.getJzlx());
				if(bean.getBmmc()==null) cstmt.setNull(3, Types.NULL); else cstmt.setString(3,bean.getBmmc());
				if(bean.getBmqc()==null) cstmt.setNull(4, Types.NULL); else cstmt.setString(4,bean.getBmqc());
				if(bean.getYzmc()==null) cstmt.setNull(5, Types.NULL); else cstmt.setString(5,bean.getYzmc());
				if(bean.getBmjb()==null) cstmt.setNull(6, Types.NULL); else cstmt.setString(6,bean.getBmjb());
				if(bean.getFzr()==null) cstmt.setNull(7, Types.NULL); else cstmt.setString(7,bean.getFzr());
				if(bean.getLxr()==null) cstmt.setNull(8, Types.NULL); else cstmt.setString(8,bean.getLxr());
				if(bean.getLxdh()==null) cstmt.setNull(9, Types.NULL); else cstmt.setString(9,bean.getLxdh());
				if(bean.getCzhm()==null) cstmt.setNull(10, Types.NULL); else cstmt.setString(10,bean.getCzhm());
				if(bean.getLxdz()==null) cstmt.setNull(11, Types.NULL); else cstmt.setString(11,bean.getLxdz());
				if(bean.getSjbm()==null) cstmt.setNull(12, Types.NULL); else cstmt.setString(12,bean.getSjbm());
				if(bean.getJlzt()==null) cstmt.setNull(13, Types.NULL); else cstmt.setString(13,bean.getJlzt());
				if(bean.getBz()==null) cstmt.setNull(14, Types.NULL); else cstmt.setString(14,bean.getBz());
				cstmt.registerOutParameter(15,Types.NUMERIC);
				cstmt.registerOutParameter(16,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(15));
				result.setMsg(cstmt.getString(16));
				return result;
			}
		};
		callBack.setParameterObject(bean);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
	/** 设置公安用户信息表（表名为：PLS_SYSUSER）对象 */
	public DbResult setPlsSysuser(SysUser bean) throws Exception{
		String callString="{call RM_SETDATA_PKG.setPLS_SYSUSER(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysUser bean=(SysUser)getParameterObject();
				DbResult result = new DbResult();
				cstmt.setString(1,bean.getYhdh());
				if(bean.getXm()==null) cstmt.setNull(2, Types.NULL); else cstmt.setString(2,bean.getXm());
				if(bean.getMm()==null) cstmt.setNull(3, Types.NULL); else cstmt.setString(3,bean.getMm());
				cstmt.setString(4,bean.getGlbm());
				cstmt.setString(5,bean.getSfzmhm());
				if(bean.getIpks()==null) cstmt.setNull(6, Types.NULL); else cstmt.setString(6,bean.getIpks());
				if(bean.getIpjs()==null) cstmt.setNull(7, Types.NULL); else cstmt.setString(7,bean.getIpjs());
				if(bean.getZhyxq()==null) cstmt.setNull(8, Types.NULL); else cstmt.setString(8,bean.getZhyxq());
				if(bean.getMmyxq()==null) cstmt.setNull(9, Types.NULL); else cstmt.setString(9,bean.getMmyxq());
				if(bean.getSfmj()==null) cstmt.setNull(10, Types.NULL); else cstmt.setString(10,bean.getSfmj());
				if(bean.getRybh()==null) cstmt.setNull(11, Types.NULL); else cstmt.setString(11,bean.getRybh());
				if(bean.getQxms()==null) cstmt.setNull(12, Types.NULL); else cstmt.setString(12,bean.getQxms());
				if(bean.getZt()==null) cstmt.setNull(13, Types.NULL); else cstmt.setString(13,bean.getZt());
				if(bean.getZjdlsj()==null) cstmt.setNull(14, Types.NULL); else cstmt.setString(14,bean.getZjdlsj());
				if(bean.getMac()==null) cstmt.setNull(15, Types.NULL); else cstmt.setString(15,bean.getMac());
				if(bean.getGdip()==null) cstmt.setNull(16, Types.NULL); else cstmt.setString(16,bean.getGdip());
				if(bean.getGdip1()==null) cstmt.setNull(17, Types.NULL); else cstmt.setString(17,bean.getGdip1());
				if(bean.getGdip2()==null) cstmt.setNull(18, Types.NULL); else cstmt.setString(18,bean.getGdip2());
				if(bean.getBz()==null) cstmt.setNull(19, Types.NULL); else cstmt.setString(19,bean.getBz());
				if(bean.getXtgly()==null) cstmt.setNull(20, Types.NULL); else cstmt.setString(20,bean.getXtgly());
				cstmt.registerOutParameter(21,Types.NUMERIC);
				cstmt.registerOutParameter(22,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(21));
				result.setMsg(cstmt.getString(22));
				return result;
			}
		};
		callBack.setParameterObject(bean);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
	
	public DbResult setTrafficDepartment(Department bean) throws Exception {
		String callString="{call RM_SETDATA_PKG.setFrm_Department(?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				Department bean=(Department)getParameterObject();
				DbResult result = new DbResult();
				cstmt.setString(1,bean.getGlbm());
				if(bean.getGlxzqh()==null) cstmt.setNull(2, Types.NULL); else cstmt.setString(2,bean.getGlxzqh());
				cstmt.registerOutParameter(3,Types.NUMERIC);
				cstmt.registerOutParameter(4,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(3));
				result.setMsg(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(bean);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}
	
//	public DbResult setTrafficDepartmentInfo(DepartmentInfo bean) throws Exception {
//		String callString="{call RM_SETDATA_PKG.setFrm_DepartmentInfo(?,?,?,?,?,?,?,?,?,?)}";
//		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
//			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
//				DepartmentInfo bean=(DepartmentInfo)getParameterObject();
//				DbResult result = new DbResult();
//				cstmt.setString(1,bean.getGlbm());
//				if(bean.getFzr()==null) cstmt.setNull(2, Types.NULL); else cstmt.setString(2,bean.getFzr());
//				if(bean.getLxr()==null) cstmt.setNull(3, Types.NULL); else cstmt.setString(3,bean.getLxr());
//				if(bean.getLxdh()==null) cstmt.setNull(4, Types.NULL); else cstmt.setString(4,bean.getLxdh());
//				if(bean.getCzhm()==null) cstmt.setNull(5, Types.NULL); else cstmt.setString(5,bean.getCzhm());
//				if(bean.getLxdz()==null) cstmt.setNull(6, Types.NULL); else cstmt.setString(6,bean.getLxdz());
//				if(bean.getJd()==null) cstmt.setNull(7, Types.NULL); else cstmt.setString(7,bean.getJd());
//				if(bean.getWd()==null) cstmt.setNull(8, Types.NULL); else cstmt.setString(8,bean.getWd());
//				cstmt.registerOutParameter(9,Types.NUMERIC);
//				cstmt.registerOutParameter(10,Types.VARCHAR);
//				cstmt.execute();
//				result.setCode(cstmt.getInt(9));
//				result.setMsg(cstmt.getString(10));
//				return result;
//			}
//		};
//		callBack.setParameterObject(bean);
//		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
//		return result;
//	}
	
	/** 设置（表名为：RM_TRANS_TASKLOG）对象 */
	private DbResult setRmTransTasklog(RmTransTasklog bean) throws Exception{
		String callString="{call RM_SETDATA_PKG.setRM_TRANS_TASKLOG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				RmTransTasklog bean=(RmTransTasklog)getParameterObject();
				DbResult result = new DbResult();
				if(bean.getXh()==null) cstmt.setNull(1, Types.NULL); else cstmt.setString(1,bean.getXh());
				if(bean.getFzjg()==null) cstmt.setNull(2, Types.NULL); else cstmt.setString(2,bean.getFzjg());
				if(bean.getAzdm()==null) cstmt.setNull(3, Types.NULL); else cstmt.setString(3,bean.getAzdm());
				if(bean.getRwid()==null) cstmt.setNull(4, Types.NULL); else cstmt.setString(4,bean.getRwid());
				if(bean.getRwmc()==null) cstmt.setNull(5, Types.NULL); else cstmt.setString(5,bean.getRwmc());
				if(bean.getJqmc()==null) cstmt.setNull(6, Types.NULL); else cstmt.setString(6,bean.getJqmc());
				if(bean.getSjlx()==null) cstmt.setNull(7, Types.NULL); else cstmt.setString(7,bean.getSjlx());
				if(bean.getSjmc()==null) cstmt.setNull(8, Types.NULL); else cstmt.setString(8,bean.getSjmc());
				if(bean.getJcsj()==null) cstmt.setNull(9, Types.NULL); else cstmt.setString(9,bean.getJcsj());
				if(bean.getKssj()==null) cstmt.setNull(10, Types.NULL); else cstmt.setString(10,bean.getKssj());
				if(bean.getJssj()==null) cstmt.setNull(11, Types.NULL); else cstmt.setString(11,bean.getJssj());
				if(bean.getFlag()==null) cstmt.setNull(12, Types.NULL); else cstmt.setString(12,bean.getFlag());
				if(bean.getCwdm()==null) cstmt.setNull(13, Types.NULL); else cstmt.setString(13,bean.getCwdm());
				if(bean.getFhxx()==null) cstmt.setNull(14, Types.NULL); else cstmt.setString(14,bean.getFhxx());
				if(bean.getJysl()==null) cstmt.setNull(15, Types.NULL); else cstmt.setLong(15,bean.getJysl().longValue());
				cstmt.registerOutParameter(16,Types.NUMERIC);
				cstmt.registerOutParameter(17,Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(16));
				result.setMsg(cstmt.getString(17));
				return result;
			}
		};
		callBack.setParameterObject(bean);
		DbResult result = (DbResult)jdbcTemplate.execute(callString, callBack);
		return result;
	}

}
