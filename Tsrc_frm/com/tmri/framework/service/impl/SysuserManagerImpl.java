package com.tmri.framework.service.impl;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.tmri.rfid.mapper.SysuserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;



import com.tmri.framework.bean.FrmUserloginfail;
import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.PassWord;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.bean.SysUserFinger;
import com.tmri.framework.bean.SysUserSeal;
import com.tmri.framework.bean.UserDesk;
import com.tmri.framework.bean.UserMenu;
import com.tmri.framework.bean.UserRole;
import com.tmri.framework.dao.DepartmentDao;
import com.tmri.framework.dao.RoleDao;
import com.tmri.framework.dao.SysuserDao;
import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
import com.tmri.framework.service.SysuserManager;
import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.dao.GDepartmentDao;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
@Service

public class SysuserManagerImpl implements SysuserManager{
	@Autowired
	SysuserDao sysuserDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	FrmDataObjDaoJdbc frmDataObjDao;
	@Autowired
	RoleDao   roleDao;
	@Autowired
	GDepartmentDao gDepartmentDao;

	@Autowired
	private SysuserMapper sysuserMapper;

	public SysUser getSysuser(String yhdh){
		SysUser sysuser=this.sysuserDao.getSysuser(yhdh);
		if(sysuser!=null){
			if(sysuser.getMmyxq().length()>10){
				sysuser.setMmyxq(sysuser.getMmyxq().substring(0,10));
			}
			if(sysuser.getZhyxq().length()>10){
				sysuser.setZhyxq(sysuser.getZhyxq().substring(0,10));
			}
		}
		return sysuser;
	}
	public SysUser getAtmSysUserByIpdz(String ipdz){
		return this.sysuserDao.getAtmSysUserByIpdz(ipdz);
	}
	public List getSysusers(SysUser user){
		return this.sysuserDao.getSysusers(user);
	}
	public List getAtmSysUserMenus(String yhdh){
		return this.sysuserDao.getAtmSysUserMenus(yhdh);
	}
	// 修改增加返回值处理
	// zhoujn 20100524
	public SysResult saveSysuser(SysUser user,Log log,String modal) throws Exception{
		SysResult result=null;
		result=frmDataObjDao.setOracleData(user,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=sysuserDao.saveSysuser(modal);

		return result;
	}
	
	//用户权限信息
	public SysResult saveSysuserrole(SysUser user,UserRole userRole,UserMenu userMenu,UserRole userGrantrole,Log log,String modal) throws Exception{
		SysResult result=null;
		result=frmDataObjDao.setOracleData(user,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=sysuserDao.saveSysuserRole(modal);
		if(result.getFlag()==1){
			SysResult temp;
			if(user.getQxms().equals("1")){
				temp=sysuserDao.saveUserRole(userRole);
			}else{
				temp=sysuserDao.saveUserMenu(userMenu);
			}
			// 系统管理授权权限
			if(user.getXtgly().equals("1")){
				temp=sysuserDao.saveUserGrantrole(userGrantrole);
			}
		}
		return result;
	}	
	
	

	public SysResult removeSysuser(SysUser user,Log log) throws Exception{
		SysResult result=null;
		result=frmDataObjDao.setOracleData(user,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=sysuserDao.removeSysuser();
		return result;
	}

	public SysResult saveSetuppassword(SysUser user,Log log) throws Exception{
		SysResult result=null;
		result=frmDataObjDao.setOracleData(user,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=sysuserDao.saveSetuppassword();
		return result;
	}
	

	public void setSysuserRoles(SysUser sysuser){
		List list=null;
		if(sysuser!=null){
			// 求取用户的角色记录
			if(sysuser.getQxms().equals("1")){
				list=this.sysuserDao.getUserRole(sysuser.getYhdh());
				if(list!=null){
					String tmp="";
					Iterator iterator=list.iterator();
					while(iterator.hasNext()){
						Role userRole=(Role)iterator.next();
						tmp=tmp+userRole.getJsdh()+"A";
					}
					// 将A去掉
					if(!tmp.equals("")){
						tmp=tmp.substring(0,tmp.length()-1);
					}
					sysuser.setRoles(tmp);
				}
			}
			// 直接求取用户的菜单记录
			if(sysuser.getQxms().equals("2")){
				list=this.sysuserDao.getUserMenu(sysuser.getYhdh());
				if(list!=null){
					String tmp="";
					Iterator iterator=list.iterator();
					while(iterator.hasNext()){
						UserMenu userMenu=(UserMenu)iterator.next();
						if(userMenu.getGnlb().equals("")){
							tmp=tmp+userMenu.getXtlb()+"-"+userMenu.getCdbh()+"A";
						}else{
							tmp=tmp+userMenu.getXtlb()+"-"+userMenu.getCdbh()+"-"+userMenu.getGnlb()+"A";
						}

					}
					sysuser.setCdbhs(tmp);
				}
			}
		}
	}

	// 设置grantrole

	// 检测是否同一授权
	public boolean checkSameGrant(List rolelist,SysUser sysuser){
		boolean result=false;
		if(rolelist==null){
			return result;
		}

		if(sysuser!=null){
			// 求取用户的角色记录
			if(sysuser.getQxms().equals("1")){
				List list=this.sysuserDao.getUserRole(sysuser.getYhdh());
				// 比较两个list
				if(list!=null){
					result=true;
					for(int i=0;i<list.size();i++){
						Role userrole=(Role)list.get(i);
						// 在里面
						if(!checkRolelist(rolelist,userrole.getJsdh())){
							result=false;
							break;
						}
					}
				}
			}
		}
		return result;
	}

	private boolean checkRolelist(List rolelist,String jsdh){
		boolean result=false;
		for(int i=0;i<rolelist.size();i++){
			Role role=(Role)rolelist.get(i);
			if(role.getJsdh().equals(jsdh)){
				result=true;
				break;
			}
		}
		return result;
	}

	//获取用户已授权的可授权权限
	public List getUserGrantRoleList(String yhdh) throws Exception {
		return this.sysuserDao.getUserGrantRole(yhdh);
	}
	
	
	public void setSysuserGrantRoles(SysUser sysuser){
		List list=null;
		if(sysuser!=null){
			// 如果是系统管理员
			if(sysuser.getXtgly().equals("1")){
				list=this.sysuserDao.getUserGrantRole(sysuser.getYhdh());
				if(list!=null){
					String tmp="";
					Iterator iterator=list.iterator();
					while(iterator.hasNext()){
						Role userRole=(Role)iterator.next();
						tmp=tmp+userRole.getJsdh()+"A";
					}
					// 将A去掉
					if(!tmp.equals("")){
						tmp=tmp.substring(0,tmp.length()-1);
					}
					sysuser.setGrantroles(tmp);
				}
			}

		}
	}

	public List getLoginFail(String yhdh){
		return this.sysuserDao.getLoginFail(yhdh);
	}

	public SysResult saveUnlockUser(SysUser user,Log log){
		SysResult result=null;
		result=frmDataObjDao.setOracleData(user,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=sysuserDao.saveUnlockUser();
		return result;
	}

	public int validateSysuser(SysUser sysuser,UserSession userSession,String strRemoteAddr) throws Exception{
		int result=0;
		result=this.sysuserDao.validateSysuser(sysuser,strRemoteAddr);
		if(result==0){
			userSession.setYhdh(sysuser.getYhdh());
			this.sysuserDao.getLoginInfo(userSession);
			if("1".equals(userSession.getDlms())){
				userSession.setDlms("普通");
			}else if("2".equals(userSession.getDlms())){
				userSession.setDlms("PKI");
			}else{
				userSession.setDlms("身份证");
			}
			if("1".equals(userSession.getScdlms())){
				userSession.setScdlms("普通");
			}else if("2".equals(userSession.getScdlms())){
				userSession.setScdlms("PKI");
			}else if("3".equals(userSession.getScdlms())){
				userSession.setScdlms("身份证");
			}else{
				userSession.setScdlms("指纹");
			}
		}else{
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
		}
		// 通过所有前置用户判断后，判断安全系统是否符合状态
		if(result==0){
			String s="0";// deactive in develop mode F1.getBj1();
			if(s.equals("0")){
				// 正常状态，不处理
			}else{
				try{
					if(s.equals("81")){
						throw new Exception(Constants.SAFE_CTRL_MSG_81);
					}else if(s.equals("82")){
						throw new Exception(Constants.SAFE_CTRL_MSG_82);
					}else if(s.equals("83")){
						throw new Exception(Constants.SAFE_CTRL_MSG_83);
					}else if(s.equals("84")){
						// throw new
						// Exception(com.tmri.pub.Constants.SAFE_CTRL_MSG_84);
					}else if(s.equals("85")){
						// throw new
						// Exception(com.tmri.pub.Constants.SAFE_CTRL_MSG_85);
					}else if(s.equals("91")){
						throw new Exception(Constants.SAFE_CTRL_MSG_91);
					}else if(s.equals("92")){
						throw new Exception(Constants.SAFE_CTRL_MSG_92);
					}else if(s.equals("93")){
						throw new Exception(Constants.SAFE_CTRL_MSG_93);
					}else{
						throw new Exception(Constants.SAFE_CTRL_MSG_99);
					}
				}catch(Exception e){
					System.err.println(e.getMessage());
					// 取消下面的注释以启动安全限制
					throw e;
				}
			}
		}
		return result;
	}
//公安版本：获取交警用户的数量
	public int getTrafficPolice(String yhdh) throws Exception{
		return this.sysuserDao.getTrafficPolice(yhdh);
	}
//公安版本：校验公安用户
	public int validatePoliceman(SysUser sysuser,UserSession userSession,String strRemoteAddr) throws Exception{
		int result=this.sysuserDao.validatePoliceman(sysuser,strRemoteAddr);
		if(result==0){
			userSession.setYhdh(sysuser.getYhdh());
			this.sysuserDao.getPolicemanInfo(userSession);
			if("1".equals(userSession.getDlms())){
				userSession.setDlms("普通");
			}else if("2".equals(userSession.getDlms())){
				userSession.setDlms("PKI");
			}else if("3".equals(userSession.getDlms())){
				userSession.setDlms("身份证");
			}else {
				userSession.setDlms("首次");
			}
			if("1".equals(userSession.getScdlms())){
				userSession.setScdlms("普通");
			}else if("2".equals(userSession.getScdlms())){
				userSession.setScdlms("PKI");
			}else if("3".equals(userSession.getScdlms())){
				userSession.setScdlms("身份证");
			}else if("4".equals(userSession.getScdlms())){
				userSession.setScdlms("指纹");
			}else{
				userSession.setScdlms("首次");
			}
		}else{
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
		}
		return result;
	}
//公安版本：获取公安用户
	public SysUser getPoliceman(String yhdh){
		SysUser sysuser=this.sysuserDao.getPoliceman(yhdh);
		if(sysuser!=null){
			if(sysuser.getMmyxq().length()>10){
				sysuser.setMmyxq(sysuser.getMmyxq().substring(0,10));
			}
			if(sysuser.getZhyxq().length()>10){
				sysuser.setZhyxq(sysuser.getZhyxq().substring(0,10));
			}
		}
		return sysuser;
	}
	public SysResult savapassword(PassWord passWord,Log log) throws Exception{
		SysResult result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		return this.sysuserDao.savapassword(passWord);
	}
	//公安版本：
	public SysResult savaPlspassword(PassWord passWord,Log log) throws Exception{
		SysResult result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		return this.sysuserDao.savaPlspassword(passWord);
	}
	public SysResult savagdip(PassWord passWord,Log log) throws Exception{
		SysResult result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		return this.sysuserDao.savagdip(passWord);
	}
	public SysUserFinger getSysUserFinger(String yhdh) throws Exception{
		return sysuserDao.getSysUserFinger(yhdh);
	}
	public SysResult savaFinger(SysUserFinger sysUserFinger,Log log) throws Exception{
		SysResult result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		return this.sysuserDao.savaFinger(sysUserFinger);
	}
	public List getSysusersByPagesize(SysUser user,PageController controller){
		return this.sysuserDao.getSysusersByPagesize(user,controller);
	}

	// 保存桌面设置
	public int Savedesk(UserDesk userDesk) throws Exception{
		return this.sysuserDao.Savedesk(userDesk);
	}
	// zhoujn
	public String getBmmc(String bmdm) throws Exception{
		Department dept=this.gDepartmentDao.getDepartment(bmdm);
		String result="";
		if(dept!=null){
			result=dept.getBmmc();
		}
		return result;
	}

	// 处理ip地址
	// bj 1开始 2结束
	public String getIpdz(String ipdz,String bj,int idx) throws Exception{
		String result="";
		if(ipdz==null||ipdz.equals("")){
			return "";
		}
		if(bj.equals("1")){
			result="1";
			String[] tmparr=ipdz.split("\\.");
			if(tmparr.length==4){
				result=tmparr[idx-1];
			}			
		}else if(bj.equals("2")){
			result="255";
			String[] tmparr=ipdz.split("\\.");
			if(tmparr.length==4){
				result=tmparr[idx-1];
			}
		}else if(bj.equals("3")){
			result="00";
			String[] tmparr=ipdz.split("\\-");
			if(tmparr.length==6){
				result=tmparr[idx-1];
			}
		}
		
		return result;
	}

	// zhoujn 20100524
	public Blob getQmtp(String yhdh) throws Exception{
		return this.sysuserDao.getQmtp(yhdh);
	}

	//
	public int getQmtpNum(String yhdh){
		return this.sysuserDao.getQmtpNum(yhdh);
	}

	public SysResult saveQmtp(SysUserSeal obj,final byte[] qmtp) throws Exception{
		SysResult returninfo=new SysResult();
		int photoSize=(int)obj.getQmtp().getSize();
		String photoSetSize="1024";// 默认参数无设置时为1M;

		if((photoSize/1024)<Integer.parseInt(photoSetSize)){
			SysResult sysresult=this.sysuserDao.saveQmtp(obj.getYhdh(),obj,qmtp);
			returninfo.setFlag(1);
		}else{
			returninfo.setFlag(5);
		}
		return returninfo;
	}

	public SysResult delQmtp(SysUserSeal obj) throws Exception{
		SysResult returninfo=new SysResult();
		returninfo.setFlag(99);
		SysResult sysresult=this.sysuserDao.delQmtp(obj.getYhdh());
		if(sysresult.getFlag()==1){
			returninfo.setFlag(3);
		}
		return returninfo;
	}

	// 取随机数
	public int getRand(){
		Random rand=new Random();
		int randnum=Math.abs(rand.nextInt());
		return randnum;
	}

	// 获取状态
	public String getZtmc(String zt){
		String result="正常";
		if(zt.equals("1")){
			result="正常";
		}else if(zt.equals("2")){
			result="锁定";
		}else if(zt.equals("3")){
			result="停用";
		}
		return result;
	}

	// 获取用户角色list
	public List getUserRoleList(String yhdh) throws DataAccessException{
		return this.sysuserDao.getUserRoleList(yhdh);
	}

	// 获取授权角色
	// onclick='getusermenu(1);'
	//bz  1用户的角色，2管理员角色 3和管理员共有角色4属于管理员角色孩子角色
	//1 1
	//4 2
	//3 3
	//2,4
	public String getRolelistHtml(String ckname,int column,Hashtable rolelist){
		// 设定每行几个
		String innerStr="";
		/*
		String innerStr="";
		int l=0;
		for(int i=0;i<rolelist.size();i++){
			Role role=(Role)rolelist.get(i);
			String tmpdh=role.getJsdh();
			String tmpmc=role.getJsmc();
			if(i!=0&&i%column==0){
				innerStr=innerStr+"<br>";
			}
			//bz  1用户的角色，2管理员角色 3共有角色
			String abled="";
			String title="";
			if(role.getBz().equals("1")){
				abled=" disabled  checked ";
				title+="当前用户原拥有角色，本管理员无法管理";
			}else if(role.getBz().equals("3")){
				abled=" checked ";
				title+="当前用户原拥有角色，本管理员可管理";
			}else if(role.getBz().equals("4")){
				abled=" ";
				title+="非当前用户原拥有角色，属于本管理员角色";
			}else if(role.getBz().equals("2")){
				abled=" checked ";
				title+="当前用户原拥有角色,属于本管理员角色的子角色，可管理";
			}
			title+=",点击查看角色权限";
			innerStr=innerStr+"<input  type='checkbox' "+ abled +" name='"+ckname+"' value='"+tmpdh+"' >"
				+" <input title=\"" + title + "\" type='text'  class='text_nobord' onclick='queryrolemenu(\""+tmpdh+"\");'"+" style='background-color: #EFEFFE;width:120;cursor:hand' value='"+tmpmc
				+"' readonly>";
			
			//
			
		}
		*/
		boolean brflag=false;
		List rolelist1=(List)rolelist.get("rolelist1");
		if(rolelist1!=null&&rolelist1.size()!=0){
			if(brflag) innerStr+="<br>";
			//innerStr+=" <input type='text' value='当前用户原拥有角色，本管理员无法管理' class='text_nobord'  style='background-color: #EFEFFE;width:120;cursor:hand'  readonly><br>";
			//innerStr+="<label>当前用户原拥有角色，本管理员无法管理</label><br>";
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_title('其他管理员已授予角色')</script>";
			innerStr+=StringUtil.tss_title("其他管理员已授予角色");
			for(int i=0;i<rolelist1.size();i++){
				Role role=(Role)rolelist1.get(i);
				if(i!=0&&i%column==0){
					innerStr=innerStr+"<br>";
				}
				innerStr+=getRoleHtml(" checked disabled ",ckname,role);
			}
			if(rolelist1.size()%column==0){
				brflag=false;
			}else{
				brflag=true;
			}
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_down()</script>";
			innerStr+=StringUtil.tss_down();
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_down()</script>";
		}
		
		List rolelist2=(List)rolelist.get("rolelist2");
		if(rolelist2!=null&&rolelist2.size()!=0){
			if(brflag) innerStr+="<br>";
			//innerStr+=" <input type='text' value='当前用户原拥有角色,属于本管理员角色的子角色，可管理' class='text_nobord'  style='background-color: #EFEFFE;width:120;cursor:hand'  readonly><br>";
			//innerStr+="<label>当前用户原拥有角色,属于本管理员角色的子角色，可管理</label><br>";
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_title('本管理员可管理角色')</script>";
			innerStr+=StringUtil.tss_title("本管理员可管理角色");
			for(int i=0;i<rolelist2.size();i++){
				Role role=(Role)rolelist2.get(i);
				if(i!=0&&i%column==0){
					innerStr=innerStr+"<br>";
				}
				innerStr+=getRoleHtml("checked",ckname,role);
			}
			if(rolelist2.size()%column==0){
				brflag=false;
			}else{
				brflag=true;
			}
			innerStr+=StringUtil.tss_down();
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_down()</script>";
		}
		
		List rolelist3=(List)rolelist.get("rolelist3");
		if(rolelist3!=null&&rolelist3.size()!=0){
			if(brflag) innerStr+="<br>";
			//innerStr+=" <input type='text' value='当前用户原拥有角色，本管理员可管理' class='text_nobord'  style='background-color: #EFEFFE;width:120;cursor:hand'  readonly><br>";
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_title('本管理员已授予角色')</script>";
			innerStr+=StringUtil.tss_title("本管理员已授予角色");
			for(int i=0;i<rolelist3.size();i++){
				Role role=(Role)rolelist3.get(i);
				if(i!=0&&i%column==0){
					innerStr=innerStr+"<br>";
				}
				innerStr+=getRoleHtml("checked",ckname,role);
			}
			
			if(rolelist3.size()%column==0){
				brflag=false;
			}else{
				brflag=true;
			}
			innerStr+=StringUtil.tss_down();
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_down()</script>";
		}
		
		List rolelist4=(List)rolelist.get("rolelist4");
		if(rolelist4!=null&&rolelist4.size()!=0){
			if(brflag) innerStr+="<br>";
			//innerStr+=" <input type='text' value='非当前用户原拥有角色，属于本管理员角色' class='text_nobord'  style='background-color: #EFEFFE;width:120;cursor:hand'  readonly><br>";
			//innerStr+="<label>非当前用户原拥有角色，属于本管理员角色</label><br>";
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_title('本管理员可授予角色')</script>";
			innerStr+=StringUtil.tss_title("本管理员可授予角色");
			for(int i=0;i<rolelist4.size();i++){
				Role role=(Role)rolelist4.get(i);
				if(i!=0&&i%column==0){
					innerStr=innerStr+"<br>";
				}
				innerStr+=getRoleHtml("",ckname,role);
			}
			innerStr+=StringUtil.tss_down();
			//innerStr+="<script language=\"JavaScript\" type=\"text/javascript\">tss_down()</script>";
		}		
		//innerStr+="<input type='button' onclick=\"queryselectedrolemenu(formedit."+ckname+");\""+" class='button' style='width:100' value='查询选中角色权限' readonly>";
		
		
		//20111102增加 rolelist5为当前管理员角色
		List rolelist5=(List)rolelist.get("rolelist5");
		if(rolelist5!=null&&rolelist5.size()!=0){
			if(brflag) innerStr+="<br>";
			innerStr+=StringUtil.tss_title("当前用户角色");
			for(int i=0;i<rolelist5.size();i++){
				Role role=(Role)rolelist5.get(i);
				if(i!=0&&i%column==0){
					innerStr=innerStr+"<br>";
				}
				innerStr+=getRoleHtml("",ckname,role);
			}
			innerStr+=StringUtil.tss_down();
		}		
		return innerStr;
	}
	
	private String getRoleHtml(String abled,String ckname,Role role){
		String result="<input  type='checkbox' "+ abled +" name='"+ckname+"' value='"+role.getJsdh()+"' >"
			+" <input type='text'  class='text_nobord' onclick='queryrolemenu(\""+role.getJsdh()+"\");'"+" style='background-color: #EFEFFE;width:120;cursor:hand' value='"+role.getJsmc()+"' readonly>";
		return result;
	}
	
	
	// 构造授权角色
	public String getGrantRolelistHtml(String ckname,int column,List rolelist){
		// 设定每行几个
		String innerStr="";
		int l=0;
		for(int i=0;i<rolelist.size();i++){
			Role role=(Role)rolelist.get(i);
			String tmpdh=role.getJsdh();
			String tmpmc=role.getJsmc();
			if(i!=0&&i%column==0){
				innerStr=innerStr+"<br>";
			}
			
			//bz  1用户的角色，2管理员角色 3共有角色
			//分不同颜色显示
			String abled="";
			if(role.getBz().equals("1")){
				abled=" disabled ";
			}
			innerStr=innerStr+"<input type='checkbox' "+ abled +" name='"+ckname+"' value='"+tmpdh+"' >"
				+" <input type='text' title='点击查看角色权限' class='text_nobord' onclick='queryrolemenu(\""+tmpdh+"\");'"+" style='background-color: #EFEFFE;width:120;cursor:hand' value='"+tmpmc
					+"' readonly>";
			
		}
		innerStr+="<input type='button' onclick=\"queryselectedrolemenu(formedit."+ckname+");\""+" class='button' style='width:100' value='查询选中角色权限' readonly>";
		return innerStr;
	}
	
	
	// 获取用户人员信息
	public SysUser getUserinfo(String rylb,String sfzmhm) throws Exception{
		return this.sysuserDao.getUserinfo(rylb,sfzmhm);
	}

	// 获取人员类型
	public String getRylxOptionHtml(String defval){
		String[][] temp=new String[][]{{"1","民警"},{"2","协警"},{"3","工作人员"},{"4","ATM设备"}};
		String _returnStrings="";
		for(int i=0;i<temp.length;i++){

			if(defval.equals(temp[i][0])){
				_returnStrings+="<option value='"+temp[i][0]+"' selected>"+temp[i][1]+"</option>";
			}else{
				_returnStrings+="<option value='"+temp[i][0]+"'>"+temp[i][1]+"</option>";
			}
		}
		return _returnStrings;
	}


	public String getRylxHtml(String defval){
		String[][] temp=new String[][]{{"1","民警"},{"2","协警"},{"3","工作人员"},{"4","ATM设备"}};
		String _returnStrings="";
		for(int i=0;i<temp.length;i++){

			if(defval.equals(temp[i][0])){
				_returnStrings+=temp[i][1];
				break;
			}
		}
		return _returnStrings;
	}

	// 获取用户列表
	public List getUserinfoList(SysUser obj) throws Exception{
		return this.sysuserDao.getUserinfoList(obj);
	}
	
	
	//合并两类权限
	//bz  1用户的角色，4管理员角色 3和管理员共有角色2属于管理员角色孩子角色
	//
	public Hashtable combinRoleList(List xtglyrolelist,List userrolelist) {
		Hashtable resultht=new Hashtable();
		List result1=new ArrayList();
		List result2=new ArrayList();
		List result3=new ArrayList();
		List result4=new ArrayList();
		//先系统管理员
		for(int i=0;i<xtglyrolelist.size();i++){
			Role role =(Role)xtglyrolelist.get(i);
			role.setBz("4");
			result4.add(role);
		}
		
		for(int i=0;i<userrolelist.size();i++){
			Role role =(Role)userrolelist.get(i);
			//判断是否在
			//子集 0不在，1在管理员角色，2在管理员角色的子角色
			String checkexist=checkExist(xtglyrolelist,role);
			if(checkexist.equals("0")){
				role.setBz("1");
				result1.add(role);
			}else if(checkexist.equals("1")){
				result4=removeList(result4,role);
				//共有的
				role.setBz("3");
				result3.add(role);
			}else if(checkexist.equals("2")){
				//在管理员角色的子角色
				//周建宁20110218
				role.setBz("2");
				result2.add(role);
				
				//role.setBz("1");
				//result1.add(role);
			}	
		}		
		
		resultht.put("rolelist1", result1);
		resultht.put("rolelist2", result2);
		resultht.put("rolelist3", result3);
		resultht.put("rolelist4", result4);
		
		
		return resultht;
	}
	
	private List removeList(List rolelist,Role role){
		for(int i=0;i<rolelist.size();i++){
			Role temp =(Role)rolelist.get(i);
			if(temp.getJsdh().equals(role.getJsdh())){
				rolelist.remove(i);
				break;
			}
		}
		return rolelist;
	}
	
	//子集 0不在，1在管理员角色，2在管理员角色的子角色
	public String checkExist(List userrolelist,Role role){
		String result="0";
		for(int i=0;i<userrolelist.size();i++){
			Role temp =(Role)userrolelist.get(i);
			//先判断是否本角色
			if(role.getJsdh().equals(temp.getJsdh())){
				result="1";
				break;
			}
		}
		
		if(result.equals("0")){
			for(int i=0;i<userrolelist.size();i++){
				Role temp =(Role)userrolelist.get(i);			
				List childlist=this.roleDao.getChildRoleList(temp.getJsdh());
				if (checkExistParent(childlist,role)){
					result="2";
					break;
				}
			}
		}
		return result;
	}
	
	
	//子集
	public boolean checkExistParent(List userrolelist,Role role){
		boolean result=false;
		for(int i=0;i<userrolelist.size();i++){
			Role temp =(Role)userrolelist.get(i);
			if(role.getJsdh().equals(temp.getJsdh())){
				result=true;
				break;
			}
		}
		return result;
	}	
	
	//在线用户数记录
	public void recZxyhxx(String flag){
		this.sysuserDao.recZxyhxx(flag);
	}
	//保存登录失败信息
	public void saveUserLoginFail(FrmUserloginfail frmUserloginfail){
		this.frmDataObjDao.setOracleData(frmUserloginfail,1);
		this.sysuserDao.saveUserLoginFail();
	}
	//获取调用KEY
	public String getOracleKey(){
		return frmDataObjDao.getKey();
	}
	
	public int getTrafficPoliceSfzmhm(String sfzmhm) throws Exception {
		return this.sysuserDao.getTrafficPoliceSfzmhm(sfzmhm);
	}
	public String getFwzbh(String yhdh) {
		return this.sysuserDao.getFwzbh(yhdh);
	}

	@Override
	public int create(SysUser sysUser) {

		return sysuserMapper.create(sysUser);
	}
}
