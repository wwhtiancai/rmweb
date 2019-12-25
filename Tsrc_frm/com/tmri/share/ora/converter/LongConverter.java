package com.tmri.share.ora.converter;

import org.apache.commons.beanutils.Converter;

public class LongConverter implements Converter {
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
            return new Long(value.toString());
        }
    }
}


