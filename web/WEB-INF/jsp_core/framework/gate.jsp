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
<meta http-equiv="keywords" content="公安部,交通,管理,科研所">
<meta http-equiv="description" content="公安部交通管理科学研究所">
<%
  SysService sysservice = (SysService)request.getAttribute("sysservice");
  %>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="frmjs/ajax_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/jquery.js"
			type="text/javascript"></script>
<script language="javascript" type="text/JavaScript" src="rmjs/swfobject.js"></script>
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

#ax{width:250px;height:50px;background:#EEEEEE;position:fixed;right:20px;bottom:20px;}

</style>
<script language="JavaScript" type="text/javascript">
<!--
window.onerror=function(){ return true;}
function init(){
try{
	var leftfrmHeigh = window.parent.frames["leftfrm"].document.documentElement.scrollHeight;
	document.getElementById("helloid").height = (leftfrmHeigh - 80);
	}catch(e){
	try{
	document.getElementById("helloid").height = document.documentElement.clientHeight;
	}catch(e){
		document.getElementById("helloid").style.height = document.documentElement.clientHeight;
	}
	}
	var swfVersionStr = "11.1.0";
	// To use express install, set to playerProductInstall.swf, otherwise the empty string. 
	var xiSwfUrlStr = "playerProductInstall.swf";
	var flashvars = {};
	var params = {};
	params.quality = "high";
	params.bgcolor = "#ffffff";
	params.allowscriptaccess = "sameDomain";
	params.allowfullscreen = "true";
	var attributes = {};
	attributes.id = "jcbk_Dynamic_TOC_1127";
	attributes.name = "jcbk_Dynamic_TOC_1127";
	attributes.align = "middle";
	swfobject.embedSWF(
	    "chart/pgis/jcbk_Dynamic_TOC_1127.swf", "flashContent", 
	    "100%", "100%", 
	    swfVersionStr, xiSwfUrlStr, 
	    flashvars, params, attributes);
	// JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
	swfobject.createCSS("#flashContent", "display:block;text-align:left;");
}

var json = '{"indicator":{"head":{"kkbh":"000"}, "body":{"gcs":"0", "yjs":"0", "bks":"0"}}}';
function getInfo(kkbh){
	$.ajax({
			url:'main.frm?method=getInfo&kkbh=' + kkbh,
			type:"GET",
			dataType:"json",
			cache:false,
			async:false,
			success:function(data){
				json =  '{"indicator":{"head":{"kkbh":"' + kkbh + '"}, "body":{"gcs":"'+data.gcs+'", "yjs":"'+ (data.gcs == 0? 0 : data.yjs)+'", "bks":"'+data.bks+'"}}}';
				document.getElementById("jcbk_Dynamic_TOC_1127").doCallBack(json);
				return json;
			},
			error:function(){
				json = '{"indicator":{"head":{"kkbh":"' + kkbh + '"}, "body":{"gcs":"0", "yjs":"0", "bks":"0"}}}';
				document.getElementById("jcbk_Dynamic_TOC_1127").doCallBack(json);
				return json;
			}
		});	
}

//-->
</script>
</head>

<body onLoad="init()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" id="helloid">
			<div class="s1" style="height:7px;"></div>
			<div id="flashContent">
	            <p style="display:none">
	                To view this page ensure that Adobe Flash Player version 
	                11.1.0 or greater is installed. 
	            </p>
	            <script type="text/javascript"> 
	                var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://"); 
	                document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
	                                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
	            </script> 
	        </div>
			<noscript>
	            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="jcbk_Dynamic_TOC_1127">
	                <param name="movie" value="chart/pgis/jcbk_Dynamic_TOC_1127.swf" />
	                <param name="quality" value="high" />
	                <param name="bgcolor" value="#ffffff" />
	                <param name="allowScriptAccess" value="sameDomain" />
	                <param name="allowFullScreen" value="true" />
	                <!--[if !IE]>-->
	                <object type="application/x-shockwave-flash" data="chart/pgis/jcbk_Dynamic_TOC_1127.swf" width="100%" height="100%">
	                    <param name="quality" value="high" />
	                    <param name="bgcolor" value="#ffffff" />
	                    <param name="allowScriptAccess" value="sameDomain" />
	                    <param name="allowFullScreen" value="true" />
	                <!--<![endif]-->
	                <!--[if gte IE 6]>-->
	                    <p> 
	                        Either scripts and active content are not permitted to run or Adobe Flash Player version
	                        11.1.0 or greater is not installed.
	                    </p>
	                <!--<![endif]-->
	                    <a href="http://www.adobe.com/go/getflashplayer">
	                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
	                    </a>
	                <!--[if !IE]>-->
	                </object>
	                <!--<![endif]-->
	            </object>
	        </noscript> 
		</td>
	</tr>
</table>
</body>
</html>
