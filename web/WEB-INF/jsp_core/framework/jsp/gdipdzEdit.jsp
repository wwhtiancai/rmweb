<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.share.frm.bean.SysUser"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<%
String message=(String)request.getAttribute("message");
SysUser sysuser = (SysUser)request.getAttribute("sysuser");
String reqipString = (String)request.getAttribute("reqip");
%>
<title>
�û��̶�IP��ַ�޸�
</title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function CheckData(){
  if(editpassform.gdipdz11.value==""){
  	alert ("������̶�IP��ַ1��");
  	editpassform.gdipdz11.focus();
  	return false;  
  }
  if(editpassform.gdipdz12.value==""){
  	alert ("������̶�IP��ַ1��");
  	editpassform.gdipdz12.focus();
  	return false;  
  }
  if(editpassform.gdipdz13.value==""){
  	alert ("������̶�IP��ַ1��");
  	editpassform.gdipdz13.focus();
  	return false;  
  }  
  if(editpassform.gdipdz14.value==""){
  	alert ("������̶�IP��ַ1��");
  	editpassform.gdipdz14.focus();
  	return false;  
  }
  if(editpassform.gdipdz21.value!=""||editpassform.gdipdz22.value!=""||editpassform.gdipdz23.value!=""||editpassform.gdipdz24.value!=""){
  	if(editpassform.gdipdz21.value==""||editpassform.gdipdz22.value==""||editpassform.gdipdz23.value==""||editpassform.gdipdz24.value==""){
	  	alert ("�̶�IP��ַ2������������");
	  	editpassform.gdipdz21.focus();
	  	return false;    	
  	}else{
  		document.editpassform.gdip1.value = document.editpassform.gdipdz21.value + "." + document.editpassform.gdipdz22.value + "." + document.editpassform.gdipdz23.value + "." + document.editpassform.gdipdz24.value;
  	}
  }else{
  	document.editpassform.gdip1.value = "";
  }    
  if(editpassform.gdipdz31.value!=""||editpassform.gdipdz32.value!=""||editpassform.gdipdz33.value!=""||editpassform.gdipdz34.value!=""){
	  	if(editpassform.gdipdz31.value==""||editpassform.gdipdz32.value==""||editpassform.gdipdz33.value==""||editpassform.gdipdz34.value==""){
		  	alert ("�̶�IP��ַ3������������");
		  	editpassform.gdipdz31.focus();
		  	return false;    	
	  	}else{
	  		document.editpassform.gdip2.value = document.editpassform.gdipdz31.value + "." + document.editpassform.gdipdz32.value + "." + document.editpassform.gdipdz33.value + "." + document.editpassform.gdipdz34.value;
	  	}
	  }else{
	  	document.editpassform.gdip2.value = "";
	  } 
  document.editpassform.gdip.value = document.editpassform.gdipdz11.value + "." + document.editpassform.gdipdz12.value + "." + document.editpassform.gdipdz13.value + "." + document.editpassform.gdipdz14.value;
  document.editpassform.action="login.frm?method=savegdip";
  var s = "ȷ�ϵ�ǰ�û��̶�IP�����Ƿ���ȷ��\n";
  s = s + "�̶�IP��ַ1��"  + document.editpassform.gdip.value;
  if(document.editpassform.gdip1.value!=""){
  	s = s + "\n�̶�IP��ַ2��" + document.editpassform.gdip1.value;
  }
  if(document.editpassform.gdip2.value!=""){
  	s = s +  "\n�̶�IP��ַ3��" + document.editpassform.gdip2.value;
  }
  if(confirm(s)){
  	document.editpassform.submit();  
  }
}

//���ؽ��
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	alert(strMessage); 	
 	window.close();
  }
  else
  {
    alert(strMessage);
  }
}
function init(){
	<%
	reqipString = reqipString.replaceAll("\\.",":");
	String[] ipStrings = reqipString.split(":");
	if(ipStrings.length>0){
		out.println("editpassform.gdipdz11.value = '" + ipStrings[0] + "';");		
	}
	if(ipStrings.length>1){
		out.println("editpassform.gdipdz12.value = '" + ipStrings[1] + "';");
	}
	if(ipStrings.length>2){
		out.println("editpassform.gdipdz13.value = '" + ipStrings[2] + "';");	
	}
	if(ipStrings.length>3){
		out.println("editpassform.gdipdz14.value = '" + ipStrings[3] + "';");
	}	
	%>
}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<link href="css/i_new2.0.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<body onload="init()">
<script language="JavaScript" type="text/javascript">vio_title('�û��̶�IP��ַ����')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<form name="editpassform" method="post"  action="" target="paramIframe">
<table border="0" cellspacing="1" cellpadding="0" class="list_table">
<tr>
<td class="list_head" width="100">�û�����&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="text" name="yhdh" readonly value="<c:out value='${sysuser.yhdh}'/>" class="input_text" maxlength="12" style="width:145"></td>
<td class="list_body_out" rowspan="6" width="280">
	<b>˵����</b><br>1���̶�IP��ַ1��2��3Ϊ�û����ʹؼ�ҵ��ʹ�õĿͻ���IP��ַ��<br>2����ͨ�û����ǹ���Ա�û�������ʹ�ù̶�IP��ַ1��2��3��¼���鲼��ƽ̨��
</td>
</tr>
<tr>
<td class="list_head">IP��ʼ��ַ&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="text" name="ipks"  class="input_text" maxlength="18" style="width:145" readOnly value="<%=sysuser.getIpks() %>"></td>
</tr>
<tr>
<td class="list_head">IP������ַ&nbsp;</td>
<td class="list_body_out">&nbsp;<input type="text" name="ipjs"  class="input_text" maxlength="18" style="width:145" readOnly value="<%=sysuser.getIpjs() %>"></td>
</tr>
<tr>
<td class="list_head">�̶�IP��ַ1&nbsp;</td>
<td class="list_body_out">&nbsp;<input name="gdipdz11" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz12" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz13" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz14" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'><input type="hidden" name="gdip" value=""/></td>
</tr>
<tr>
<td class="list_head">�̶�IP��ַ2&nbsp;</td>
<td class="list_body_out">&nbsp;<input name="gdipdz21" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz22" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz23" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz24" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'><input type="hidden" name="gdip1" value=""/></td>
</tr>
<tr>
<td class="list_head">�̶�IP��ַ3&nbsp;</td>
<td class="list_body_out">&nbsp;<input name="gdipdz31" type="text" class="text_12" maxlength="3"
							style="width: 28"  value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz32" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz33" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'>.<input type="text" style="width: 28" name="gdipdz34" maxlength="3"
							class="text_12" value="" onKeypress='fCheckInputInt()'><input type="hidden" name="gdip2" value=""/></td>
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
