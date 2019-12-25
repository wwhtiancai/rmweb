package com.tmri.pub.util;

import java.util.List;

import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.bean.Department;

public class XmlUtil {
	
	private static boolean checkExist(String defvalue,String bmdm){
		boolean result=false;
		/*
		String[] def=defvalue.split("A");
		for(int i=0;i<def.length;i++){
			if(def[i].equals(bmdm)){
				result=true;
				break;
			}
		}*/
		if(defvalue.indexOf(bmdm)>=0){
			result=true;
		}
		return result;
	}
	
	public static String getCheckboxFoldXml(String bmdm,String bmmc,String defvalue){
    	String result="";
    	if(checkExist(defvalue,bmdm)){
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" open=\"1\" checked=\"1\">";
		}else{
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" open=\"1\">";
		}	    	
    	return result;
    }
	
    public static String getCheckboxMenuXml(String bmdm, String bmmc,String defvalue){
    	String result="";
    	if(checkExist(defvalue,bmdm)){
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"book_titel.gif\" im1=\"book_titel.gif\" im2=\"book_titel.gif\" checked=\"1\"/>";
		}else{
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"book_titel.gif\" im1=\"book_titel.gif\" im2=\"book_titel.gif\"/>";
		}	
		return result;
    }	
    
	
	public static String getFoldXml(String bmdm,String bmmc,String defvalue){
    	String result="";
    	if(defvalue.indexOf(bmdm)>=0){
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" open=\"1\" select=\"1\">";
		}else{
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" open=\"1\">";
		}	    	
    	return result;
    }
    
    public static String getMenuXml(String bmdm, String bmmc,String defvalue){
    	String result="";
    	if(defvalue.indexOf(bmdm)>=0){
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"book_titel.gif\" im1=\"book_titel.gif\" im2=\"book_titel.gif\" select=\"1\"/>";
		}else{
			result+="<item text=\""+bmmc+"\" id=\""+bmdm+"\" im0=\"book_titel.gif\" im1=\"book_titel.gif\" im2=\"book_titel.gif\"/>";
		}	
		return result;
    }
    
    public static  String getClose(){
    	return "</item>";
    }
	//
    
    public static  String buildCheckboxTree(List objList,String parentid,String defvalue){
   	 String result="";
   	 String strBmdm = "", strBmmc = "", strSjbm="";
   	 for(int j=0;j<objList.size();j++){
   		Department obj = (Department) objList.get(j);
   		strBmdm = obj.getGlbm();
   		strBmmc = obj.getBmmc();
   		strSjbm = obj.getSjbm();    
			if(strSjbm.equals(parentid)){
				if(has_child(objList,strBmdm)){	
					result=result+getCheckboxFoldXml(strBmdm,strBmmc,defvalue);
					result=result+buildCheckboxTree(objList,strBmdm,defvalue);//递归调用
					result=result+getClose();
				}else{
					result=result+getCheckboxMenuXml(strBmdm,strBmmc,defvalue);
				}
				
			}
		}
   	return result;
   }
    
    
    public static  String buildTree(List objList,String parentid,String defvalue){
    	 String result="";
    	 String strBmdm = "", strBmmc = "", strSjbm="";
    	 for(int j=0;j<objList.size();j++){
    		Department obj = (Department) objList.get(j);
    		strBmdm = obj.getGlbm();
    		strBmmc = obj.getBmmc();
    		strSjbm = obj.getSjbm();    
			if(strSjbm.equals(parentid)){
				if(has_child(objList,strBmdm)){	
					result=result+getFoldXml(strBmdm,strBmmc,defvalue);
					result=result+buildTree(objList,strBmdm,defvalue);//递归调用
					result=result+getClose();
				}else{
					result=result+getMenuXml(strBmdm,strBmmc,defvalue);
				}
				
			}
		}
    	return result;
    }
    

    //判断parentid是否有孩子节点
	public static boolean has_child(List objlist,String parentid){
		String strSjbm="";
		boolean flag=false;
		for(int j=0;j<objlist.size();j++){
			Department obj = (Department) objlist.get(j);
			strSjbm = obj.getSjbm(); 
			if(strSjbm.equals(parentid)){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	public static boolean has_child_code(List objlist,String parentid){
		String strDmz="";
		boolean flag=false;
		for(int j=0;j<objlist.size();j++){
			Code obj = (Code) objlist.get(j);
			strDmz = obj.getDmz(); 
			if(strDmz.equals(parentid)){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
