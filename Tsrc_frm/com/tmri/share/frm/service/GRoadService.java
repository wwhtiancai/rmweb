package com.tmri.share.frm.service;

import java.util.List;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Roaditem;

public interface GRoadService {
	/** ȡ�õ�·�б� */
	public List<Roaditem> getRoads() throws Exception;
	/** ȡ�õ�·��װ�� */
	public Roaditem getRoad(String dldm,String glbm) throws Exception;
	/** ȡ�õ�·���� */
	public String getRoadValue(String dldm,String glbm) throws Exception;
	/** ���ݲ����еĹ�����������ȡ�õ�·�б� */
	public List<Roaditem> getRoadsByDistrict(Department department) throws Exception;
	
	public List<Roaditem> getRoadsByDistrict(Department department,String glxzqh) throws Exception;	
	/** ���ݲ����еĹ�����ȡ�õ�·�б� */
	public List<Roaditem> getRoadsByManagement(Department departmen) throws Exception;
	public List getRoadsegList(String glbm,String dldm) throws Exception;
	
	/**
	 * ���ݵ�·����͹����Ŵӵ�·���ȡ��·����,�����ڴ��л�ȡ
	 * @param dldm		��·����
	 * @param glbm		������
	 * @throws Exception
	 */
	public String getRoaditemValue(String dldm, String glbm) throws Exception;
	public List<Roaditem> getRoaditemList(String dldm) throws Exception;
}
