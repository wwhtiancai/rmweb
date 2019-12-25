package com.tmri.rfid.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmri.framework.dao.SysuserDao;
import com.tmri.rfid.bean.ClientUser;
import com.tmri.rfid.service.ClientUserService;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author stone
 * @date 2015-12-4 ÏÂÎç3:55:07
 */
@Service
public class ClientUserServiceImpl implements ClientUserService {

	@Autowired
	private SysuserDao sysuserDao;

	@Override
	public int validateSysuser(String yhdh, String mm, String strRemoteAddr) throws Exception {
		// TODO Auto-generated method stub
		
		SysUser sysuser = new SysUser();
		sysuser.setYhdh(yhdh);
		sysuser.setMm(mm);
		sysuser.setDlms("1");
		
		int result=0;
		result=this.sysuserDao.validateSysuser(sysuser,strRemoteAddr);
		
		if(result != 0){
			if(result==1){
				throw new Exception(Constants.SYS_NO_THE_USER);
			}else if(result==2){
				throw new Exception(Constants.SYS_CHANGE_PASSWORD);
			}else if(result==3){
				throw new Exception(Constants.SYS_PASSWORD_INVALID);
			}else if(result==4){
				throw new Exception(Constants.SYS_USER_INVALID);
			}else if(result==5){
				throw new Exception(Constants.SYS_IP_INVALID);
			}else if(result==6){
				throw new Exception(Constants.SYS_PASSWORD_ERROR);
			}else if(result==7){
				throw new Exception(Constants.SYS_NO_HIGHXZQH);
			}else if(result==8){
				throw new Exception(Constants.SYS_NO_RIGHT);
			}else if(result==9){
				throw new Exception(Constants.SYS_ZT_INVALID);
			}else if(result==10){
				throw new Exception(Constants.SYS_SFZ_LOGIN);
			}else if(result==12){
				throw new Exception(Constants.SYS_PKI_LOGIN);
			}else if(result==13){
				throw new Exception(Constants.SYS_GLY_PASS);
			}else if(result==14){
				throw new Exception(Constants.SYS_PTYH_PASS);
			}else if(result==15){
				throw new Exception(Constants.SYS_PTYH_GDDZTS);
			}else if(result==16){
				throw new Exception(Constants.SYS_PTYH_GDDZFS);
			}
		} else {

        }
		
		return result;
	}

}
