package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.Role;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.util.PageController;

public interface RoleDao {
	
	public List getRoleList(Role frmRole) throws SQLException;
	public SysResult saveRole(Role role,List rolemenuList);
	public List getRoleListByPagesize(Role frmRole,PageController controller) throws Exception ;
	public SysResult removeRole();
	public List getUsergrantroleList(String yhdh) throws Exception ;
	
	//zhoujn 20100522 ��ȡ��ɫ����
	public Role getRole(String jsdh) throws DataAccessException;
	
	//���ݽ�ɫ���Ż�ȡ���н�ɫ
	public List getRoleList(String jsdh) throws Exception;
	
	//��ȡ���ӽ�ɫ
	public List getChildRoleList(String jsdh);
	
	//��ȡ��ɫ����
	//��ȡ���û�����ӵ�н�ɫ����������1������3
	public String getJslx(String jsdh,String yhdh) throws Exception;
	
	public SysResult saveUsermenuCancel(String glbm,List rolemenuList)throws Exception;
}
