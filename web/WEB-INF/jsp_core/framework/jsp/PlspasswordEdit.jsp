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
�û������޸�
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
  document.editpassform.action="login.frm?method=savePlspassword";
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
<script language="JavaScript" type="text/javascript">vio_title('�û������޸�')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<script language="JavaScript" type="text/javascript">ts_title('�����޸�')</script>
<form name="editpassform" method="post"  action="" target="paramIframe">
<table border="0" cellspacing="1" cellpadding="0" class="list_table">
<tr>
<td class="list_head" width="100">�û�����&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="text" name="yhdh" readonly value="<c:out value='${sysuser.yhdh}'/>" class="input_text" maxlength="12" style="width:145"></td>
<td class="list_body_out" rowspan="5" width="280">
<%if(sysuser.getXtgly().equals("1")){ %>
	<b>˵���������û�Ϊ<font color="red">��ϵͳ����Ա�˺š�</font>������ǿ�ȵȼ�����Ϊ<font color="red">��������</font>�����ϣ�����Ϊ�û����룬<font color="red">��������</font>�ı�׼Ϊ���볤�ȴ��ڵ�����λ�����ٰ������֡���ĸ�������ַ�</b>��
<%}else{ %>
	<b>˵���������û�Ϊ<font color="red">����ͨ�û��˺š�</font>������ǿ�ȵȼ�����Ϊ<font color="red">��������</font>�����ϣ�����Ϊ�û�����<font color="red">��������</font>�ı�׼Ϊ���볤�ȴ��ڵ�����λ�����ٰ������ֺ���ĸ��</b>
<%} %>
</td>
</tr>
<tr>
<td class="list_head">������&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="password" name="jmm"  class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td class="list_head">������&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="password" name="xmm"  class="input_text" maxlength="18" style="width:145" onkeyup="setPasswordLevel(this, editpassform.passwordLevel)" onblur="setPasswordLevel(this, editpassform.passwordLevel)">����ǿ��&nbsp;<input type="text" name="passwordLevel" id="passwordLevel"  title="����ǿ���Ƕ������밲ȫ�Ը����������������ο���Ϊ���ʺŵİ�ȫ�ԣ�����ǿ�ҽ��������ø�ǿ�ȵ����롣��ǿ�ȵ�����Ӧ���ǣ�������Сд��ĸ�����ֺͷ��ţ��ҳ��Ȳ��˹��̣���ò�����10λ�����������ա��ֻ�������ױ��³�����Ϣ�����������ڸ������룬��Ҫ���װ������ʺŻ�������͸¶�����ˡ�" class="rank r0" class='text_nobord' disabled="disabled" /></td>
</tr>
<tr>
<td class="list_head">ȷ��������&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="password" name="qrxmm" class="input_text" maxlength="18" style="width:145"></td>
</tr>
<tr>
<td class="list_head">&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="button" name="q" value=" ȷ �� " class="button" onclick="CheckData()">&nbsp;<input name="exit" type=button class="button" style="cursor:hand;"  value=" �� �� " onclick="quit()"></td>
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
