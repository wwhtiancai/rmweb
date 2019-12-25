<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>��ͨ����ͳ��</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="chart/css/stat.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/treeview/jquery.treeview.css" />
<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/jquery-position.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/jquery.loadthumb.js" type="text/javascript"></script>
<script src="rmjs/treeview/jquery.treeview.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>
<script type="text/javascript">
<!--

var gnid;
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
	changepanel('<c:out value="${firstTab}"/>');
	<c:if test="${tfcpass!=null}">
		$("[limit]").limit();
		$("#kssj").val("<c:out value='${tfcpass.kssj}'/>");
		$("#jssj").val("<c:out value='${tfcpass.jssj}'/>");
		$("#kkbh").val("<c:out value='${tfcpass.kkbh}'/>");
		$("#kkjc").val("<c:out value='${tfcpass.kkjc}'/>");
		$("#lltjlx").val("<c:out value='${tfcpass.lltjlx}'/>");
		getDirect();
	</c:if>
});


function doChecking(){
	if(!checkNull($("#kssj"),"��ʼʱ��")) return false;
	if(!checkNull($("#jssj"),"����ʱ��")) return false;
	if(!compareDate($("#kssj").val(),$("#jssj").val(),"��ʼʱ��","����ʱ��")) return false;
	return true;
}

function changepanel(i){

	<c:forEach items="${funcList}" var="current">
		$("#tagitem<c:out value='${current.gnid}'/>").attr("class", "tag_head_close");
		$("#<c:out value='${current.gnid}'/>TR").css("display", "none");
		$("#<c:out value='${current.gnid}'/>TR2").css("display", "none");
		$("#<c:out value='${current.gnid}'/>TR3").css("display", "none");
	</c:forEach>
	$("#tagitem" + i).attr("class", "tag_head_open");
	$("#" + i + "TR").css("display", "block");
	gnid = i;
	$("#gatetree_road").treeview();

}
function selectTollgate(){

	curKKBH = $("#kkbh").val();
	curKKJC = $("#kkjc").val();
	
	curArgs = curKKBH + "||" + curKKJC;
	
	var r = window.showModalDialog("component.dev?method=fwdTollgateRadio", curArgs, "dialogWidth:800px;dialogHeight:600px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r != "undefined"){
		$("#kkbh").val(r[0]);
		$("#kkjc").val(r[1]);
	}else{
		$("#kkbh").val('');
		$("#kkjc").val('');
	}
	
	closes();
	getDirect();
	opens();
}
function getDirect(){
	$("#fxlx").empty();
	curKkbh = $("#kkbh").val();
	if(curKkbh == null || curKkbh == ''){
		return;
	}
	curUrl = "<c:url value='component.dev?method=getTollgateDirect&kkbh='/>" + curKkbh;
	$.getJSON(curUrl, 
		function(data){
			$.each(data, function(i,item){
				$("#fxlx").addOption(item.fxbh, decodeURI(item.fxmc));
			});
			$("#fxlx").val("<c:out value='${tfcpass.fxlx}'/>");
		}		
	);
}

function stat_cmd(){
	if(gnid == 'l131'){
		$("#gnid").val(gnid);
		$("#myform").attr("action","<c:url value='/passstat.tfc?method=fwdFlowStatResult'/>");
		closes();
		$("#myform").submit();
	}else if(gnid == 'l132'){
		$("#gnid").val(gnid);
		$("#myform").attr("action","<c:url value='/passstat.tfc?method=statPassFlowReg'/>");
		closes();
		$("#myform").submit();
	}
	
}

function stat_cmd2(){
	if(!doChecking1()){
		return;
	}
	var kkbhs = $("input[name=kkbhs]");
	isChecked = false;
	for(i = 0; i < kkbhs.length; i++){
		if(kkbhs[i].checked){
			isChecked = true;
			break;
		}
	}
	if(!isChecked){
		alert('��ѡ����Ҫͳ�ƵĿ��ڣ�');
		return;
	}
	curWid = openwin("", "flowstat");
	$("#myform").attr("target", "flowstat");
	$("#myform").attr("action","<c:url value='/passstat.tfc?method=fwdFlowStatResult'/>");
	$("#myform").submit();
	curWid.focus();
}

function doChecking1(){
	if(!compareDate($("#kssj1").val(),$("#jssj1").val(),"��ʼʱ��","����ʱ��")) return false;
	return true;
}
//-->
</script>

</head>
<body>

<div id="panel" style="display:none">
	<div id="paneltitle">��ͨ����ͳ��</div>
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<c:forEach items="${funcList}" var="current">
				<td class="tag_head_close" align="center" onClick="changepanel('<c:out value="${current.gnid}"/>')" id="tagitem<c:out value="${current.gnid}"/>"><c:out value="${current.mc}"/></td>
			</c:forEach>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>
	<form name="myform" id="myform" action="" method="post">
		<c:forEach items="${funcList}" var="current">
		<c:if test='${current.gnid == "l131"}'>
			<table border="0" cellspacing="0" cellpadding="0" id="l131TR">
				<tr >
					<td width="30%">
						<fieldset style="width:100%;border:1px solid #CCCCCC;">
							<legend style="font-size: 12px;">ͳ�ƿ���</legend>
							<div style="width:100%;height:600px;overflow-y:auto;"><c:out value="${gateHtml}" escapeXml="false"/></div>
						</fieldset>
					</td>
					<td width="6px"></td>
					<td valign="top" style="padding-top:6px;" width ="70%">
						<table border="0" cellspacing="1" cellpadding="0" class="query">
							<tr>
								<td class="head" width="15%">ͳ����ʼʱ��</td>
								<td class="body" width="35%"><h:datebox id="kssj1" name="kssj1" showType="1"/></td>
								<td class="head" width="15%">ͳ�ƽ�ֹʱ��</td>
								<td class="body" width="35%"><h:datebox id="jssj1" name="jssj1" showType="1"/></td>
							</tr>
							<tr>
								<td colspan="4" class="submit">
									<input type="button" class="button_other" onclick="stat_cmd2()" value="�վ�����">
									<input type="button" class="button_other" onclick="stat_cmd3()" value="Сʱ����">
									<input type="button" class="button_close" onclick="javascript:window.close();" value="�ر�">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</table>
			</c:if>
		<c:if test='${current.gnid == "l132"}'>
		<table border="0" cellspacing="1" cellpadding="0" class="query" width="100%" id="l132TR">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
			<tr >
				<td class="head" width="10%">��������</td>
			     <td class="body" width="30%">
					<input type="text" id="kkjc" name="kkjc" value="" readonly="readonly" style="width:260px;">
					<img alt="" src="images/tollgate-s2.gif" onclick="selectTollgate()" align="absmiddle" style="cursor:hand">
				</td>
				<td class="head" width="8%">��ط���</td>
				<td class="body" width="16%">
					<select id="fxlx" name="fxlx" style="width:100%"></select>
				</td>
				<td class="body" width="50%" colspan="2"></td>

			</tr>
			<tr>
				<td class="head" >ͳ������</td>
				<td class="body"><select style="width:100%" name="lltjlx" id="lltjlx">
					<c:forEach items="${lltjlxlist}" var="current">
						<option value="<c:out value='${current.dmz}'/>"><c:out value='${current.dmz}'/>:<c:out value="${current.dmsm1}"/></option>
					</c:forEach>
				</select>		
				</td>
				<td class="head" >ͳ����ʼʱ��</td>
				<td class="body" colspan="3"><h:datebox id="kssj" name="kssj" showType="1"/>�� <h:datebox id="jssj" name="jssj" showType="1"/>	
				</td>
				
			</tr>
			<tr >
			<td  class="submit" colspan="6">
				<input type="hidden" id="kkbh" name="kkbh" value="">
				<input type="hidden" id="gnid" name="gnid" value="">
				<input type="button" value="ͳ��" class="button_query" onClick="stat_cmd()">&nbsp;
				<input type="button" value="�ر�" class="button_close" onClick="javascript:window.close()">
			</td>
			</tr>
			</table>
	   </c:if>
	</c:forEach>
	
	</form>
	<div class="queryresult"></div>
	<c:if test="${html != null}">
	<div id="result">
		<div id="resulttitle">ͳ������</div>
		<%=request.getAttribute("html") %>
		<div id="statchart">
			<div class='chartcmd'>
				<input type="image" src="chart/css/zzt.png" onclick="showChart(0)">
				<input type="image" src="chart/css/bt.png" onclick="showChart(1)">
				<input type="image" src="chart/css/word.png" onclick="saveAsWord()">
				<input type="image" src="chart/css/excel.png" onclick="saveAsExcel()">
			</div>
			<div id='chart' class='chart'></div>
			<div id='chart2' class='chart'></div>		
		</div>
	</div>
	</c:if>
</div>
</body>
</html>