<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>卡口过车数据接入量分析</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
	<c:if test="${bean!=null}">
		
	</c:if>
});
//-->
</script>
</head>
<body>
<form action="" method="post" name="myform" id="myform">
<div id="panel" style="display:none">
	<div id="paneltitle">卡口过车数据接入量分析</div>
	<div class="s1" style="height:7px;"></div>
	<table border="0" cellspacing="1" cellpadding="0" class="detail" id="article">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<td class="head">卡口编号</td>
			<td class="body" id="kkbhs"></td>
			<td class="head">卡口简称</td>
			<td class="body" id="kkjcs"></td>
			<td class="head">卡口名称</td>
			<td class="body" id="kkmcs"></td>
		</tr>
	</table>
	<table border="0" cellspacing="1" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button" name="printbutton" onclick="doPrint()" value="打印" class="button_print">
			<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>
