<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.util.Constants"%>

<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.pub.bean.*"%>
<%@page import="java.util.*"%>
<%
SysService sysService = (SysService)request.getAttribute("sysService");
List list = (List) request.getAttribute("querylist");
Department depObj = (Department)request.getAttribute("department");
String sqms = (String) request.getAttribute("sqms");
%>
<html>
<head>
	<title>请选择功能名称</title>
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

function query(){
    document.userform.action="sysuser.frm?method=choosecdbhresult";
    document.userform.target="queryresult";
    document.userform.submit();
}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<body>
<form name="userform" action="" method="post" target="">
<input type="hidden" name="glbm" value="<%=depObj.getGlbm()%>"/>
<input type="hidden" name="sqms" value="<%=sqms%>"/>
<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
<tr>
	<%if(sqms.equals("1")){ %>
		<td width="10%" class="list_headrig">角色类型</td>
		<td width="20%" class="list_body_out">
			<select name="jslx"  class="text_12 text_size_150">
			<option value="">全部</option>
			<option value="1">自拥有角色</option>
			<option value="2" selected>自定义角色</option>
			<option value="3">继承自定义角色</option>
			</select>
		</td>		
		<td width="10%" class="list_headrig">角色名称：</td>
		<td width="60%" class="list_body_out" align="left">
			<input type="text" name="jsmc" class="input_text" maxlength="12" style="width:150">
			<input type=button class="button" value=" 查 询 " style="cursor:hand" alt="" onClick="query()">
			<input type=button class="button" name=btexit onclick="parent.window.close();" value=" 退 出 ">
		</td>
	<%}else if(sqms.equals("2")){ %>	
		<td width="30%" class="list_headrig">功能名称：</td>
		<td width="70%" class="list_body_out" align="left">
			<input type="text" name="cxmc" class="input_text" maxlength="12" style="width:150">
			<input type=button class="button" value=" 查 询 " style="cursor:hand" alt="" onClick="query()">
			<input type=button class="button" name=btexit onclick="parent.window.close();" value=" 退 出 ">
		</td>
	<%} %>
</tr>
</table>
</form>

<span class="s4"><br><br></span>
<iframe src="" name="queryresult" id="queryresult" marginwidth="1"
	marginheight="1" hspace="0" vspace="0" scrolling="auto" frameborder="0" allowtransparency="true" 
	style="width:100%;height:550"></iframe>  
<iframe name="paramIframe" style="DISPLAY: none"></iframe>


</body>
</html>
