package com.tmri.share.ora.converter;

import java.sql.Timestamp;

import org.apache.commons.beanutils.Converter;

public class DatetimeConverter implements Converter {
    /**
     * Double×ª»»Æ÷.
     * @param type Class
     * @param value Object
     * return Double Object.
     */
    public Object convert(Class type,Object value){
        if(value == null){
            return null;
        }else {
        	String format;
			return Timestamp.valueOf(value.toString());
        }
    }
}
