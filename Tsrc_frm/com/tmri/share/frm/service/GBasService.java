package com.tmri.share.frm.service;

import java.util.List;

import com.tmri.share.frm.bean.BasAlldept;
import com.tmri.share.frm.bean.BasAllxzqh;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.FrmXzqhLocal;
import com.tmri.share.frm.bean.Machine;

public interface GBasService {
	public FrmXzqhLocal getLocalxzqh(String xzqh) throws Exception;

    public Code getLocalxzqhCode(String xzqh) throws Exception;

    public String getSjxzqhs(String xzqh) throws Exception;

    public String getSjxzqhsByComma(String xzqh) throws Exception;
	
	public List<FrmXzqhLocal> getLocalxzqhList() throws Exception;
	
	public List<Code> getLocalXzqhCodeList() throws Exception;
	
	public BasAllxzqh getXzqh(String xzqh) throws Exception;
	
	public Code getXzqhCode(String xzqh) throws Exception;
	
	//获取行政区划名称
	public String getXzqhmc(String xzqh) ;
	
	public BasAlldept getDeptByGlbm(String glbm) throws Exception;
	
	public String getBmmcByGlbm(String glbm) throws Exception;
	
	public String getBmqcByGlbm(String glbm) throws Exception;
	
    public BasAlldept getDeptByFzjg(String fzjg) throws Exception;

    public Code getDeptCodeByFzjg(String fzjg) throws Exception;

    public Code getDeptCodeByXzqh(String xzqh) throws Exception;
	
	public String getBmmcByFzjg(String fzjg) throws Exception;
	
	public String getBmqcByFzjg(String fzjg) throws Exception;
	
	public BasAlldept getDeptByXzqh(String xzqh) throws Exception;
	
	public String getBmmcByXzqh(String xzqh) throws Exception;
	
	public String getBmqcByXzqh(String xzqh) throws Exception;

    public String getCityName(String cs) throws Exception;
    
    public List<BasAlldept> getDeptListBySft(String sft) throws Exception;
    
    public List<BasAllxzqh> getXzqhBySft(String sft) throws Exception;
    
  	public String getLdName(String cs) throws Exception;
  	
  	public String getBmjcByGlbm(String glbm) throws Exception;
  	
 	public String getBmjcByXzqh(String xzqh) throws Exception;
 	
 	public String getBmjcByFzjg(String fzjg) throws Exception;
 	
 	public List<Machine> queryXzqhList(String glbmHead) throws Exception;
 	
 	public String getGlbmByFzjg(String fzjg) throws Exception;
 	
 	public List<Department> getStatOrg(Department dept, boolean includeSelf)
			throws Exception ;
 	
 	public List<Department> getStatOrg(String glbm, boolean includeSelf)
			throws Exception ;
 	
 	public List<Department> getStatOrg(Department dept) throws Exception;
 	
 	public List<BasAllxzqh> getXzqhList() throws Exception ;
 	
 	public List<Code> getXzqhCodeList() throws Exception ;
 	
 	public String transXzqhBySplit(String xzqh, String split);
 	
 	public String getFzjg(String gcxh) throws Exception ;
 	
 	public List<Department> getStatOrgWithBase(String glbm, boolean includeSelf) throws Exception;
}
