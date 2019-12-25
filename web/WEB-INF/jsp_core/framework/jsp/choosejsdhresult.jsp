<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.framework.service.RoleManager"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="java.util.*"%>
<% 
SysService sysService = (SysService)request.getAttribute("sysService");
RoleManager roleManager = (RoleManager)request.getAttribute("roleManager");
List list = (List) request.getAttribute("querylist");
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");
GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)request.getAttribute("gSysparaCodeService");
%>
<html>
<head>
<title></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript" type="text/javascript">
function tondblclick(){
 	var val=getradio(formedit.xh);
	window.returnValue=val;
	window.close();
}		

function tok(){
 	var val=getradio(formedit.xh);
	window.returnValue=val;
	window.close();
}

function tt(){
	window.close();
}

function chooseradio(objid){
	document.getElementById(objid).checked=true;
}
</script>

<body>
<form name="formedit" action="" method="post" target="paramIframe">
<table  border="0" align="center" cellpadding="0" cellspacing="1" class="detail_table ">
   	<tr align="center" class="detail_head" height="20">
   	  <td width="5%">选择</td>
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
			String xh=role.getJsdh();
			String val=role.getJsdh()+"$"+role.getJsmc();
  %>  	
	<tr class="<%=str_class%>" align="center" onMouseOver="mouse_over(this)"
		     onMouseOut="mouse_out(this)" style="cursor:hand"
		     onclick="chooseradio('<%=xh%>');" ondblclick="tondblclick();">
		<td><INPUT TYPE="radio" NAME="xh" id="<%=xh%>" value="<%=val%>"></td>
		<td><%=role.getJsdh()%></td>
		<td><%=role.getJsmc()%></td>
		<td><%=gSysparaCodeService.transMultiCode(Constants.SYS_XTLB_FRAME,"0003",role.getJscj(),1,",")%></td>
		<td><%=roleManager.getJsmc(role.getSjjsdh())%></td>
		<td><%=roleManager.getJssx(role.getJssx())%></td>
		<td><%=gDepartmentService.getDepartmentName(role.getGlbm())%></td>
	</tr>
	<%
		}
	}
	%>
  </table>
</form>

<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1"   id="sorttable"  class="text_12">
	<tr>
		<td align="right">
			<input type="button" style="width:200" name="qr" value="确认" onclick="tok();">
			<input type="button" style="width:200" name="qx" value="取消" onclick="tt();">
		</td>
	</tr>
</table>

<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
</body>
</html>
