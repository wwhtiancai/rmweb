<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>����Сʱ�������</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/treeview/jquery.treeview.css" />
<link rel="stylesheet" href="rmjs/treeview/screen.css" />
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script src="rmjs/treeview/jquery.treeview.js" type="text/javascript"></script>
<script src="chart/FusionCharts.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var flowMonitorTimer;
var flowMonitorChart;
$(document).ready(function(){
	changeGateDiv(1);
	$("#gatetree_road").treeview();
	changepanel(1);
	//test();
});
function startMonitor(kkbh, fxlx){
	fucname = "reladFlow(\'" + kkbh, + "\', \'" + fxlx + "\')";
	alert();
	clearFlowMonitorTimer();
	curFlowXml = getFlowXml(kkbh, fxlx);
	if(curFlowXml == '0'){
		alert("��ȡ������Ϣʧ�ܣ������²�����");
		return;
	}
	flowMonitorChart = new FusionCharts("chart/MSCombiDY2D.swf", "flowMonitorChart", "100%", "500", "0", "0");
	flowMonitorChart.setDataXML(curFlowXml);
	flowMonitorChart.render("FlowMonitorChart");
	reloadFlow(kkbh, fxlx, fucname);
}
function reloadFlow(kkbh, fxlx, funcname){
	curFlowXml = getFlowXml(kkbh, fxlx);
	if(curFlowXml != '0'){
		updateChartXML("flowMonitorChart", curFlowXml);
	}
	flowMonitorTimer = setTimeout(funcname, 30000);
}
function getFlowXml(kkbh, fxlx){
	curUrl = '<c:url value="/passstat.tfc?method=getFlowMonitorXml&kkbh=" />' + kkbh + '&fxlx=' + fxlx;
	curXml = '0';
	$.getJSON(curUrl, function(data){
		if(data['val'] == '0'){
			
		}else if(data['val'] == '1'){
			curXml = data['flowXml'];
		}else{
			
		}
	});
	return curXml;
}
function clearFlowMonitorTimer(){
	clearTimeout(flowMonitorTimer);
}
function changeGateDiv(orderType){
	//var r = window.showModalDialog("component.dev?method=fwdTollgateRadio", '', "dialogWidth:800px;dialogHeight:600px;center:1;help:0;resizable:0;status:0;scroll:0;");
	//alert("$" + r[0] + "#" + r[1] + "$");
	//return r;
	if(orderType == 1 && ($("#OrderByRoad").attr("checked") == null || $("#OrderByRoad").attr("checked") == '')){
		$("#OrderByRoad").attr("checked", "checked");	
		$("#OrderByDept").removeAttr("checked");
		$("#GateDiv_OrderByDept").css("display", "none");
		$("#GateDiv_OrderByRoad").css("display", "block");
	}else if(orderType == 2 && ($("#OrderByDept").attr("checked") == null || $("#OrderByDept").attr("checked") == '')){
		$("#OrderByDept").attr("checked", "checked");
		$("#OrderByRoad").removeAttr("checked");
		$("#GateDiv_OrderByRoad").css("display", "none");
		$("#GateDiv_OrderByDept").css("display", "block");
	}
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
function doE(kkbh, kkjc){
	alert(kkbh + "#" + kkjc);
}
function test(){
	chart = new FusionCharts("chart/MSCombiDY2D.swf", "chart", "100%", "588", "0", "0");
	var curXml='<chart caption="" showValues="0" sNumberSuffix="" decimals="3" setAdaptiveYMin="1" setAdaptiveSYMin="1" lineThickness="5" exportEnabled="1" exportAtClient="1" exportHandler="fcExporter1" animation="0" formatNumberScale="0">';
	curXml += ' <categories><category label="0ʱ" /><category label="1ʱ" /><category label="2ʱ" /><category label="3ʱ" /><category label="4ʱ" /><category label="5ʱ" /><category label="6ʱ" /><category label="7ʱ" /> <category label="8ʱ" /> <category label="9ʱ" /> <category label="10ʱ" /> <category label="11ʱ" /> <category label="12ʱ" /><category label="13ʱ" /><category label="14ʱ" /><category label="15ʱ" /><category label="16ʱ" /><category label="17ʱ" /><category label="18ʱ" /><category label="19ʱ" /><category label="20ʱ" /><category label="21ʱ" /><category label="22ʱ" /><category label="23ʱ" /> </categories>';
	curXml += ' <dataset seriesname="Сʱ����"> <set value="2673"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/><set value="8081"/> <set value="8081"/>';
	curXml += ' <set value="0"/>';
	curXml += ' <set value="0"/> <set value="0"/> </dataset>';
	curXml += ' <dataset seriesname="���¾�ֵ" renderAs="Area"> <set value="2173"/> <set value="4081"/> <set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/><set value="3489"/> <set value="2551"/> <set value="2151"/> </dataset>';
	curXml += ' <dataset parentYAxis="S" seriesname="Сʱƽ���ٶ�"> <set value="67"/> <set value="51"/> <set value="42"/> <set value="0"/> <set value="0"/> </dataset>';
	curXml += ' </chart>';
	
	//chart = new FusionCharts("chart/MSCombiDY2D.swf", "chart", "560", "400", "0", "0");
	chart.setDataXML(curXml);
	//chart.setDataURL("http://10.2.44.55/demo/FlowXML.jsp");
	chart.render("monitorChart");
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
	<div id="paneltitle">����ʵʱ���</div>
	<table border="0" cellspacing="0" cellpadding="0" align="center" class="tag_head_table">
		<tr id="tag_console">
			<td class="tag_head_front"></td>
			<td class="tag_head_close" align="center" onClick="changepanel(1)" id="tagitem1">�����������</td>
			<td class="tag_head_close" align="center" onClick="changepanel(2)" id="tagitem2">��·�������</td>
			<td class="tag_head_bg"><span class="s1">&nbsp;</span></td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" style="display:none;" class="tag_body_table" id="tagpanel1" width="100%">
		<tr>
			<td valign="middle"">
				<table border="0" cellspacing="1" cellpadding="0" class="detail">
					<tr height="42px">
						<td class="body" rowspan="2">
						<div id="monitorChart"></div>
						</td>
						<td class="body" width="200px;">
							<div style="width:100%;text-align:center;height:20px;font-weight:bold;line-height:20px;">������Ϣ</div>
							<div style="width:100%;text-align:center;height:22px;line-height:22px;">
								<input type="radio" id="OrderByRoad" onclick="changeGateDiv(1)"><label style="cursor:hand;" onclick="changeGateDiv(1)">����·����</label>&nbsp;
								<input type="radio" id="OrderByDept" onclick="changeGateDiv(2)"><label style="cursor:hand;" onclick="changeGateDiv(2)">����������</label>
							</div>
						</td>
					</tr>
					<tr height="546px">
						<td class="body" width="200px;">
							<div id="GateDiv_OrderByRoad" class="GateDiv"><c:out value="${gateHtmlByRoad}" escapeXml="false"/></div>
							<div id="GateDiv_OrderByDept" class="GateDiv"><c:out value="${gateHtmlByDept}" escapeXml="false"/></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table border="0" cellspacing="1" cellpadding="0" style="display:none;" class="tag_body_table" id="tagpanel2">
		<tr>
			<td>
			
			</td>
		</tr>
	</table>
</div>
</body>
</html>