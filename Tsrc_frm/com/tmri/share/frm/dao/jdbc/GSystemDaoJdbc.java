package com.tmri.share.frm.dao.jdbc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.dao.GSystemDao;
import com.tmri.share.frm.util.Constants;

@Repository
public class GSystemDaoJdbc extends FrmDaoJdbc implements GSystemDao {
	public String[] getMenuId(String className, String methodName) {
		String[] r = new String[2];
		HashMap<String, Program> map = (HashMap<String, Program>) SystemCache
				.getInstance().getValue("relation");
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Program pro = map.get(key);
		}
		Program p = (Program) map.get(className.substring(className
				.lastIndexOf(".") + 1, className.length())
				+ "_" + methodName);
		if (p == null || p.getXtlb() == null || p.getCdbh() == null
				|| p.getXtlb().length() < 1 || p.getCdbh().length() < 1) {
			return null;
		} else {
			r[0] = p.getXtlb();
			r[1] = p.getCdbh();
			return r;
		}
	}

	public String getDistrictByDepartment(String glbm) throws Exception {
		try {
			String sql = "select csz from frm_syspara_value where gjz='GLXZQH' and xtlb='00' and glbm=?";
			Object[] paraObjects = new Object[] { glbm };
			return (String) jdbcTemplate.queryForObject(sql, paraObjects,
					String.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getCdmc(String xtlb, String cdbh) {
		String cxmc = "";
		String sql = "select cxmc from frm_program where xtlb='" + xtlb
				+ "' and cdbh='" + cdbh + "'";
		Object obj = this.jdbcTemplate.queryForObject(sql, String.class);
		if (null != obj) {
			cxmc = obj.toString();
		}
		return cxmc;
	}

	public List<SysUser> getUsers(String glbm) throws Exception {
		String sql = "";
		HashMap<String, String> map = new HashMap<String, String>();
		if (glbm != null && glbm.length() > 0) {
			sql = " and glbm=:glbm";
			map.put("glbm", glbm);
		}
		if (sql.length() > 1)
			sql = " where" + sql.substring(4);
		sql = "select * from frm_sysuser " + sql + " order by yhdh";
		return jdbcTemplate.queryForList(sql, map, SysUser.class);
	}

	public SysUser getPoliceman(String yhdh) throws Exception {
		String sql = "select * from pls_sysuser where yhdh=?";
		Object[] paraObjects = new Object[] { yhdh };
		return (SysUser) jdbcTemplate.queryForBean(sql, paraObjects,
				SysUser.class);
	}

	public List<SysUser> getPolicemen(String glbm) throws Exception {
		String sql = "";
		HashMap<String, String> map = new HashMap<String, String>();
		if (glbm != null && glbm.length() > 0) {
			sql = " and glbm=:glbm";
			map.put("glbm", glbm);
		}
		if (sql.length() > 1)
			sql = " where" + sql.substring(4);
		sql = "select * from pls_sysuser " + sql + " order by yhdh";
		return jdbcTemplate.queryForList(sql, map, SysUser.class);
	}

	public SysUser getUser(String yhdh) throws Exception {
		String sql = "select * from frm_sysuser where yhdh=?";
		Object[] paraObjects = new Object[] { yhdh };
		return (SysUser) jdbcTemplate.queryForBean(sql, paraObjects,
				SysUser.class);
	}

	public String getDistrictByDepartmentInScope(Department department)
			throws Exception {
		String key = "";
		if (department.getGlbm().endsWith("10")) {
			if ("1".equals(department.getBmjb())) {
				key = "010000000000";
			} else if ("2".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 2) + "0000000010";
			} else if ("3".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 4) + "00000010";
			} else if ("4".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 8) + "0010";
			} else if ("5".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 8) + "0010";
			}
		} else {
			if ("1".equals(department.getBmjb())) {
				key = "010000000000";
			} else if ("2".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 2) + "0000000000";
			} else if ("3".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 4) + "00000000";
			} else if ("4".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 8) + "0000";
			} else if ("5".equals(department.getBmjb())) {
				key = department.getGlbm().substring(0, 8) + "0000";
			}
		}
		String sql = "select csz from frm_syspara_value where gjz='GLXZQH' and xtlb='00' and glbm=?";
		Object[] paraObjects = new Object[] { key };
		return (String) jdbcTemplate.queryForObject(sql, paraObjects,
				String.class);
	}
	
	public String getProgramName(String xtlb, String cdbh) throws Exception {
		String sql = "select cxmc from frm_program where xtlb='"+xtlb+"' and cdbh='"+cdbh+"' and rownum =1";
		return (String)jdbcTemplate.queryForObject(sql, String.class);
	}
	
	public String getFunctionName(String xtlb, String cdbh, String gnid) throws Exception {
		String sql = "select mc from frm_function where xtlb='"+xtlb+"' and cdbh='"+cdbh+"' and gnid='"+gnid+ "' and rownum =1";
		return (String)jdbcTemplate.queryForObject(sql, String.class);
	}
	
	public String getUrl(String xtlb, String cdbh) throws Exception {
		String sql = "select ymdz from frm_program where xtlb='" + xtlb + "' and cdbh='" + cdbh + "'";
		return (String)jdbcTemplate.queryForObject(sql, String.class);
	}

	public Function getOneFunction(String xtlb,String cdbh,String gnid){
		Function function=null;
		String key="function:"+xtlb + "-" + gnid;
		Hashtable tab=(Hashtable)SystemCache.getInstance().getValue(Constants.MEM_FRM_FUNCTION);
		function = (Function)tab.get(key);
		if(function==null){
			String strSql="select * from frm_function where xtlb=:xtlb and gnid=:gnid";
			HashMap map = new HashMap();
			map.put("xtlb",xtlb);
			map.put("gnid",gnid);
			function=(Function)jdbcTemplate.queryForSingleObject(strSql,map,Function.class);
			if(function!=null)
				tab.put(key,function);
		}
		return function;
	}
	

}
