package com.tmri.share.frm.dao;

import java.util.Map;


public interface JkxlhCheckDao extends FrmDao {
	public Map<String, Object>  queryControlByJklxh(String jkxlh);
	
	public Map<String, Object>  queryJkxlhCreator(Map<String, Object> para);
	
	public Map<String, Object> queryControlJyw(Map<String, Object> appformBean);
	
	public Map<String, Object> queryHf(Map<String, Object> appformBean, String jkid);
	
	public Map<String, Object> getContent(String jkid);
}
