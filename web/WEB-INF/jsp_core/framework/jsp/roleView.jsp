<%@include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.framework.service.RoleManager"%>
<%@page import="java.util.*"%>
<html>
<head>
<title>角色权限维护</title>
<%
	Role role = (Role)request.getAttribute("frmRole");
	//List foldList = (List)request.getAttribute("foldList");
	//String roleMenus = (String)request.getAttribute("roleMenus");
	String modal = (String)request.getAttribute("modal");
	//String jbs = (String)request.getAttribute("jbs");
	SysService sysService = (SysService)request.getAttribute("sysService");
	GHtmlService gHtmlService = (GHtmlService)request.getAttribute("gHtmlService");
	RoleManager roleManager = (RoleManager)request.getAttribute("roleManager");
	List roleList = (List)request.getAttribute("roleList");
	List jbslist = (List)request.getAttribute("jbslist");
	String jslx = (String)request.getAttribute("jslx");
 %>
</head>
<link href="theme/domtab/domtab.css" rel="stylesheet" type="text/css"> 
<script language="javascript" type="text/javascript"  src="theme/domtab/domtab.js"></script>
<%=JspSuport.getInstance().JS_ALL%>
<script languag="javascript">
function init(jssx,jsdh){
	var tmpsx;
	var i = 0;
	checkfields[i++] = new CheckObj("jsmc","角色名称",FRM_CHECK_NULL,0,1);
	checkfields[i++] = new CheckObj("cxdh","角色权限",FRM_CHECK_NULL,0,0);
	checklen = i;	
	//获取可选择菜单
	changemenu('<%=role.getSjjsdh()%>','<%=role.getJsdh()%>','<%=role.getJscj()%>');
}


function changejscj(){
	var sjjsdh=formedit.sjjsdh.value;
	var jsdh=formedit.jsdh.value;
	var jscj=formedit.jscj.value;
	changemenu(sjjsdh,jsdh,jscj);
}

//改变属性层级
function changemenu(sjjsdh,jsdh,jscj){
	var spath="role.frm?method=rolemenuresult&jsdh="+jsdh+"&jscj=" + jscj;
	send_request(spath,"filljscj()",false);
}

function filljscj(){
	//处理管辖部门
	var results = _xmlHttpRequestObj.responseText;
	divroles.innerHTML=results;
	domtab.init();
}
function tab_c(idx){
	var tab=document.getElementsByName("tab")
	for(var i=0;i<tab.length;i++) {
		tab[i].style.display="none";
	}
	var idxtab=document.getElementById("tab"+idx);
	idxtab.style.display=""
}

function del(){
	if(confirm("是否确人删除该角色？")){
		if(confirm("将会删除该角色及其子角色，该角色及其子角色对应权限，"
				+"用户拥有该角色及其子角色的操作权限，用户拥有该角色及其子角色的管理权限，"
				+"再次确认是否删除?")){
			document.formedit.action="role.frm?method=removeRole";
			document.formedit.submit();
		}
	}
}

//返回结果
function resultsubmit(code,result,message){
  if(code=='2'){
	displayInfoHtml(message);
  	window.opener.query();
  	window.close();
  }else {
	displayInfoHtml(message);
  }
}

</script>

<body onLoad="init('<%=role.getJssx()%>','<%=role.getJsdh()%>')">
<script language="JavaScript" type="text/javascript">vio_title('角色管理')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<div class="s1" style="height:4px;"></div>
	<form name="formedit" action="" method="post" target="paramIframe">
	<input type="hidden" name="jsdh" value="<%=role.getJsdh()%>">
    <input type="hidden" name="cxdh">
    <input type="hidden" name="jssx" value="<%=role.getJssx()%>">
	<%=JspSuport.getInstance().getWebKey(session)%>
	<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
		<tr>
			<td colspan="4" class="list_body_out" align="right" >
			<%if(jslx.equals("3")){%>
			<input type="button" name="delbutton" onClick="del()" value=" 删 除 " class="button">
			<%}%>
	       	<input type="button" name="Submit2" value=" 退 出 " onclick="quit()" class="button">
	       	</td>
		</tr>	
		<tr>
			<td class="list_headrig" width="10%">角色名称</td>
			<td class="list_body_out" align="left"><input type="text" name="jsmc" style="width:150" value="<%=role.getJsmc()%>"></td>
			<td class="list_headrig" width="10%">角色级别</td>
			<td class="list_body_out" align="left">
				<%=gHtmlService.transListToCheckBoxHtml(jbslist, "jscj1", role.getJscj(), "3")%>
			</td>
		</tr>
		<tr>
			<td class="list_headrig">上级角色代号</td>
			<td class="list_body_out" align="left"><select name="sjjsdh" style="width:150" onchange="changejscj();">
				<%=gHtmlService.transListToOptionHtml(roleList, role.getSjjsdh(), false, "3")%>
			</select></td>
			
			<td class="list_headrig">角色属性</td>
			<td class="list_body_out" align="left"><input type="text" readOnly name="jssxmc" style="width:150" value="<%=roleManager.getJssx(role.getJssx())%>"></td>
		</tr>		
		<tr>
			<td class="list_headrig">角色权限</td>
			<td class="list_body_out" colspan="3" id="divroles">
			</td>
		</tr>
		<tr>
			<td class="list_headrig">备　　注</td>
			<td class="list_body_out" colspan="3" align="left"><input type="text" name="bz" style="width:98%" value="<%=role.getBz()%>"></td>
		</tr>						

	</table></form>
<script language="JavaScript" type="text/javascript">vio_down();</script>

<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
</body>
</html>