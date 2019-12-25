package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.tmri.framework.bean.Fold;
import com.tmri.share.frm.bean.Function;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;

/**
 * ���� Ŀ¼ �洢 frm_fold frm_program �ӿ�
 * @author long
 * 
 */
public interface ProgramFoldDao{
	public List getFoldList2(SysUser sysUser,HashMap paras);

	public List getFoldList(String yhdh);

	public List getTopFoldList();

	public Fold getFold(String mldh);

	public List getProgramList(SysUser sysuser,HashMap paras);

	public List getUserDeskList(String yhdh,Hashtable rightsobj) throws SQLException;
	
	public String getGnmc(String xtlb,String cdbh,String gnid);
	
	public String getGnmc(String xtlb,String gnid);
	
	public Function getOneFunction(String xtlb,String cdbh,String gnid);
	
	//zhoujn 20110311 ���ݹ������ƻ�ȡ�б�
	public List getProgramList(String gnmc) throws Exception ;
	
	public Program getOneProgram(String xtlb,String cdbh);
}
