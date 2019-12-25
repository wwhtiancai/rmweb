package com.tmri.framework.service.impl;

import java.util.List;
import java.util.Map;

import com.tmri.rfid.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.framework.dao.DepartmentDao;
import com.tmri.framework.dao.SysParaDao;
import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
import com.tmri.framework.service.DepartmentManager;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.ora.bean.DbResult;
@Service

public class DepartmentManagerImpl implements DepartmentManager{
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	FrmDataObjDaoJdbc frmDataObjDao;
	@Autowired
	SysParaDao sysparaDao = null;
	@Autowired
	GDepartmentDao gDepartmentDao;

	@Autowired
	private DepartmentMapper departmentMapper;
	
//	public void setgDepartmentDao(GDepartmentDao gDepartmentDao) {
//		this.gDepartmentDao = gDepartmentDao;
//	}
//
//	public void setDepartmentDao(DepartmentDao departmentDao){
//		this.departmentDao=departmentDao;
//	}
//	
//	public void setFrmDataObjDao(FrmDataObjDaoJdbc dataObjDao){
//		this.frmDataObjDao=dataObjDao;
//	}
//	
//
//	public void setSysparaDao(SysParaDao sysparaDao){
//		this.sysparaDao=sysparaDao;
//	}	


	public Department getDepartment(String glbm) throws Exception{
		return this.gDepartmentDao.getDepartment(glbm);
	}
	
	public DbResult savekeyWorkConfig(List list) {
		DbResult dbreturninfo=new DbResult();
		for(int i=0;i<list.size();i++)
		{
			java.util.Map temp=(java.util.Map)list.get(i);
			dbreturninfo=this.departmentDao.savekeyWorkConfig(temp);

		}
		if(list.size()>0){
			this.departmentDao.do_program_jyw();
		}
		return dbreturninfo;
	}
	
	public Map getCheckboxSate() {
		Map m=new java.util.HashMap();
		List list=this.departmentDao.getCheckboxStateList();
		for(int i=0;i<list.size();i++)
		{
			Program temp=(Program)list.get(i);
			String fwfs=temp.getFwfs();
			String xtlb=temp.getXtlb();
			String cdbh=temp.getCdbh();
			String keyprefix=xtlb+cdbh;
			if(fwfs!=null&&!fwfs.equals(""))
			{
				if(fwfs.indexOf("1")!=-1)
				{
					m.put(keyprefix+"pki", 1);

				}else{
					m.put(keyprefix+"pki", 0);

				}
				if(fwfs.indexOf("2")!=-1)
				{
					m.put(keyprefix+"ip", 2);

				}else{
					m.put(keyprefix+"ip", 0);

				}

			}else{
				m.put(keyprefix+"ip", 0);
				m.put(keyprefix+"pki", 0);

			}
		}
		return m;
	}
	
	

	public String getDepartmentTreeStr(Department department){
		return this.departmentDao.getDepartmentTreeStr(department);
	}

	public int create(Department department) {
		return departmentMapper.create(department);
	}

	public int delete(String glbm) {
		return departmentMapper.deleteById(glbm);
	}
	
}
