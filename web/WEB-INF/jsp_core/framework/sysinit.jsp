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
<title><%=Constants.SYS_SYSTEMTOPIC %>-初始化</title>
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
	  alert ("网址不能为空！");
	  return false;	
	}
	if(document.formedit.mm.value!=document.formedit.qrmm.value){
	  alert ("二次密码必须保持一致！");
	  formedit.mm.focus();
	  return false;	
	}
	if(document.getElementById("mygzdms").checked){
	  document.all["gzdms"].value="1";
	}else{
	  document.all["gzdms"].value="0";
	}
	if(confirm("[00G0010J1]:初始化只能一次，确认系统初始化信息正确？")){
	    document.formedit.action="<c:url value='/sysInit.frm?method=doSysInit'/>";
		document.formedit.submit();			
	}
}
//返回结果
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
  		document.getElementById("wzt1").innerHTML="市公安局网站网址";
  		if(document.getElementById("mygzdms").checked){
		  document.getElementById("wzt2").innerHTML="交警总队网站网址";
		}else{
		  document.getElementById("wzt2").innerHTML="交警支队网站网址";
		}
  		document.getElementById("wzt3").innerHTML="支队综合应用平台网址";
  		document.getElementById("gzkjzms").value="1";	
  	}else if(formedit.xtyxms.value=="2"||formedit.xtyxms.value=="3"){
  		formedit.djglbm.value = formedit.dqsfdm.value + "0000000000";
  		formedit.sjglbm.value = "010000000000";
  		document.getElementById("wzt1").innerHTML="省公安厅网站网址";
  		document.getElementById("wzt2").innerHTML="交警总队网站网址";
  		document.getElementById("wzt3").innerHTML="总队综合应用平台网址";
  		document.getElementById("gzkjzms").value="2";
  	}else{
  		formedit.djglbm.value = "010000000000";
  		formedit.sjglbm.value = "000000000000";
  		document.getElementById("wzt1").innerHTML="公安部网站网址";
  		document.getElementById("wzt2").innerHTML="交管局网站网址";
  		document.getElementById("wzt3").innerHTML="部级综合应用平台网址";	  		
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
<script language="JavaScript" type="text/javascript">vio_title('<%=Constants.SYS_SYSTEMTOPIC %>-初始化')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>

<form name="formedit" action="" method="post" target="paramIframe">
	<input type="hidden" name="refreshlx"/>
	<input type="hidden" name="freshother" value="1"/> 
	<span class="s1" style="height: 3px;"></span>
	<table border="0" cellspacing="1" cellpadding="0" class="list_table">
		<tr>
			<td width="20%" class="list_headrig">&nbsp;当前省份代码&nbsp;</td>
			<td width="30%" class="list_body_out" align="left">
			<select name="dqsfdm" class="text_12 text_size_140" size="1" onchange="changesfdm()">
			<%if(!Constants.SYS_TYPE.equals("1")){%>
				<option value="00" selected>00:部</option>
			<%}%>
				<%=gHtmlService.transDmlbToOptionHtml("00","32","",false,"3","") %>
			</select>			
			</td>
			<td width="20%" class="list_headrig">&nbsp;系统运行模式&nbsp;</td>
			<td width="30%" class="list_body_out" align="left">
			<select name="xtyxms" class="text_12 text_size_140" size="1" onchange="changesfdm()">
			<%if(Constants.SYS_TYPE.equals("1")){%>
				<option value="1" selected>支队业务集中程序</option>
				<option value="2">总队业务集中程序</option>
				<option value="3">总队端程序</option>
			<%}else{%>				
				<option value="4">部局资源库端程序</option>
		    <%}%>
			</select>	
			</td>			
		</tr>
		<tr>
			<td class="list_headrig">&nbsp;顶级管理部门代码&nbsp;</td>
			<td class="list_body_out" align="left">
				<input type="text" name="djglbm"  maxlength="12" class="text_12 text_size_140" onKeypress='fCheckInputInt()'>
			</td>		
			<td class="list_headrig">&nbsp;上级管理部门代码&nbsp;</td>			
			<td class="list_body_out" align="left">
			   <input type="text" name="sjglbm"  maxlength="12" class="text_12 text_size_140" readonly>
			</td>			
		</tr>			
		<tr>
			<td class="list_headrig">&nbsp;部门名称&nbsp;</td>
			<td class="list_body_out" align="left">
			    <input type="text" name="bmmc"  maxlength="64" class="text_12 text_size_300">
			</td>		
			<td class="list_headrig">&nbsp;部门全称&nbsp;</td>
			<td class="list_body_out" align="left">
			    <input type="text" name="bmqc"  maxlength="128" class="text_12 text_size_300">
			</td>			
		</tr>	
		<tr>
			<td class="list_headrig">&nbsp;印章名称&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="text" name="yzmc"  maxlength="64" class="text_12 text_size_300">
			</td>		
			<td class="list_headrig">&nbsp;部门发证机关&nbsp;</td>
			<td class="list_body_out" align="left">
				<input type="text" name="fzjg"  maxlength="128" class="text_12 text_size_300">
			</td>			
		</tr>		
		<tr>
			<td class="list_headrig">&nbsp;本地发证机关&nbsp;</td>
			<td class="list_body_out" align="left" colspan="3">
			<input type="text" name="bdfzjg"  maxlength="128" class="text_12 text_size_600">
			</td>		
		</tr>	
		<tr id="div_gzkjzms" style="display:<%=Constants.SYS_TYPE.equals("1")?"none":"block" %>">
			<td class="list_headrig">&nbsp;工作库集中模式&nbsp;</td>
			<td class="list_body_out" align="left">
			<select name="gzkjzms" id="gzkjzms" class="text_12 text_size_140" size="1">
				<option value="1" selected>1:支队集中模式</option>
				<option value="2">2:总队集中模式</option>
			</select>				
			</td>		
			<td class="list_headrig">&nbsp;&nbsp;</td>
			<td class="list_body_out" align="left">&nbsp;
			</td>
		</tr>			
		<tr>
			<td class="list_headrig">&nbsp;是否为高支队&nbsp;</td>
			<td colspan="3" class="list_body_out" align="left"><input type="checkbox" id="mygzdms" value="1" onClick="changesfdm()">高速支队模式（不存在对应公安厅或公安局）<input type="hidden" name="gzdms" id="gzdms" value=""></td>		
		</tr>	
		<tr>
			<td class="list_headrig">&nbsp;系统管理员用户代号&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="text" name="yhdh"  maxlength="6" class="text_12 text_size_140">
			</td>		
			<td class="list_headrig">&nbsp;姓名&nbsp;</td>
			<td class="list_body_out" align="left">
				<input type="text" name="xm"  maxlength="32" class="text_12 text_size_140">
			</td>			
		</tr>
		<tr>
			<td class="list_headrig">&nbsp;密码&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="password" name="mm"  class="input_text" maxlength="18" style="width:145" onkeyup="setPasswordLevel(this, formedit.passwordLevel)" onblur="setPasswordLevel(this, formedit.passwordLevel)">密码强度&nbsp;<input type="text" name="passwordLevel" id="passwordLevel"  title="密码强度是对您密码安全性给出的评级，供您参考。为了帐号的安全性，我们强烈建议您设置高强度的密码。高强度的密码应该是：包括大小写字母、数字和符号，且长度不宜过短，最好不少于10位。不包含生日、手机号码等易被猜出的信息。建议您定期更换密码，不要轻易把您的帐号或者密码透露给别人。" class="rank r0" class='text_nobord' style="width:100" disabled="disabled" />
			</td>		
			<td class="list_headrig">&nbsp;确认密码&nbsp;</td>
			<td class="list_body_out" align="left"><input type="password" name="qrmm" class="input_text" maxlength="18" style="width:145">
			</td>			
		</tr>
		<tr>
			<td class="list_headrig">&nbsp;接入服务器IP地址&nbsp;</td>
			<td class="list_body_out" align="left">
			<input type="text" name="jrip"  maxlength="32" class="text_12 text_size_140">
			</td>		
			<td class="list_headrig">&nbsp;接入数据库IP地址&nbsp;</td>
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
				<input style="cursor: hand" class="button" name="savebutton" value=" 初始化 " type="button" onClick="saveForm();">&nbsp;
				<input style="cursor: hand" class="button" name="closebutton" type="button" value=" 退 出 " onclick="window.close()">
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

