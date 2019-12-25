package com.tmri.rm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.rm.bean.RmVersion;
import com.tmri.rm.dao.RmVersionDao;
import com.tmri.rm.service.JcbkManager;
import com.tmri.share.frm.bean.BasAlldept;
import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.service.GBasService;

@Service

public class JcbkManagerImpl implements JcbkManager {
	@Autowired
	private RmVersionDao rmVersionDao;
	@Autowired
	private GBasService gBasService;
	
	public List<RmVersion> getJcbkSfList(String yxms) throws Exception {
		List<RmVersion> list = rmVersionDao.getSfJc();
		List<RmVersion> result = new ArrayList<RmVersion>(); 
		
		if(yxms.equals("bj")){
			
			for(int i = 0; i < list.size(); i++){
				RmVersion rmVersion = new RmVersion();
				String fzjg = "";
				String ds = "";
				RmVersion tempRmVersion = list.get(i);
				if(tempRmVersion.getSfdm().equals("²¿")){
					continue;
				}
				Code code = rmVersionDao.getSfMz(tempRmVersion.getSfdm()).get(0);
				rmVersion.setSf(code.getDmsm1());
				List<RmVersion> dslist = rmVersionDao.getDsJc(tempRmVersion.getSfdm());
				//RmVersion rmVer = null;
				List<String> fzjglist = new ArrayList<String>();
				
				for(int j = 0; j < dslist.size(); j++){
					RmVersion tempDs = dslist.get(j);
					String str = tempDs.getFzjg();
					
					boolean flag = true;
					
					if(str.indexOf(",") > 0 || str.indexOf("£¬") > 0){
						str = str.substring(0, 1) + "ZD";
					}
					if((str.indexOf(",") < 0 || str.indexOf("£¬") < 0) && str.indexOf("ZD") < 0){
						str = str.substring(0,2);
					}
					
					
					for(int k = 0; fzjglist != null && k < fzjglist.size(); k++){
						if (str.equals(fzjglist.get(k))) {
							flag = false;
							break;
						}
					}
					if(flag){
						fzjglist.add(str);
                        BasAlldept dept = this.gBasService.getDeptByFzjg(str);
                        if (dept != null) {
							fzjg = fzjg + tempDs.getFzjg() + " ";
                            ds += dept + " ";
						}
					}
					
				}
				rmVersion.setFzjg(fzjg);
				rmVersion.setDs(ds);
				
				result.add(rmVersion);
			}
		}else if(yxms.substring(1).equals("ZD")){
			RmVersion rmVersion = new RmVersion();
			String fzjg = "";
			String ds = "";
			Code code = rmVersionDao.getSfMz(yxms.substring(0, 1)).get(0);
			rmVersion.setSf(code.getDmsm1());
			List<RmVersion> dslist = rmVersionDao.getDsJc(yxms.substring(0, 1));
			List<String> fzjglist = new ArrayList<String>();
			for(int j = 0; j < dslist.size(); j++){
				RmVersion tempDs = dslist.get(j);
				boolean flag = true;
				String str = tempDs.getFzjg();
				
				if(str.indexOf(",") > 0 || str.indexOf("£¬") > 0){
					str = str.substring(0, 1) + "ZD";
				}
				if((str.indexOf(",") < 0 && str.indexOf("£¬") < 0) && str.indexOf("ZD") < 0){
					str = str.substring(0,2);
				}
				
				for(int k = 0; fzjglist != null && k < fzjglist.size(); k++){
					if (str.equals(fzjglist.get(k))) {
						flag = false;
						break;
					}
				}
				if(flag){
					fzjglist.add(str);
//					if(rmVersionDao.getDsMz(str) == null || rmVersionDao.getDsMz(str).size() == 0){
//						continue;
//					}
//					
//					Code code2 = rmVersionDao.getDsMz(str).get(0);
					if(this.gBasService.getDeptByFzjg(str)!=null){
						fzjg = fzjg + tempDs.getFzjg() + " ";
						ds = ds + this.gBasService.getBmjcByFzjg(str) + " ";
					}
				}
				
			}
			rmVersion.setFzjg(fzjg);
			rmVersion.setDs(ds);
			result.add(rmVersion);
		}else{
			RmVersion rmVersion = new RmVersion();
			String fzjg = yxms;
			String ds = "";
			Code code = rmVersionDao.getSfMz(yxms.substring(0, 1)).get(0);
			rmVersion.setSf(code.getDmsm1());
			if(yxms.indexOf(",") > 0 || yxms.indexOf("£¬") > 0){
				yxms = yxms.substring(0,1) + "ZD";
			}
			//Code code2 = rmVersionDao.getDsMz(yxms).get(0);
			//ds = code2.getDmsm2();
			rmVersion.setFzjg(fzjg);
			rmVersion.setDs(this.gBasService.getBmjcByFzjg(yxms));
			result.add(rmVersion);
		}
		
		return result;
	}

}
