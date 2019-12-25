package com.tmri.share.frm.util;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.jsp.*;
import java.io.PrintWriter;

public class ObjectCreator{
	/**
	 * 获取页面数
	 * @param _request
	 * @param m_Object
	 * @return
	 */
	public static Object CreateObjectsFromPage(HttpServletRequest _request,Object m_Object){
		try{
			Class classObject=m_Object.getClass(); // 得到class实例
			Field m_Field[]=classObject.getDeclaredFields(); // 获取私有变量
			HashMap m_value=new HashMap();
			for(int i=0;i<m_Field.length;i++){
				try{
					if(_request.getParameter(m_Field[i].getName().toString())!=null){

						String m_key=m_Field[i].getName().toString();
						String m_singleValue=_request.getParameter(m_key);
						if(m_singleValue==null||m_singleValue.toLowerCase().equals("null")){
							m_singleValue="";
						}
						m_value.put(m_key,m_singleValue.trim());
					}

				}catch(Exception novalue){
					novalue.printStackTrace();
				}
			}
			return CreateSingleObject(m_Object,m_value);

		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取页面数据
	 * @param _request HttpServletRequest http请求实例。
	 * @param _className String 需要构造的类名称
	 * @return Object 返回类实例
	 */
	public static Object CreateObjectsFromPage(HttpServletRequest _request,String _className){
		try{
			Object m_Object=Class.forName(_className).newInstance(); // 创建类实例
			Class classObject=m_Object.getClass(); // 得到class实例
			Field m_Field[]=classObject.getDeclaredFields(); // 获取私有变量
			HashMap m_value=new HashMap();
			for(int i=0;i<m_Field.length;i++){
				try{
					if(_request.getParameter(m_Field[i].getName().toString().toLowerCase())!=null){

						String m_key=m_Field[i].getName().toString();
						String m_singleValue=_request.getParameter(m_key.toLowerCase());
						if(m_singleValue==null||m_singleValue.toLowerCase().equals("null")){
							m_singleValue="";
						}
						m_value.put(m_key,m_singleValue.trim());
					}

				}catch(Exception novalue){
					novalue.printStackTrace();
				}
			}
			return CreateSingleObject(_className,m_value);

		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取页面数据，使用前缀区分同一类相同同页面显示
	 * @param _request HttpServletRequest http请求
	 * @param _className String 类名
	 * @param preFix String 前缀
	 * @return Object 类实例
	 */
	public static Object CreateObjectsFromPage(HttpServletRequest _request,String _className,String preFix){
		try{
			Object m_Object=Class.forName(_className).newInstance(); // 创建类实例
			Class classObject=m_Object.getClass(); // 得到class实例
			Field m_Field[]=classObject.getDeclaredFields(); // 获取私有变量
			HashMap m_value=new HashMap();
			for(int i=0;i<m_Field.length;i++){
				try{
					if(_request.getParameter(preFix+m_Field[i].getName().toString().toLowerCase())!=null){
						String m_key=m_Field[i].getName().toString();
						String m_singleValue=_request.getParameter(preFix+m_key.toLowerCase());
						m_value.put(m_key,m_singleValue.trim());
					}
				}catch(Exception novalue){
					novalue.printStackTrace();
				}
			}
			return CreateSingleObject(_className,m_value);

		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 将HashMap数据封装为bean
	 * @param _className
	 * @param _hashMap
	 * @return
	 * @throws Exception
	 */
	private static Object CreateSingleObject(String _className,HashMap _hashMap) throws Exception{

		Object obj=Class.forName(_className).newInstance();

		BeanUtils.populate(obj,_hashMap);

		return obj;

	}

	/**
	 * 将HashMap数据封装为bean
	 * @param _className
	 * @param _hashMap
	 * @return
	 * @throws Exception
	 */
	private static Object CreateSingleObject(Object m_Object,HashMap _hashMap) throws Exception{

		BeanUtils.populate(m_Object,_hashMap);

		return m_Object;

	}

	public static Map<String, Object> CreateHashMapByObject(Object m_Object,Map<String, Object> data) throws Exception{
		Class classObject=m_Object.getClass();
		Field m_field[]=classObject.getDeclaredFields();
		for(int i=0;i<m_field.length;i++){			
			String colName=m_field[i].getName();
			data.put(colName,BeanUtils.getProperty(m_Object,colName));
		}
		return data;
	}
	private static String FormatColName(String colName){
		return colName.substring(0,1).toUpperCase()+colName.substring(1);
	}

	/**
	 * 将Java的对象列表转换为Javascript对象数组
	 * @return
	 */
	public static String transListToJsArr(List list,String jsArr,String jslen){
		if(list==null)return jslen + " = 0;\n";
		StringBuffer resultBuffer=new StringBuffer();
		try{
			resultBuffer.append(jslen+" = "+list.size()+";\n");
			Class parameters[]=new Class[0];
			Object objvarargs[]=new Object[0];
			for(int i=0;i<list.size();i++){
				Object object=list.get(i);
				resultBuffer.append(jsArr+"["+i+"] = new Object();\n");
				Class classObject=object.getClass();
				Field m_field[]=classObject.getDeclaredFields();
				for(int j=0;j<m_field.length;j++){
					String colName=m_field[j].getName();
					String methName="get"+FormatColName(colName);
					if(colName.equals("lastmfqfrq")){
						System.out.println("asdfa");
					}
					Method meth=classObject.getMethod(methName,parameters);
					
					if(meth.invoke(object,objvarargs)!=null){
						String m_curValue=meth.invoke(object,objvarargs).toString();
						m_curValue=ReplaceString(m_curValue,"\'","\\\'");
						m_curValue=ReplaceString(m_curValue,"\"","\\\"");
						m_curValue=ReplaceString(m_curValue,"\n","\\n");
						resultBuffer.append(jsArr+"["+i+"]." + colName + " = '" +  m_curValue + "';\n");
					}else{
						resultBuffer.append(jsArr+"["+i+"]." + colName + " = '';\n");
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return resultBuffer.toString();
	}
	/**
	 * 将Javascript对象，给表单域赋值
	 * @param type 1-正常 2-同时赋值 field1的值
	 * @return
	 */
	public static String transJsObjSetPageValue(Class classObject,String jsObject,String type,String _formName){
		StringBuffer resultBuffer=new StringBuffer();
		try{
		Field m_field[]=classObject.getDeclaredFields();
		for(int j=0;j<m_field.length;j++){
			String colName=m_field[j].getName();
			resultBuffer.append("try{\n");
			resultBuffer.append(_formName + "." + colName + ".value=" + jsObject + "." + colName + ";\n");
			if(type.equals("2")){
				resultBuffer.append(_formName + "." + colName + "1.value=" + jsObject + "." + colName + ";\n");
			}
			resultBuffer.append("}catch(ex){}\n");			
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return resultBuffer.toString();
	}
	/**
	 * 将Javascript对象，给表单域赋值
	 * @return
	 */
	public static String transJsObjSetPageValue(Class classObject,String jsObject,String _formName){
		return transJsObjSetPageValue(classObject,jsObject,"1",_formName);
	}
	/**
	 * 采集表单域值，赋值到JavaScript对象中
	 * @return
	 */

	public static String transFormValueToJsObject(Class classObject,String _formName,String jsObject){
		StringBuffer resultBuffer=new StringBuffer();
		try{
		Field m_field[]=classObject.getDeclaredFields();
		for(int j=0;j<m_field.length;j++){
			String colName=m_field[j].getName();
			resultBuffer.append("try{\n");
			resultBuffer.append(jsObject + "." + colName + "=" + _formName + "." + colName + ".value;");
			resultBuffer.append("}catch(ex){}\n");			
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return resultBuffer.toString();
	}

	/**
	 * 根据 type获取赋值语句
	 * @param type 1-正常 2-同时赋值 field1的值
	 * @return
	 */
	public static String getSetPageValueNoHead(Object _obj,String _formName,String type,boolean isParent){
		if(_obj==null){
			return "";
		}
		StringBuffer resultBuffer=new StringBuffer();
		try{
			// 赋值--------------------begin----------------------------
			Class classObject=_obj.getClass();
			Field m_field[]=classObject.getDeclaredFields();
			Class parameters[]=new Class[0];
			Object objvarargs[]=new Object[0];
			for(int i=0;i<m_field.length;i++){
				resultBuffer.append("try{");
				String colName=m_field[i].getName();
				String methName="get"+FormatColName(colName);
				Method meth=classObject.getMethod(methName,parameters);
				String m_curValue="";
				if(meth.invoke(_obj,objvarargs)!=null){
					m_curValue=meth.invoke(_obj,objvarargs).toString();
					if(!colName.toLowerCase().equals("dzzb")){
						m_curValue=ReplaceString(m_curValue,"\'","\\\'");
						m_curValue=ReplaceString(m_curValue,"\"","\\\"");
						m_curValue=ReplaceString(m_curValue,"\n","\\n");
						m_curValue=ReplaceString(m_curValue,"\r","\\r");
					}

				}
				if(isParent){
					resultBuffer.append("parent.document."+_formName+"."+m_field[i].getName().toString().toLowerCase()+".value='"+m_curValue+"';");
					if(type.equals("2")){
						resultBuffer.append("parent.document."+_formName+"."+m_field[i].getName().toString().toLowerCase()+"1.value='"+m_curValue+"';");
					}
				}else{
					resultBuffer.append(_formName+"."+m_field[i].getName().toString().toLowerCase()+".value='"+m_curValue+"';");
					if(type.equals("2")){
						resultBuffer.append(_formName+"."+m_field[i].getName().toString().toLowerCase()+"1.value='"+m_curValue+"';");
					}
				}
				resultBuffer.append("}catch(ex){}\n");
			}
			// -------------------------------end------------------------------
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return resultBuffer.toString();
	}
	public static String getSetPageValueNoHead(Object _obj,String _formName,boolean isParent){
		return getSetPageValueNoHead(_obj,_formName,"1",isParent);
	}

	/**
	 * 给页面赋值,如果给当前页面赋值，请将调用代码放到页面最后
	 * 
	 * @param _obj Object bean实体
	 * @param _pw PrintWriter 输出实例
	 * @param _formName String 表单名称
	 * @param isParent boolean 是否有父亲
	 */
	public static void SetPageValue(Object _obj,JspWriter _pw,String _formName,boolean isParent){
		try{
			_pw.print("<script language=javascript>");
			String resultString=getSetPageValueNoHead(_obj,_formName,isParent);
			_pw.println(resultString);
			_pw.println("</script>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void SetPageValueOld(Object _obj, JspWriter _pw, String _formName,
			boolean isParent) {
		try {
			_pw.print("<script language=javascript>");
			// 赋值--------------------begin----------------------------
			Class classObject = _obj.getClass();
			Field m_field[] = classObject.getDeclaredFields();
			Class parameters[] = new Class[0];
			Object objvarargs[] = new Object[0];
			for (int i = 0; i < m_field.length; i++) {
				_pw.print("try{");
				String colName = m_field[i].getName();
				String methName = "get" + colName.substring(0,1).toUpperCase()+colName.substring(1);;
				Method meth = classObject.getMethod(methName, parameters);
				String m_curValue = "";
				if (meth.invoke(_obj, objvarargs) != null) {
					m_curValue = meth.invoke(_obj, objvarargs).toString();
					m_curValue = ReplaceString(m_curValue, "\'", "\\\'");
					m_curValue = ReplaceString(m_curValue, "\"", "\\\"");
					m_curValue = ReplaceString(m_curValue, "\n", "\\n");

				}
				if (isParent) {
					_pw.println("parent.document." + _formName + "."
							+ m_field[i].getName().toString().toLowerCase() + ".value='"
							+ m_curValue + "';");
				} else {
					_pw.println(_formName + "."
							+ m_field[i].getName().toString().toLowerCase() + ".value='"
							+ m_curValue + "';");
				}
				_pw.println("}catch(ex){}");
			}
			// -------------------------------end------------------------------

			_pw.println("</script>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 给页面赋值,如果给当前页面赋值，请将调用代码放到页面最后
	 * 
	 * @param _obj Object bean实体
	 * @param _pw PrintWriter 输出实例
	 * @param _formName String 表单名称
	 * @param _formFieldAppendName String 表单字段名附加字符串
	 * @param isParent boolean 是否有父亲
	 */
	public static void SetPageValue(Object _obj,PrintWriter _pw,String _formName,String _formFieldAppendName,boolean isParent,boolean isEmpty){
		try{
			_pw.print("<script language=javascript>");
			Class classObject=_obj.getClass();
			Field m_field[]=classObject.getDeclaredFields();
			Class parameters[]=new Class[0];
			Object objvarargs[]=new Object[0];
			for(int i=0;i<m_field.length;i++){
				_pw.println("try{");
				String colName=m_field[i].getName();
				if(colName.length()>0){
					colName=colName.substring(0,1).toUpperCase()+colName.substring(1);
				}
				String methName="get"+colName;
				Method meth=classObject.getMethod(methName,parameters);
				String m_curValue="";
				if(meth.invoke(_obj,objvarargs)!=null){
					m_curValue=meth.invoke(_obj,objvarargs).toString();
					m_curValue=ReplaceString(m_curValue,"\'","\\\'");
					m_curValue=ReplaceString(m_curValue,"\"","\\\"");
					m_curValue=ReplaceString(m_curValue,"\n","\\n");
				}
				m_curValue=m_curValue==null?"":m_curValue;
				if(isParent){
					if(isEmpty){
						_pw.println("parent.document."+_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='';");
					}else{
						_pw.println("parent.document."+_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';");
					}

				}else{
					if(isEmpty){
						_pw.println(_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='';");
					}else{
						_pw.println(_formName+"."+m_field[i].getName().toString().toLowerCase()+_formFieldAppendName+".value='"+m_curValue+"';");
					}
				}
				_pw.println("}catch(ex){}");
			}
			_pw.println("</script>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

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
	//合并两个对象，前一个对象的同名属性为空时，以后一个同名对象填充
	//flag = 1 前一对象属性为NULL合并 flag=2 前一对象属性为空或NULL时合并	
	public static Object UnitBeanByFlag(Object obj1,Object obj2,String flag){
		try{
			Class classObject1 = obj1.getClass();
			Class classObject2 = obj2.getClass();
			Field m_field1[] = classObject1.getDeclaredFields();
			Field m_field2[] = classObject2.getDeclaredFields();
		    for(int i=0;i<m_field1.length; i++) {
		    	String colName1 = m_field1[i].getName();
		    	String colType1=m_field1[i].getType().toString();
		    	if(colName1.length()>0){
		        	colName1= colName1.substring(0,1).toUpperCase()+colName1.substring(1);
		        }else{
		        	continue;
		        }
		    	try{
		        String methNameget1 = "get" + colName1;
		        Class parameters[] = new Class[0];
		        Object objvarargs[] = new Object[0];
		        Method meth = classObject1.getMethod(methNameget1, parameters);
		        String m_curValue = "";
		        if(flag.equals("1")){
			        if (meth.invoke(obj1, objvarargs) == null){
			        	//当前属性为空时查找对象2的同名属性
			        	for(int j=0;j<m_field2.length; j++) {
			        		String colName2 = m_field2[j].getName();
			        		String colType2 = m_field2[j].getType().toString();
					    	if(colName2.length()>0){
					        	colName2= colName2.substring(0,1).toUpperCase()+colName2.substring(1);
					        }else{
					        	continue;
					        }
					    	if(colName2.equals(colName1)&&colType2.equals(colType1)){
					    		String methNameget2="get"+colName2;
					    		String methNameset1="set"+colName2;
					    		Class parameters2[] = new Class[1];
							    Object objvarargs2[] = new Object[1];
					    		meth=classObject2.getMethod(methNameget2, parameters);
					    		if (meth.invoke(obj2, objvarargs) != null&&(!meth.invoke(obj2, objvarargs).toString().equals(""))){
					    			parameters2[0]=m_field2[j].getType();
					    			objvarargs2[0]=meth.invoke(obj2, objvarargs);
					    			meth=classObject1.getMethod(methNameset1, parameters2);
					    			meth.invoke(obj1, objvarargs2);
					    			break;
					    		}
				        	}
			        	}
			        }
		        }else{
		        	if (meth.invoke(obj1, objvarargs) == null||meth.invoke(obj1, objvarargs).toString().equals("")){
			        	//当前属性为空时查找对象2的同名属性
			        	for(int j=0;j<m_field2.length; j++) {
			        		String colName2 = m_field2[j].getName();
			        		String colType2 = m_field2[j].getType().toString();
					    	if(colName2.length()>0){
					        	colName2= colName2.substring(0,1).toUpperCase()+colName2.substring(1);
					        }else{
					        	continue;
					        }
					    	if(colName2.equals(colName1)&&colType2.equals(colType1)){
					    		String methNameget2="get"+colName2;
					    		String methNameset1="set"+colName2;
					    		Class parameters2[] = new Class[1];
							    Object objvarargs2[] = new Object[1];
					    		meth=classObject2.getMethod(methNameget2, parameters);
					    		if (meth.invoke(obj2, objvarargs) != null&&(!meth.invoke(obj2, objvarargs).toString().equals(""))){
					    			parameters2[0]=m_field2[j].getType();
					    			objvarargs2[0]=meth.invoke(obj2, objvarargs);
					    			meth=classObject1.getMethod(methNameset1, parameters2);
					    			meth.invoke(obj1, objvarargs2);
					    			break;
					    		}
				        	}
			        	}
			        }
		        }
		    	}catch(Exception ex){
		    		
		    	}

		    }
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return obj1;
	}

	public static Object UnitBean(Object obj1,Object obj2){
		return UnitBeanByFlag(obj1,obj2,"2");
	}
	//合并两个对象，前一个对象的同名属性为空时，以后一个同名对象填充
	public static Object UnitBeanByNull(Object obj1,Object obj2){
		return UnitBeanByFlag(obj1,obj2,"1");
	}
}
