<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>汽车电子标识发行管理系统</title>
</head>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" bgcolor="#F8F8F8">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="20%">
	<tr>
		<td height="32" bgcolor="#094e89" style="font-size:14px;font-weight:bold;color:#FF5510;padding-top:3px;">&nbsp;汽车电子标识发行管理系统&nbsp;-&nbsp;关于</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#ffffff"></td>
	</tr>
	<tr>
		<td height="1" bgcolor="#707477"></td>
	</tr>
	<tr>
	<td height="9"></td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="55%">
	<tr><td>
		<ul valign="top" style="font-size:14px;color:black;line-height:18px;">
		<li>系统版本号：<span style="font-size:14px;color:008284;">V<%=Constants.SYS_VERSION%></span></li>
		<c:forEach items="${downloadlist}" var="current">
		<li>${current.ljmc}：<a href="${current.ljdz}" target="_blank" style="font-size:14px;color:blue;">下载</a></li>
 		</c:forEach>
 		</ul>
	</td></tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="9"></td>
	</tr>
	<tr>
		<td height="1" bgcolor="#707477"></td>
	</tr>
	<tr>
		<td height="1" bgcolor="#ffffff"></td>
	</tr>
	<tr>
		<td bgcolor="#EFF3FE" style="font-size:12px;color:#888888;padding-top:3px;line-height:15px;">
		&nbsp;&nbsp;版权所有：公安部交通管理科研所<br>
		&nbsp;&nbsp;地址：江苏省无锡市钱荣路88号 邮编：214151<br>
		&nbsp;&nbsp;电话：0510-85508551 85522205
		</td>
	</tr>
</table>
</body>
</html>
