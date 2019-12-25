package com.tmri.share.frm.dao;

import java.util.List;

import com.tmri.share.frm.bean.Roaditem;

public interface GRoadDao {
	/** 取得道路列表 */
	public List<Roaditem> getRoads() throws Exception;
	/** 取得道路封装类 */
	public Roaditem getRoad(String dldm,String glbm) throws Exception;
	/** 取得道路名称 */
	public String getRoadValue(String dldm,String glbm) throws Exception;
	
	public String getRoadSegName(String glbm, String dldm, String lkh) throws Exception;
	public List getRoadsegList(String glbm,String dldm) throws Exception;
	
	/**
	 * 根据道路代码和管理部门从道路表获取道路,不从内存中获取
	 * @param dldm		道路代码
	 * @param glbm		管理部门
	 * @throws Exception
	 */
	public Roaditem getRoaditem(String dldm, String glbm) throws Exception;
	public List<Roaditem> getRoaditemList(String dldm) throws Exception;
}
