package com.tmri.share.frm.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Digester;

import com.tmri.share.frm.bean.FrmResultBean;

public class BeanUtil{
	
	
	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	public static void transMap2Bean(Map<String, Object> map,Object obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					if(setter!=null){
						setter.invoke(obj, new Object[]{value});
					}
				}

			}

		} catch (Exception e) {
			System.out.println("transMap2Bean Error " + e);
		}
	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					if(getter!=null){
						Object value = getter.invoke(obj);
						map.put(key, value);
					}
				}	
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}
	
	public static HashMap<String, Object> MapToUpperMap(HashMap hashMap){
		HashMap result=new HashMap();
		Iterator  iterator=hashMap.keySet().iterator();
		while (iterator.hasNext()){
			Object key=iterator.next();
			Object value=hashMap.get(key);
			result.put(((String)key).toUpperCase(),value);
		}
		return result;
	}
	
	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return
	 * @throws
	 */
	public static HashMap<String, Object> BeanToUpperMap(Object bean)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Class type = bean.getClass();
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName.toUpperCase(), result);
				} else {
					returnMap.put(propertyName.toUpperCase(), "");
				}
			}
		}
		return returnMap;
	}

	public static void beansTrimnull(Object sourceObject,Object aimObject){
		try{
			// 依据Bean产生一个相关的BeanInfo类
			BeanInfo bInfoObject=Introspector.getBeanInfo(sourceObject.getClass(),sourceObject.getClass().getSuperclass());
			MethodDescriptor[] mDescArray=bInfoObject.getMethodDescriptors();
			for(int i=0;i<mDescArray.length;i++){
				// 获得该方法对象
				Method methodObj=mDescArray[i].getMethod();
				if(methodObj.getName().length()>=3){
					if(methodObj.getName().startsWith("get")){
						if(methodObj.getReturnType().equals(String.class)){
							try{
								String value=(String)methodObj.invoke(sourceObject,null);
								// 将calbject转化为字符串
								if(value!=null&&value.equals("|null|")){
									String colValue="";
									invokeset(aimObject,methodObj.getName().substring(3),colValue);
								}
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}

					}
				}
			}
		}catch(Exception e){
			System.out.println("异常："+e.getMessage());
		}
	}
	
	//通过反射获取信息
	//增加详细映射
	public static String getValue(Object sourceObject,String method){
		String m_curValue = "";
		try{
			Class classObject = sourceObject.getClass();
			String methName = "get" + method.substring(0, 1).toUpperCase()
					+ method.substring(1);
			Class parameters[] = new Class[0];
			Object objvarargs[] = new Object[0];
			Method meth = classObject.getMethod(methName, parameters);
			m_curValue = meth.invoke(sourceObject, objvarargs).toString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return m_curValue;
	}

	// zhoujn 20100702
	public static void beans2aCalendar(Object sourceObject,Object aimObject){
		try{
			// 依据Bean产生一个相关的BeanInfo类
			BeanInfo bInfoObject=Introspector.getBeanInfo(sourceObject.getClass(),sourceObject.getClass().getSuperclass());
			/*
			 * BeanInfo.getMethodDescriptors()
			 * 用于获取该Bean中的所有允许公开的成员方法，以MethodDescriptor数组的形式返回
			 * 
			 * MethodDescriptor类 用于记载一个成员方法的所有信息 MethodDescriptor.getName() 获得该方法的方法名字
			 * MethodDescriptor.getMethod() 获得该方法的方法对象（Method类）
			 * 
			 * Method类 记载一个具体的的方法的所有信息 Method.getParameterTypes()
			 * 获得该方法所用到的所有参数，以Class数组的形式返回
			 * 
			 * Class..getName() 获得该类型的名字
			 */
			MethodDescriptor[] mDescArray=bInfoObject.getMethodDescriptors();
			for(int i=0;i<mDescArray.length;i++){
				// 获得该方法对象
				Method methodObj=mDescArray[i].getMethod();
				if(methodObj.getName().startsWith("get")){
					if(methodObj.getReturnType().equals(Calendar.class)){
						try{
							Calendar calobject=(Calendar)methodObj.invoke(sourceObject,null);
							// 将calbject转化为字符串
							if(calobject!=null){
								String colValue=getDatetime(calobject);
								invokeset(aimObject,methodObj.getName().substring(3),colValue);
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}

				}
			}
		}catch(Exception e){
			System.out.println("异常："+e.getMessage());
		}
	}

	// 处理国通的Calendar的转换为String
	public static Object beans2a(Object sourceObject,Object aimObject){
		try{
			beanValueCopy(sourceObject,aimObject);
			beans2aCalendar(sourceObject,aimObject);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return aimObject;
	}
	
	//20140313处理字符串
	//日期字段
	public static Object beanValueCopy(Object sourceObject,Object aimObject,String datefields){
		try{
			BeanUtils.copyProperties(aimObject,sourceObject);
			beans2Date(sourceObject,aimObject,datefields);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return aimObject;
	} 
	
	//格式化输出
	private static String getDate(String datetime){
		String result=datetime;
		if(datetime.length()>=10){
			result=datetime.substring(0,10);
		}
		return result;
	}
	
	//datefields 例如 yxqz,cfrq
	//yxqz 例如yyyy-mm-dd hh24:mi:ss.s
	
	public static void beans2Date(Object sourceObject,Object aimObject,String datefields){
		try{
			String[] fields=datefields.split(",");

			BeanInfo bInfoObject=Introspector.getBeanInfo(sourceObject.getClass(),sourceObject.getClass().getSuperclass());
			MethodDescriptor[] mDescArray=bInfoObject.getMethodDescriptors();
			for(int i=0;i<mDescArray.length;i++){
				// 获得该方法对象
				Method methodObj=mDescArray[i].getMethod();
				if(methodObj.getName().startsWith("get")){
					//如果method在fields,则格式化该字符串
					if(StringUtil.checkExistArray(fields, methodObj.getName().substring(3))){
						String fieldvalue=(String)methodObj.invoke(sourceObject,null);
						if(fieldvalue!=null){
							fieldvalue=getDate(fieldvalue);
							invokeset(aimObject,methodObj.getName().substring(3),fieldvalue);
						}
					}
				}
			}
		}catch(Exception e){
			System.out.println("异常："+e.getMessage());
		}
	}
	
	
	public static String getDatetime(Calendar calobject){
		Date date=calobject.getTime();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//屏蔽原因是，举例说明出生日期往前挪一天
		//simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		return simpleDateFormat.format(date);
	}

	// 触发set方法
	public static void invokeset(Object aimObject,String colName,String colValue){
		try{
			Object values[]={colValue};
			Method meth=aimObject.getClass().getMethod("set"+colName,String.class);
			meth.invoke(aimObject,values);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 复制Bean的属性值
	 * 
	 * @param sourceObject Object 源对象
	 * @param aimObject Object 目标对象
	 * @return Object by 武红斌
	 */
	public static Object beanValueCopy(Object sourceObject,Object aimObject) throws Exception{
		try{
			BeanUtils.copyProperties(aimObject,sourceObject);
		}catch(InvocationTargetException ex){
			throw new Exception(ex.getMessage());
		}catch(IllegalAccessException ex){
			throw new Exception(ex.getMessage());
		}
		return aimObject;
	}

	/**
	 * 将Map中的主键统一成小写
	 * @param dataMap
	 * @return
	 */
	public static Map lowerCaseMapKey(Map dataMap){
		HashMap map=new HashMap();
		Set set=dataMap.keySet();
		Iterator i=set.iterator();
		while(i.hasNext()){
			Object key=i.next();
			Object value=dataMap.get(key);
			map.put(((String)key).toLowerCase(),value);
		}
		return map;
	}

	/**
	 * 将Map中的值封装到Bean中
	 * @param dataMap
	 * @param aimObject
	 * @return
	 */
	public static Object popuBeanValueByMap(Map dataMap,Object aimObject){
		try{
			BeanUtils.populate(aimObject,dataMap);
		}catch(InvocationTargetException ex){
			ex.printStackTrace();
		}catch(IllegalAccessException ex){
			ex.printStackTrace();
		}
		return aimObject;
	}

	/**
	 * 赋值bean的值
	 * @param _className
	 * @param sourceObj
	 * @return
	 * @throws Exception
	 */
	public static Object beanValueCopy(String _className,Object sourceObj) throws Exception{
		Object targetObj=null;
		try{
			if(sourceObj!=null){
				targetObj=Class.forName(_className).newInstance(); // 创建类实例
				org.apache.commons.beanutils.PropertyUtils.copyProperties(targetObj,sourceObj);
			}else{
				targetObj=null;
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		return targetObj;
	}

	/**
	 * 将bean的值输出为JSP页面的Input控件
	 * @param bean
	 * @param isValue
	 * @return
	 * @throws Exception
	 */
	public static String bean2Input(Object bean,String isValue) throws Exception{
		StringBuffer buf=new StringBuffer();
		try{

			Map p=BeanUtils.describe(bean);
			Set s=p.keySet();
			Iterator i=s.iterator();
			while(i.hasNext()){
				Object key=i.next();
				if(key.equals("class")){
				}else{
					Object value=p.get(key);
					if(value==null||value.toString().toLowerCase().equals("null")){
						value="";
					}
					if(isValue.equals("1")){
						buf.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>\n");
					}else{
						buf.append("<input type=\"hidden\" name=\""+key+"\" value=\"\"/>\n");
					}
				}
			}
		}catch(Exception ex){
			throw new Exception("Error occurred while trans bean to Input node:"+ex.getMessage());
		}
		return buf.toString();
	}

	/**
	 * 将bean的值输出为JSP页面的Input控件
	 * @param bean
	 * @param isValue
	 * @param exceptFields
	 * @return
	 * @throws Exception
	 */
	public static String bean2Input(Object bean,String isValue,String exceptFields) throws Exception{
		StringBuffer buf=new StringBuffer();
		String _objName="";
		String _exceptFields=","+exceptFields+",";
		try{
			Map p=BeanUtils.describe(bean);
			Set s=p.keySet();
			Iterator i=s.iterator();
			while(i.hasNext()){
				Object key=i.next();
				_objName=","+key.toString()+",";
				if(!(key.equals("class"))){
					if(!(_exceptFields.indexOf(_objName)>=0)){
						Object value=p.get(key);
						if(value==null||value.toString().toLowerCase().equals("null")){
							value="";
						}
						if(isValue.equals("1")){
							buf.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>\n");
						}else{
							buf.append("<input type=\"hidden\" name=\""+key+"\" value=\"\"/>\n");
						}
					}
				}
			}
		}catch(Exception ex){
			throw new Exception("Error occurred while trans bean to Input node:"+ex.getMessage());
		}
		return buf.toString();
	}

	/**
	 * 将bean的值输出为JSP页面的Input控件
	 * @param bean
	 * @param isValue
	 * @param exceptFields
	 * @param _formFieldAppendName
	 * @return
	 * @throws Exception
	 */
	public static String bean2Input(Object bean,String isValue,String exceptFields,String _formFieldAppendName) throws Exception{
		StringBuffer buf=new StringBuffer();
		String _objName="";
		String _exceptFields=","+exceptFields+",";
		try{
			Map p=BeanUtils.describe(bean);
			Set s=p.keySet();
			Iterator i=s.iterator();
			while(i.hasNext()){
				Object key=i.next();
				_objName=","+key.toString()+",";
				if(!(key.equals("class"))){

					if(!(_exceptFields.indexOf(_objName)>=0)){
						Object value=p.get(key);
						if(value==null||value.toString().toLowerCase().equals("null")){
							value="";
						}
						if(key.toString().toLowerCase().equals("sfzmhm")){

						}
						if(isValue.equals("1")){
							buf.append("<input type=\"hidden\" name=\""+_formFieldAppendName+key.toString().toLowerCase()+"\" value=\""+value+"\"/>\n");
						}else{
							buf.append("<input type=\"hidden\" name=\""+_formFieldAppendName+key.toString().toLowerCase()+"\" value=\"\"/>\n");
						}
					}
				}
			}
		}catch(Exception ex){
			throw new Exception("Error occurred while trans bean to Input node:"+ex.getMessage());
		}
		return buf.toString();
	}

	/**
	 * 生成清空JSP页面的input控件的值的JS脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @return
	 */
	public static String clearPageInputValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					if(isParent){
						buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");

					}else{
						buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 生成清空JSP页面的input控件的值的JS脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @return
	 */
	public static String clearPageInputValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent,boolean isLeft){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					if(isParent){
						if(isLeft){
							buf.append("parent.document."+_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".value='';\n");
						}else{
							buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");
						}

					}else{
						if(isLeft){
							buf.append(_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".value='';\n");
						}else{
							buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");
						}
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 生成清空JSP页面的input控件的值的JS脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @return
	 */
	public static String clearPageSpanValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					if(isParent){
						buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");

					}else{
						buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 生成清空JSP页面的input控件的值的JS脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @return
	 */
	public static String clearPageSpanValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent,boolean isLeft){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					if(isParent){
						if(isLeft){
							buf.append("parent.document."+_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".innerHTML='';\n");
						}else{
							buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");
						}

					}else{
						if(isLeft){
							buf.append(_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".innerHTML='';\n");
						}else{
							buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");
						}
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();

	}

	/**
	 * 生成JSP页面赋值脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @param isEmpty
	 * @return
	 */
	public static String setPageInputValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent,boolean isEmpty){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					Object value=p.get(key);
					String m_curValue=(String)value;
					m_curValue=ReplaceString(m_curValue,"\'","\\\'");
					m_curValue=ReplaceString(m_curValue,"\"","\\\"");
					m_curValue=ReplaceString(m_curValue,"\n","\\n");
					if(m_curValue==null||m_curValue.toLowerCase().equals("null")){
						m_curValue="";
					}
					if(isParent){
						if(isEmpty){
							buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");
						}else{
							buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';\n");
						}

					}else{
						if(isEmpty){
							buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");
						}else{
							buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';\n");
						}
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 生成JSP页面赋值脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @param isEmpty
	 * @return
	 */
	public static String setPageInputValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent,boolean isEmpty,boolean isLeft){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					Object value=p.get(key);
					String m_curValue=(String)value;
					m_curValue=ReplaceString(m_curValue,"\'","\\\'");
					m_curValue=ReplaceString(m_curValue,"\"","\\\"");
					m_curValue=ReplaceString(m_curValue,"\n","\\n");
					if(m_curValue==null||m_curValue.toLowerCase().equals("null")){
						m_curValue="";
					}
					if(isParent){
						if(isEmpty){
							if(isLeft){
								buf.append("parent.document."+_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".value='';\n");
							}else{
								buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");
							}
						}else{
							if(isLeft){
								buf.append("parent.document."+_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".value='"+m_curValue+"';\n");
							}else{
								buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';\n");
							}
						}

					}else{
						if(isEmpty){
							if(isLeft){
								buf.append(_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".value='';\n");
							}else{
								buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='';\n");
							}
						}else{
							if(isLeft){
								buf.append(_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".value='"+m_curValue+"';\n");
							}else{
								buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';\n");
							}
						}
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 生成清空JSP页面的input控件的值的JS脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @param isEmpty
	 * @return
	 */
	public static String setPageSpanValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent,boolean isEmpty){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					Object value=p.get(key);
					String m_curValue=(String)value;
					m_curValue=ReplaceString(m_curValue,"\'","\\\'");
					m_curValue=ReplaceString(m_curValue,"\"","\\\"");
					m_curValue=ReplaceString(m_curValue,"\n","\\n");
					if(m_curValue==null||m_curValue.toLowerCase().equals("null")){
						m_curValue="";
					}
					if(isParent){
						if(isEmpty){
							buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");
						}else{
							buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='"+m_curValue+"';\n");
						}

					}else{
						if(isEmpty){
							buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");
						}else{
							buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='"+m_curValue+"';\n");
						}
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 生成清空JSP页面的input控件的值的JS脚本
	 * @param _obj
	 * @param _formName
	 * @param _formFieldAppendName
	 * @param isParent
	 * @param isEmpty
	 * @return
	 */
	public static String setPageSpanValue(Object _obj,String _formName,String _formFieldAppendName,boolean isParent,boolean isEmpty,boolean isLeft){
		StringBuffer buf=new StringBuffer();
		try{
			Map p=BeanUtils.describe(_obj);
			Set s=p.keySet();
			Iterator k=s.iterator();
			while(k.hasNext()){
				buf.append("try{\n");
				Object key=k.next();
				if(key.equals("class")){
				}else{
					Object value=p.get(key);
					String m_curValue=(String)value;
					m_curValue=ReplaceString(m_curValue,"\'","\\\'");
					m_curValue=ReplaceString(m_curValue,"\"","\\\"");
					m_curValue=ReplaceString(m_curValue,"\n","\\n");
					if(m_curValue==null||m_curValue.toLowerCase().equals("null")){
						m_curValue="";
					}
					if(isParent){
						if(isEmpty){
							if(isLeft){
								buf.append("parent.document."+_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".innerHTML='';\n");
							}else{
								buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");
							}

						}else{
							if(isLeft){
								buf.append("parent.document."+_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".innerHTML='"+m_curValue+"';\n");
							}else{
								buf.append("parent.document."+_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='"+m_curValue+"';\n");
							}
						}

					}else{
						if(isEmpty){
							if(isLeft){
								buf.append(_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".innerHTML='';\n");
							}else buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='';\n");
							{

							}
						}else{
							if(isLeft){
								buf.append(_formName+"."+_formFieldAppendName+key.toString().toLowerCase()+".innerHTML='"+m_curValue+"';\n");
							}else{
								buf.append(_formName+"."+key.toString().toLowerCase()+_formFieldAppendName+".innerHTML='"+m_curValue+"';\n");
							}
						}
					}
				}
				buf.append("}catch(ex){}\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 字符串替换
	 * @param original
	 * @param find
	 * @param replace
	 * @return
	 */
	public static String ReplaceString(String original,String find,String replace){
		if(original==null){
			original="";
		}
		String returnStr="";
		if(original.indexOf(find)<0){
			returnStr=original;
		}
		try{
			while(original.indexOf(find)>=0){
				int indexbegin=original.indexOf(find);
				String leftstring=original.substring(0,indexbegin);
				original=original.substring(indexbegin+find.length());
				if(original.indexOf(find)<=0){
					returnStr+=leftstring+replace+original;
				}else{
					returnStr+=leftstring+replace;
				}
			}
			return (returnStr);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return original;
		}
	}

	/**
	 * 输出JSP页面INPUT控件
	 * @param objName
	 * @param objValue
	 * @param _maxLength
	 * @param _size
	 * @param _isRead
	 * @param _styleclass
	 * @return
	 */
	public static String getInputHtml(String objName,String objValue,int _maxLength,int _size,boolean _isRead,String _styleclass){
		StringBuffer buf=new StringBuffer();
		buf.append("<input type='text' name='"+objName+"' value='"+objValue+"'  onfocus='movelast();'");
		buf.append("maxlength='"+_maxLength+"' size='"+_size+"' ");
		if(_isRead){
			buf.append(" readonly ");
		}
		if(!(_styleclass==null||_styleclass.equals("")||_styleclass.toLowerCase().equals("null"))){
			buf.append("class='"+_styleclass+"' ");
		}
		buf.append("/>");
		return buf.toString();
	}

	/**
	 * 输出JSP页面INPUT控件
	 * @param objName
	 * @param objValue
	 * @param onchange_name
	 * @param _maxLength
	 * @param _size
	 * @param _isRead
	 * @param _styleclass
	 * @return
	 */
	public static String getInputHtml_onchange(String objName,String objValue,String onchange_name,int _maxLength,int _size,boolean _isRead,String _styleclass){
		StringBuffer buf=new StringBuffer();
		buf.append("<input type='text' name='"+objName+"' value='"+objValue+"'  onfocus='movelast();'");
		if(onchange_name!=null&&!onchange_name.equals("")){
			buf.append(" onchange='"+onchange_name+"' ");
		}
		buf.append("maxlength='"+_maxLength+"' size='"+_size+"' ");
		if(_isRead){
			buf.append(" readonly");
		}
		if(!(_styleclass==null||_styleclass.equals("")||_styleclass.toLowerCase().equals("null"))){
			buf.append("class='"+_styleclass+"' ");
		}
		buf.append("/>");
		return buf.toString();
	}

	/**
	 * 输出JSP页面SPAN控件
	 * @param objName
	 * @param objValue
	 * @return
	 */
	public static String getSpanHtml(String objName,String objValue){
		StringBuffer buf=new StringBuffer();
		String _value=objValue;
		if(objValue==null||objValue.equals("")||objValue.toLowerCase().equals("null")){
			_value="--";
		}
		buf.append("<span id='"+objName+"'>&nbsp;"+_value+"</span>");
		return buf.toString();
	}

	/**
	 * 输出JSP页面SPAN控件
	 * @param objName
	 * @param objValue
	 * @param isBlur
	 * @return
	 */
	public static String getSpanHtml(String objName,String objValue,boolean isBlur)//
	{
		StringBuffer buf=new StringBuffer();
		String _value=objValue;
		if(objValue==null||objValue.equals("")||objValue.toLowerCase().equals("null")){
			_value="--";
		}
		if(isBlur){
			buf.append("<font color='red'><span id='"+objName+"'>&nbsp;"+_value+"</span></font>");
		}else{
			buf.append("<span id='"+objName+"'>&nbsp;"+_value+"</span>");
		}
		return buf.toString();
	}

	/**
	 * bean转换为XML文档
	 * @param bean
	 * @param itemName
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public static String bean2XML(Object bean,String itemName,String itemId) throws Exception{
		StringBuffer buffer=new StringBuffer();
		try{
			Map p=BeanUtils.describe(bean);
			Set s=p.keySet();
			Iterator i=s.iterator();
			if(!(itemName==null||itemName.equals("")||itemName.toLowerCase().equals("null"))){
				buffer.append("<");
				buffer.append(itemName);
				if(itemId==null||itemId.equals("")){
					buffer.append(">");
				}else{
					buffer.append(" id=\"");
					buffer.append(itemId);
					buffer.append("\">");
				}
			}

			while(i.hasNext()){
				Object key=i.next();
				if(key.equals("class")){
				}else{
					Object value=p.get(key);
					buffer.append("\n  <");
					buffer.append(key);
					buffer.append(">");
					if(value==null||value.equals("")){
						buffer.append("--");
					}else{
						buffer.append(value);
					}
					buffer.append("</");
					buffer.append(key);
					buffer.append(">");
				}
			}
			if(!(itemName==null||itemName.equals("")||itemName.toLowerCase().equals("null"))){
				buffer.append("\n</");
				buffer.append(itemName);
				buffer.append(">\n");
			}
		}catch(Exception ex){
			throw new Exception("Error occurred while trans bean to XML node:"+ex.getMessage());
		}
		return buffer.toString();
	}

	/**
	 * 解析XML文档为bean
	 * @param xmlDoc
	 * @param _className
	 * @param preFix
	 * @return
	 * @throws Exception
	 */
	public static Object parseXML2Bean(String xmlDoc,String _className,String preFix) throws Exception{
		try{
			Object m_Object=Class.forName(_className).newInstance(); // 创建类实例
			Map p=BeanUtils.describe(m_Object);
			Set s=p.keySet();
			Iterator i=s.iterator();

			Digester digester=new Digester();
			digester.setValidating(false);
			digester.addObjectCreate("root",m_Object.getClass());
			while(i.hasNext()){
				Object key=i.next();
				if(!(key.equals("class"))){
					digester.addBeanPropertySetter("root/"+preFix+"/"+key.toString(),key.toString());
				}
			}
			byte[] src=xmlDoc.getBytes();
			ByteArrayInputStream stream=new ByteArrayInputStream(src);
			m_Object=(Object)digester.parse(stream);
			return m_Object;
		}catch(Exception ex){
			throw new Exception("XML文档格式不正确："+ex.getMessage());
		}
	}



	/**
	 * 以Byte为单位截取定长的String
	 * @以Byte为单位截取定长的String，用于显示的美观，超过定长部分用..代替
	 * @param String str将被截取的源字符串
	 * @param
	 * @byteLength 以byte为单位的字符串长度
	 * @param boolean isFillNeeded 源字符串长度不够时，是否需要填充空格。
	 */
	public static String truncateString(String str,int byteLength,boolean isFillNeeded){
		try{
			if(str.getBytes().length<byteLength){
				if(isFillNeeded){
					int spaceNeeded=byteLength-str.getBytes().length;
					StringBuffer sb=new StringBuffer(byteLength);
					sb.append(str);
					for(int i=0;i<spaceNeeded;i++){
						sb.append(" ");
					}
					return sb.toString();
				}else{
					return str;
				}
			}else{
				while(str.getBytes().length>byteLength-2){
					str=str.substring(0,str.length()-1);
				}
				int spaceNeeded=byteLength-2-str.getBytes().length;
				StringBuffer sb=new StringBuffer(byteLength);
				sb.append(str);
				for(int i=0;i<spaceNeeded;i++){
					sb.append(" ");
				}
				return sb.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将字符串分解为数组
	 * @param str
	 * @param byteColCount
	 * @return
	 */
	public static String[] tranStringList(String str,int byteColCount){
		int byteLength=str.getBytes().length;
		String tempStr[]={"","","","","","","","","",""};
		int colCount=byteColCount;
		String lxStr=str;
		for(int i=1;i<10;i++){
			tempStr[i-1]=truncateString(lxStr,colCount,false);
			int reColCount=0;
			for(int j=0;j<i;j++){
				reColCount+=tempStr[j].length();
			}
			if(str.length()>reColCount){
				lxStr=str.substring(reColCount,str.length());
			}else{
				lxStr="";
			}
		}
		return tempStr;
	}

	/**
	// 为了获取盗抢车信息
	public static VehStole parseVehstole(String queryresult){
		VehStole re=new VehStole();
		;
		try{
			Digester digester=new Digester();
			digester.setValidating(false);
			digester.addObjectCreate("root",VehStole.class);
			digester.addBeanPropertySetter("root/retCode","retCode");
			digester.addBeanPropertySetter("root/rowCount","rowCount");
			digester.addBeanPropertySetter("root/error","error");

			digester.addObjectCreate("root/vehicles/vehicle",VehStoleItem.class);
			digester.addBeanPropertySetter("root/vehicles/vehicle/hpzl","hpzl");
			digester.addBeanPropertySetter("root/vehicles/vehicle/hphm","hphm");
			digester.addBeanPropertySetter("root/vehicles/vehicle/clpp1","clpp1");
			digester.addBeanPropertySetter("root/vehicles/vehicle/clxh","clxh");
			digester.addBeanPropertySetter("root/vehicles/vehicle/clsbdh","clsbdh");
			digester.addBeanPropertySetter("root/vehicles/vehicle/fdjh","fdjh");
			digester.addBeanPropertySetter("root/vehicles/vehicle/csys","csys");
			digester.addBeanPropertySetter("root/vehicles/vehicle/syr","syr");
			digester.addBeanPropertySetter("root/vehicles/vehicle/ccdjrq","ccdjrq");
			digester.addBeanPropertySetter("root/vehicles/vehicle/zt","zt");
			digester.addSetNext("root/vehicles/vehicle","addVehStoleItem");
			byte[] src=queryresult.getBytes();
			ByteArrayInputStream stream=new ByteArrayInputStream(src);
			re=(VehStole)digester.parse(stream);
		}catch(Exception ex){
			ex.printStackTrace();
			re.setRetCode("-1");
			re.setError("XML文档格式不正确："+ex.getMessage());
		}
		return re;
	}
	*/
	
	 /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 
     * @throws 
     */
    public static Map BeanToMap(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    
	// 获取某属性
	public static void bean2cstmt(Object object,String strProperties,CallableStatement cstmt,int index){
		try{
			if(BeanUtils.getProperty(object,strProperties)==null){
				cstmt.setNull(index,Types.VARCHAR);
			}else{
				cstmt.setString(index,BeanUtils.getProperty(object,strProperties));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	// 获取某属性（Blob）
	public static void bean2cstmtBlob(Object object,String strProperties,CallableStatement cstmt,int index){

		try{
			if(BeanUtils.getProperty(object,strProperties)==null){
				cstmt.setNull(index,Types.VARCHAR);
			}else{
				byte[] dataBytes = BeanUtils.getProperty(object,strProperties).getBytes();
				ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);
				cstmt.setBinaryStream(index, bais,dataBytes.length);//使用二进制读取，可以直接写入汉字，否则容易产生乱码
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static ArrayList pareXML2ArrayList(String xmlDoc,String _className,String preFix) throws Exception{
		try{
			FrmResultBean re=null;
			Object m_Object=Class.forName(_className).newInstance(); // 创建类实例
			Map p=BeanUtils.describe(m_Object);
			Set s=p.keySet();
			Iterator i=s.iterator();

			Digester digester=new Digester();
			digester.setValidating(false);
			digester.addObjectCreate("root",FrmResultBean.class);
			digester.addObjectCreate("root/body/"+preFix,m_Object.getClass());
			while(i.hasNext()){
				Object key=i.next();
				if(!(key.equals("class"))){
					digester.addBeanPropertySetter("root/body/"+preFix+"/"+key.toString(),key.toString());
				}
			}

			digester.addSetNext("root/body/"+preFix,"addObject");
			byte[] src=xmlDoc.getBytes();
			ByteArrayInputStream stream=new ByteArrayInputStream(src);
			re=(FrmResultBean)digester.parse(stream);
			ArrayList list=re.getObjectItems();
			return list;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("XML文档格式不正确："+ex.getMessage());
		}
	}

}
