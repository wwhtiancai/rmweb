package com.tmri.framework.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.tmri.share.frm.bean.SysUser;

/**
 * ���� Ŀ¼����
 * 
 * @author long
 * 
 */
public interface ProgramFoldManager{

  public HashMap getFoldMenuStr(SysUser sysUser,HashMap paras);	

	public List getUserDeskList(String yhdh,Hashtable rightsobj) throws Exception;

	public List getFoldList(String yhdh) throws Exception;

	public List getTopFoldList() throws Exception;
	
	public List getProgramList(SysUser sysUser,HashMap paras);

	//zhoujn 20110311 ���ݹ������ƻ�ȡ�б�
	public List getProgramList(String gnmc) throws Exception ;
}
