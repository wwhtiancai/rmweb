<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>公安用户管理</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="css/xtree.css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
	ch = parent.getBodyHeight() - 106;
	$("#ifrm_plsdeplist").attr("src", "department.vmc?method=listPolice");
	$("#ifrm_plsuserlist").attr("src", "sysuser.vmc?method=fwdPlsSysUserMain");
	$("#ifrm_plsdeplist").css("height", ch);
	$("#ifrm_plsuserlist").css("height", ch);
});
function editGlbm(s_glbm){
	$("#ifrm_plsuserlist").attr("src", "sysuser.vmc?method=fwdPlsSysUserMain&glbm=" + s_glbm);
}
//-->
</script>
</head>
<body onUnload="closesubwin();">
<table border="0" cellpadding="0" cellspacing="0" width="100%" style="padding:0px; margin:0px;">
	<tr>
		<td width="25%" valign="top">
			<fieldset id="fld" style="border:1px solid #CCCCCC;width:100%;">
				<legend style="font-size: 12px;">部门选择</legend>
				<iframe src="" name="ifrm_plsdeplist" id="ifrm_plsdeplist" marginwidth="1" marginheight="1" hspace="0" vspace="0" 
			       	scrolling="yes" frameborder="0" style="width:100%;height:550px;">
				</iframe>
			</fieldset>
		</td>
		<td width="6px"></td>
		<td valign="top">
			<iframe src="" name="ifrm_plsuserlist" id="ifrm_plsuserlist" marginwidth="1" marginheight="1" hspace="0" vspace="0" 
				scrolling="auto" frameborder="0" style="width:100%;height:550;"></iframe>
		</td>
	</tr>
</table>
</body>
</html>
