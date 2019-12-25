<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>电子标签已贴上传</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">电子标签已贴上传</div>
	<form action="/rmweb/eri.frm?method=upload" method="post" name="myform" id="myform" enctype="multipart/form-data">
	<div id="block">
		<div id="blocktitle">电子标签信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="20%">
			<col width="10%">
			<col width="60%">
			
			<tr>
				<td class="head">上传类型</td>
		       	<td class="body" colspan = "3">
		           	<input type="radio" name="uploadType" value="1" checked="checked"/>普通做卡 
					<input type="radio" name="uploadType" value="2" />公安网做卡
		       	</td>
			</tr>
			<tr>
				<td class="head">领用日期</td>
		       	<td class="body">
		           	<h:datebox id="lyrq" name="lyrq" showType="1" width="80%"/>
		       	</td>
		       	<td class="head">已贴标签上传</td>
		       	<td class="body">
		           	<input type="file" name="file_upload"  style="width:100%;">
		       	</td>
		   	</tr>
		</table>
	</div>
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
				<input type="submit" name="savebutton" id="savebutton" value="保存" class="button_save">
			</td>
		</tr>
	</table>
	</form>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
$(function() {
	$.datepicker.setDefaults($.datepicker.regional['']);
	$(".jscal").each(function () {
        eval($(this).html());
    });
	
	<c:if test="${errMsg != null}">
		var errMsg = "<c:out value='${errMsg}'/>"+"</br>请重新上传符合条件的Excel！";
		displayDialog(3,errMsg,"");
	</c:if>
	
});
</script>
</body>
</html>
