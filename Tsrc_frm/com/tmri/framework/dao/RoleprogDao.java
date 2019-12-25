package com.tmri.framework.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.FrmUserprog;



public interface RoleprogDao {
	public List getFunctionList(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFunctionListByJsdh(FrmUserprog frmuserprog) throws DataAccessException ;
	public List getFunctionListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFoldList(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFoldListByJsdh(FrmUserprog frmuserprog) throws DataAccessException;
	public List getFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	public List getProgramList(FrmUserprog frmuserprog) throws DataAccessException;
	public List getProgramListByJsdh2(FrmUserprog frmuserprog) throws DataAccessException;
	public List getProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	
	public List getProgramListByJsdh(String jsdh) throws DataAccessException;
	public List getUserMenu(String yhdh) throws DataAccessException;
	//zhoujn 20100524 ��ȡά���û��ص���ɫ����
	public List getRoleList(String yhdh,String whyh) throws DataAccessException;
	
	public String getXtlbmc(String xtlb);
	public String getMlmc(String mlbh);
	
	//���ɽ�ɫ������id
	public List getFunctionListByYhdh(String yhdh) throws DataAccessException;
	//��������Ȩ�޻�ȡ�û���fold
	public List getFoldListByYhdh(String yhdh) throws DataAccessException;
	//����Ȩ��
	public List getProgramListByYhdh(String yhdh) throws DataAccessException;
	
	public List getUserDeskmenu(String yhdh)throws DataAccessException;
	
	
	//��ȡ�û�����Ȩ��
	public List getUserProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	//��ȡ�û�����Ȩ��
	public List getUserFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException ;
	
	//����foldlist����һ��Ŀ¼,��ȡ���е�Ŀ¼�ṹ
	public List getAllfoldlist(List foldlist);
	public List getChildfoldlist(List foldlist,String sjmldh) ;
}
