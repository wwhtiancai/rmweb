package com.tmri.framework.service;

import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.tmri.framework.bean.FrmUserprog;

public interface RoleprogManager {
	public List getFunctionList(FrmUserprog frmuserprog);
	public List getFunctionListByJsdh(FrmUserprog frmuserprog);
	public List getFunctionListByYhdh(FrmUserprog frmuserprog);
	
	public List getFoldList(FrmUserprog frmuserprog);
	public List getFoldListByJsdh(FrmUserprog frmuserprog);
	public List getFoldListByYhdh(FrmUserprog frmuserprog);
	
	
	public List getProgramList(FrmUserprog frmuserprog);
	public List getProgramListByJsdh2(FrmUserprog frmuserprog);
	public List getProgramListByYhdh(FrmUserprog frmuserprog);
	
	public List getProgramListByJsdh(String jsdh);
	
	/**
	 * �����Է�ʽ�ϲ��˵��б������б�ͳһ��Ŀ¼�б���
	 * @param foldList Ŀ¼�б�
	 * @param proList  �˵��б�
	 * @param funList  �����б�
	 * @return
	 */
	public List unionFoldList(List foldList,List proList,List funList);
	
	//��ȡ�û��˵�
	//zhoujn 20100524
	public List getUserMenuListByYhdh(String yhdh);

	//zhoujn 20100524 ��ȡά���û��ص���ɫ����
	public List getRoleList(String yhdh,String whyh);	
	public Hashtable getXtlbUnionFoldList(List foldList,List proList,List funList);
	//����Ŀ¼������Ϣ
	public Hashtable getUnionFoldList(List foldList,List proList,List funList);
	
	public String getoutcdbh(Hashtable foldHt,List roleMenuList,String qxms,int irowsize);
	
	
	//��ȡ�û�����Ȩ��
	public List getUserProgramListByYhdh(FrmUserprog frmuserprog) throws DataAccessException;
	//��ȡ�û�����Ȩ��
	public List getUserFoldListByYhdh(FrmUserprog frmuserprog) throws DataAccessException ;
	
	
	public List getFunctionListByYhdh(String jsdh);
	public List getProgramListByYhdh(String yhdh);
	public List getFoldListByYhdh(String yhdh);
	
	//�ų����ܲ��빹��
	public Hashtable getUnionFoldList(List foldList,List proList);
	//20110223������ҳ�����ÿ�ݲ˵�
	public String getDeskoutcdbh(Hashtable foldHt,List roleMenuList,String qxms,int irowsize);
	
	public List getUserDeskmenu(String yhdh)throws Exception;
}
