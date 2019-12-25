<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="com.tmri.pub.service.SysService"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%
 SysService sysservice = (SysService)request.getAttribute("sysservice");
%>
<c:if test="${control!=null&&control!='1'}">
<c:if test="${urlsign!=null&&urlsign!=''}">
<script>
location.href="<c:out value='${urls}'/>"</script>
</c:if>
</c:if>
<c:if test="${first!=null&&first=='1'}">
<script>location.href="main.frm?method=desktop"</script>
</c:if>
<c:if test="${first=='0'}">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title></title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="js/basjs/dragDiv.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/ajax_func.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/drop.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/pop/jquery.min.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/pop/tmri.pop.js" type="text/javascript"></script>
<script language="JavaScript" src="theme/1/style.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<link href="rmjs/pop/pop.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body{
	background-COLOR:#094E89;
}
.spanDiv{
	position:relative;
	width:5px;
	height:5px;
}
.dragDiv,.nodragDiv{
	position:relative;
	filter:alpha(opacity=100);
	opacity:1;
	margin-bottom:6px;
	background-color:#FFFFFF;
}
.ajaxloading{
	font-size:12px;
	height:50px;
	background:cccccc;
	padding-top:7px;
}
.content{
	display:none;
	width:98%;
	text-align:left;
	padding:9px 0 9px 0;
	font-size:12px;
	background:#f0f0f0;
}
.setting{
	display:none;
	width:100%;
	height:196px;
	text-align:center;
	padding:9px 0 9px 0;
	font-size:12px;
	background:#DEDBDE;
}
.fieldset{
	border-width:thin;
	text-align:left;
	padding:2px 5px 7px 5px;
}
.legend{
	font-size:12px;
	color:black;
}
.list_body_tr_5{
	font-size:12px;
	background-color: #eaeaea;
	padding:2px 5px 0 5px;
	word-break:break-all;
	line-height: 20px;
}
.list_body_tr_6{
	font-size:12px;
	background-color: #dfdfdf;
	padding:2px 5px 0 5px;
	word-break:break-all;
	line-height: 20px;
}

/*	for query	*/
.query{width:100%;text-align:center;border-collapse:collapse;border-spacing:0}
.query .top{text-align:left;font-size:12px;font-weight:bold;font-family:宋体,Arial Narrow;width:100%;line-height:24px;padding:1px 1px 1px 1px;color:#cc5522;border-bottom-color:#c0d0df;border-bottom-width:2px;border-bottom-style:solid;}
.query .bottom{font-size:14px;font-family:宋体,Arial Narrow;line-height:20px;width:100%;padding:2px 2px 2px 2px;}
.query .head{text-align:right;font-family:宋体,Arial Narrow;line-height:24px;padding:0px 0px 0px 0px;font-size:12px;}
.query .body{text-align:left;font-family:宋体,Arial Narrow;line-height:16px;font-size:12px;width:100%;}
.query .deepblue{font-weight:bold;color:#577693;}
.query .button{background-image:url('/rmweb/rmtheme/main/log_an2.gif');BORDER-TOP-WIDTH:0px;background-position:0px 0px;BORDER-LEFT-WIDTH:0px;border-bottom-width:0px;width:98px;line-height:19px;border-right-width:0px;font-size: 12px;text-decoration: none;cursor:hand;}
.statpercent{HEIGHT:20PX;WIDTH:99%;BACKGROUND-COLOR:#EEEEEE;BORDER:1PX #DDDDDD SOLID;padding:1px}

/* for ul */
.dragDiv .info-group-one,
.dragDiv .info-group-two,
.dragDiv .info-group-three,
.dragDiv .info-group-four{margin: 0 auto;list-style-type: none;clear:both;display: block;width:100%;float: left;}
.dragDiv ul{margin: 0px; padding:0px; list-style: none; float: left	;width: 100%;}
.dragDiv ul.info-group-one li{width:335px !important;width: 100%;}
.dragDiv ul.info-group-two li{width: 50%;}
.dragDiv ul.info-group-three li{width: 33%;}
.dragDiv ul.info-group-four li{width: 25%;}
.dragDiv ul.info-group-one li {display: block;float: left;border-bottom: 1px solid #dfdfdf;border-top: 1px solid #fff;line-height:28px;background: #f9f9f9;padding: 2px 0;}
.dragDiv ul.info-group-two li,
.dragDiv ul.info-group-three li,
.dragDiv ul.info-group-four li {display: block;float: left;border-bottom: 1px solid #dfdfdf;display: inline;margin: 0;padding: 0;}
</style>
<script language="JavaScript" type="text/javascript">
var nows="<c:out value='${nows}'/>";
function openWindow(sPath){
  var windowheight=screen.height;
  var windowwidth =screen.width;
  windowheight=(windowheight-768)/2;
  windowwidth=(windowwidth-1024)/2;
  var pop;
  pop=window.open(sPath,"frmmains","resizable=no,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=1014,height=690,top="+windowheight+",left="+windowwidth);
  pop.focus();
}
function init(){
<c:if test="${mylist!=null}">
<c:forEach items="${mylist}" var="current">
	new Drag("titleBar<c:out value='${current.mkbh}'/>", "dragDiv<c:out value='${current.mkbh}'/>");
	doinit("<c:out value='${current.mkbh}'/>","<c:out value='${current.lj}'/>");
	interfaceattribute<c:out value='${current.mkbh}'/>("<c:out value='${current.mksx1}'/>","<c:out value='${current.mksx2}'/>","<c:out value='${current.mksx3}'/>","<c:out value='${current.mksx4}'/>");
</c:forEach>
</c:if>
}
function closetip(){
    $('#sitefocus').hide(600, 0);  
    return false;
}  
function doinit(bh,lj){
	send_request_tb(lj,"returnhtml('"+bh+"')",false);	
}
function refresh(bh,lj){
	send_request_tb(lj,"returnhtml('"+bh+"')",false);
	document.getElementById("content"+bh).style.display="block";
	document.getElementById("setting"+bh).style.display="none";	
	if(bh=='2'||bh=='3')return;	
	document.getElementById("editbox"+bh).innerHTML="设置";
}
function returnhtml(bh){
	var html=_xmlHttpRequestObj.responseText;
	document.getElementById("content"+bh).innerHTML=html;
	document.getElementById("ajaxloading"+bh).style.display="none";
	document.getElementById("content"+bh).style.display="block";
	//if(bh=='2'||bh=='3')return;
	//document.getElementById("editbox"+bh).style.display="block";
}
function dosetting(bh){
	if(document.getElementById("content"+bh).style.display=="block"){
		document.getElementById("content"+bh).style.display="none";
		document.getElementById("setting"+bh).style.display="block";
		document.getElementById("editbox"+bh).innerHTML="返回";
	}else{
		document.getElementById("content"+bh).style.display="block";
		document.getElementById("setting"+bh).style.display="none";		
		document.getElementById("editbox"+bh).innerHTML="设置";
	}
}
<c:if test="${mylist!=null}"><c:forEach items="${mylist}" var="current"><c:out value="${current.js}" escapeXml="false"/></c:forEach></c:if>
/*
function getwordshow(str,len){
	var t_Str = "";
	if(len<str.length){
		t_Str+=str.substr(0,len)+"..";
	}else{
		t_Str=str;
	}
	return t_Str;
}
*/
function interfaceattribute10(mksx1,mksx2,mksx3,mksx4){}
function showycyw(vglbm,vbdate,vedate){
	var sPath = "jgkh.vio?method=doStat_wfpd&ywlb=9001&ksrq="+vbdate+"&jsrq="+vedate+"&bmdm="+vglbm+"&sfbj=0&qtrkcdbh=A013";
	openwin(sPath,'bussiness1<c:out value="${userSession.sysuser.yhdh}"/>',true);	  
}
function showrate(vglbm,vbdate,vedate){
	var sPath = "jgkh.vio?method=doStat&ywlb=1001&ksrq="+vbdate+"&jsrq="+vedate+"&bmdm="+vglbm+"&sfbj=0&qtrkcdbh=A013";
	openwin(sPath,'bussiness1<c:out value="${userSession.sysuser.yhdh}"/>',true);	
}
function showxgsc(vglbm,vbdate,vedate){
	var sPath = "jgkh.vio?method=doStat_xgscjg&ywlb=9002&ksrq="+vbdate+"&jsrq="+vedate+"&bmdm="+vglbm+"&sfbj=0&qtrkcdbh=A013";	
	openwin(sPath,'bussiness1<c:out value="${userSession.sysuser.yhdh}"/>',true);	
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

$(document).ready(function(){
	var inf = "";
	$.ajax({
		url:"<c:url value='/unlogoutcheck.ana?method=getInfo'/>",
		cache:false,
		async:false,
		type:'GET',
		success:function(data){
			inf = decodeURIComponent(data);
			inf = inf.replace(/\+/gim, "");
		}
	});
	if("" != inf){
		var pop=new Pop("",
			"<c:url value='/unlogoutcheck.ana?method=mainFrame'/>",
			inf);
	}
});
</script>
</head>
<body onLoad="init()">
<table id="dragTable" cellpadding="2" style="width:100%;">
	<tr>
		<td valign="top" style="width:33%" rowspan="2">
		<c:if test="${mylist!=null}">
		<c:forEach items="${mylist}" var="current">
		<c:if test="${current.mkbh=='2'}">
			<div class="dragDiv" id="dragDiv<c:out value='${current.mkbh}'/>">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="4" height="28" background="theme/page/p1.gif"></td>
						<td background="theme/page/p2.gif">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="25" align="center" style="padding-top:6px;"><img src="theme/easy/desktop<c:out value='${current.mkbh}'/>.png" alt="" border="0"></td>
									<td title="拖动此处设置本窗口位置" id="titleBar<c:out value='${current.mkbh}'/>" style="cursor:move;font-size:12px;padding-top:8px;">
									&nbsp;<c:out value='${current.mkmc}'/>
									</td>
									<td width="60" align="right" style="padding-top:8px;padding-right:5px;">
									<a href="#" style="font-size:12px;color:blue;" id="refresh<c:out value='${current.mkbh}'/>" onClick="refresh('<c:out value="${current.mkbh}"/>','<c:out value="${current.lj}"/>');">刷新</a>
									</td>
								</tr>
							</table>
						</td>
						<td width="4" background="theme/page/p3.gif"></td>
					</tr>
					<tr>
						<td background="theme/page/p4.gif"></td>
						<td align="center" valign="top" bgcolor="#f0f0f0" height="469">
							<div id="ajaxloading<c:out value='${current.mkbh}'/>" class="ajaxloading"><img src="theme/page/ajaxloading.gif" alt="" width="48" height="48" border="0" align="absmiddle">&nbsp;&nbsp;加载中，请稍候&nbsp;……</div>
							<div class="content" id="content<c:out value='${current.mkbh}'/>"></div><c:out value='${current.html}' escapeXml="false"/>
						</td>
						<td background="theme/page/p6.gif"></td>
					</tr>
					<tr>
						<td height="13" background="theme/page/p7.gif"></td>
						<td background="theme/page/p8.gif"></td>
						<td background="theme/page/p9.gif"></td>
					</tr>
				</table>
			</div>
		</c:if>
		</c:forEach>
		</c:if>
		</td>
		<td valign="top" style="width:33%" rowspan="2">
		<c:if test="${mylist!=null}">
		<c:forEach items="${mylist}" var="current">
		<c:if test="${current.mkbh=='3'}">
			<div class="dragDiv" id="dragDiv<c:out value='${current.mkbh}'/>">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="4" height="28" background="theme/page/p1.gif"></td>
						<td background="theme/page/p2.gif">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="25" align="center" style="padding-top:6px;"><img src="theme/easy/desktop<c:out value='${current.mkbh}'/>.png" alt="" border="0"></td>
									<td title="拖动此处设置本窗口位置" id="titleBar<c:out value='${current.mkbh}'/>" style="cursor:move;font-size:12px;padding-top:8px;">
									&nbsp;<c:out value='${current.mkmc}'/>
									</td>
									<td width="60" align="right" style="padding-top:8px;padding-right:5px;">
									<a href="#" style="font-size:12px;color:blue;" id="refresh<c:out value='${current.mkbh}'/>" onClick="refresh('<c:out value="${current.mkbh}"/>','<c:out value="${current.lj}"/>');">刷新</a>
									</td>
								</tr>
							</table>
						</td>
						<td width="4" background="theme/page/p3.gif"></td>
					</tr>
					<tr>
						<td background="theme/page/p4.gif"></td>
						<td align="center" valign="top" bgcolor="#f0f0f0" height="469">
							<div id="ajaxloading<c:out value='${current.mkbh}'/>" class="ajaxloading"><img src="theme/page/ajaxloading.gif" alt="" width="48" height="48" border="0" align="absmiddle">&nbsp;&nbsp;加载中，请稍候&nbsp;……</div>
							<div class="content" id="content<c:out value='${current.mkbh}'/>"></div><span ><c:out value='${current.html}' escapeXml="false"/></span>
						</td>
						<td background="theme/page/p6.gif"></td>
					</tr>
					<tr>
						<td height="13" background="theme/page/p7.gif"></td>
						<td background="theme/page/p8.gif"></td>
						<td background="theme/page/p9.gif"></td>
					</tr>
				</table>
			</div>
		</c:if>
		</c:forEach>
		</c:if>
		</td>
		<td valign="top" style="width:33%">
		<c:if test="${mylist!=null}">
		<c:forEach items="${mylist}" var="current">
		<c:if test="${current.mkbh=='1'||current.mkbh=='4'}">
			<div class="dragDiv" id="dragDiv<c:out value='${current.mkbh}'/>">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="4" height="28" background="theme/page/p1.gif"></td>
						<td background="theme/page/p2.gif">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="25" align="center" style="padding-top:6px;"><img src="theme/easy/desktop<c:out value='${current.mkbh}'/>.png" alt="" border="0"></td>
									<td title="拖动此处设置本窗口位置" id="titleBar<c:out value='${current.mkbh}'/>" style="cursor:move;font-size:12px;padding-top:8px;">
									&nbsp;<c:out value='${current.mkmc}'/>
									</td>
									<td width="60" align="right" style="padding-top:8px;padding-right:5px;">
									<a href="#" style="font-size:12px;color:blue;" id="refresh<c:out value='${current.mkbh}'/>" onClick="refresh('<c:out value="${current.mkbh}"/>','<c:out value="${current.lj}"/>');">刷新</a>
									<a href="#" style="font-size:12px;color:blue;" id="editbox<c:out value='${current.mkbh}'/>" onClick="dosetting('<c:out value='${current.mkbh}'/>')">设置</a>
									</td>
								</tr>
							</table>
						</td>
						<td width="4" background="theme/page/p3.gif"></td>
					</tr>
					<tr>
						<td background="theme/page/p4.gif"></td>
						<td align="center" valign="top" bgcolor="#f0f0f0" height="211">
							<div id="ajaxloading<c:out value='${current.mkbh}'/>" class="ajaxloading"><img src="theme/page/ajaxloading.gif" alt="" width="48" height="48" border="0" align="absmiddle">&nbsp;&nbsp;加载中，请稍候&nbsp;……</div>
							<div class="content" id="content<c:out value='${current.mkbh}'/>"></div><span ><c:out value='${current.html}' escapeXml="false"/></span>
						</td>
						<td background="theme/page/p6.gif"></td>
					</tr>
					<tr>
						<td height="13" background="theme/page/p7.gif"></td>
						<td background="theme/page/p8.gif"></td>
						<td background="theme/page/p9.gif"></td>
					</tr>
				</table>
			</div>
		</c:if>
		</c:forEach>
		</c:if>
		</td>
	</tr>
</table>

<div id="pop" style="display:none;">
	<div id="popHead">
	<a id="popClose" title="关闭">关闭</a>
	<h2>系统提示</h2>
	</div>
	<div id="popContent">
	<dl>
		<dd id="popIntro"></dd>
	</dl>
	<p id="popMore"><a href="#">查看详情</a></p>
	</div>
</div>
<%--<%=sysservice.outputMessage(session)%>--%>
</body>
</html>
</c:if>
