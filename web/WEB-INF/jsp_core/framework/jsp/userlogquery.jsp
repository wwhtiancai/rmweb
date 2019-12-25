<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.service.GDepartmentService"%>
<%@page import="com.tmri.share.frm.service.GSystemService"%>
<%@page import="java.util.List"%>
<%@page import="com.tmri.share.frm.bean.Department"%>
<% 
String cdbh = (String)request.getAttribute("cdbh");

GSystemService sysService = (GSystemService)request.getAttribute("gSystemService");
String cdmc = sysService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
Department dep = sysService.getSessionUserInfo(session).getDepartment();
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");
Department depObj=gDepartmentService.getDepartment(gDepartmentService.getZdGlbm(dep));
%>
<html>
<head>
<title><%=cdmc%></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="js/drvjs/calendar.js"    type="text/javascript"></script>
<script language="JavaScript" src="theme/blue/blue1.js"    type="text/javascript"></script>
<script language="JavaScript">
function init(){
	formquery.ksrq.value=getTodayN(-7);
	formquery.jsrq.value=getToday();
}

function query(){
	formquery.isxls.value="6";
    formquery.action="log.frm?method=logfrmresulttrack";
    formquery.target="queryresult";
    formquery.submit();
}
function xls(){
	formquery.isxls.value="7";
    formquery.action="log.frm?method=logfrmresulttrack";
    formquery.target="queryresult";
    formquery.submit();
}
 
function fnOpen(){
  	var value=formquery.glbm.value;
	var sFeatures="dialogHeight:400px;dialogWidth:400px;help:no;status:no ";
	var vReturnValue = window.showModalDialog("dept.conf?method=getDeptList&showOffice=true&glbm="+value, "",sFeatures);
	if(typeof vReturnValue!= "undefined"){
		changebmdm(vReturnValue);
	}
}

//将部门代码$部门名称
function changebmdm(vReturnValue){
	var idx=vReturnValue.indexOf("$");
	formquery.glbm.value=vReturnValue.substring(0,idx);
	formquery.bmmc.value=vReturnValue.substring(idx+1);
}

</script>

<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<body onload="init();">
<script language="JavaScript" type="text/javascript">vio_title('<%=cdmc%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<div class="s1" style="height: 3px;"></div>

<form name="formquery" method="post" action="">
<input type="hidden" name="glbm" value="<%=depObj.getGlbm()%>"/>
<input type="hidden" name="isxls" value="6"/>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
  <tr>
	<td width="10%" class="list_head" align="right">管理部门</td>
	<td width="30%" class="list_head">
		<input name="bmmc" type="text"  value="<%=depObj.getBmmc()%>"  
			class="text_12 text_size_150" readOnly> <input type=button
			value="选择" onclick="fnOpen();" class="button_query" style="height: 19">
			包括下级 <input type=checkbox name=bhxj value="1" checked>
	</td>

	<td width="10%" class="list_head" align="right">开始日期</td>
	<td width="20%" class="list_head">
		<input type="text" name="ksrq" style="width:70" value="">
		<a href="#" onClick="riqi('ksrq')" HIDEFOCUS><img
				src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
				width="34" height="22" border="0" align="absmiddle">
		</a>		
	</td>
	<td width="10%" class="list_head" align="right">结束日期</td>
	<td width="20%" class="list_head">
		<input type="text" name="jsrq" style="width:70" value="">
		<a href="#" onClick="riqi('jsrq')" HIDEFOCUS><img
				src="frmjs/cal/calbtn.gif" alt="" name="popcal" id="popcal"
				width="34" height="22" border="0" align="absmiddle">
		</a>
	</td>   
  </tr>	
  <tr>
	<td width="10%" class="list_head" align="right">操作用户</td>
	<td width="20%"  class="list_head">
		<input name="yhdh" value="" type="text"	class="text_12 text_size_150"> 
	</td>	
	
	<td width="10%" class="list_head" align="right">操作内容</td>
	<td width="20%"  class="list_head">
		<input name="cznr" value="" type="text"	class="text_12 text_size_150"> 
	</td>
	
    <td  align="right" class="list_body_out" colspan="2">
      <input name="btquery" type="button" class="button" value=" 查 询 "  onclick="query();">
      <input name="btquery" type="button" class="button" value=" 导出excel "  onclick="xls();">
      <input name="btclose" type="button" class="button" value=" 退 出 "  onclick="quit();">
    </td> 	
  </tr>		
</table>
</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="5"></td>
    </tr>
</table>

<iframe src="" name="queryresult" id="queryresult" marginwidth="1"
        marginheight="1" hspace="0" vspace="0" scrolling="yes" frameborder="0" 
        allowtransparency="true" style="width:100%;height:400"></iframe>
        

<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>

<%=JspSuport.getInstance().outputCalendar()%>
</body>
</html>                                                    