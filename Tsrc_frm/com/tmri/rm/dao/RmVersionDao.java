package com.tmri.rm.dao;

import java.util.List;

import com.tmri.rm.bean.RmLastVersion;
import com.tmri.rm.bean.RmPkgVersion;
import com.tmri.rm.bean.RmVersion;
import com.tmri.rm.bean.RmWebVersion;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.dao.FrmDao;

public interface RmVersionDao extends FrmDao {
	
	public List<RmVersion> getAzdmIpGjzList(RmVersion bean) throws Exception;
	
	public RmVersion getRmVersion(String azdm,String ip,String gjz) throws Exception;
	
	public List<RmLastVersion> getLastVersionList() throws Exception;	
	
	public RmLastVersion getLastVersion(String gjz) throws Exception;
	
	public List<RmVersion> getSfList() throws Exception;

	public RmVersion getSfDetail(String sfdm) throws Exception;

	public RmVersion getCount(String sfdm) throws Exception;

	public List<RmVersion> getDsList(String sfdm) throws Exception;

	public RmVersion getDsCount(String fzjg) throws Exception;

	public List<RmPkgVersion> getPkgList(String fzjg) throws Exception;

	public List<RmWebVersion> getWebList(String fzjg) throws Exception;
	
	public List<Code> getSfMz(String sfdm) throws Exception;
	
	public List<RmVersion> getSfJc() throws Exception;
	
	public List<RmVersion> getDsJc(String sfdm) throws Exception;
}
