<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
<title>${cxmc}</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
	var obj = window.dialogArguments;
	var html = "<input type='text' name='mrz' id='mrz' style='width:100%' value=''>";
	if(obj.xsxs=='2'){
		var vals=obj.csz.split(";");
		html="<select id='mrz' name='mrz'>";
		html+="<option value=''></option>";
		for(var i=0; i<vals.length&&obj.gjz!='GISJB'; ++i){
			html += "<option value='" + vals[i].split("-")[0] + "'";
			html += ">" + vals[i] + "</option>";
		}
		for(var i=0; i<vals.length&&obj.gjz=='GISJB'; ++i){
			if(obj.xtyxms=='1'){
				if("1" == vals[i].split("-")[0]||"2" == vals[i].split("-")[0]){
					html += "<option value='" + vals[i].split("-")[0] + "'";
					html += ">" + vals[i] + "</option>";
				}
			}
			if(obj.xtyxms=='2'){
				if("1" == vals[i].split("-")[0]){
					html += "<option value='" + vals[i].split("-")[0] + "' selected>" + vals[i] + "</option>";
				}
			}
			if(obj.xtyxms=='3'){
				if("1" == vals[i].split("-")[0]){
					html += "<option value='" + vals[i].split("-")[0] + "' selected>" + vals[i] + "</option>";
				}
			}
			if(obj.xtyxms=='4'){
				if("0" == vals[i].split("-")[0]){
					html += "<option value='" + vals[i].split("-")[0] + "' selected>" + vals[i] + "</option>";
				}
			}
		}
		html+="</select>";
	}
	$("#signDiv").append(html);
	$("#gislx").val(obj.gislx);
	$("#xtyxms").val(obj.xtyxms);
	$("#gjz").val(obj.gjz);
	//$("#csz").val(obj.csz);
	$("#csmc").val(obj.csmc);
	$("#cssm").val(obj.cssm);
	$("#sfxs").val(obj.sfxs);
	$("#xssx").val(obj.xssx);
	$("#dmlb").val(obj.dmlb);
	$("#mrz").val(obj.mrz);
	$("#sjgf").val(obj.sjgf);
	$("#bz").val(obj.bz);
	$("#xsxs").val(obj.xsxs);
	$("#ButTest").hide();
	if(obj.gjz=='GISDTFWDZ1'||obj.gjz=='GISDTFWDZ2'||obj.gjz=='GISDTFWDZ3'||obj.gjz=='GISSJFWGXDZ'){
		$("#ButTest").show();
	}
});

function savePara(){
	if(!doChecking()) return;
	$("#myform").attr("action","<c:url value='/gispara.frm'/>?method=saveGispara");
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
	});
}
function returns(data) { 
	if(data["code"]=="1"){
		alert("GIS参数配置保存成功！");
		window.returnValue=1;
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}
function doChecking(){
	if(!checkNull($("#cssm"),"参数说明")) return false;
	var gislx=$("#gislx").val();
	var gjz=$("#gjz").val();
	if(gislx!='1'&&(gjz=='GISLX'||gjz=='GISKJSJYQ'||gjz=='GISFWYH'||gjz=='GISXZQHB'||gjz=='GISDLSLXXB'||gjz=='GISDLTPGXB')){
		if(!checkNull($("#mrz"),"参数值")) return false;
	}
	return true;
}
function testfwdz(){
	if(!checkNull($("#mrz"),"参数值")) return false;
	$("#myform").attr("action","<c:url value='/gispara.frm'/>?method=testActiveWebIp&fwdz="+$("#mrz").val());
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returnsFwdz
	});
}
function returnsFwdz(data) { 
	if(data["code"]=="1"){
		alert(decodeURIComponent(data["message"]));
		opens();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}
</script>
</head>
<body>
<div class="s1" style="height:5px;"></div>
	<form action="" method="post" name="myform" id="myform" enctype="multipart/form-data">
	<input type="hidden" name="gislx" id="gislx" value="">
	<input type="hidden" name="xtyxms" id="xtyxms" value="">
	<input type="hidden" name="csz" id="csz" value="">
	<input type="hidden" name="sfxs" id="sfxs" value="">
	<input type="hidden" name="xssx" id="xssx" value="">
	<input type="hidden" name="dmlb" id="dmlb" value="">
	<input type="hidden" name="sjgf" id="sjgf" value="">
	<input type="hidden" name="bz" id="bz" value="">
	<input type="hidden" name="xsxs" id="xsxs" value="">
	<table border="0" cellspacing="1" cellpadding="0" class="list_table">		
		<tr>
			<td width="70" class="list_headrig">关键值&nbsp;</td>
			<td width="120"  class="list_body_left">
				<input type="text" id="gjz" name="gjz" id="gjz" class="text_12" value="" readonly>
			</td>
			<td width="70" class="list_headrig">参数名称&nbsp;</td>
			<td class="list_body_left">
				<input type="text" id="csmc" name="csmc" class="text_12"  style="width:200" value="" readonly>
			</td>
		</tr>
		<tr>
			<td class="list_headrig" valign="top">参数说明&nbsp;</td>
			<td class="list_body_left" colspan="3" style="word-break:break-all" align="left" >
				<textarea id="cssm" name="cssm" rows="8" style="width:98%" readonly></textarea>
			</td>
		</tr>
		<tr>
			<td class="list_headrig">参数值&nbsp;</td>
			<td class="list_body_left" colspan="3">
				<div id="signDiv"></div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="list_body_out" align="right">
			<input type="button" id="ButTest" value="测试地址" onClick="testfwdz()" class="button_other">
			<input type="button" value=" 保 存 " onClick="savePara()" class="button_save">
			<input type="button" value=" 退 出 " onclick="quit()" class="button_close">
			</td>
		</tr>
	</table>
	</form>
	<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>

