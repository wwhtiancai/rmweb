<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.service.*"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="com.tmri.share.frm.service.*"%>
<html>
<head>
<%
SysService sysService = (SysService)request.getAttribute("sysservice");
GHtmlService gHtmlService = (GHtmlService)request.getAttribute("gHtmlService");
%>
<title><%=Constants.SYS_SYSTEMTOPIC %>-��ʼ��</title>
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
<script language="JavaScript" src="theme/blue/blue1.js"	type="text/javascript"></script>
<script language="javascript">
var context = "<%=Constants.SYS_CONTEXT%>";
<%=sysService.procJspLogicVar("00","G001","1")%>
function saveForm(){	
    <%=sysService.procJspCheckCode("00","G001","1","")%>
    if(document.formedit.wz1.value.length<1||document.formedit.wz2.value.length<1||document.formedit.wz3.value.length<1){
	  alert ("��ַ����Ϊ�գ�");
	  return false;	
	}
	if(document.formedit.mm.value!=document.formedit.qrmm.value){
	  alert ("����������뱣��һ�£�");
	  formedit.mm.focus();
	  return false;	
	}
	if(document.getElementById("mygzdms").checked){
	  document.all["gzdms"].value="1";
	}else{
	  document.all["gzdms"].value="0";
	}
	if(confirm("[00G0010J1]:��ʼ��ֻ��һ�Σ�ȷ��ϵͳ��ʼ����Ϣ��ȷ��")){
	    document.formedit.action="<c:url value='/sysInit.frm?method=doSysInit'/>";
		document.formedit.submit();			
	}
}
//���ؽ��
function resultSave(strResult,strMessage){
  if(strResult=="1")
  {
 	displayInfoHtml(strMessage);
    document.sysparaForm.action="<c:url value='/fresh.frm?method=freshsysparacode'/>";
	document.sysparaForm.submit(); 	
	var url = window.location.href;
	var index = url.indexOf(context);
	url = url.substring(0,index + context.length);
	parent.window.location=url;
  }
  else
  {
    displayInfoHtml(strMessage);
  }
}
function init(){   
	<%=sysService.procJspLogicCode("00","G001","1")%>
	changesfdm();
}
function changesfdm(){
  if(formedit.dqsfdm.value==""){
  	formedit.djglbm.value = "";
  	formedit.sjglbm.value = "";
  }else{
  	if(formedit.xtyxms.value=="1"){
  		formedit.djglbm.value = formedit.dqsfdm.value + "0000000000";
  		formedit.sjglbm.value = formedit.djglbm.value
  		document.getElementById("wzt1").innerHTML="�й�������վ��ַ";
  		if(document.getElementById("mygzdms").checked){
		  document.getElementById("wzt2").innerHTML="�����ܶ���վ��ַ";
		}else{
		  document.getElementById("wzt2").innerHTML="����֧����վ��ַ";
		}
  		document.getElementById("wzt3").innerHTML="֧���ۺ�Ӧ��ƽ̨��ַ";
  		document.getElementById("gzkjzms").value="1";	
  	}else if(formedit.xtyxms.value=="2"||formedit.xtyxms.value=="3"){
  		formedit.djglbm.value = formedit.dqsfdm.value + "0000000000";
  		formedit.sjglbm.value = "010000000000";
  		document.getElementById("wzt1").innerHTML="ʡ��������վ��ַ";
  		document.getElementById("wzt2").innerHTML="�����ܶ���վ��ַ";
  		document.getElementById("wzt3").innerHTML="�ܶ��ۺ�Ӧ��ƽ̨��ַ";
  		document.getElementById("gzkjzms").value="2";
  	}else{
  		formedit.djglbm.value = "010000000000";
  		formedit.sjglbm.value = "000000000000";
  		document.getElementById("wzt1").innerHTML="��������վ��ַ";
  		document.getElementById("wzt2").innerHTML="���ܾ���վ��ַ";
  		document.getElementById("wzt3").innerHTML="�����ۺ�Ӧ��ƽ̨��ַ";	  		
  	}  	
  }
  /*
  if(formedit.xtyxms.value=="3"){
  	div_gzkjzms.style.display = "block";  	
  }else{
  	div_gzkjzms.style.display = "none";
  }
  */
}
</script>	
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<link href="css/i_new2.0.css" rel="stylesheet" type="text/css">
</head>
<body onload="init()">
<script language="JavaScript" type="text/javascript">vio_title('<%=Constants.SYS_SYSTEMTOPIC %>-��ʼ��')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>

<form name="formedit" action="" method="post" target="paramIframe">
	<input type="hidden" name="refreshlx"/>
	<input type="hidden" name="freshother" value="1"/> 
	<span class="s1" style="height: 3px;"></span>
	<table border="0" cellspacing="1" cellpadding="0" class="list_table">
		<tr>
			<td width="20%" class="list_headrig">&nbsp;��ǰʡ�ݴ���&nbsp;</td>
			<td width="30%" class="list_body_out" align="left">
			<select name="dqsfdm" class="text_12 text_size_140" size="1" onchange="changesfdm()">
			<%if(!Constants.SYS_TYPE.equals("1")){%>
				<option value="00" selected>00:��</option>
			<%}%>
				<%=gHtmlService.transDmlbToOptionHtml("00","32","",false,"3","") %>
			</select>			
			</td>
			<td width="20%" class="list_headrig">&nbsp;ϵͳ����ģʽ&nbsp;</td>
			<td width="30%" class="list_body_out" align="left">
			<select name="xtyxms" class="text_12 text_size_140" size="1" onchange="changesfdm()">
			<%if(Constants.SYS_TYPE.equals("1")){%>
				<option value="1" selected>֧��ҵ���г���</option>
				<option value="2">�ܶ�ҵ���г���</option>
				<option value="3">�ܶӶ˳���</option>
			<%}else{%>				
				<option value="4">������Դ��˳���</option>
		    <%}%>
			</select>	
			</td>			
		</tr>
		<tr>
			<td class="list_headrig">&nbsp;���������Ŵ���&nbsp;</td>
			<td class="list_body_out" align="left">
				<input type="text" name="djglbm"  maxlength="12" class="text_12 text_size_140" onKeypress='fCheckInputInt()'>
			</td>		
			<td class="list_headrig">&nbsp;�ϼ������Ŵ���&nbsp;</td>			
			<td class="list_body_out" align="left">
			   <input type="text" name="sjglbm"  maxlength="12" class="text_12 text_size_140" readonly>
			</td>			
		</tr>			
		<tr>
			<td class="list_headrig">&nbsp;��������&nbsp;</td>
			<td class="list_body_out" align="left">
			    <input type="text" name="bmmc"  maxlength="64" class="text_12 text_size_300">
			</td>		
			<td class="list_headrig">&nbsp;����ȫ��&nbsp;</td>
			<td class="list_body_out" align="left">
			    <input type="text" name="bmqc"  maxlength="128" class="text_12 text_size_300">
			</td>			
		</tr>	
		<tr>
			<td class="list_headrig">&nbsp;ӡ������&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="text" name="yzmc"  maxlength="64" class="text_12 text_size_300">
			</td>		
			<td class="list_headrig">&nbsp;���ŷ�֤����&nbsp;</td>
			<td class="list_body_out" align="left">
				<input type="text" name="fzjg"  maxlength="128" class="text_12 text_size_300">
			</td>			
		</tr>		
		<tr>
			<td class="list_headrig">&nbsp;���ط�֤����&nbsp;</td>
			<td class="list_body_out" align="left" colspan="3">
			<input type="text" name="bdfzjg"  maxlength="128" class="text_12 text_size_600">
			</td>		
		</tr>	
		<tr id="div_gzkjzms" style="display:<%=Constants.SYS_TYPE.equals("1")?"none":"block" %>">
			<td class="list_headrig">&nbsp;�����⼯��ģʽ&nbsp;</td>
			<td class="list_body_out" align="left">
			<select name="gzkjzms" id="gzkjzms" class="text_12 text_size_140" size="1">
				<option value="1" selected>1:֧�Ӽ���ģʽ</option>
				<option value="2">2:�ܶӼ���ģʽ</option>
			</select>				
			</td>		
			<td class="list_headrig">&nbsp;&nbsp;</td>
			<td class="list_body_out" align="left">&nbsp;
			</td>
		</tr>			
		<tr>
			<td class="list_headrig">&nbsp;�Ƿ�Ϊ��֧��&nbsp;</td>
			<td colspan="3" class="list_body_out" align="left"><input type="checkbox" id="mygzdms" value="1" onClick="changesfdm()">����֧��ģʽ�������ڶ�Ӧ�������򹫰��֣�<input type="hidden" name="gzdms" id="gzdms" value=""></td>		
		</tr>	
		<tr>
			<td class="list_headrig">&nbsp;ϵͳ����Ա�û�����&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="text" name="yhdh"  maxlength="6" class="text_12 text_size_140">
			</td>		
			<td class="list_headrig">&nbsp;����&nbsp;</td>
			<td class="list_body_out" align="left">
				<input type="text" name="xm"  maxlength="32" class="text_12 text_size_140">
			</td>			
		</tr>
		<tr>
			<td class="list_headrig">&nbsp;����&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="password" name="mm"  class="input_text" maxlength="18" style="width:145" onkeyup="setPasswordLevel(this, formedit.passwordLevel)" onblur="setPasswordLevel(this, formedit.passwordLevel)">����ǿ��&nbsp;<input type="text" name="passwordLevel" id="passwordLevel"  title="����ǿ���Ƕ������밲ȫ�Ը����������������ο���Ϊ���ʺŵİ�ȫ�ԣ�����ǿ�ҽ��������ø�ǿ�ȵ����롣��ǿ�ȵ�����Ӧ���ǣ�������Сд��ĸ�����ֺͷ��ţ��ҳ��Ȳ��˹��̣���ò�����10λ�����������ա��ֻ�������ױ��³�����Ϣ�����������ڸ������룬��Ҫ���װ������ʺŻ�������͸¶�����ˡ�" class="rank r0" class='text_nobord' style="width:100" disabled="disabled" />
			</td>		
			<td class="list_headrig">&nbsp;ȷ������&nbsp;</td>
			<td class="list_body_out" align="left"><input type="password" name="qrmm" class="input_text" maxlength="18" style="width:145">
			</td>			
		</tr>
		<tr>
			<td class="list_headrig">&nbsp;���������IP��ַ&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="text" name="jrip"  maxlength="32" class="text_12 text_size_140">
			</td>		
			<td class="list_headrig">&nbsp;�������ݿ�IP��ַ&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="text" name="jrkip"  maxlength="32" class="text_12 text_size_140">
			</td>			
		</tr>
	</table>
	<table border="0" cellspacing="1" cellpadding="0" class="list_table">
		<tr>
			<td width="20%" class="list_headrig" id="wzt1"></td>
			<td width="80%" class="list_body_out" align="left"><input type="text" name="wz1" class="input_text" maxlength="64" style="width:250"/></td>
		</tr>
		<tr>
			<td class="list_headrig" id="wzt2"></td>
			<td class="list_body_out" align="left"><input type="text" name="wz2" class="input_text" maxlength="64" style="width:250"/></td>			
		</tr>		
		<tr>
			<td class="list_headrig" id="wzt3">(</td>
			<td class="list_body_out" align="left"><input type="text" name="wz3" class="input_text" maxlength="64" style="width:250"/></td>			
		</tr>
		<tr>
			<td  class="list_body_out" colspan="5" align="center">
				<input style="cursor: hand" class="button" name="savebutton" value=" ��ʼ�� " type="button" onClick="saveForm();">&nbsp;
				<input style="cursor: hand" class="button" name="closebutton" type="button" value=" �� �� " onclick="window.close()">
			</td>
		</tr>				
	</table>
</form>
<form name="sysparaForm" action="" method="post" target="paramIframe">
		<input type=hidden name="xtlb" value="">
		<input type=hidden name="cslx" value="">
		<input type=hidden name="gjz" value="">
</form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>		
</body>
</html>

