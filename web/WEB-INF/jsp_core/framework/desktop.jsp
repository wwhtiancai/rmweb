<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="公安部,交通,管理,科研所,违法业务">
<meta http-equiv="description" content="公安部交通管理科学研究所">
<%
  SysService sysservice = (SysService)request.getAttribute("sysservice");
  %>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="frmjs/ajax_func.js" type="text/javascript"></script>
<script language="javascript" type="text/JavaScript" src="js/query/jquery.js"></script>
<style>
body{
	background-COLOR:#094E89;
}
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
.help{
	font-size:12px;
	color:red;
}

#ax{
	width:250px;
	height:50px;
	background:#EEEEEE;
	position:fixed;
	right:20px;
	bottom:20px;
}
.button{
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
.button:hover {
	text-decoration: none;
}
.button:active {
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
<script language="JavaScript" type="text/javascript">
<!--
function init(){
<c:if test="${first=='0'}">
	var tmp=",<c:out value='${mylists}'/>,";
	var len=document.all["modulecheckbox"].length;
	for(var i=0;i<len;i++){	
		if(tmp.indexOf(","+document.all["modulecheckbox"].item(i).value+",")!=-1){
			document.all["modulecheckbox"].item(i).checked=true;
		}
	}
</c:if>
<c:if test="${indexs==''}">
	document.getElementById("chooseclass1").checked=true;
</c:if>
<c:if test="${indexs!=null&&indexs!=''}">
	document.getElementById("indexdisplay").value="<c:out value='${indexs}'/>";
</c:if>
}
function dosubmit(){
	var tmp="";
	for(var i=0;i<document.all["modulecheckbox"].length;i++){
		if(document.all["modulecheckbox"].item(i).checked){
			tmp+=document.all["modulecheckbox"].item(i).value+",";
		}
	}
	if(tmp.length>1){
		tmp=tmp.substr(0,tmp.length-1);
	}else{
		alert("请至少选择一个模块！");
		return;
	}
	if(document.getElementById("indexdisplay").value.length<1){
		send_request_tb("main.frm?method=desktopsave&content="+tmp,"returndesktop()",false);
	}else{
		tmp="0,"+tmp;
		send_request_tb("main.frm?method=desktopsave&content="+tmp+"&indexs="+document.getElementById("indexdisplay").options[document.getElementById("indexdisplay").selectedIndex].value,"returndesktop()",false);
	}
}
function returndesktop(){
	var r=_xmlHttpRequestObj.responseText;
	if(r==1){
		if(typeof window.opener!='undefined'){
			window.opener.refreshIframe("rightfrm");
			window.close();
		}else{
			parent.parent.refreshIframe("rightfrm");
		}
		//parent.parent.window.opener.refreshIframe("rightfrm");
		//location.href="main.frm?method=right";
	}else{
		alert("保存失败！");	
	}
}
function dosubmit2(){
	var tmp="";
	for(var i=0;i<document.all["modulecheckbox"].length;i++){	
		if(document.all["modulecheckbox"].item(i).checked){
			tmp+=document.all["modulecheckbox"].item(i).value+",";
		}
	}
	if(tmp.length>1){
		location.href="main.frm?method=right";
	}else{
		alert("请至少选择一个模块！");
		return;
	}
}

var isie6 = window.XMLHttpRequest?false:true;
window.onload = function(){
    var ax = document.getElementById('ax');
    var d = document.getElementById('d');
   if(isie6){
         ax.style.position = 'absolute';         
         window.onscroll = function(){
              d.innerHTML = '';
          }
   }else{
      ax.style.position = 'fixed';
   }
      ax.style.right = '0';
      ax.style.bottom = '0';
}
function closetip()
{
    $('#sitefocus').hide(600, 0);  
    return false;
}
//-->
</script>
</head>

<body onLoad="init()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center">
			<div class="s1" style="height:7px;"></div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#e7e7e7">
				<tr>
					<td width="450" height="23" align="left" background="theme/page/configurate_title.gif" style="font-size:12px;font-weight:bold;color:white;padding-left:10px;">定制内容</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="s1" style="height:5px;"></div>
			<table width="100%" border="0" cellspacing="1" cellpadding="0" align="center" class="table">
				<tr>
					<td width="30%" class="td1" id="cctd11">优先显示</td>
					<td width="70%" class="td2" id="cctd12">
						<select name="indexdisplay" id="indexdisplay" style="font-size:12px;">
							<c:forEach items="${desktoplist}" var="current1">
							<option value="<c:out value='${current1.cdbh}'/>"><c:out value='${current1.cxmc}'/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="cctr1">
					<td class="td1" id="cctd21">桌面模块</td>
					<td class="td2" id="cctd22">
					<c:if test="${querylist!=null}">
					<c:forEach items="${querylist}" var="current">
					<input type="checkbox" name="modulecheckbox" id="modulecheckbox<c:out value='${current.mkbh}'/>" value="<c:out value='${current.mkbh}'/>"><label id="modulecheckboxs<c:out value='${current.mkbh}'/>"><c:out value='${current.mkmc}'/></label><span class="s4"><br></span>
					</c:forEach>
					</c:if></td>
				</tr>
				<tr>
					<td colspan="2" class="td3">
						<input type="button" value=" 保存 " onClick="dosubmit()" style="font-size;12px" class="button small white">
						<input type="button" value=" 关闭 " onClick="javascript:window.close();" style="font-size;12px;" class="button small white">
					</td>
				</tr>
			</table>

		</td>
	</tr>
</table>
<%--=sysservice.outputMessage(session)--%>
</body>
</html>
