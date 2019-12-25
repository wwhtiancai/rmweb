package com.tmri.share.ora.converter;

import org.apache.commons.beanutils.Converter;

public class StringConverter	implements Converter {
    /**
     * String ×ª»»Æ÷.
     * @param type Class
     * @param value Object
     * return Double Object.
     */
    public Object convert(Class type,Object value){
        if(value == null){
            return "";
        }else if(value.equals("null")){
        	 return "";
        }else {
        	return value;
        }
    }
}
