package com.tmri.framework.service;

import java.util.List;

import com.tmri.framework.bean.Log;
import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.util.PageController;

public interface RoleManager {
	public Role getRole(String jsdh);
	
	public List getRoleList(Role role) throws Exception ;
	
	public List getRoleListByPagesize(Role frmRole,PageController controller) throws Exception ;
	
	public SysResult saveRole(Role frmRole,Log log) throws Exception;

	public SysResult removeRole(Role frmRole,Log log) throws Exception;
	
	public List getUsergrantroleList(String yhdh) throws Exception ;
	
	/**
	 * zhoujn 20100522 ��ȡ��ɫ����
	 */
	public String getJsmc(String jsdh) throws Exception;
	/**
	 * zhoujn 20100522 ��ȡ��ɫ����
	 */
	public String getJssx(String jssx) throws Exception;	
	
	public List getRoleList(String jsdh)throws Exception;
	
	//��ȡ��ɫ����
	//��ȡ���û�����ӵ�н�ɫ����������1������3
	public String getJslx(String jsdh,String yhdh) throws Exception;
	
	public SysResult saveUsermenuCancel(String glbm,Role frmRole,Log log) throws Exception;
}
