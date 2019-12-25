package com.tmri.cache.service;


public interface WebCacheService {
	
	public void initDepartments() throws Exception;
	public void initPlsDepartments() throws Exception;

	public void initProgramRelation() throws Exception;
	public void initFuntions() throws Exception;
	
	public void initRoaditems() throws Exception;
	public void initSysParaValue() throws Exception;
	

	public void initSysParas() throws Exception;
	
	/**
	 * 重新加载frm_code代码表已加载部分的内容
	 * */
	public void initFrmCode() throws Exception;
}
