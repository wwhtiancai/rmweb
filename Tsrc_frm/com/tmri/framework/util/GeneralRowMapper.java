package com.tmri.framework.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
/**
 * �Զ���ͨ��mapper,���ڷ�װ������������б�
 * @author Administrator
 *
 */
public class GeneralRowMapper implements org.springframework.jdbc.core.RowMapper 
{
	private Class targartObjectClass;
    public GeneralRowMapper(Class objectClass){
    	this.targartObjectClass=objectClass;
    }
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Object t=null;
		ResultSetMetaData crmd=rs.getMetaData();
	    Map map=new HashMap();
		for (int j = 1; j <= crmd.getColumnCount(); j++) {					
		    map.put(crmd.getColumnName(j).toLowerCase(), rs.getObject(crmd.getColumnName(j).toLowerCase()));
		}
		try {
			t=targartObjectClass.newInstance();
			BeanUtils.populate(t, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

}
