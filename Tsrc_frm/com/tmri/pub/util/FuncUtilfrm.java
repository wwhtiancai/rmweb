package com.tmri.pub.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tmri.framework.bean.Fold;
import com.tmri.framework.bean.SysResult;
import com.tmri.share.frm.bean.Department;


public class FuncUtilfrm {
	//�Խ�ɫ��list����
	public static List sortRoleList(List objlist){
		ComparatorRole comparator=new ComparatorRole();
		Collections.sort(objlist, comparator);
		return objlist;
	}
	
	//��list����
	public static List sortList(List objlist){
		ComparatorCode comparator=new ComparatorCode();
		Collections.sort(objlist, comparator);
		return objlist;
	}
	
	public static List getVector2List(List foldList){
		List result=new ArrayList();
		for(int i=0;i<foldList.size();i++){
			Fold fold=(Fold)foldList.get(i);
			result.add(fold);
		}
		return result;
	}
	
	
	// ����ip��ַ�Ͷ˿�
	//
	public static SysResult getUrl(String url,String dkh){
		SysResult sysresult=new SysResult();
		sysresult.setRetcode("1");

		String strUrl="";
		if(!dkh.equals("")){
			strUrl="http://"+url+":"+dkh;
		}else{
			strUrl="http://"+url;
		}
		/*
		if(strUrl.endsWith("/"))
	    {
			strUrl=strUrl+"rmweb";
	    }
	    else
	    {
	    	strUrl=strUrl+"/rmweb";
	    }
		*/
		if(strUrl.equals("")){
			sysresult.setRetcode("0");
			sysresult.setRetdesc("��ַδ���ã�");

		}
		sysresult.setRetval(strUrl);
		return sysresult;
	}
	
	//�жϹ������Ƿ���֧������һ����,�ܶӣ�֧�ӣ�ʡ����
	public static boolean checkZdglbm(Department dept){
		boolean result=false;
		if(dept.getBmjb().equals("2")||dept.getBmjb().equals("3")){
			result=true;
		}else if(dept.getBmjb().equals("4")){
			if(dept.getGlbm().substring(2,4).equals("90")){
				result=true;
			}
		}
		return result;
	}
	
	//���ݹ������г���ѯ����
	public static String getPublicGlbm(Department deptuser,Department deptquery){
		String result="";
		if(deptuser.getBmjb().equals("2")){
			//�ܶ��û�
			if(deptquery.getBmjb().equals("")){
				result=deptquery.getGlbm().substring(0,2);
			}else if(deptquery.getBmjb().equals("2")){
				result=deptquery.getGlbm();
			}else if(deptquery.getBmjb().equals("3")||deptquery.getBmjb().equals("4")){
				if(deptquery.getGlbm().substring(2,4).equals("90")){
					result=deptquery.getGlbm().substring(0,6);
				}else{
					result=deptquery.getGlbm().substring(0,4);
				}
			}
		}else if(deptuser.getBmjb().equals("3")){
			//֧���û�
			if(deptquery.getGlbm().substring(2,4).equals("90")){
				result=deptquery.getGlbm().substring(0,6);
			}else{
				result=deptquery.getGlbm().substring(0,4);
			}
		}else if(deptquery.getBmjb().equals("4")){
			if(deptquery.getGlbm().substring(2,4).equals("90")){
				result=deptquery.getGlbm().substring(0,6);
			}else{
				result=deptquery.getGlbm().substring(0,8);
			}
		}
		
		return result;
		
	}
	
	
	public static String getStrGlbmNosep(List deptList){
		String result="";
		for(int i=0;i<deptList.size();i++){
			Department dept=(Department)deptList.get(i);
			result+=dept.getGlbm()+",";
		}
		if(!result.equals("")){
			result=result.substring(0,result.length()-1);
		}
		return result;
	}
	
	
	// ��ȡ������'bmdm','bmdm'
	public static String getStrGlbm(List deptList){
		String result="";
		for(int i=0;i<deptList.size();i++){
			Department dept=(Department)deptList.get(i);
			result+="'"+dept.getGlbm()+"',";
		}
		if(!result.equals("")){
			result=result.substring(0,result.length()-1);
		}
		return result;
	}
	

}
