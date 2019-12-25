<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.SysUser"%>
<html>
<head>
<%
String message=(String)request.getAttribute("message");
SysUser sysuser = (SysUser)request.getAttribute("sysuser");
System.out.println("Sysuser is null:" + (sysuser == null));
System.out.println(message);
%>
<title>
用户密码修改
</title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
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
  document.editpassform.action="login.frm?method=savePlspassword";
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
<script language="JavaScript" type="text/javascript">vio_title('用户密码修改')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<script language="JavaScript" type="text/javascript">ts_title('密码修改')</script>
<form name="editpassform" method="post"  action="" target="paramIframe">
<table border="0" cellspacing="1" cellpadding="0" class="list_table">
<tr>
<td class="list_head" width="100">用户代码&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="text" name="yhdh" readonly value="<c:out value='${sysuser.yhdh}'/>" class="input_text" maxlength="12" style="width:145"></td>
<td class="list_body_out" rowspan="5" width="280">
<%if(sysuser.getXtgly().equals("1")){ %>
	<b>说明：您的用户为<font color="red">【系统管理员账号】</font>，密码强度等级必须为<font color="red">【三级】</font>及以上，不能为用户代码，<font color="red">【三级】</font>的标准为密码长度大于等于六位，至少包含数字、字母及特殊字符</b>。
<%}else{ %>
	<b>说明：您的用户为<font color="red">【普通用户账号】</font>，密码强度等级必须为<font color="red">【二级】</font>及以上，不能为用户代吗，<font color="red">【二级】</font>的标准为密码长度大于等于六位，至少包含数字和字母。</b>
<%} %>
</td>
</tr>
<tr>
<td class="list_head">旧密码&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="password" name="jmm"  class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td class="list_head">新密码&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="password" name="xmm"  class="input_text" maxlength="18" style="width:145" onkeyup="setPasswordLevel(this, editpassform.passwordLevel)" onblur="setPasswordLevel(this, editpassform.passwordLevel)">密码强度&nbsp;<input type="text" name="passwordLevel" id="passwordLevel"  title="密码强度是对您密码安全性给出的评级，供您参考。为了帐号的安全性，我们强烈建议您设置高强度的密码。高强度的密码应该是：包括大小写字母、数字和符号，且长度不宜过短，最好不少于10位。不包含生日、手机号码等易被猜出的信息。建议您定期更换密码，不要轻易把您的帐号或者密码透露给别人。" class="rank r0" class='text_nobord' disabled="disabled" /></td>
</tr>
<tr>
<td class="list_head">确认新密码&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="password" name="qrxmm" class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td class="list_head">&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="button" name="q" value=" 确 认 " class="button" onclick="CheckData()">&nbsp;<input name="exit" type=button class="button" style="cursor:hand;"  value=" 退 出 " onclick="quit()"></td>
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
