package com.tmri.share.ora;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
/**
 * �Զ���ͨ��mapper,���ڷ�װ������������б�
 * @author Administrator
 *
 */
public class SheetRowMapper implements RowMapper 
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData crmd=rs.getMetaData();
	    Map<String,Object> map=new HashMap<String,Object>();
		for (int j = 1; j <= crmd.getColumnCount(); j++) {					
		    map.put(crmd.getColumnName(j).toLowerCase(), rs.getString(crmd.getColumnName(j).toLowerCase()));
		}
		return map;
	}

}
