<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>卡口小时流量监控</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/treeview/jquery.treeview.css" />
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script src="rmjs/treeview/jquery.treeview.js" type="text/javascript"></script>
<script src="chart/FusionCharts-mmm.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var flowMonitorTimer;
var flowMonitorChart;

var isStop = true;
var isInit = false;

$(document).ready(function(){
	changepanel(1);
});
function selectTollgate(){
	if(isStop == false){
		alert('您正在监控  “' + $("#kkjc").val() + '” 卡口，请先暂停后再选择需要监控的其他卡口！');
		return;
	}
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
	$("#direct").empty();
	curKkbh = $("#kkbh").val();
	
	if(curKkbh == null || curKkbh == ''){
		return;
	}
	curUrl = "<c:url value='component.dev?method=getTollgateDirect&kkbh='/>" + curKkbh;
	$.getJSON(curUrl, 
		function(data){
			$.each(data, function(i,item){
				$("#direct").addOption(item.fxbh, decodeURI(item.fxmc));
			});
		}		
	);
}
function startMonitor(){
	isStop = false;
	$("#startBtn").attr("disabled", true);
	$("#stopBtn").attr("disabled", false);
	clearFlowMonitorTimer();
	reloadFlow();
}
function stopMonitor(){
	clearFlowMonitorTimer();
	isStop = true;
	isInit = false;
	$("#stopBtn").attr("disabled", true);
	$("#startBtn").attr("disabled", false);
}
function reloadFlow(){
	curKkbh = $("#kkbh").val();
	curFxlx = $("#direct").val();
	curUrl = '<c:url value="/passstat.tfc?method=getFlowMonitorJSON&kkbh=" />' + curKkbh + '&fxlx=' + curFxlx;
	if(!isInit){
		flowMonitorChart = new FusionCharts("chart/charts/MSCombiDY2D.swf", "tmrichart", "100%", "550", "0", "0");
		$.ajax({
			type: "GET",
			url: curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success: function(data){
				codeVal = data.code;
				msgVal = data.msg;
				if(codeVal == '1'){
					curXML = getFlowMonitorXML(msgVal, data.chour);
					flowMonitorChart.setDataXML(curXML);
					flowMonitorChart.render("flowChart");
					isInit = true;
				}
			}
		});
	}else{
		$.ajax({
			type: "GET",
			url: curUrl,
			cache:false,
			async:false,
			dataType:"json",
			success: function(data){
				codeVal = data.code;
				msgVal = data.msg;
				if(codeVal == '1'){
					curXML = getFlowMonitorXML(msgVal, data.chour);
					updateChartXML("tmrichart", curXML);
				}
			}
		});
	}
	flowMonitorTimer = setTimeout("reloadFlow()", 10000);
}

function getFlowMonitorXML(data, chour){
	var xml = "<chart caption='' showValues='0' sNumberSuffix='' decimals='3' setAdaptiveYMin='1' setAdaptiveSYMin='1' lineThickness='5' animation='0' formatNumberScale='0'>";
	var xml_cg = "<categories>";
	var xml_ll = "<dataset seriesname='流量'>";
	var xml_sd = "<dataset parentYAxis='S' seriesname='平均速度'>";
	
	for(i = 0; i < 24; i++){
		var ikey = "";
		if(i < 10){
			ikey = "0" + i;
		}else{
			ikey = i;
		}
		xml_cg = xml_cg + "<category label='" + i + "时'/>";
		if(i <= chour){
			xml_ll = xml_ll + "<set value='" + data[0][ikey] + "'/>";
			xml_sd = xml_sd + "<set value='" + data[1][ikey] + "'/>";
		}else{
			xml_ll = xml_ll + "<set value=''/>";
			xml_sd = xml_sd + "<set value=''/>";
		}
		
	}
	xml = xml + xml_cg + "</categories>" + xml_ll + "</dataset>" + xml_sd + "</dataset></chart>";
	return xml;
}

function clearFlowMonitorTimer(){
	clearTimeout(flowMonitorTimer);
}
function changepanel(i){
	var tag=$("#tag_console > td").size() - 2;
	for (var j=1;j<=tag;j++){
		document.getElementById('tagpanel'+j).style.display="none";
		document.getElementById('tagitem'+j).className="tag_head_close";	
	}
	document.getElementById('tagpanel'+i).style.display="block";
	document.getElementById('tagitem'+i).className="tag_head_open";
}
function test(){
	chart = new FusionCharts("chart/charts/MSCombiDY2D.swf", "chart", "100%", "550", "0", "0");
	var curXml="<chart caption='卡口流量监控' showValues='0' sNumberSuffix='' decimals='3' setAdaptiveYMin='1' setAdaptiveSYMin='1' lineThickness='5' animation='0' formatNumberScale='0'><categories><category label='0时' /><category label='1时' /><category label='2时' /><category label='3时' /><category label='4时' /><category label='5时' /><category label='6时' /><category label='7时' /><category label='8时' /><category label='9时' /><category label='10时' /><category label='11时' /><category label='12时' /><category label='13时' /><category label='14时' /><category label='15时' /><category label='16时' /><category label='17时' /><category label='18时' /><category label='19时' /><category label='20时' /><category label='21时' /><category label='22时' /><category label='22时' /> </categories><dataset seriesname='流量'><set value='763'/><set value='769'/><set value='745'/><set value='727'/><set value='758'/><set value='857'/><set value='793'/><set value='843'/><set value='882'/><set value='1210'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/></dataset><dataset parentYAxis='S' seriesname='平均速度'><set value='71'/><set value='76'/><set value='70'/><set value='70'/><set value='69'/><set value='71'/><set value='71'/><set value='71'/><set value='69'/><set value='72'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/><set value='0'/></dataset></chart>";
	chart.setDataXML(curXml);
	chart.render("flowChart");
}
//-->
</script>
<style type="text/css">
.GateDiv{
	overflow-y:scroll; 
	height:546px;
	display:block;
}
</style>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">流量实时监控</div>
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<td class="tag_head_close" align="center" onClick="changepanel(1)" id="tagitem1">卡口流量监控</td>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>
	<div style="display:none;width:100%;height:600px;" class="tag_body_table" id="tagpanel1">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<tr>
				<td class="head" width="8%">卡口</td>
				<td class="body" width="40%">
					<input type="text" id="kkjc" name="kkjc" value="" readonly="readonly" style="width:330px;">
					<img alt="" src="images/tollgate-s2.gif" onclick="selectTollgate()" align="absmiddle" style="cursor:hand">
				</td>
				<td class="head" width="8%">方向</td>
				<td class="body" width="24%">
					<select id="direct" style="width:100%"></select>
				</td>
				<td class="submit" width="40%">
					<input type="hidden" id="kkbh" name="kkbh" value="">
					<input id="startBtn" type="button" value="开始" onclick="startMonitor()" class="button_save">
					<input id="stopBtn" type="button" value="停止" onclick="stopMonitor()" class="button_close">
				</td>
			</tr>
		</table>
		
		<div id="flowChart" style="display:block;width:100%;height:550px;margin-top:6px;"></div>
		
	</div>
	<div style="display:none;width:100%;height:600px;" class="tag_body_table" id="tagpanel2">
		
	</div>
</div>
</body>
</html>