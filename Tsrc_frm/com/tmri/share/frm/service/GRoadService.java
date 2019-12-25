package com.tmri.share.frm.service;

import java.util.List;

import com.tmri.share.frm.bean.Department;
import com.tmri.share.frm.bean.Roaditem;

public interface GRoadService {
	/** 取得道路列表 */
	public List<Roaditem> getRoads() throws Exception;
	/** 取得道路封装类 */
	public Roaditem getRoad(String dldm,String glbm) throws Exception;
	/** 取得道路名称 */
	public String getRoadValue(String dldm,String glbm) throws Exception;
	/** 根据部门中的管理行政区划取得道路列表 */
	public List<Roaditem> getRoadsByDistrict(Department department) throws Exception;
	
	public List<Roaditem> getRoadsByDistrict(Department department,String glxzqh) throws Exception;	
	/** 根据部门中的管理部门取得道路列表 */
	public List<Roaditem> getRoadsByManagement(Department departmen) throws Exception;
	public List getRoadsegList(String glbm,String dldm) throws Exception;
	
	/**
	 * 根据道路代码和管理部门从道路表获取道路名称,不从内存中获取
	 * @param dldm		道路代码
	 * @param glbm		管理部门
	 * @throws Exception
	 */
	public String getRoaditemValue(String dldm, String glbm) throws Exception;
	public List<Roaditem> getRoaditemList(String dldm) throws Exception;
}
