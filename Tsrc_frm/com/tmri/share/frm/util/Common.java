package com.tmri.share.frm.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author WengYufeng
 * @Date:Apr 4, 2007 9:51:00 AM
 * 
 */
public class Common{
	
	
	public static String translateDllx(String dldm) {
		if (StringUtil.checkBN(dldm)) {
			return dldm.substring(0, 1);
		}
		return "";
	}
	
	public static String changeHphmForRm(String hphm){
		String tmpHphm = hphm.replaceAll("%", "");
		tmpHphm = tmpHphm.replaceAll("_", "");
		tmpHphm = tmpHphm.replaceAll("\\*", "%");
		tmpHphm = tmpHphm.replaceAll("\\?", "_");
		return tmpHphm;
	}
	
	public static String getNow(){
		Date now=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(now);
	}
	public static String ScriptToHtml(String strJava){
		String r=null;
		if(strJava!=null){
			r=strJava.replaceAll("'","’");
//			r=r.replaceAll("\"","\\\"");
			r=r.replaceAll("\r","");
			r=r.replaceAll("\n","");
			r=r.replaceAll("\\(","（");
			r=r.replaceAll("\\)","）");
		}
		return r;
	}
	public static String ScriptToText(String str){
		String r=null;
		if(str==null){
			r="";
		}else{
			r=str.replaceAll("<","〈");
			r=r.replaceAll(">","〉");
		}
		return r;
	}
	public static String TextToHtml(String str){
		String r="";
		if(str==null){
			r="";
		}else{
			r=str.replaceAll("\r\n","<br>");
		}
		return r;
	}
	public static String replace(String strOriginal,String strOld,String strNew){
		int i=0;
		StringBuffer strBuffer=new StringBuffer(strOriginal);
		while((i=strOriginal.indexOf(strOld,i))>=0){
			strBuffer.delete(i,i+strOld.length());
			strBuffer.insert(i,strNew);
			i=i+strNew.length();
			strOriginal=strBuffer.toString();
		}
		return strOriginal;
	}

	public static String translate(Map<String,String> map,String key){
		if(map==null||!map.containsKey(key)||map.get(key)==null)
			return key;
		else
			return (String)map.get(key);
	}
	
	public static String changeHphm(String hphm){
		String Chphm="";
		if(!"".equals(hphm)){
			char [] tmp = hphm.toCharArray();
			for(int i=0; i<tmp.length; i++){
				if(tmp[i] == '?'){
					tmp[i] = '_';
				}else if(tmp[i] == '*'){
					tmp[i] = '%';
				}else if(tmp[i] == '%' || tmp[i] == '_'){
					tmp[i] = ' ';
				}
			}
			Chphm = new String(tmp);
		}else{
			Chphm=hphm;
		}
		return Chphm;
	}
	
	public static String changeBfb(String bfb){
		String cbfb = "";
		if(bfb.equals("100.0%")){
			cbfb = "100%";
		}else if(bfb.equals("0.0%")){
			cbfb = "-";
		}else{
			cbfb = bfb;
		}
		
		return cbfb;
	}

	public static String changeSqlChar(String value){
		char [] cs = null;
		if(null != value && !"".equals(value)){
			cs = value.toCharArray();
			for(int i = 0; i < cs.length; i++){
				if(cs[i] == '?'){
					cs[i] = '_';
				}else if(cs[i] == '*'){
					cs[i] = '%';
				}
			}
		}
		if(cs == null){
			return "";
		}else{
			return String.valueOf(cs);
		}
	}
	public static String getStringByXml(String xml,String path) throws Exception{
		if(xml==null||xml.length()<1||path==null||path.length()<1)
			return null;
		Document doc=DocumentHelper.parseText(xml);
		Node node=doc.selectSingleNode(path);
		String value = node.getText();
		if(StringUtil.checkBN(value)){
//			value = URLEncoder.encode(value, "UTF-8");
			value = URLDecoder.decode(value, "UTF-8");
		}
		return value;
	}
	public static Object getBeanByXml(Class<?> bean,String xml,String path) throws Exception{
		if(bean == null||xml==null||xml.length()<1||path==null||path.length()<1)
			return null;
		Document doc=DocumentHelper.parseText(xml);
		List<Element> list=doc.selectNodes(path);
		if(list!=null&&list.size()==1){
			Element e=list.get(0);
			List<Element> li=e.elements();
			if(li!=null&&li.size()>0){
				Object obj=bean.newInstance();
				for(Element element2:li){
					String name=element2.getName();
					String value=element2.getText();
					
					if(StringUtil.checkBN(value)){
						value = URLDecoder.decode(value, "UTF-8");
					}
					
					Method mtd=bean.getMethod("set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length()).toLowerCase(),new Class[]{String.class});
					mtd.invoke(obj,new Object[]{value});
				}
				return obj;	
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	public static <T> List<T> getListByXml(Class<T> bean,String xml,String path) throws Exception{
		if(bean == null||xml==null||xml.length()<1||path==null||path.length()<1)
			return null;
		List<T> result=new Vector<T>();
		Document doc=DocumentHelper.parseText(xml);
		List<Element> list=doc.selectNodes(path);
		if(list!=null&&list.size()>0){
			for(Element e:list){
				List<Element> li=e.elements();
				if(li!=null&&li.size()>0){
					T obj=bean.newInstance();
					for(Element element2:li){
						String name=element2.getName();
						String value=element2.getText();
						if(StringUtil.checkBN(value)){
							value = URLDecoder.decode(value, "UTF-8");
						}
						Method mtd=bean.getMethod("set"+name.substring(0,1).toUpperCase()+name.substring(1,name.length()).toLowerCase(),new Class[]{String.class});
						mtd.invoke(obj,new Object[]{value});
					}
					result.add(obj);
				}
			}
			return result;
		}else{
			return null;
		}
	}
	private static void convertValue(Field field,Object obj,String value) throws Exception{
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(field.getGenericType().toString().equals("class java.lang.Integer")){
			field.set(obj,Integer.parseInt(value));
		}else if(field.getGenericType().toString().equals("boolean")){
			field.set(obj,Boolean.parseBoolean(value));
		}else if(field.getGenericType().toString().equals("class java.util.Date")){
			field.set(obj,sim.parse(value));
		}else{
			field.set(obj,value);
		}
	}
	
	public static String getTrffErrmsg(String code) throws Exception{
		String result=code;
		if(code!=null){
			if(code.equals("~~2")){
				result="被访问的综合平台中该安装代码的备案信息校验位被非法修改";
			}
			if(code.equals("~~3")){
				result="该应用服务器地址非法，被访问的综合平台的备案信息中无此应用服务器地址信息";
			}
		}
		return result;
	}
	
	/**
	 * 从本地发证机关或备案的发证机关中判断是否为总队版本程序
	 * @param fzjg
	 * @return
	 * @throws Exception
	 */
	public static boolean checkZdVersion(String fzjg)throws Exception{
		if(fzjg!=null){
			if(fzjg.length()>2){
				if(fzjg.substring(1,3).equalsIgnoreCase("ZD")){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取总队版发证机关
	 * @param sft
	 * @return
	 */
	public static String getZdbbFzjg(String sft) {
		return	sft+"ZD";
	}
	
    /**
     * 判断是否直辖市
     * 
     * @param glbm 管理部门代码
     * @return 是否直辖市 true 直辖市 false 非直辖市
     */
    public static boolean isZxs(String glbm) {
        if (!StringUtil.checkBN(glbm)) {
            return false;
        }

        if (glbm.startsWith("11") || glbm.startsWith("12") || glbm.startsWith("31")
                || glbm.startsWith("50")) {
            return true;
        }

        return false;
    }
    
    
    /**
     * 判断是否直辖市
     * 
     * @param glbm 管理部门代码
     * @return 是否直辖市 true 直辖市 false 非直辖市
     */
    public static boolean isZxsFzjg(String fzjg) {
        if (!StringUtil.checkBN(fzjg)) {
            return false;
        }

        if (fzjg.startsWith("京") || fzjg.startsWith("津") || fzjg.startsWith("沪")
                || fzjg.startsWith("渝")) {
            return true;
        }

        return false;
    }
}
