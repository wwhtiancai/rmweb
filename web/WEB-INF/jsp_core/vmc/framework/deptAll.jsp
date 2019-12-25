<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>部门选择</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript"> 
<!--
var gnids=new Array();
gnids[0]=new Array("0","l921","交警部门","sysuser.frm?method=queryGlbmYwlbList");
gnids[1]=new Array("1","l922","公安部门","department.vmc?method=listPolice");
$(document).ready(function(){
	doResize();
	window.onresize=doResize;
	doload(1);
	changepanel(1);
});
var iframecount=1;
function doload(c){
	var iframe=document.getElementById("tagframe"+c);
	iframe.src=gnids[(c-1)][3];
	if(iframe.attachEvent){
		iframe.attachEvent("onload",function(){
			iframecount++;
			if(iframecount<=gnids.length){
				doload(iframecount);
			}
		});
	}else{
	  iframe.onload=function(){
			iframecount++;
			if(iframecount<=gnids.length){
				doload(iframecount);
			}
		};
	}
}
function changepanel(idx){
	for (var i=1;i<=gnids.length;i++){
		$("#tagitem"+i).attr("className","tag_head_close");
		$("#tagpanel"+i).css("display","none");
	}
	$("#tagitem"+idx).attr("className","tag_head_open");
	$("#tagpanel"+idx).css("display","block");
	
	if(idx == 1){
		$("#bmjzlx").val('17');
	}else{
		$("#bmjzlx").val('99');
	}
}
function doResize(){
	var h=document.body.clientHeight-140;
	for (var i=1;i<=2;i++){
		$("#tagframe"+i).attr("height",h);
	}
}

function doClose(){
	window.close();
}
function doSure(){
	setVal();
	window.close();
}
function setVal(){
	var r = new Array(3);
	r[0] = $("#bmjzlx").val();
	r[1] = $("#glbm").val();
	r[2] = $("#bmmc").val();
	window.returnValue = r;
}

function doReset(){
	$("#glbm").val('');
	$("#bmmc").val('');
}
function editGlbm(s_glbm){
	$.getJSON("<c:url value='department.vmc?method=getDept&jzlx='/>" + $("#bmjzlx").val() + "&glbm=" + s_glbm, 
		function(data){
			if(data.code == '1'){
				msg = data.message;
				$("#glbm").val(s_glbm);
				$("#bmmc").val(decodeURI(msg.bmmc));
			}
		}		
	);
}
//-->
</script>
</head>
<body onunload="setVal()">
<div id="panel" style="display:none">
	<div id="paneltitle">部门管理</div>
	
	<table border="0" cellspacing="1" cellpadding="0" class="query">
		<tr>
			<td class="head" width="10%">类型</td>
			<td class="body" width="10%">
				<select id="bmjzlx" style="width:100%" disabled="disabled">
					<option value="17">交警</option>
					<option value="99">公安</option>
				</select>
			</td>
			<td class="head" width="10%">名称</td>
			<td class="body" width="40%">
				<input type="text" id="bmmc" value="" readonly="readonly">
			</td>
			<td class="command" width="30%">
				<input type="hidden" id="glbm" value="">
				<input type="button" class="button_save" value="确定" onclick="doSure()">
				<input type="button" class="button_del" value="清空" onclick="doReset()">
				<input type="button" class="button_close" value="关闭" onclick="doClose()">
			</td>
		</tr>
	</table>
 	<div class="s1" style="height:4px;"></div>
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<td class="tag_head_close" align="center" onClick="changepanel(1)" id="tagitem1">交警部门管理</td>
			<td class="tag_head_close" align="center" onClick="changepanel(2)" id="tagitem2">公安部门管理</td>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>
 
	<table border="0" cellspacing="0" cellpadding="0" class="tag_body_table" id="tagpanel1" style="display:none;">
		<tr>
			<td><iframe id="tagframe1" src="" width="100%" marginwidth="0" marginheight="0" align="top" scrolling="no" frameborder="0"></iframe></td>
		</tr>
	</table>
 
	<table border="0" cellspacing="0" cellpadding="0" class="tag_body_table" id="tagpanel2" style="display:none;">
		<tr>
			<td><iframe id="tagframe2" src="" width="100%" marginwidth="0" marginheight="0" align="top" scrolling="no" frameborder="0"></iframe></td>
		</tr>
	</table>
</div>
</body>
</html>