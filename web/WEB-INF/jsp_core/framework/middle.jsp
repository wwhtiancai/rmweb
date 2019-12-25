<%@ page contentType="text/html; charset=gb2312"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="公安部,交通,管理,科研所,违法业务">
<meta http-equiv="description" content="公安部交通管理科学研究所">
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
<script language="JavaScript" type="text/javascript">
<!--
var s=0;
function doexchange(){
	if(s==0){
		top.frames["controlfrm"].cols="0,9,*";
		controlimg.src="theme/middle_exchange_close.jpg";
		s=1;
	}else{
		top.frames["controlfrm"].cols="200,9,*";
		controlimg.src="theme/middle_exchange_open.jpg";
		s=0;
	}
}
//-->
</script>
</head>
<body class="main_center">
<table height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" style="cursor:hand" onClick="doexchange()"><img src="theme/middle_exchange_open.jpg" alt="" name="controlimg" id="controlimg" border="0"></td>
	</tr>
</table>
</body>
</html>
