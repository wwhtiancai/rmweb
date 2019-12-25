package com.tmri.share.frm.dao;

import java.util.List;

import com.tmri.share.frm.bean.Roaditem;

public interface GRoadDao {
	/** ȡ�õ�·�б� */
	public List<Roaditem> getRoads() throws Exception;
	/** ȡ�õ�·��װ�� */
	public Roaditem getRoad(String dldm,String glbm) throws Exception;
	/** ȡ�õ�·���� */
	public String getRoadValue(String dldm,String glbm) throws Exception;
	
	public String getRoadSegName(String glbm, String dldm, String lkh) throws Exception;
	public List getRoadsegList(String glbm,String dldm) throws Exception;
	
	/**
	 * ���ݵ�·����͹����Ŵӵ�·���ȡ��·,�����ڴ��л�ȡ
	 * @param dldm		��·����
	 * @param glbm		������
	 * @throws Exception
	 */
	public Roaditem getRoaditem(String dldm, String glbm) throws Exception;
	public List<Roaditem> getRoaditemList(String dldm) throws Exception;
}
