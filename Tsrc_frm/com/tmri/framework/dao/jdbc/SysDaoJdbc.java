package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.SysDao;
import com.tmri.pub.bean.BasPolice;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;
@Repository
public class SysDaoJdbc extends FrmDaoJdbc implements SysDao{

	
	public SysResult saveFrmErrLog() throws Exception{
		String callString="{call FRM_SYS_PKG.SaveFrmErrlog(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc(cstmt.getString(2));
				result.setDesc1(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}
	public SysResult saveFrmSysrun() throws Exception{
		String callString="{call FRM_SYS_PKG.SaveFrmSysRun(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc(cstmt.getString(2));
				result.setDesc1(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}	
	public SysResult saveFrmQueryerrlog() throws Exception{
		String callString="{call FRM_SYS_PKG.SaveFrmQueryerrlog(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc(cstmt.getString(2));
				result.setDesc1(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}	

	public SysResult saveFrmRbspLog() throws Exception{
		String callString="{call FRM_SYS_PKG.SaveFrmRbspLog(?,?,?)}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				SysResult result=new SysResult();
				cstmt.registerOutParameter(1,Types.NUMERIC);
				cstmt.registerOutParameter(2,Types.VARCHAR);
				cstmt.registerOutParameter(3,Types.VARCHAR);
				cstmt.execute();
				result.setFlag(cstmt.getLong(1));
				result.setDesc(cstmt.getString(2));
				result.setDesc1(cstmt.getString(3));
				return result;
			}
		};
		SysResult result=(SysResult)jdbcTemplate.execute(callString,callBack);
		return result;
	}	
	
	// 根据SQL语句返回List的方法。
	public List queryForList(String strSql,Class bClass) {
		return this.jdbcTemplate.queryForList(strSql,bClass);
	}
	
	/**
	 * 获取人员信息的基本情况，主要用于违法打印
	 */
	public BasPolice getRyjbxx(String sfzmhm,String sfmj) throws Exception{
		BasPolice info=null;
		String sql="";
		if(sfmj.equals("1")){
			sql="select * from bas_police where sfzmhm ='"+sfzmhm+"' and jlzt ='1'";
		}else{
			sql="select * from bas_employee where sfzmhm ='"+sfzmhm+"' and jlzt ='1'";
		}
		List queryList = this.jdbcTemplate.queryForList(sql,BasPolice.class);
		if(queryList!=null&&queryList.size()>0){
			info = (BasPolice)queryList.get(0);
		}
		return info;
	}
	
	/**
	 * 根据省份头获取异地交换应用服务器IP地址
	 */
	public DbResult Getjhkwepip(String sft) throws Exception{
		DbResult result = new DbResult();
		result.setObj(sft);
		String callString = "{call Vio_Trans_Basic_Pkg.Getjhkwepip(?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				DbResult result = (DbResult) getParameterObject();
				String sft = (String) result.getObj();
				cstmt.setString(1,sft);
				cstmt.registerOutParameter(2, Types.NUMERIC);
				cstmt.registerOutParameter(3, Types.VARCHAR);
				cstmt.registerOutParameter(4, Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(2));
				result.setMsg(cstmt.getString(3));
				result.setMsg1(cstmt.getString(4));
				return result;
			}
		};
		callBack.setParameterObject(result);
		result = (DbResult) jdbcTemplate.execute(callString, callBack);
		return result;
	}
}
