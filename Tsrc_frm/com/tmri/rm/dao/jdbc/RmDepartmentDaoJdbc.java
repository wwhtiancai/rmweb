package com.tmri.rm.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.rm.bean.DepartmentInfo;
import com.tmri.rm.dao.RmDepartmentDao;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysparaValue;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;

@Repository
public class RmDepartmentDaoJdbc extends FrmDaoJdbc implements RmDepartmentDao {

	public void update() {
		SystemCache.getInstance().remove("policestation");
		String key = null;
		String key1 = null;
		String strSql = "select t.* from pls_department t where jlzt='1' order by glbm";
		List departments = jdbcTemplate.queryForList(strSql, Department.class);
		Department department = null;
		Hashtable tab = new Hashtable();
		for (int i = 0; i < departments.size(); i++) {
			department = (Department) departments.get(i);
			key = "policestation:" + department.getGlbm();
			tab.put(key, department);
			key1 = "policestation_xj:" + department.getSjbm();
			List list = (List) tab.get(key1);
			if (list == null) {
				list = new Vector();
			}
			list.add(department);
			tab.put(key1, list);
		}
		SystemCache.getInstance().reg("policestation", tab);
	}

	public List<Department> getDepartmentList(Department department,
			PageController controller) throws Exception {
		String sql = " where jlzt='1' ";
		if (StringUtil.checkBN(department.getGlbm()))
			sql += " and glbm like '" + department.getGlbm() + "%'";
		if (StringUtil.checkBN(department.getBmmc()))
			sql += " and bmmc like '%" + department.getBmmc() + "%'";
		sql = "select glbm,bmmc,bmqc,sjbm,fzjg,bmjb,jlzt from frm_department "
				+ sql + " order by glbm";
		return controller.getWarpedList(sql, Department.class, jdbcTemplate);
	}

	public Department getDepartment(String glbm) throws Exception {
		String sql = "select * from frm_department where glbm='" + glbm + "'";
		return (Department) jdbcTemplate.queryForBean(sql, Department.class);
	}

	public String getDistrict(String glbm) throws Exception {
		String sql = "select csz from frm_syspara_value where xtlb='00' and gjz='GLXZQH' and glbm='"
				+ glbm + "'";
		List<SysparaValue> list = jdbcTemplate.queryForList(sql,
				SysparaValue.class);
		if (null == list || list.size() == 0) {
			return "";
		} else {
			return list.get(0).getCsz();
		}
		// return jdbcTemplate.queryForObject(sql,String.class);
	}

	public String getPoliceStationTree(Department department) throws Exception {
		String strResult = "";
		strResult = strResult + "if (document.getElementById) {\n";
		StringBuffer treeBuffer = new StringBuffer();
		outputGlbmTree(department, treeBuffer, true);
		strResult = strResult + treeBuffer + "document.write(glbm"
				+ department.getGlbm() + ");" + "}\n";
		treeBuffer.delete(0, treeBuffer.length());
		return strResult;
	}

	private void outputGlbmTree(Department dep, StringBuffer treeBuffer,
			boolean isRoot) {
		String nodename = "glbm" + dep.getGlbm();
		String temp = null;
		if (isRoot) {
			Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(
					"policestation");
			Department department = (Department) (tab.get("policestation:"
					+ dep.getGlbm()));
			if (null != department) {
				temp = "var " + nodename + "= new WebFXTree('"
						+ department.getBmmc() + "','javascript:editGlbm(\\'"
						+ department.getGlbm() + "\\')');\n";
			}
		} else {
			temp = "var " + nodename + "= new WebFXTreeItem('" + dep.getBmmc()
					+ "','javascript:editGlbm(\\'" + dep.getGlbm()
					+ "\\')');\n";
		}
		treeBuffer.append(temp + "\n");
		String key = "policestation_xj:" + dep.getGlbm();
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(
				"policestation");
		List list1 = (List) tab.get(key);
		Department dep1 = null;
		if (list1 != null) {
			for (int i = 0; i < list1.size(); i++) {
				dep1 = (Department) list1.get(i);
				outputGlbmTree(dep1, treeBuffer, false);
				temp = nodename + ".add(glbm" + dep1.getGlbm() + ");";
				treeBuffer.append(temp + "\n");
			}
		}
	}

	public Department getPoliceStation(String glbm) throws Exception {
		String sql = "select * from pls_department where glbm='" + glbm + "'";
		return (Department) jdbcTemplate.queryForBean(sql, Department.class);
	}

	/** 保存公安部门信息表（表名为：PLS_DEPARTMENT）对象 */
	public DbResult saveDepartment() throws Exception {
		String callString = "{call RM_PKG.saveDEPARTMENT(?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				DbResult result = new DbResult();
				cstmt.registerOutParameter(1, Types.NUMERIC);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(1));
				result.setMsg(cstmt.getString(2));
				return result;
			}
		};
		DbResult result = (DbResult) jdbcTemplate.execute(callString, callBack);
		return result;
	}

	/** 删除公安部门信息表（表名为：PLS_DEPARTMENT）对象 */
	public DbResult delDepartment() throws Exception {
		String callString = "{call RM_PKG.delDEPARTMENT(?,?)}";
		CallableStatementCallbackImpl callBack = new CallableStatementCallbackImpl() {
			public Object doInCallableStatement(CallableStatement cstmt)
					throws SQLException, DataAccessException {
				DbResult result = new DbResult();
				cstmt.registerOutParameter(1, Types.NUMERIC);
				cstmt.registerOutParameter(2, Types.VARCHAR);
				cstmt.execute();
				result.setCode(cstmt.getInt(1));
				result.setMsg(cstmt.getString(2));
				return result;
			}
		};
		DbResult result = (DbResult) jdbcTemplate.execute(callString, callBack);
		return result;
	}

	public List<Department> getDepartments(Department department,
			PageController controller, String sqlHead) throws Exception {
		String sql = " where jlzt='1' ";
		if (StringUtil.checkBN(sqlHead))
			sql += " and glbm like '" + sqlHead + "%'";
		if (StringUtil.checkBN(department.getBmmc()))
			sql += " and bmmc like '%" + department.getBmmc() + "%'";
		sql = "select * from frm_department "
				+ sql + " order by glbm";
		return controller.getWarpedList(sql, Department.class, jdbcTemplate);
	}
	
	// 标注经纬度的查询
	public List<Department> getDepartmentsFull(Department department,String bzjwd,
			PageController controller, String sqlHead) throws Exception {
		String sql = " where jlzt='1' ";
		if (StringUtil.checkBN(sqlHead))
			sql += " and glbm like '" + sqlHead + "%'";
		if (StringUtil.checkBN(department.getBmmc()))
			sql += " and bmmc like '%" + department.getBmmc() + "%'";
		 
		if ("1".equals(bzjwd)){
		    sql = "select glbm,bmmc,bmqc,sjbm,fzjg,bmjb,jlzt,fzr,lxr,lxdh,czhm,lxdz,jd,wd from ( " +
		    		" select a.glbm,a.bmmc,a.bmqc,a.sjbm,a.fzjg,a.bmjb,a.jlzt,b.fzr,b.lxr,b.lxdh,b.czhm,b.lxdz,b.jd,b.wd " +
		    		" from frm_department a,frm_department_info b " +
		    		" where a.glbm=b.glbm and (b.jd is not null and b.wd is not null)) "
				+ sql + " order by glbm";
		}else{
			sql = " select glbm,bmmc,bmqc,sjbm,fzjg,bmjb,jlzt,fzr,lxr,lxdh,czhm,lxdz,jd,wd from ( " +
					" select a.glbm,a.bmmc,a.bmqc,a.sjbm,a.fzjg,a.bmjb,a.jlzt,a.fzr,a.lxr,a.lxdh,a.czhm,a.lxdz,'' jd,'' wd " +
					" from frm_department a where glbm not in (" +
					"   select glbm from frm_department_info" +
					" ) " +
					" union all " +
					" select a.glbm,a.bmmc,a.bmqc,a.sjbm,a.fzjg,a.bmjb,a.jlzt,b.fzr,b.lxr,b.lxdh,b.czhm,b.lxdz,b.jd,b.wd " +
					" from frm_department a,frm_department_info b " +
					" where a.glbm=b.glbm and (b.jd is null or b.wd is null) " +
				  " ) "
				+ sql + " order by glbm";
		}
		
		//return controller.getWarpedList(sql, Department.class, jdbcTemplate);
		return null;
	}

	public List<Department> getPoliceList(Department department,
			PageController controller, String sqlHead) throws Exception {
		// String sql =" where jlzt='1' ";
		String sql = "";
		if (StringUtil.checkBN(sqlHead))
			sql += " and glbm like '" + sqlHead + "%'";
		if (StringUtil.checkBN(department.getBmmc()))
			sql += " and bmmc like '%" + department.getBmmc() + "%'";
		if (sql.length() > 0) {
			sql = " where " + sql.substring(4);
		}
		sql = "select * from pls_department " + sql
				+ " order by glbm,gxsj desc ";
		return controller.getWarpedList(sql, Department.class, jdbcTemplate);

	}

	public DepartmentInfo getDepartmentInfo(String glbm) throws Exception{
		DepartmentInfo bean=null;
		String strSql = "select * from frm_department_info where glbm=:glbm";
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("glbm",glbm);
		/*List<DepartmentInfo> mylist=(List<DepartmentInfo>)jdbcTemplate.queryForList(strSql,map,DepartmentInfo.class);
		if(mylist!=null && mylist.size()>0){
			bean = mylist.get(0);
		}*/
        return bean;
	}
}
