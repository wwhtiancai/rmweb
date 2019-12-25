<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>机动车登记信息查询验证码</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--

function dosubmit(){
	$("#myform").attr("action","<c:url value='/tfcQuery.tfc?method=checkValidCode'/>");
	$("#myform").ajaxSubmit({
		dataType:"json",
		async:true,
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:localReturns
	});
}


function dosure(){
	window.returnValue=$("#yzm").val();
	window.close();
	
}


function localReturns(data){
	window.returnValue=data;
	window.close();
	
}

//-->
</script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
<form action="" method="post" name="myform" id="myform">
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="detail">
		<col width="25%">
		<col width="75%">
		<tr>
			<td class="head" id="namepanel">验证码</td>
			<td  class="body">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50"><input name="yzm" id="yzm" type="text" maxlength="4" style="width:50;text-transform:uppercase;" value=""></td>
						<td width="5"></td>
						<td width="120"><iframe name="ifrmaeecsideveh" width="120" height="30" frameborder="0" src="tfcQuery.tfc?method=getValidImageDialog" allowTransparency="false" scrolling=no></iframe></td>
					</tr>
				</table>
            </td>
		</tr>
		<tr>
			<td colspan="2" height="27" style="background:#cccccc" align="center">
				<input type="button" onclick="dosubmit()" value=" 确定 " class="button_save">
				<input type="button" onclick="window.close()" value=" 关闭 " class="button_close">
			</td>
		</tr>
	</table>
</form>
</body>
</html>
