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
<title>�û������޸�</title>
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
  alert ("����������룡");
  editpassform.jmm.focus();
  return false;
  }
  if (editpassform.xmm.value==""){
  alert ("�����������룡");
  editpassform.xmm.focus();
  return false;
  }
  if(editpassform.xmm.value == "888888"){
  alert ("�����벻��Ϊ��ʼ����888888��");
  editpassform.xmm.focus();
  return false;
  }
  if(editpassform.xmm.value.length<6){
  alert ("�����볤�Ȳ���С��6λ��");
  editpassform.xmm.focus();
  return false;
  }
  if (editpassform.xmm.value!=editpassform.qrxmm.value){
  alert ("������������뱣��һ�£�");
  editpassform.qrxmm.focus();
  return false;
  }
  if (editpassform.jmm.value==editpassform.xmm.value){
  alert ("�¾�����һ�£������޸ģ�");
  editpassform.xmm.focus();
  return false;
  }
  if (editpassform.yhdh.value==editpassform.xmm.value){
  alert ("�û����벻�����û�������ͬ��");
  editpassform.xmm.focus();
  return false;
  }
  var passLevel_obj = document.all("passwordLevel");  
  <%if(sysuser.getXtgly().equals("1")){ %>
 
  if(passLevel_obj.className=="rank r0"||passLevel_obj.className=="rank r1"||passLevel_obj.className=="rank r2"){
  	alert("ϵͳ����Ա�û�������ǿ�ȵȼ��������������ϣ�");
	editpassform.xmm.focus();
	return false;  	
  }
<%}else{%>
  if(passLevel_obj.className=="rank r0"||passLevel_obj.className=="rank r1"){
  	alert("��ͨ�û�������ǿ�ȵȼ�������������ϣ�");
	editpassform.xmm.focus();
	return false;  	
  }
<%}%>
  document.editpassform.action="login.frm?method=savepassword";
  document.editpassform.submit();
}

//���ؽ��
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
		<td width="450" height="23" align="left" background="theme/page/configurate_title.gif" style="font-size:12px;font-weight:bold;color:white;padding-left:10px;">�û������޸�</td>
		<td>&nbsp;</td>
	</tr>
</table>
<div class="s1" style="height:5px;"></div>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" style="width:100%">
<tr>
<td class="td1" width="100">�û�����&nbsp;</td>
<td class="td2">&nbsp;
<input type="text" name="yhdh" readonly value="<c:out value='${sysuser.yhdh}'/>" class="input_text" maxlength="12" style="width:145;background:#FBF1C9;color:555555"></td>
</tr>
<tr>
<td class="td1">������&nbsp;</td>
<td class="td2">&nbsp;
<input type="password" name="jmm"  class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td class="td1">������&nbsp;</td>
<td class="td2">&nbsp;
<input type="password" name="xmm"  class="input_text" maxlength="18" style="width:145" onkeyup="setPasswordLevel(this, editpassform.passwordLevel)" onblur="setPasswordLevel(this, editpassform.passwordLevel)">
<br>&nbsp;&nbsp;<a href="#" title="����ǿ���Ƕ������밲ȫ�Ը����������������ο���Ϊ���ʺŵİ�ȫ�ԣ�����ǿ�ҽ��������ø�ǿ�ȵ����롣��ǿ�ȵ�����Ӧ���ǣ�������Сд��ĸ�����ֺͷ��ţ��ҳ��Ȳ��˹��̣���ò�����10λ�����������ա��ֻ�������ױ��³�����Ϣ�����������ڸ������룬��Ҫ���װ������ʺŻ�������͸¶�����ˡ�">����ǿ��</a>
<input type="hidden" name="passwordLevel" id="passwordLevel"  title="����ǿ���Ƕ������밲ȫ�Ը����������������ο���Ϊ���ʺŵİ�ȫ�ԣ�����ǿ�ҽ��������ø�ǿ�ȵ����롣��ǿ�ȵ�����Ӧ���ǣ�������Сд��ĸ�����ֺͷ��ţ��ҳ��Ȳ��˹��̣���ò�����10λ�����������ա��ֻ�������ױ��³�����Ϣ�����������ڸ������룬��Ҫ���װ������ʺŻ�������͸¶�����ˡ�" class="rank r0" class='text_nobord'/></td>
</tr>
<tr>
<td class="td1">ȷ��������&nbsp;</td>
<td class="td2">&nbsp;
<input type="password" name="qrxmm" class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td colspan="2" class="td3">
<input type="button" name="q" value=" ȷ�� " onClick="CheckData()" style="font-size;12px" class="button1 small white">
<input type="button" value=" �ر� " onClick="quit();" style="font-size;12px;" class="button1 small white">
&nbsp;&nbsp;</td>
</tr>
<tr>
<td class="td3" colspan="2" width="280" style="text-align:left;">
<%if(sysuser.getXtgly().equals("1")){ %>
	<b>˵���������û�Ϊ<font color="red">��ϵͳ����Ա�˺š�</font>������ǿ�ȵȼ�����Ϊ<font color="red">��������</font>�����ϣ�����Ϊ�û����룬<font color="red">��������</font>�ı�׼Ϊ���볤�ȴ��ڵ�����λ�����ٰ������֡���ĸ�������ַ�</b>��
<%}else{ %>
	<b>˵���������û�Ϊ<font color="red">����ͨ�û��˺š�</font>������ǿ�ȵȼ�����Ϊ<font color="red">��������</font>�����ϣ�����Ϊ�û�����<font color="red">��������</font>�ı�׼Ϊ���볤�ȴ��ڵ�����λ�����ٰ������ֺ���ĸ��</b>
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
