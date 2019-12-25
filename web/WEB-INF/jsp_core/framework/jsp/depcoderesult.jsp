<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.util.StringUtil"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="java.util.*"%>
<% 
String cdbh = (String)request.getAttribute("cdbh");
SysService sysService = (SysService)request.getAttribute("sysService");
GSystemService gSystemService = (GSystemService)request.getAttribute("gSystemService");
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");

String cdmc = gSystemService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
List list = (List) request.getAttribute("querylist");
%>
<html>
<head>
<title><%=cdmc%></title>
</head>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function showdetail(glbm,dmlb,dmz){
	parent.showdetail(glbm,dmlb,dmz);
}
</script>

<body>
<table  border="0" align="center" cellpadding="0" cellspacing="1" class="detail_table ">
   	<tr align="center" class="detail_head" height="20">
      <td width="12%">代码值</td>
      <td width="15%">名称</td>
      <td width="20%">使用部门</td>
      <td width="8%">联系人</td>
      <td width="8%">联系电话</td>
      <td>地址</td>
      <td width="15%">创建部门</td>
      <td width="6%">状态</td>      
    </tr>	
<%
	if (list != null) {
		for (int i = 0; i < list.size(); i++) {
			Depcode obj = (Depcode) list.get(i);
			String str_class = "list_body_tr_1";
        	if (i % 2 == 0) {
          		str_class = "list_body_tr_2";
        	}	  	 
        	String zt="";
        	if(obj.getZt().equals("1")){
        		zt="正常";
        	}else if(obj.getZt().equals("2")){
        		zt="停用";
        	}
  %>  	
 	<tr class="<%=str_class%>" height="23" align="center" style="cursor:hand"
		onClick="showdetail('<%=obj.getGlbm()%>','<%=obj.getDmlb()%>','<%=obj.getDmz()%>')">
        <td><%=obj.getDmz()%></td>
        <td><%=obj.getDmsm1()%></td>
        <td><%=sysService.transGlbmdmToGlbmmc(obj.getSyglbm(),",")%></td>
        <td><%=obj.getDmsm2()%></td>
        <td><%=obj.getDmsm3()%></td>
        <td><%=StringUtil.displayMax(obj.getDmsm4(),10)%></td>
        <td><%=gDepartmentService.getDepartmentName(obj.getCjglbm())%></td>
        <td><%=zt%></td>        
	</tr>
	<%
	}
	%>
	<tr class="">
		<td colspan="8" align="right" class="page"><c:out
			value="${controller.clientScript}" escapeXml="false" /> <c:out
			value="${controller.clientPageCtrlDesc}" escapeXml="false" />
		</td>
	</tr>
	<%
	}
	%>
  </table>

<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
</body>
</html>
