<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>交通流量统计</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/treeview/jquery.treeview.css" />
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script src="rmjs/treeview/jquery.treeview.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript">
<!--
if (!window.console) {
	window.console = {
		log: function() {
			alert(arguments[0]);
		}
	}
}
$(window).bind("load",function(){
	$.datepicker.setDefaults($.datepicker.regional['']);
	$(".jscal").each(function () {
		eval($(this).html());
	});
});
$(document).ready(function(){
	$("#gatetree_road").treeview();
	<c:if test="${tollgate!=null}">
		$("[limit]").limit();
	</c:if>
});
function stat_cmd(){
	if(!doChecking1()){
		return;
	}
	var kkbhs = $("input[name=kkbh]");
	isChecked = false;
	for(i = 0; i < kkbhs.length; i++){
		if(kkbhs[i].checked){
			isChecked = true;
			break;
		}
	}
	if(!isChecked){
		alert('请选择需要统计的卡口！');
		return;
	}
	curWid = openwin("", "flowstat");
	$("#myformA").attr("target", "flowstat");
	$("#myformA").attr("action","<c:url value='/passstat.tfc?method=fwdFlowStatResult'/>");
	$("#myformA").submit();
	curWid.focus();
}
function create(){
	openwin("<c:url value='/tollgate.dev'/>?method=newReg", "tollgate");
}
function showdetail(kkbh){
	openwin("<c:url value='/tollgate.dev'/>?method=detailReg&kkbh="+kkbh, "tollgate");
}
function doE2(kkbh, kkjc){
	
}
function doChecking1(){
	if(!compareDate($("#kssj1").val(),$("#jssj1").val(),"开始时间","结束时间")) return false;
	return true;
}
//-->
</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">交通流量统计</div>
	<div id="query">
		<div id="querytitle">统计条件</div>
		<form action="" method="post" name="myformA" id="myformA">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="30%">
						<fieldset style="width:100%;border:1px solid #CCCCCC;">
							<legend style="font-size: 12px;">统计卡口</legend>
							<div style="width:100%;height:600px;overflow-y:auto;"><c:out value="${gateHtml}" escapeXml="false"/></div>
						</fieldset>
					</td>
					<td width="6px"></td>
					<td valign="top" style="padding-top:6px;">
						<table border="0" cellspacing="1" cellpadding="0" class="query">
							<tr>
								<td class="head" width="15%">统计起始时间</td>
								<td class="body" width="35%"><h:datebox id="kssj1" name="kssj" showType="1"/></td>
								<td class="head" width="15%">统计截止时间</td>
								<td class="body" width="35%"><h:datebox id="jssj1" name="jssj" showType="1"/></td>
							</tr>
							<tr>
								<td colspan="4" class="submit">
									<input type="button" class="button_other" onclick="stat_cmd()" value="日均流量">
									<input type="button" class="button_other" onclick="stat_cmd()" value="小时流量">
									<input type="button" class="button_close" onclick="javascript:window.close();" value="关闭">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>