package com.tmri.pub.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.tmri.framework.bean.SessionKey;
import com.tmri.share.frm.util.Constants;

/**
 * �ṩ��ҳ�湫��JS����ӡ�ؼ������ݵ�ͳһ�������
 * 
 * @author jianghailong
 * 
 */
public class JspSuport{
	/**
	 * �����ӡ�ؼ�ʹ�ÿؼ�����HTML,���ؼ��Ƿ������
	 */
	public String SYS_PRINTSUPORT01=null;
	/**
	 * ��������߼����checkdata.js����
	 */
	public String JS_CHECKDATA=null;
	/**
	 * ���ҳ�湫�ù���common_fun.js����
	 */
	public String JS_COMMON=null;
	/**
	 * ���ͳ��ͼ����FusionChart.js����
	 */
	public String JS_FUSIONCHARTS=null;

	/**
	 * �������������ش�����date_fun.js����
	 */
	public String JS_DATE=null;

	/**
	 * ����ṩҳ��checkbox,radion��list����ش���obj_fun.js����
	 */
	public String JS_OBJ=null;
	/**
	 * ����ṩҳ��Ajax���� ajax_func.js
	 */
	public String JS_AJAX=null;
	/**
	 * �ṩҳ��Tree����xtree_func.js
	 */
	public String JS_XTREE=null;
	/**
	 * �ṩҳ���ַ�������str_func.js
	 */
	public String JS_STR=null;
	/**
	 * ������JS���ã���FusionChart.js
	 */
	public String JS_ALL=null;

	public String JS_STYLE=null;
	
	private String VERSION="1.0";
	
	public String JS_THEME="theme";
	
	public String JS_QUERYCOMM=null;

	public String JS_JQUERY = null;

	public String JS_JQUERY_MIGRATION = null;

	private static JspSuport jspSuport=null;
	public static JspSuport getInstance(){
		if(jspSuport==null){
			jspSuport=new JspSuport();
			jspSuport.VERSION="1.0";
			jspSuport.JS_JQUERY="<script language='javascript' src='frmjs/jquery-1.11.3.min.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_JQUERY_MIGRATION="<script language='javascript' src='frmjs/jquery-migrate-1.2.1.min.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.SYS_PRINTSUPORT01="<OBJECT id=\"tmriPrint\" style=\"display:none\" classid=\"clsid:5EB117AE-E00C-4FA7-86BC-7DCAF1AA1BDD\" codebase=\"TmriBasePrint.ocx#version=1,0,0,0\" width=690 height=455 align=center hspace=0 vspace=0></OBJECT><script language=\"javascript\">var ver=\"20101130\";var sPath = \"download.htm\";strFeatures = \"dialogWidth=520px;dialogHeight=420px;center=yes;help=no\";try{if (tmriPrint.ocx_version==undefined||ver!=tmriPrint.ocx_version){location.href=\"download.htm\"}}catch(e){location.href=\"download.htm\"}</script>";
			jspSuport.JS_CHECKDATA="<script language='javascript' src='frmjs/checkdata.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_COMMON="<script language='javascript' src='frmjs/common_func.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_FUSIONCHARTS="<script language='javascript' src='frmjs/FusionCharts.js?ver="+jspSuport.VERSION+"'></script><script language='javascript' src='frmjs/FusionChartsExportComponent.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_DATE="<script language='javascript' src='frmjs/date_func.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_OBJ="<script language='javascript' src='frmjs/obj_func.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_AJAX="<script language='javascript' src='frmjs/ajax_func.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_XTREE="<script language='javascript' src='frmjs/xtree_func.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_STR="<script language='javascript' src='frmjs/str_func.js?ver="+jspSuport.VERSION+"'></script>";
			jspSuport.JS_QUERYCOMM="<script language='javascript' src='frmjs/querycomm.js?ver="+jspSuport.VERSION+"'></script>";
		}
		if(jspSuport.JS_THEME==null)
			jspSuport.JS_THEME="style";
		jspSuport.JS_STYLE="<link href='theme/"+jspSuport.JS_THEME+"/style.css' rel='stylesheet' type='text/css'>"+"\n"+"<script language='JavaScript' src='theme/"+jspSuport.JS_THEME+"/style.js' type='text/javascript'></script>";
		jspSuport.JS_ALL=jspSuport.JS_CHECKDATA+"\n"+jspSuport.JS_JQUERY+"\n"+jspSuport.JS_JQUERY_MIGRATION+"\n"+jspSuport.JS_COMMON+"\n"+jspSuport.JS_DATE+"\n"+jspSuport.JS_OBJ+"\n"+jspSuport.JS_AJAX+"\n"+jspSuport.JS_STR+"\n"+jspSuport.JS_STYLE+"\n"+jspSuport.JS_FUSIONCHARTS+"\n";
		jspSuport.JS_ALL=jspSuport.JS_ALL + "<script language='javascript'>var V_SYS_SFT='" + Constants.SYS_SFT + "';var V_SSLPORT='"+Constants.SYS_SSLPORT+"';</script>\n";
		return jspSuport;
	}
	public void setSession(HttpSession session){
		if(session==null){
			JS_THEME="theme";
		}else{
			JS_THEME=(String)session.getAttribute("themestyle");
		}
	}
	public String getWebKey(HttpSession session,String keyname){
		HashMap map=(HashMap)session.getAttribute(Constants.SESSION_CHECKWEBKEY);
		if(map==null)
			map=new HashMap();
		SessionKey sessionKey=(SessionKey)map.get(keyname);
		String result=null;
		if(sessionKey==null){
			result="<input type='hidden' name='' value=''/>";
		}else{
			result="<input type='hidden' name='"+sessionKey.getRandname()+"' value='"+sessionKey.getRandvalue()+"'/>";
		}
		return result;
	}
	/**
	 * ����JSPҳ��WEBKEY���ر���
	 * 
	 * @param session
	 */
	public String getWebKey(HttpSession session){
		return getWebKey(session,"COMMON_KEY");
	}
	public String outputCalendar(){
		String resultString="<iframe src=\"frmjs/cal/ipopeng.htm\" name=\"gToday:normal:frmjs/cal/agenda.js:gfPop\""+"id=\"gToday:normal:frmjs/cal/agenda.js:gfPop\" width=\"174\" height=\"189\""+"scrolling=\"no\" frameborder=\"0\""+"style=\"visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0px;\"></iframe>\n";
		//wu 2010-10-28 ����Ĵ��뵼�� ��Ч���� /rmweb/frmjs/cal/frmjs/cal/agenda.js?1288234001515 ʹҳ��򿪽���
		//resultString+="<iframe src=\"frmjs/cal/ipopeng.htm\" name=\"gToday:normal:frmjs/cal/agenda.js:gfPoptime:plugins_time.js\""+"id=\"gToday:normal:frmjs/cal/agenda.js:gfPoptime:plugins_time.js\" width=\"174\" height=\"189\""+"scrolling=\"no\" frameborder=\"0\""+"style=\"visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0px;\"></iframe>\n";
		return resultString;
	}
	public String outputCalendar_now(){
		//String resultString="<iframe src=\"frmjs/cal/ipopeng.htm\" name=\"gToday:normal:frmjs/cal/agenda.js:gfPop\""+"id=\"gToday:normal:frmjs/cal/agenda.js:gfPop\" width=\"174\" height=\"189\""+"scrolling=\"no\" frameborder=\"0\""+"style=\"visibility: visible; z-index: 999; position: absolute; left: -500px; top: 0px;\"></iframe>\n";
		  String resultString="<iframe src=\"frmjs/cal/ipopeng.htm\" name=\"gToday:datetime:agenda.js:gfPoptime:plugins_time.js\" id=\"gToday:datetime:agenda.js:gfPoptime:plugins_time.js\" width=\"188\" height=\"166\"  scrolling=\"no\" frameborder=\"0\" style=\"visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;\"></iframe>\n";
		return resultString;
	}
	public String JS_TJKSRQ(){
		String tmp="var V_SG_TJKSR='"+Constants.SYS_SG_TJKSR+"';\n";
		tmp+="var V_SG_SFSYY='"+Constants.SYS_SG_SFSYY+"';\n";
		return tmp;
	}
	public String outputExpdiv(){
	    String tmp="<div id=\"fcexpDiv\" align=\"center\">FusionCharts Export Handler Component</div>";
	    return tmp;
	}
	public String outputExpChartHtml(){
	    String tmp="    var myExportComponent = new FusionChartsExportObject(\"fcExporter1\", \"chart/FCExporter.swf\");";
	    tmp=tmp+"myExportComponent.componentAttributes.width = '400';";
	    tmp=tmp+"myExportComponent.componentAttributes.height = '60';";
	    tmp=tmp+"myExportComponent.componentAttributes.bgColor ='ffffdd';";
	    tmp=tmp+"myExportComponent.componentAttributes.borderThickness = '2';";
	    tmp=tmp+"myExportComponent.componentAttributes.borderColor = '0372AB';";
	    tmp=tmp+"myExportComponent.componentAttributes.fontFace ='����';";
	    tmp=tmp+"myExportComponent.componentAttributes.fontColor = '0372AB';";
	    tmp=tmp+"myExportComponent.componentAttributes.fontSize = '12';";
	    tmp=tmp+"myExportComponent.componentAttributes.showMessage = '1';";
	    tmp=tmp+"myExportComponent.componentAttributes.message = '�ڱ������Ҽ�����,Ȼ���˰�ť����ͼƬ';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnWidth = '200';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnHeight = '25';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnColor ='E1f5ff';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnBorderColor = '0372AB';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnFontFace ='����';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnFontColor = '0372AB';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnFontSize = '15';";
	    tmp=tmp+"myExportComponent.componentAttributes.btnsavetitle = 'ͼƬ������,�������';";
	    tmp=tmp+"myExportComponent.componentAttributes.btndisabledtitle = '�������Ҽ����Ե���ͼƬ';";
	    tmp=tmp+"myExportComponent.Render(\"fcexpDiv\");";
	    return tmp;
	}

}
