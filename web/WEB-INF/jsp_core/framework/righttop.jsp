<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="公安部,交通,管理,科研所,违法业务">
<meta http-equiv="description" content="公安部交通管理科学研究所">
<%
  SysService sysservice = (SysService)request.getAttribute("sysservice");
  %>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="rmjs/jquery.js"
			type="text/javascript"></script>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
.fieldset{
	width:88%;
	border-width:thin;
	text-align:left;
	padding:2px 5px 7px 5px;
}
.legend{
	font-size:12px;
	color:black;
}
.table{
	background:#636563;
}
.td1{
	font-size:12px;
	color:black;
	background:#CEDFE7;
	text-align:right;
	padding:4px 7px 4px 4px;
}
.td2{
	font-size:12px;
	color:black;
	background:#FFFBFF;
	text-align:left;
	padding:4px 7px 4px 7px;
}
.td3{
	font-size:12px;
	color:black;
	background:#eeeeee;
	text-align:right;
	padding:4px 11px 4px 7px;
}
.help{
	font-size:12px;
	color:red;
}

#ax{width:250px;height:50px;background:#EEEEEE;position:fixed;right:20px;bottom:20px;}

</style>
<script language="JavaScript" type="text/javascript">
function showMenu(curId, url){
	parent.rightmainfrm1.location.href = url;
	var menuCount = 2 + <c:out value="${dtsz}" />;
	for(var i = 1; i <= menuCount; ++i){
		try{
			if(i == curId){
				document.getElementById("vio_" + i).background = 'theme/blue/vio_main_r3_c8.jpg';
			}else{
				document.getElementById("vio_" + i).background = 'theme/blue/vio_main_r3_c7.jpg';
			}
		}catch(e){
		
		}		
	}
}

</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td height="30" background="theme/blue/vio_main_r3_c6.jpg">
		<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td width="30">&nbsp;</td>
		<c:if test="${desktoplist!=null}">
		<c:forEach items="${desktoplist}" var="current" varStatus="order">
			<c:if test="${current.cdbh!='0000'}">
				<td width="85" background="theme/blue/vio_main_r3_c7.jpg" class="text_12" id="vio_<c:out value='${order.index + 2}' />" style="text-align:center;padding-top:10px;cursor:hand"><a href="#" target="rightmainfrm1" style="font-size:12px;" onclick="showMenu(<c:out value='${order.index + 2}' />, '<c:out value="${current.ymdz}"/>')"><c:out value="${current.cxmc}"/></a></td>
			</c:if>		
		</c:forEach>
		</c:if>
		<td width="85" background="theme/blue/vio_main_r3_c8.jpg" class="text_12" id="vio_1" style="text-align:center;padding-top:10px;cursor:hand"><a herf="#" style="font-size:12px;" onclick="showMenu(1, 'main.frm?method=right')">我的桌面</a></td>              
		<td align="right" style="font-size:12px;padding-right:10px;">用户：<span style="color:#BD2431;font-weight:bold;"><c:out value='${userSession.sysuser.xm}'/></span>，部门：<span style="color:#BD2431;font-weight:bold;"><c:out value='${userSession.department.bmmc}'/></span></td>
		</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
