<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>������֤��</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
	$("#yzm").keydown(function(e){
		var curkey=e.which;
		if(curkey==13){
			$("#btnquery").click();
			return false;
		}
	});
});

function dosubmit(){
	var cyzm = $("#yzm").val();
	if(cyzm.length != 4){
		alert("��������λ��֤�룡");
		return;
	}
	window.returnValue=cyzm;
	window.close();
}

//-->
</script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
<table border="0" cellspacing="0" cellpadding="0" align="center" class="detail">
	<col width="25%">
	<col width="75%">
	<tr>
		<td class="head" id="namepanel">��֤��</td>
		<td class="body">
			<input name="yzm" id="yzm" type="text"  maxlength="4"  style="width:75;text-transform: uppercase;"  value="" >
		    <iframe name="ifrmaeecsideveh" width="150" height="30" frameborder="0" src="tfcQuery.tfc?method=getValidImageDialog" allowTransparency="false" scrolling=no></iframe>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="27" style="background:#cccccc" align="center">
			<input type="button" id="btnquery" onclick="dosubmit()" value=" ȷ�� " class="button_save">
			<input type="button" onclick="window.close()" value=" �ر� " class="button_close">
		</td>
	</tr>
</table>
</body>
</html>
