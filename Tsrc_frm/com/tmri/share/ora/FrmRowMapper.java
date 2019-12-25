package com.tmri.share.ora;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.math.BigDecimal;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean;


import org.springframework.core.CollectionFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.tmri.share.ora.converter.BigDecimalConverter;
import com.tmri.share.ora.converter.DatetimeConverter;
import com.tmri.share.ora.converter.DoubleConverter;
import com.tmri.share.ora.converter.IntegerConverter;
import com.tmri.share.ora.converter.LongConverter;
import com.tmri.share.ora.converter.StringConverter;

public class FrmRowMapper implements RowMapper{
	private Class requiredType;

	public FrmRowMapper() {
	}
	
	public FrmRowMapper(Class requiredType) {
		this.requiredType = requiredType;
	}

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Object objIns = null; 
		try {
			objIns = requiredType.newInstance();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			Map mapOfColValues = createColumnMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				String key = getColumnKey(rsmd.getColumnName(i));
				Object obj = getColumnValue(rs, i);
				mapOfColValues.put(key, obj);
			}
			ConvertUtilsBean convertUtils = new ConvertUtilsBean();   
			DoubleConverter doubleConverter = new DoubleConverter();  
			LongConverter longConverter = new LongConverter();
			StringConverter stringConverter = new StringConverter();
			DatetimeConverter datetimeConverter = new DatetimeConverter();
			IntegerConverter intConverter = new IntegerConverter();
			BigDecimalConverter bigDecimalConverter = new BigDecimalConverter();
			convertUtils.register(doubleConverter,Double.class);
			convertUtils.register(longConverter,Long.class);
			convertUtils.register(stringConverter,String.class);
			convertUtils.register(intConverter,Integer.class);
			convertUtils.register(bigDecimalConverter,BigDecimal.class);
			convertUtils.register(datetimeConverter,Timestamp.class);
			
			BeanUtilsBean beanUtils=new BeanUtilsBean(convertUtils,new PropertyUtilsBean());
			beanUtils.populate(objIns, mapOfColValues);	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return objIns;

	}
	
	protected Map createColumnMap(int columnCount) {
		return CollectionFactory.createLinkedCaseInsensitiveMapIfPossible(columnCount);
	}
	
	protected String getColumnKey(String columnName) {
		return columnName.toLowerCase();
	}
	
	protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
		return JdbcUtils.getResultSetValue(rs, index);
	}	
}
