<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<%@ page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title>公安部门管理</title>
<link type="text/css" rel="stylesheet" href="css/xtree.css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<%=JspSuport.getInstance().JS_ALL%>
<%=JspSuport.getInstance().JS_XTREE%>
<script type="text/javascript">
<!--
$(document).ready(function(){
	parent.editGlbm('<c:out value="${department}"/>');
});
function editGlbm(glbm){
	parent.editGlbm(glbm);
}
//-->
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="1" cellpadding="0" align="center">
	<tr>
		<td><script><c:out value="${departmenttree}" escapeXml="false"/></script></td>
	</tr>
</table>
</body>
</html>
