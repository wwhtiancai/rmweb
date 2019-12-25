package com.tmri.rfid.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.tmri.share.frm.util.DateUtil;
/**
 * Created by Joey on 2015/9/15.
 */
public class MapUtilities {

    public static Map<Object, Object> buildMap(Object ... parameters) {
        int size = parameters.length / 2;
        Map<Object, Object> result = new HashMap<Object, Object>(size);
        for (int i = 0; i < size; i++) {
            result.put(parameters[i * 2], parameters[i * 2 + 1]);
        }
        return result;
    }
    
    public static Map<String, Object> transBean2Map(Object obj) {  
    	  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  

    
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
    	  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  
    
    public static <T> T map2Bean(T t,Map map) throws Exception{
        Class clazz = t.getClass();
        //实例化类
    	T entity = (T)clazz.newInstance();
    	Set<String> keys = map.keySet();
    	//变量map 赋值
    	for(String key:keys){
    		String fieldName = key;
    		//判断是sql 还是hql返回的结果
    		if(key.equals(key.toUpperCase())){
    			//获取所有域变量
    			Field[] fields = clazz.getDeclaredFields();
    			for(Field field: fields){
    				if(field.getName().toUpperCase().equals(key)) fieldName=field.getName();
    				break;
    			}
    		}
    		//设置赋值
    		try {
    			//参数的类型  clazz.getField(fieldName)
    			Class<?> paramClass = clazz.getDeclaredField(fieldName).getType();
    			//拼装set方法名称
    			String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
    			//根据名称获取方法
    			Method method = clazz.getMethod(methodName, paramClass);
    			//调用invoke执行赋值
    			if(paramClass.getName().equals("java.util.Date")){
    				method.invoke(entity, DateUtil.getDate(map.get(key).toString(), "yyyy-MM-dd HH:mm:ss"));
    			}else{
    				method.invoke(entity, map.get(key));
    			}
    		} catch (Exception e) {
    			//NoSuchMethod
    		}
    	}
        
    	return entity;
    }
}
