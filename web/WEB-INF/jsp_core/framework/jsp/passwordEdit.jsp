<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.SysUser"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
String message=(String)request.getAttribute("message");
SysUser sysuser = (SysUser)request.getAttribute("sysuser");
System.out.println("Sysuser is null:" + (sysuser == null));
System.out.println(message);
%>
<title>用户密码修改</title>
</head>
<style>
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

#ax{
	width:250px;
	height:50px;
	background:#EEEEEE;
	position:fixed;
	right:20px;
	bottom:20px;
}
.button1{
	display: inline-block;
	zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */
	*display: inline;
	vertical-align: baseline;
	margin: 0 2px;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	padding: .5em 2em .55em;
	text-shadow: 1px 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
	-webkit-box-shadow: 0 2px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 2px 2px rgba(0,0,0,.2);
	box-shadow: 0 3px 3px rgba(0,0,0,.2);
}
.button1:hover {
	text-decoration: none;
}
.button1:active {
	position: relative;
	top: 1px;
}
.medium {
	font-size: 12px;
	padding: .4em 1.5em .42em;
}
.small {
	font-size: 12px;
	padding: .2em .1em .2em;
}
/* white */
.white {
	color: #000000;
	border: solid 2px #000000;
	background: #fff;
	background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#ededed));
	background: -moz-linear-gradient(top,  #fff,  #ededed);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#ededed');
}
.white:hover {
	background: #ededed;
	background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#dcdcdc));
	background: -moz-linear-gradient(top,  #fff,  #dcdcdc);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#dcdcdc');
}
.white:active {
	color: #999;
	background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#fff));
	background: -moz-linear-gradient(top,  #ededed,  #fff);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#ededed', endColorstr='#ffffff');
}

</style>
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
<script language="JavaScript" src="frmjs/ajax_func.js" type="text/javascript"></script>
<script language="javascript" type="text/JavaScript" src="js/query/jquery.js"></script>
<script language="javascript">
function CheckData(){
  if (editpassform.jmm.value==""){
  alert ("请输入旧密码！");
  editpassform.jmm.focus();
  return false;
  }
  if (editpassform.xmm.value==""){
  alert ("请输入新密码！");
  editpassform.xmm.focus();
  return false;
  }
  if(editpassform.xmm.value == "888888"){
  alert ("新密码不能为初始密码888888！");
  editpassform.xmm.focus();
  return false;
  }
  if(editpassform.xmm.value.length<6){
  alert ("新密码长度不得小于6位！");
  editpassform.xmm.focus();
  return false;
  }
  if (editpassform.xmm.value!=editpassform.qrxmm.value){
  alert ("二次新密码必须保持一致！");
  editpassform.qrxmm.focus();
  return false;
  }
  if (editpassform.jmm.value==editpassform.xmm.value){
  alert ("新旧密码一致，无须修改！");
  editpassform.xmm.focus();
  return false;
  }
  if (editpassform.yhdh.value==editpassform.xmm.value){
  alert ("用户密码不能与用户代码相同！");
  editpassform.xmm.focus();
  return false;
  }
  var passLevel_obj = document.all("passwordLevel");  
  <%if(sysuser.getXtgly().equals("1")){ %>
 
  if(passLevel_obj.className=="rank r0"||passLevel_obj.className=="rank r1"||passLevel_obj.className=="rank r2"){
  	alert("系统管理员用户，密码强度等级必须三级及以上！");
	editpassform.xmm.focus();
	return false;  	
  }
<%}else{%>
  if(passLevel_obj.className=="rank r0"||passLevel_obj.className=="rank r1"){
  	alert("普通用户，密码强度等级必须二级及以上！");
	editpassform.xmm.focus();
	return false;  	
  }
<%}%>
  document.editpassform.action="login.frm?method=savepassword";
  document.editpassform.submit();
}

//返回结果
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	alert(strMessage);
 	<%if(message!=null&&!message.equals("")){%>
 	window.location.href="index.jsp";
 	<%}else{%>
 	opener.parent.location.href="index.jsp";
 	window.close();
 	<%}%>
  }
  else
  {
    alert(strMessage);
  }
}

</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<link href="css/i_new2.0.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<body>
<form name="editpassform" method="post"  action="" target="paramIframe">
<div class="s1" style="height:7px;"></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#e7e7e7">
	<tr>
		<td width="450" height="23" align="left" background="theme/page/configurate_title.gif" style="font-size:12px;font-weight:bold;color:white;padding-left:10px;">用户密码修改</td>
		<td>&nbsp;</td>
	</tr>
</table>
<div class="s1" style="height:5px;"></div>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" style="width:100%">
<tr>
<td class="td1" width="100">用户代码&nbsp;</td>
<td class="td2">&nbsp;
<input type="text" name="yhdh" readonly value="<c:out value='${sysuser.yhdh}'/>" class="input_text" maxlength="12" style="width:145;background:#FBF1C9;color:555555"></td>
</tr>
<tr>
<td class="td1">旧密码&nbsp;</td>
<td class="td2">&nbsp;
<input type="password" name="jmm"  class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td class="td1">新密码&nbsp;</td>
<td class="td2">&nbsp;
<input type="password" name="xmm"  class="input_text" maxlength="18" style="width:145" onkeyup="setPasswordLevel(this, editpassform.passwordLevel)" onblur="setPasswordLevel(this, editpassform.passwordLevel)">
<br>&nbsp;&nbsp;<a href="#" title="密码强度是对您密码安全性给出的评级，供您参考。为了帐号的安全性，我们强烈建议您设置高强度的密码。高强度的密码应该是：包括大小写字母、数字和符号，且长度不宜过短，最好不少于10位。不包含生日、手机号码等易被猜出的信息。建议您定期更换密码，不要轻易把您的帐号或者密码透露给别人。">密码强度</a>
<input type="hidden" name="passwordLevel" id="passwordLevel"  title="密码强度是对您密码安全性给出的评级，供您参考。为了帐号的安全性，我们强烈建议您设置高强度的密码。高强度的密码应该是：包括大小写字母、数字和符号，且长度不宜过短，最好不少于10位。不包含生日、手机号码等易被猜出的信息。建议您定期更换密码，不要轻易把您的帐号或者密码透露给别人。" class="rank r0" class='text_nobord'/></td>
</tr>
<tr>
<td class="td1">确认新密码&nbsp;</td>
<td class="td2">&nbsp;
<input type="password" name="qrxmm" class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td colspan="2" class="td3">
<input type="button" name="q" value=" 确认 " onClick="CheckData()" style="font-size;12px" class="button1 small white">
<input type="button" value=" 关闭 " onClick="quit();" style="font-size;12px;" class="button1 small white">
&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="td3" colspan="2" width="280" style="text-align:left;">
<%if(sysuser.getXtgly().equals("1")){ %>
	<b>说明：您的用户为<font color="red">【系统管理员账号】</font>，密码强度等级必须为<font color="red">【三级】</font>及以上，不能为用户代码，<font color="red">【三级】</font>的标准为密码长度大于等于六位，至少包含数字、字母及特殊字符</b>。
<%}else{ %>
	<b>说明：您的用户为<font color="red">【普通用户账号】</font>，密码强度等级必须为<font color="red">【二级】</font>及以上，不能为用户代吗，<font color="red">【二级】</font>的标准为密码长度大于等于六位，至少包含数字和字母。</b>
<%} %>
</td>
</tr>
 <tr>
<td colspan="2" bordercolor="#C0C0C0" align="center"><font color="#FF0000"> <c:out value="${message}"/></font></td>
</tr>
</table>
</form>
<script language="JavaScript" type="text/javascript">vio_down()</script>   
<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>
