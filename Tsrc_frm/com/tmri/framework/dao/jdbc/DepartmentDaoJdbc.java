package com.tmri.framework.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.tmri.framework.dao.DepartmentDao;
import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.ora.bean.CallableStatementCallbackImpl;
import com.tmri.share.ora.bean.DbResult;
@Repository
public class DepartmentDaoJdbc extends FrmDaoJdbc implements DepartmentDao {
	@Autowired
	private GDepartmentDao gDepartmentDao;
	
	public DbResult savekeyWorkConfig(java.util.Map map) {
		DbResult temp=new DbResult();
		String cdbh=(String)map.get("cdbh");
		String xtlb=(String)map.get("xtlb");
		String fwfs=(String)map.get("fwfs");
		String sql="update frm_program set fwfs='"+fwfs+"'where xtlb='"+xtlb+"' and cdbh='"+cdbh+"'";
		int i=this.jdbcTemplate.update(sql);
		if(i>0)
		{
			temp.setCode(1);
		}else{
			temp.setCode(0);
		}
		return temp;
	}

	public void do_program_jyw() {
		String callString="{call  Frm_Register_Pkg.do_program_jyw()}";
		CallableStatementCallbackImpl callBack=new CallableStatementCallbackImpl(){
			public Object doInCallableStatement(CallableStatement cstmt) throws SQLException,DataAccessException{
				cstmt.execute();
				return null;
			}
		};
		jdbcTemplate.execute(callString,callBack);
	}

	public List getCheckboxStateList() {
		String sql="select * from frm_program where  ( xtlb='05' and cdbh='R941') or ( xtlb='05' and cdbh='R942') or ( xtlb='00' and cdbh='K012') or ( xtlb='01' and cdbh='A103') or( xtlb='02' and cdbh='2001')";
		List list=this.jdbcTemplate.queryForList(sql,Program.class);
		return list;
	}

	public String getDepartmentTreeStr(Department department) {
		String strResult = "";
		Department querydept=null;
		if(department.getBmjb().equals("1")||department.getBmjb().equals("2")
				||department.getBmjb().equals("3")||department.getBmjb().equals("4")){
            if (!department.getGlbm().substring(8, 10).equals("00")
                    && department.getBmjb().equals("3")) {
				querydept=gDepartmentDao.getDepartment(department.getSjbm());
            } else {
				querydept=department;
			}
		}else{
			querydept=department;
		}
		
		strResult = strResult + "if (document.getElementById) {\n";
		StringBuffer treeBuffer = new StringBuffer();
		if(department.getBmjb().equals("1")){
			outputGlbmTreeStrBj(querydept, treeBuffer, true);
		}else{
			outputGlbmTreeStr(querydept, treeBuffer, true);
		}
		strResult = strResult + treeBuffer + "document.write(glbm"
				+ querydept.getGlbm() + ");" + "}\n";
		treeBuffer.delete(0,treeBuffer.length());
		return strResult;
	}
	
	private void outputGlbmTreeStrBj(Department dep, StringBuffer treeBuffer,
			boolean isRoot){
		String nodename = "glbm" + dep.getGlbm();
		String temp = null;
		if (isRoot) {
			temp = "var " + nodename + "= new WebFXTree('" + dep.getBmmc()
					+ "','javascript:editGlbm(\\'" + dep.getGlbm()
					+ "\\')');\n";
		} else {
			temp = "var " + nodename + "= new WebFXTreeItem('" + dep.getBmmc()
					+ "','javascript:editGlbm(\\'" + dep.getGlbm()
					+ "\\')');\n";
		}
		treeBuffer.append(temp + "\n");
		String key = "department_xj:" + dep.getGlbm();
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(
				"department");
		List list1 = (List) tab.get(key);
		// List list1 = (List) FrameContext.getInstance().getValue(key);
		Department dep1 = null;
		if (list1 != null) {
			for (int i = 0; i < list1.size(); i++) {
				dep1 = (Department) list1.get(i);
				temp = "var " + "glbm" + dep1.getGlbm() + "= new WebFXTreeItem('" + dep1.getBmmc()
				+ "','javascript:editGlbm(\\'" + dep1.getGlbm()
				+ "\\')');\n";
				treeBuffer.append(temp + "\n");
				temp = nodename + ".add(glbm" + dep1.getGlbm() + ");";
				treeBuffer.append(temp + "\n");
			}
		}
	}
	
	private void outputGlbmTreeStr(Department dep, StringBuffer treeBuffer,
			boolean isRoot) {
		String nodename = "glbm" + dep.getGlbm();
		String temp = null;
		if (isRoot) {
			temp = "var " + nodename + "= new WebFXTree('" + dep.getBmmc()
					+ "','javascript:editGlbm(\\'" + dep.getGlbm()
					+ "\\')');\n";
		} else {
			temp = "var " + nodename + "= new WebFXTreeItem('" + dep.getBmmc()
					+ "','javascript:editGlbm(\\'" + dep.getGlbm()
					+ "\\')');\n";
		}
		treeBuffer.append(temp + "\n");
		String key = "department_xj:" + dep.getGlbm();
		Hashtable tab = (Hashtable) SystemCache.getInstance().getValue(
				"department");
		List list1 = (List) tab.get(key);
		// List list1 = (List) FrameContext.getInstance().getValue(key);
		Department dep1 = null;
		if (list1 != null) {
			for (int i = 0; i < list1.size(); i++) {
				dep1 = (Department) list1.get(i);
				outputGlbmTreeStr(dep1, treeBuffer, false);
				temp = nodename + ".add(glbm" + dep1.getGlbm() + ");";
				treeBuffer.append(temp + "\n");
			}
		}
	}
}
