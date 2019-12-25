package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import com.tmri.framework.bean.FrFile;
import com.tmri.framework.bean.FrmWsControl;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.UserRole;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.FrmJdbcTemplate;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;

@Repository
public class ToolsDaoJdbc extends FrmDaoJdbc {
	/**
	 * 根据代码类获取代码值信息
	 * 
	 * @param dmlb
	 * @return
	 * @throws SQLException
	 */

	/**
	 * 获取管理部门查询条件
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public static String getGlbmQueryCondition(UserSession userSession,
			String Colname) {
		String sql = "";
		String glbm = "";
		try {
			glbm = userSession.getDepartment().getGlbm();
			// 总队用户
			if (userSession.getDepartment().getBmjb().equals("1")) {
				sql = " " + Colname + " like '" + glbm.substring(0, 2) + "%' ";
			}

			// 支队用户
			if (userSession.getDepartment().getBmjb().equals("2")) {
				sql = " " + Colname + " like '" + glbm.substring(0, 4) + "%' ";
			}

			// 大队用户
			if (userSession.getDepartment().getBmjb().equals("3")) {
				sql = " " + Colname + " like '" + glbm.substring(0, 8) + "%' ";
			}

			// 中队用户
			if (userSession.getDepartment().getBmjb().equals("4")) {
				sql = " " + Colname + " = '" + glbm + "' ";
			}

			if (sql.equals("")) {
				sql = " 1=1 ";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sql;
	}

	/**
	 * 获取管理部门查询条件
	 * 
	 * @param userSession
	 * @param Colname
	 * @return
	 */
	public static String getGlbmQueryCondition(Department department,
			String Colname) {
		String sql = "";
		String glbm = "";
		try {
			glbm = department.getGlbm();
			// 总队用户
			if (department.getBmjb().equals("1")) {
				sql = " " + Colname + " like '" + glbm.substring(0, 2) + "%' ";
			}

			// 支队用户
			if (department.getBmjb().equals("2")) {
				sql = " " + Colname + " like '" + glbm.substring(0, 4) + "%' ";
			}

			// 大队用户
			if (department.getBmjb().equals("3")) {
				sql = " " + Colname + " like '" + glbm.substring(0, 8) + "%' ";
			}

			// 中队用户
			if (department.getBmjb().equals("4")) {
				sql = " " + Colname + " = '" + glbm + "' ";
			}

			if (sql.equals("")) {
				sql = " 1=1 ";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sql;
	}

	public List getDmlbCodesList(String dmlb) {
		List CodeList = null;
		String key = "code:" + dmlb;
		if (SystemCache.getInstance().contains(key)) {
			CodeList = (List) SystemCache.getInstance().getValue(key);
		} else {
			String strSql = "select * from frm_code where dmlb='" + dmlb
					+ "' order by dmz";
			CodeList = jdbcTemplate.queryForList(strSql, Code.class);
			SystemCache.getInstance().reg(key, CodeList);
		}
		return CodeList;
	};

	public List getCityFzjg(String fzjg) {
		List CodeList = null;
		String key = "code:" + fzjg;
		if (SystemCache.getInstance().contains(key)) {
			CodeList = (List) SystemCache.getInstance().getValue(key);
		} else {
			String strSql = "select substr(dmz,2,1) dmz,dmsm2 dmsm1 from frm_code where dmlb='"
					+ 69 + "' and dmz like '" + fzjg + "%'  order by dmsm4";
			CodeList = jdbcTemplate.queryForList(strSql, Code.class);
			SystemCache.getInstance().reg(key, CodeList);
		}
		return CodeList;

	}

	/**
	 * 获取当前用户下指定级别的所有部门代码List
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException
	 *             2007-02-01 xuxd
	 */
	public List getDepartmentList(UserSession userSession, String jb)
			throws DataAccessException {

		String strSql = "select * from frm_department  where jb='" + jb
				+ "' and " + getGlbmQueryCondition(userSession, "glbm")
				+ " order by glbm";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	/**
	 * 获取指定管理部门下指定级别的所有部门代码List
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException
	 *             2007-02-01 xuxd
	 */
	public List getDepartmentList(UserSession userSession,
			Department department, String jb) throws DataAccessException {

		String strSql = "select * from frm_department  where jb='" + jb
				+ "' and " + getGlbmQueryCondition(userSession, "glbm")
				+ " and sjbm='" + department.getSjbm() + "' order by glbm";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	/**
	 * 获取本部门及下一级部门代码列表
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException
	 *             2009-02-11 chiva.s
	 */
	public List getDepartmentList1(UserSession userSession)
			throws DataAccessException {

		String strSql = "select * from frm_department where glbm='"
				+ userSession.getDepartment().getGlbm() + "' or sjbm='"
				+ userSession.getDepartment().getGlbm() + "' order by glbm";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	public List getDepartmentList1(String bmdm) throws DataAccessException {

		String strSql = "select * from frm_department where glbm='" + bmdm
				+ "' or sjbm='" + bmdm + "' order by glbm";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	/**
	 * 获取下一级部门代码列表
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException
	 *             2009-02-11 chiva.s
	 */
	public List getDepartmentList2(UserSession userSession)
			throws DataAccessException {

		String strSql = "select * from frm_department where sjbm='"
				+ userSession.getDepartment().getGlbm() + "' order by glbm";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	public List getDepartmentList2(String bmdm) throws DataAccessException {

		String strSql = "select * from frm_department where sjbm='" + bmdm
				+ "' order by glbm";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	/**
	 * 获取所有的直接上级部门代码列表
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException
	 *             2009-02-11 chiva.s
	 */
	public List getUpDepartmentList(UserSession userSession)
			throws DataAccessException {
		Department dep = userSession.getDepartment();
		String tmpsql = "";
		if (dep.getBmjb().equals("4")) {
			// 中队管理部门
			tmpsql = "('" + dep.getGlbm() + "','"
					+ dep.getGlbm().substring(0, 8) + "00','"
					+ dep.getGlbm().substring(0, 4) + "000000','"
					+ dep.getGlbm().substring(0, 2) + "00000000')";
		} else if (dep.getBmjb().equals("3")) {
			tmpsql = "('" + dep.getGlbm() + "','"
					+ dep.getGlbm().substring(0, 4) + "000000','"
					+ dep.getGlbm().substring(0, 2) + "00000000')";
		} else if (dep.getBmjb().equals("2")) {
			tmpsql = "('" + dep.getGlbm() + "','"
					+ dep.getGlbm().substring(0, 2) + "00000000')";
		} else {
			tmpsql = "('" + dep.getGlbm().substring(0, 2) + "00000000')";
		}
		String strSql = "select * from frm_department where glbm in " + tmpsql
				+ " order by jb";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	/**
	 * 获取部门代码List
	 * 
	 * @return list
	 * @throws DataAccessException
	 *             2007-02-01 xuxd
	 */
	public List getDepartmentList(Department department)
			throws DataAccessException {
		String glbm = department.getGlbm();
		department = this.getDepartment(glbm);
		String strSql = "select * from frm_department where  "
				+ getGlbmQueryCondition(department, "glbm") + " order by glbm";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		return list;
	};

	public List getRoleList(String jb) throws DataAccessException {
		// String strSql = "Select * From frm_role Where jssx Like '%" + jb
		// + "%' Order By jsdh";
		String strSql = "";

		if (jb.equals("1")) {
			strSql = "select * from frm_role order by jsdh";
		}
		if (jb.equals("2")) {
			strSql = "select * from frm_role where jssx>1 order by jsdh";
		}
		if (jb.equals("3")) {
			strSql = "select * from frm_role where jssx>2 order by jsdh";
		}
		if (jb.equals("4")) {
			strSql = "select * from frm_role where jssx=4 order by jsdh";
		}
		List list = jdbcTemplate.queryForList(strSql, Role.class);
		return list;
	};

	
	public List getUserRole(String yhdh) throws DataAccessException {
		String strSql = "Select * From frm_userrole Where yhdh='" + yhdh + "'";
		List list = jdbcTemplate.queryForList(strSql, UserRole.class);
		return list;
	};


	
	// 获取系统日期
	public String getDBDate() throws SQLException {
		String strSysdate = "";
		String sql = "select to_char(sysdate,'yyyy-mm-dd') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统时间
	public String getDBDateTimeToMinute() throws SQLException {
		String strSysdate = "";
		String sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统时间
	public String getDBDateTimeToMinute(String value){
		String strSysdate = "";
		String sql = "select to_char(sysdate" + value
				+ ",'yyyy-mm-dd hh24:mi') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统日期
	public String getDBDateTime() throws SQLException {
		String strSysdate = "";
		String sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统日期
	public String getDBDateTime(String val){
		String strSysdate = "";
		String sql = "select to_char(sysdate" + val
				+ ",'yyyy-mm-dd hh24:mi:ss') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统日期
	public String getDateTime(String datetime, String val) throws SQLException {
		String strSysdate = datetime;
		if (strSysdate.length() > 20) {
			strSysdate = strSysdate.substring(0, 19);
		}
		String sql = "select to_char(to_date('" + strSysdate
				+ "','yyyy-mm-dd hh24:mi:ss')" + val
				+ ",'yyyy-mm-dd hh24:mi:ss') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	// 获取系统日期
	public String getDBDate(String val){
		String strSysdate = "";
		String sql = "select to_char(sysdate+" + val
				+ ",'yyyy-mm-dd') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	public List getSysuserListByGlbm(String glbm) throws DataAccessException {
		String strSql = "Select * From frm_sysuser Where glbm = '" + glbm
				+ "' Order By yhdh";
		List list = jdbcTemplate.queryForList(strSql, SysUser.class);
		return list;
	};

	/**
	 * 保存操作日志信息
	 * 
	 * @param glbm
	 * @param yhdh
	 * @param czlx
	 * @param cznr
	 * @param ip
	 * @return
	 * @throws SQLException
	 */
	public int saveLogs(String glbm, String yhdh, String czlx, String cznr,
			String ip) throws SQLException {
		Log log = new Log(glbm,yhdh,"",czlx,"",cznr,ip,"");
		String callString = "{call FRM_SYS_PKG.saveFRM_LOG(?,?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				Log log = (Log) getParameterObject();
				if (log.getGlbm() == null)
					cstmt.setNull(1, Types.VARCHAR);
				else
					cstmt.setString(1, log.getGlbm());
				if (log.getYhdh() == null)
					cstmt.setNull(2, Types.VARCHAR);
				else
					cstmt.setString(2, log.getYhdh());
				if (log.getCzlx() == null)
					cstmt.setNull(3, Types.VARCHAR);
				else
					cstmt.setString(3, log.getCzlx());
				if (log.getCzgn() == null)
					cstmt.setNull(4, Types.VARCHAR);
				else
					cstmt.setString(4, log.getCzgn());
				if (log.getCznr() == null)
					cstmt.setNull(5, Types.VARCHAR);
				else
					cstmt.setString(5, log.getCznr());

				if (log.getIp() == null)
					cstmt.setNull(6, Types.VARCHAR);
				else
					cstmt.setString(6, log.getIp());
				cstmt.registerOutParameter(7, Types.NUMERIC);
				cstmt.execute();
				int iResult = cstmt.getInt(7);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(log);
		Integer i = (Integer) jdbcTemplate.execute(callString, callBack);
		return i.intValue();
	}

	/*
	 * 获取系统当前日期
	 */
	public String getSysDate() throws SQLException {
		return this.getDBDate();
	}
	public String getZwSysDate() throws SQLException{
		String strTjrq = this.getDBDate();
		strTjrq = strTjrq.substring(0,4) + "年" + strTjrq.substring(5,7) + "月" + strTjrq.substring(8,10) +  "日";
		return strTjrq;
	}
	/*
	 * 获取系统当前日期加减一个时间段的日期
	 */
	public String getSysDate(String val){
		return this.getDBDate(val);
	}

	/*
	 * 获取系统当前日期v天后的时间
	 */
	public String getDataBaseSysDate(String v) throws SQLException {
		String _result = "";
		String sql = "select to_char(sysdate+" + v
				+ ",'yyyy-mm-dd') dd from dual";
		_result = (String) this.jdbcTemplate.queryForObject(sql, String.class);
		return _result;
	}

	/**
	 * 根据违法代码总类获取所有代码分类List
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException
	 *             2007-02-01 xuxd
	 */
	public List getWfxwDmflList(String dmzl) throws DataAccessException {
		String strSql = "select * from frm_code where dmlb='115' and dmsm2='"
				+ dmzl + "' order by dmz";
		List list = jdbcTemplate.queryForList(strSql, Code.class);
		return list;
	};

	
	/**
	 * 根据操作类别和变更类别获取 FRM_CODE 表中 dmlb=99 的申请原因代码
	 * 
	 * @param dmlb
	 * @return list
	 * @throws DataAccessException
	 *             2007-02-01 xuxd
	 */
	public List getSqyyDmList(String czlx) throws DataAccessException {
		String strSql = "select * from frm_code where dmlb='99' and dmsm2='"
				+ czlx + "' order by dmz";
		List list = jdbcTemplate.queryForList(strSql, Code.class);
		return list;
	};

	/**
	 * 根据代码类获取代码值信息
	 * 
	 * @param dmlb
	 * @return
	 * @throws SQLException
	 */
	public List getDmlbCodesList(String dmlb, String dmlb2) {
		List CodeList = null;
		String strSql = "select * from frm_code where dmlb='" + dmlb
				+ "' and dmsm2='" + dmlb2 + "' order by dmz";
		CodeList = jdbcTemplate.queryForList(strSql, Code.class);

		return CodeList;
	};

	// 判断vio_codewfdm的校验位
	public int Check_Wfdm_Jyw(String wfxw) throws DataAccessException {

		String callString = "{?=call Vio_Check_Pkg.Check_Wfxw_Jyw(?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String wfxw = (String) getParameterObject();
				wfxw = wfxw.substring(0, 4);
				cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
				if (wfxw == null) {
					cstmt.setNull(2, Types.VARCHAR);
				} else {
					cstmt.setString(2, wfxw);
				}
				cstmt.execute();
				int iResult = cstmt.getInt(1);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(wfxw);
		Integer i = (Integer) jdbcTemplate.execute(callString, callBack);
		return i.intValue();

	}

	// 判断处罚决定书的校验位
	public int Check_Jdsbh_Jyw(String jdsbh) throws DataAccessException {

		String callString = "{?=call Vio_Business_Pkg.Check_Jdsbh_Jyw(?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String wfbh = (String) getParameterObject();
				cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
				if (wfbh == null) {
					cstmt.setNull(2, Types.VARCHAR);
				} else {
					cstmt.setString(2, wfbh);
				}
				cstmt.execute();
				int iResult = cstmt.getInt(1);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(jdsbh);
		Integer i = (Integer) jdbcTemplate.execute(callString, callBack);
		return i.intValue();

	}

	// 判断violation表的校验位
	public int Check_Violation_Jyw(String wfbh) throws DataAccessException {

		String callString = "{?=call Vio_Business_Pkg.Check_Violation_Jyw(?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String wfbh = (String) getParameterObject();
				cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
				if (wfbh == null) {
					cstmt.setNull(2, Types.VARCHAR);
				} else {
					cstmt.setString(2, wfbh);
				}
				cstmt.execute();
				int iResult = cstmt.getInt(1);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(wfbh);
		Integer i = (Integer) jdbcTemplate.execute(callString, callBack);
		return i.intValue();

	}

	// 判断force表的校验位
	public int Check_Force_Jyw(String pzbh) throws DataAccessException {

		String callString = "{?=call Vio_Business_Pkg.Check_Force_Jyw(?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String pzbh = (String) getParameterObject();
				cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
				if (pzbh == null) {
					cstmt.setNull(2, Types.VARCHAR);
				} else {
					cstmt.setString(2, pzbh);
				}
				cstmt.execute();
				int iResult = cstmt.getInt(1);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(pzbh);
		Integer i = (Integer) jdbcTemplate.execute(callString, callBack);
		return i.intValue();

	}

	// 判断surveil表的校验位
	public int Check_Surveil_Jyw(String xh) throws DataAccessException {

		String callString = "{?=call Vio_Business_Pkg.Check_Surveil_Jyw(?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String xh = (String) getParameterObject();
				cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
				if (xh == null) {
					cstmt.setNull(2, Types.VARCHAR);
				} else {
					cstmt.setString(2, xh);
				}
				cstmt.execute();
				int iResult = cstmt.getInt(1);
				return new Integer(iResult);
			}
		};
		callBack.setParameterObject(xh);
		Integer i = (Integer) jdbcTemplate.execute(callString, callBack);
		return i.intValue();

	}


	/**
	 * 获取所在部门的所有是警察的人员信息
	 * 
	 * @param userSession
	 * @return
	 * @throws DataAccessException
	 */
	public List getJbr2List(UserSession userSession) throws DataAccessException {
		List list = null;
		String strSql = "Select * From frm_sysuser Where glbm ='"
				+ userSession.getDepartment().getGlbm() + "' And rylb='1'";
		list = jdbcTemplate.queryForList(strSql, SysUser.class);
		return list;
	};

	/**
	 * 获取部门信息
	 * 
	 * @param glbm
	 * @return
	 * @throws DataAccessException
	 */
	public Department getDepartment(String glbm) throws DataAccessException {
		Department code = null;
		String strSql = "select * from frm_department where glbm='" + glbm
				+ "'";
		List list = jdbcTemplate.queryForList(strSql, Department.class);
		if (list.size() > 0) {
			code = (Department) list.get(0);
		}
		return code;
	};

	/**
	 * 获取用户信息
	 * 
	 * @param yhdh
	 * @return
	 * @throws DataAccessException
	 */
	public SysUser getSysuser(String yhdh) throws DataAccessException {
		SysUser code = null;
		String strSql = "select * from frm_sysuser where yhdh='" + yhdh + "'";
		List list = jdbcTemplate.queryForList(strSql, SysUser.class);
		if (list.size() > 0) {
			code = (SysUser) list.get(0);
		}
		return code;
	};

	/**
	 * 获取用户列表
	 */
	public List getSysusers(UserSession userSession) throws DataAccessException {
		String tmpSql = "";

		tmpSql = "select * from frm_sysuser where "
				+ getGlbmQueryCondition(userSession, "glbm") + tmpSql
				+ "  order by glbm";

		return jdbcTemplate.queryForList(tmpSql, SysUser.class);
	}

	

	public void saveSusp(String type, String xh) throws Exception {
		String callString = "";
		if (type.equals("1")) {
			callString = "{call Vio_Basic_Pkg.Updatesuspcar(?)}";
		} else {
			callString = "{call Vio_Basic_Pkg.Updatesusphuman(?)}";
		}
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String xh = (String) this.getParameterObject();
				cstmt.setString(1, xh);
				cstmt.execute();
				return new Integer(1);
			}
		};
		callBack.setParameterObject(xh);
		jdbcTemplate.execute(callString, callBack);
	}

	
	public String getMonthSysdate(String date, String v) {
		String strSysdate = "";
		String sql = "select to_char(add_months(to_date('" + date
				+ "','YYYY-MM-DD')," + v + "),'YYYY-MM-DD') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	public String getDaySysdate(String date, String v) {
		String strSysdate = "";
		String sql = "select to_char(to_date('" + date + "','YYYY-MM-DD')+" + v
				+ ",'YYYY-MM-DD') dd from dual";
		strSysdate = (String) this.jdbcTemplate.queryForObject(sql,
				String.class);
		return strSysdate;
	}

	public int compareDay(String date) {
		Integer strSysdate;
		String sql = "select trunc(sysdate)-to_date('" + date
				+ "','YYYY-MM-DD') dd from dual";
		strSysdate = (Integer) this.jdbcTemplate.queryForObject(sql,
				Integer.class);
		return strSysdate.intValue();
	}

	/**
	 * 获取打印序号
	 * 
	 * @param tid
	 * @param glbm
	 * @return
	 */
	public String getPrintXh(String tid, String glbm) {
		String result = "";
		FrFile ff = null;
		String sql = "Select * From frm_frfile Where tid='" + tid
				+ "' And (glbm ='0000000000' Or glbm='" + glbm
				+ "')  Order By tlb Desc ";
		List list = jdbcTemplate.queryForList(sql, FrFile.class);
		if (list.size() > 0) {
			ff = (FrFile) list.get(0);
			result = ff.getXh();
		}
		return result;
	}

	/**
	 * 判断驾驶证记分是否已超分，主要用于电子监控处理
	 * 
	 * @param jszh
	 * @param xm
	 * @param hpzl
	 * @param hphm
	 * @param jf
	 * @return
	 * @throws Exception
	 */
	public String checkDriverJf(String jszh, String xm, String hpzl,
			String hphm, int jf) throws Exception {
		class Para {
			public String p1;
			public String p2;
			public String p3;
			public String p4;
			public int p5;
		}
		String callString = "{call Vio_Business_Pkg.Checkdriverjf(?,?,?,?,?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				String result = "";
				Para np = (Para) getParameterObject();
				cstmt.setString(1, np.p1);
				cstmt.setString(2, np.p2);
				cstmt.setString(3, np.p3);
				cstmt.setString(4, np.p4);
				cstmt.setInt(5, np.p5);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.execute();
				result = cstmt.getString(6);
				return result;
			}
		};
		Para para = new Para();
		para.p1 = jszh;
		para.p2 = xm;
		para.p3 = hpzl;
		para.p4 = hphm;
		para.p5 = jf;
		callBack.setParameterObject(para);
		String result = (String) jdbcTemplate.execute(callString, callBack);
		return result;
	}

	public int getSystemExpiryDate(){
		String callString = "{?=call Admin_Check_Pkg.A95}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				int result = 0;
				cstmt.registerOutParameter(1, Types.INTEGER);
				cstmt.execute();
				result = cstmt.getInt(1);
				return result + "";
			}
		};
		String result = (String) jdbcTemplate.execute(callString, callBack);
		return new Integer(result).intValue();
	}
	public List getInterfaceExpirylist(){
		String sql = "select * from frm_ws_control Where Jkjzrq < Sysdate + 30";		
		return jdbcTemplate.queryForList(sql,FrmWsControl.class);
	}
	
}
