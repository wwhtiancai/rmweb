package com.tmri.rm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.rm.bean.RmLastVersion;
import com.tmri.rm.bean.RmPkgVersion;
import com.tmri.rm.bean.RmVersion;
import com.tmri.rm.bean.RmWebVersion;
import com.tmri.rm.dao.RmVersionDao;
import com.tmri.rm.dao.jdbc.RmDataObjDaoJdbc;
import com.tmri.rm.service.RmVersionManager;

@Service

public class RmVersionManagerImpl implements RmVersionManager {
	@Autowired
	private RmVersionDao rmVersionDao;
	@Autowired
	private RmDataObjDaoJdbc rmDataObjDao;
	
	public List<RmVersion> getAzdmIpGjzList(RmVersion bean) throws Exception{
		return rmVersionDao.getAzdmIpGjzList(bean);
	}
	
	public RmVersion getRmVersion(String azdm,String ip,String gjz) throws Exception{
		return rmVersionDao.getRmVersion(azdm,ip, gjz);
	}
	
	public List<RmLastVersion> getLastVersionList() throws Exception{
		return rmVersionDao.getLastVersionList();
	}
	
	public RmLastVersion getLastVersion(String gjz) throws Exception{
		return rmVersionDao.getLastVersion(gjz);
	}

	public List<RmVersion> getSfList() throws Exception {
		List<RmVersion> list = rmVersionDao.getSfList();
		
		List<RmVersion> result = new ArrayList<RmVersion>();
		
		RmVersion rmVersion = null;
		if(null != list){
			for (RmVersion v : list) {
				if(!"00".equals(v.getSfdm())){
					rmVersion = rmVersionDao.getCount(v.getSfdm());
					rmVersion.setSfdm(v.getSfdm());
					
					// 应用程序更新状态
					if(rmVersion.getAzds() == rmVersion.getNewWebCount()){
						rmVersion.setWeb("全部更新");
						rmVersion.setWebClass("green");
					}else if (0 == rmVersion.getNewWebCount()) {
						rmVersion.setWeb("未更新");
						rmVersion.setWebClass("red");
					}else {
						rmVersion.setWeb("部分更新(" + rmVersion.getNewWebCount() + "/" + rmVersion.getAzds() + ")");
						rmVersion.setWebClass("black");
					}
					
					// 存储过程更新状态
					if(rmVersion.getAzds() == rmVersion.getNewPkgCount()){
						rmVersion.setPkg("全部更新");
						rmVersion.setPkgClass("green");
					}else if (0 == rmVersion.getNewPkgCount()) {
						rmVersion.setPkg("未更新");
						rmVersion.setPkgClass("red");
					}else {
						rmVersion.setPkg("部分更新(" + rmVersion.getNewPkgCount() + "/" + rmVersion.getAzds() + ")");
						rmVersion.setPkgClass("black");
					}
					
					// 运行状态
					if(rmVersion.getAzds() == rmVersion.getNormalCount()){
						rmVersion.setStatus("全部正常");
						rmVersion.setStatusClass("green");
					}else if (0 == rmVersion.getNormalCount()) {
						rmVersion.setStatus("不正常");
						rmVersion.setStatusClass("red");
					}else {
						rmVersion.setStatus("部分正常(" + rmVersion.getNormalCount() + "/" + rmVersion.getAzds() + ")");
						rmVersion.setStatusClass("black");
					}
					
					result.add(rmVersion);
				}
			}
		}
		
		return result;
	}

	public List<RmVersion> getDsList(String sfdm) throws Exception {
		List<RmVersion> list = rmVersionDao.getDsList(sfdm);
		
		List<RmVersion> result = new ArrayList<RmVersion>();
		
		RmVersion rmVersion = null;
		String fzjg = "";
		for (RmVersion v : list) {
			if(hasAnother(v, list)){
				continue;
			}
			fzjg = v.getFzjg();
			rmVersion = rmVersionDao.getDsCount(fzjg);
			rmVersion.setFzjg(fzjg);
			if(null != rmVersion.getDs() && !"".equals(rmVersion.getDs())){
				// 应用程序
				if (1 == rmVersion.getNewWebCount()) {
					rmVersion.setWeb("已更新");
					rmVersion.setWebClass("green");
				}else if (0 == rmVersion.getWebSc()) {
					rmVersion.setWeb("未上传");
					rmVersion.setWebClass("red");
				}else {
					rmVersion.setWeb("未更新");
					rmVersion.setWebClass("black");
				}
				
				// 存储过程
				if (1 == rmVersion.getNewPkgCount()) {
					rmVersion.setPkg("已更新");
					rmVersion.setPkgClass("green");
				}else if (0 == rmVersion.getPkgSc()) {
					rmVersion.setPkg("未上传");
					rmVersion.setPkgClass("red");
				}else {
					rmVersion.setPkg("未更新");
					rmVersion.setPkgClass("black");
				}
				
				// 状态
				if (1 == rmVersion.getNormalCount()) {
					rmVersion.setStatus("正常");
					rmVersion.setStatusClass("green");
				}else {
					rmVersion.setStatus("不正常");
					rmVersion.setStatusClass("red");
				}
				
				result.add(rmVersion);
			}
		}
		
		return result;
	}

	private boolean hasAnother(RmVersion v, List<RmVersion> list) {
		
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx" + v.getFzjg() + "," + v.getJcsj());
		for (RmVersion r : list) {
			//System.out.println("==============" + v.getFzjg()+ "," + v.getJcsj() + "==========");
			if(!r.getFzjg().endsWith("ZD") && r.getFzjg().substring(0, 2).equals(v.getFzjg().substring(0, 2)) && r.getAzdm().compareTo(v.getAzdm()) > 0){
				return true;
			}
		}
		
		return false;
	}

	public List<RmPkgVersion> getPkgList(String fzjg) throws Exception {
		return rmVersionDao.getPkgList(fzjg);
	}

	public List<RmWebVersion> getWebList(String fzjg) throws Exception {
		return rmVersionDao.getWebList(fzjg);
	}

}
