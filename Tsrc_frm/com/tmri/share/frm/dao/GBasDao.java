package com.tmri.share.frm.dao;

import java.util.List;

import com.tmri.share.frm.bean.BasAlldept;
import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.bean.Machine;

public interface GBasDao {
	//////////ÄÚ´æ²Ù×÷//////////////////////
	public BasAlldept getBasAlldept(String glbm) throws Exception;
	public BasAllxzqh getBasAllxzqh(String xzqh) throws Exception;
    public String getSjxzqhs(String xzqh) throws Exception;
//	public List<Department> getAllXjDepartmentBySjbm(String glbm) throws Exception;
	////////////////////
	public FrmXzqhLocal getFrmLocalxzqh(String xzqh) throws Exception;
	
	public List<FrmXzqhLocal> getFrmLocalxzqhList() throws Exception;
	
	public BasAlldept getBasAlldeptByFzjg(String fzjg) throws Exception;
	
	public BasAlldept getBasAlldeptByXzqh(String xzqh) throws Exception;

    public List<BasAlldept> getBasDeptList() throws Exception;
    
    public List<Machine> getBasAlldeptList(String glbmHead) throws Exception;

//    public List<Code> getRemoteCity(String fzjg) throws Exception;
    
    public List<BasAlldept> getBasAlldeptListBySft(String sft) throws Exception;
    
    public List<Department> getStatOrg(Department dept, boolean includeSelf)
			throws Exception ;
    
    public List<Department> getStatOrg(Department dept) throws Exception;
    
    public List<BasAllxzqh> getBasAllxzqhList() throws Exception ;
    
    public List<Code> getBasAllXzqhCodeList() throws Exception;
    
    public List<BasAllxzqh> getBasAllxzqhBySft(String sft) throws Exception;
	public List<Department> getStatOrg(Department dept, boolean includeSelf, 
			boolean isContainOffice) throws Exception;
	public List<Department> getStatOrg(String glbm, boolean includeSelf) throws Exception;
	public List<Department> getStatOrgWithBase(String glbm, boolean includeSelf) throws Exception;
}
