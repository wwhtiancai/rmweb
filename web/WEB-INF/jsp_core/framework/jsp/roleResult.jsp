<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.framework.service.RoleManager"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="com.tmri.share.frm.service.GSystemService"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="java.util.*"%>
<% 
String cdbh = (String)request.getAttribute("cdbh");
GSystemService sysService = (GSystemService)request.getAttribute("gSystemService");
GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)request.getAttribute("gSysparaCodeService");
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");

RoleManager roleManager = (RoleManager)request.getAttribute("roleManager");
String cdmc = sysService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
List list = (List) request.getAttribute("queryList");
%>
<html>
<head>
<title><%=cdmc%></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript" type="text/javascript">
function showdetail(jsdh){
	parent.showdetail(jsdh);
}
</script>

<body>
<table  border="0" align="center" cellpadding="0" cellspacing="1" class="detail_table ">
   	<tr align="center" class="detail_head" height="20">
   	  <td width="5%">序号</td>
	  <td width="8%">角色代号</td>
	  <td width="20%">角色名称</td>
	  <td width="8%">角色层级</td>
	  <td width="20%">上级角色</td>
	  <td width="8%">角色属性</td>
	  <td>创建角色部门</td>
    </tr>	
<%
	if (list != null) {
		for (int i = 0; i < list.size(); i++) {
			Role role = (Role) list.get(i);
			String str_class = "list_body_tr_1";
        	if (i % 2 == 0) {
          		str_class = "list_body_tr_2";
        	}	  	 
  %>  	
    <tr class="<%=str_class%>" height="23" align="center" style="cursor:hand"
		onClick="showdetail('<%=role.getJsdh()%>')">
		<td><%=(i + 1)%></td>
		<td><%=role.getJsdh()%></td>
		<td><%=role.getJsmc()%></td>
		<td><%=gSysparaCodeService.transMultiCode(Constants.SYS_XTLB_FRAME,"0003",role.getJscj(),1,",")%></td>
		<td><%=roleManager.getJsmc(role.getSjjsdh())%></td>
		<td><%=roleManager.getJssx(role.getJssx())%></td>
		<td><%=gDepartmentService.getDepartmentName(role.getGlbm())%></td>
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
