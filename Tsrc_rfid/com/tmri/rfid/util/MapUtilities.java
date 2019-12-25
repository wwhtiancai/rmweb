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
  
                // ����class����  
                if (!key.equals("class")) {  
                    // �õ�property��Ӧ��getter����  
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
                    // �õ�property��Ӧ��setter����  
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
        //ʵ������
    	T entity = (T)clazz.newInstance();
    	Set<String> keys = map.keySet();
    	//����map ��ֵ
    	for(String key:keys){
    		String fieldName = key;
    		//�ж���sql ����hql���صĽ��
    		if(key.equals(key.toUpperCase())){
    			//��ȡ���������
    			Field[] fields = clazz.getDeclaredFields();
    			for(Field field: fields){
    				if(field.getName().toUpperCase().equals(key)) fieldName=field.getName();
    				break;
    			}
    		}
    		//���ø�ֵ
    		try {
    			//����������  clazz.getField(fieldName)
    			Class<?> paramClass = clazz.getDeclaredField(fieldName).getType();
    			//ƴװset��������
    			String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
    			//�������ƻ�ȡ����
    			Method method = clazz.getMethod(methodName, paramClass);
    			//����invokeִ�и�ֵ
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
