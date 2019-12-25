package com.tmri.framework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.RoleMenu;
import com.tmri.framework.bean.SysResult;
import com.tmri.framework.dao.RoleDao;
import com.tmri.framework.dao.jdbc.FrmDataObjDaoJdbc;
import com.tmri.framework.service.RoleManager;
import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.util.StringUtil;
@Service

public class RoleManagerImpl extends FrmService implements RoleManager{
	@Autowired
	RoleDao roleDao;
	@Autowired
	FrmDataObjDaoJdbc frmDataObjDao = null;

//	public void setRoleDao(RoleDao roleDao){
//		this.roleDao=roleDao;
//	}
//	public void setFrmDataObjDao(FrmDataObjDaoJdbc dataObjDao){
//		this.frmDataObjDao=dataObjDao;
//	}
	public List getRoleList(Role role) throws Exception{
		return this.roleDao.getRoleList(role);
	}
	

	/**
	 * zhoujn 20100522 ��ȡ��ɫ����
	 */
	
	public String getJsmc(String jsdh) throws Exception{
		String result=jsdh;
		Role role = this.roleDao.getRole(jsdh);
		if(role!=null){
			result=role.getJsmc();
		}
		return result;
	}
	
	/**
	 * zhoujn 20100522 ��ȡ��ɫ����
	 */
	public String getJssx(String jssx) throws Exception{
		String result="";
		if(jssx.equals("1")){
			result="Ԥ����";
		}else if(jssx.equals("2")){
			result="��ͨ";
		}else if(jssx.equals("3")){
			result="ATM����";
		}
		return result;
	}	

	public SysResult saveRole(Role frmRole,Log log){
		SysResult result=null;
		List rolemenuList=new Vector();
		String[] arrCdbh=StringUtil.splitString(frmRole.getCxdh(),'#');
		
		RoleMenu roleMenu=null;
		String xtlbs="";
		if(arrCdbh!=null){
			HashMap<String, RoleMenu> validMap = new HashMap<String, RoleMenu>();
			for(int i=0;i<arrCdbh.length;i++){
				if(arrCdbh[i]!=null&&arrCdbh[i]!=""){
					String[] menu=arrCdbh[i].split("-");
					roleMenu=new RoleMenu();
					roleMenu.setJsdh(frmRole.getJsdh());
					roleMenu.setXtlb(menu[0]);
					roleMenu.setCdbh(menu[1]);
					if(menu.length>2)
						roleMenu.setGnlb(menu[2]);
					else{
						roleMenu.setGnlb("");
					}
					if(xtlbs.indexOf(roleMenu.getXtlb())<0){
						if(xtlbs.equals("")){
							xtlbs+=roleMenu.getXtlb();
						}else{
							xtlbs+=","+roleMenu.getXtlb();
						}
					}
					
					rolemenuList.add(roleMenu);
					validMap.put(roleMenu.getXtlb() + "-" + roleMenu.getCdbh(), roleMenu);
				}
			}
			RoleMenu bkMenu = validMap.get("63-R111");
			//����У��
			if(bkMenu != null){
				if(StringUtil.checkBN(bkMenu.getGnlb())){
					if(bkMenu.getGnlb().indexOf("l011") >= 0){
						if(bkMenu.getGnlb().indexOf("l013") >= 0 || bkMenu.getGnlb().indexOf("l014") >= 0){
							SysResult r = new SysResult();
							r.setRetcode("-1");
							r.setRetdesc("�벻Ҫ���������롢��ˡ�����ͬʱ��Ȩ��һ����ɫ��");
							return r;
//							throw new RuntimeException("�벻Ҫ���������롢��ˡ�����ͬʱ��Ȩ��һ����ɫ��");
						}
					}
					if(bkMenu.getGnlb().indexOf("l013") >= 0 && bkMenu.getGnlb().indexOf("l014") >= 0){
						SysResult r = new SysResult();
						r.setRetcode("-1");
						r.setRetdesc("�벻Ҫ��������ˡ�����ͬʱ��Ȩ��һ����ɫ��");
						return r;
//						throw new RuntimeException("�벻Ҫ��������ˡ�����ͬʱ��Ȩ��һ����ɫ��");
					}
				}
			}
			if(validMap.containsKey("61-A010") && validMap.containsKey("61-A011")){
				SysResult r = new SysResult();
				r.setRetcode("-1");
				r.setRetdesc("�벻Ҫ�����ڱ��������ͬʱ��Ȩ��һ����ɫ��");
				return r;
//				throw new RuntimeException("�벻Ҫ�����ڱ��������ͬʱ��Ȩ��һ����ɫ��");
			}
		}
		frmRole.setXtlb(xtlbs);
		result=frmDataObjDao.setOracleData(frmRole,1);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=roleDao.saveRole(frmRole,rolemenuList);
		return result;
	}

	
	public SysResult saveUsermenuCancel(String glbm,Role frmRole,Log log) throws Exception  {
		SysResult result=null;
		List rolemenuList=new Vector();
		String[] arrCdbh=StringUtil.splitString(frmRole.getCxdh(),'#');
		RoleMenu roleMenu=null;
		String xtlbs="";
		if(arrCdbh!=null){
			for(int i=0;i<arrCdbh.length;i++){
				if(arrCdbh[i]!=null&&arrCdbh[i]!=""){
					String[] menu=arrCdbh[i].split("-");
					roleMenu=new RoleMenu();
					roleMenu.setJsdh(frmRole.getJsdh());
					roleMenu.setXtlb(menu[0]);
					roleMenu.setCdbh(menu[1]);
					if(menu.length>2)
						roleMenu.setGnlb(menu[2]);
					else{
						roleMenu.setGnlb("");
					}
					if(xtlbs.indexOf(roleMenu.getXtlb())<0){
						if(xtlbs.equals("")){
							xtlbs+=roleMenu.getXtlb();
						}else{
							xtlbs+=","+roleMenu.getXtlb();
						}
					}
					rolemenuList.add(roleMenu);
				}
			}
		}
		result=frmDataObjDao.setOracleData(log,1);
		if(result.getFlag()==0){
			return result;
		}
		result=roleDao.saveUsermenuCancel(glbm,rolemenuList);
		return result;
	}
	
	
	public SysResult removeRole(Role frmRole,Log log){
		SysResult result=null;
		result=frmDataObjDao.setOracleData(frmRole,0);
		if(result.getFlag()==0){
			return result;
		}
		result=frmDataObjDao.setOracleData(log,0);
		if(result.getFlag()==0){
			return result;
		}
		result=roleDao.removeRole();
		return result;
	}

	public Role getRole(String jsdh){
		return this.roleDao.getRole(jsdh);
	}

	public List getRoleListByPagesize(Role frmRole,PageController controller) throws Exception{
		return this.roleDao.getRoleListByPagesize(frmRole,controller);
	}

	public RoleDao getRoleDao(){
		return roleDao;
	}
	
	public List getUsergrantroleList(String yhdh) throws Exception {
		return this.roleDao.getUsergrantroleList(yhdh);
	}
	
	
	public List getRoleList(String jsdh)throws Exception{
		return this.roleDao.getRoleList(jsdh);
	}	
	
	//��ȡ��ɫ����
	//��ȡ���û�����ӵ�н�ɫ����������1������3
	public String getJslx(String jsdh,String yhdh) throws Exception{
		return this.roleDao.getJslx(jsdh, yhdh);
	}

}
