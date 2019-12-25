package com.tmri.cache.dao.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmri.cache.dao.WebCacheDao;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.Roaditem;
import com.tmri.share.frm.bean.SysPara;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.StringUtil;

@Repository
public class WebCacheDaoJdbc extends FrmDaoJdbc implements WebCacheDao {

	public List<Function> getAllFunction() throws SQLException {
		String strSql = "select * from frm_function order by xtlb,cdbh,gnid";
		return this.jdbcTemplate.queryForList(strSql, Function.class);
	}

	public List<Program> getAllPrograms(String xtlbs) throws SQLException {
		String sqlxtlb = StringUtil.getXtlbsql(xtlbs, "v");

		String sql = " select xtlb,cdbh,mldh,bz from " + " frm_program v where 1=1 " + sqlxtlb + " order by xtlb,cdbh";
		return jdbcTemplate.queryForList(sql, Program.class);
	}

	public List<SysparaValue> getAllSysPara_values() throws SQLException {
		String strSql = "select * from frm_syspara_value order by xtlb,glbm,gjz";
		return jdbcTemplate.queryForList(strSql, SysparaValue.class);
	}

	public List<Roaditem> getAllRoaditems() throws SQLException {
		String sql = "select * from frm_roaditem where xzqh is not null order by dldm";
		return jdbcTemplate.queryForList(sql, Roaditem.class);
	}

	public List<Department> getAllPlsDepartments() throws SQLException {
		String strSql = "select t.* from pls_department t where jlzt='1' order by glbm";
		return jdbcTemplate.queryForList(strSql, Department.class);
	}

	public List getAllSysParas() throws Exception {
		String strSql = "select XTLB,CSLX,GJZ,CSMC,CSSM,MRZ,XGJB,SFXS,XSSX,DMLB,SJGF,CSSX,BZ,'1' JYW,XSXS from frm_syspara order by xtlb,cslx,gjz";
		return jdbcTemplate.queryForList(strSql, SysPara.class);
	}
}
