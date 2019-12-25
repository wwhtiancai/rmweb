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
	 * ���¼���frm_code������Ѽ��ز��ֵ�����
	 * */
	public void initFrmCode() throws Exception;
}
