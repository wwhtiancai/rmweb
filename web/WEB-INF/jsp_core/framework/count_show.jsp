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
<script language="JavaScript" src="chart/FusionCharts.js" type="text/javascript"></script>
<script language="JavaScript" src="chart/css/stat.js" type="text/javascript"></script>
<style>
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
	line-height:24px;
	background:#66ffff;
}
.td2{
	font-size:12px;
	color:black;
	line-height:24px;
	background:#FFFFFF;
}
.td3{
	font-size:12px;
	color:black;
	background:#eeeeee;
}
.help{
	font-size:12px;
	color:red;
}
table td, table th{ text-align:center; font-size:12px; line-height:20px}
#ax{width:250px;height:50px;background:#EEEEEE;position:fixed;right:20px;bottom:20px;}
</style>
<script type="text/javascript">
function showChart(charttype){
	if(charttype == 0){
		var chart = new FusionCharts("chart/charts/Column3D.swf", "chart", "100%", "240");
		chart.setDataXML("<c:out value='${mscxml}' escapeXml='false'/>");	   
		chart.render("chart");
	}else if(charttype == 1){
		var chart = new FusionCharts("chart/charts/Pie3D.swf","chart","100%","200");
		chart.setDataXML("<c:out value='${pXml}' escapeXml='false'/>");
		chart.render("chart2");
		var chart2 = new FusionCharts("chart/charts/Pie3D.swf","chart","100%","200");
		chart2.setDataXML("<c:out value='${p2Xml}' escapeXml='false'/>");
		chart2.render("chart3");
	}else if(charttype == 2){
		var chart = new FusionCharts("chart/charts/Column3D.swf", "chart", "100%", "200");
		chart.setDataXML("<c:out value='${mscxml_susp}' escapeXml='false'/>");	   
		chart.render("chart4");
	}else if(charttype == 3){
		var chart = new FusionCharts("chart/charts/Column3D.swf", "chart", "100%", "200");
		chart.setDataXML("<c:out value='${mscxml_alarm}' escapeXml='false'/>");	   
		chart.render("chart5");
	}else if(charttype == 4){
		var chart = new FusionCharts("chart/charts/MSColumn3D.swf", "chart", "100%", "200");
		chart.setDataXML("<c:out value='${asXML}' escapeXml='false'/>");	   
		chart.render("chart6");
	}else if(charttype == 5){
		var chart = new FusionCharts("chart/charts/Column3D.swf", "chart", "100%", "240");
		chart.setDataXML("<c:out value='${mscxml_gcl}' escapeXml='false'/>");	   
		chart.render("chart7");
	}else{
		alert("图表类型错误!");
	}
}
function init(){
	//showChart(0);
	showChart(1);
	//showChart(2);
	//showChart(3);
	//showChart(4);
	showChart(5);
}
</script>
</head>

<body onload="init()">
<table width="100%" border="0" cellspacing="0" cellpadding="4">
	<tr>
		<td width="75%">
			<table width="100%" border="0" cellspacing="0" cellpadding="4">
				<tr>
					<td>
						<div id='chart2'>&nbsp;</div>
					</td>
					<td>
						<div id='chart3'>&nbsp;</div>	
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id='chart7'>&nbsp;</div>
					</td>
				</tr>
			</table>
		</td>
		<td width="25%" valign="top">
			<table width="95%" border="1" cellspacing="0" cellpadding="0" bordercolor="#666666" style="border-collapse:collapse; margin-top:10px" align="center">
				<tr>
					<th class="td1" width="60%" colspan="2">统计内容</th>
					<th class="td1" width="20%"><c:out value="${cell[0][3].cellVal}" /></th>
					<th class="td1" width="20%">比例</th>
				</tr>
				<tr>
					<td class="td2"  colspan="2">卡口总数</td>
					<td class="td2"><c:out value="${cell[1][3].cellVal}" /></td>
					<td class="td2">100%</td>
				</tr>
				<tr>
					<td class="td2" rowspan="4">卡口位置</td>
					<td class="td2">省际卡口</td>
					<td class="td2"><c:out value="${cell[2][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[3][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2">市际卡口</td>
					<td class="td2"><c:out value="${cell[4][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[5][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2">县际卡口</td>
					<td class="td2"><c:out value="${cell[6][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[7][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2">其他卡口</td>
					<td class="td2"><c:out value="${cell[8][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[9][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2" rowspan="5">卡口分布</td>
					<td class="td2">高速卡口</td>
					<td class="td2"><c:out value="${cell[10][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[11][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2">国道卡口</td>
					<td class="td2"><c:out value="${cell[12][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[13][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2">省道卡口</td>
					<td class="td2"><c:out value="${cell[14][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[15][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2">县道卡口</td>
					<td class="td2"><c:out value="${cell[16][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[17][3].cellVal}" /></td>
				</tr>
				<tr>
					<td class="td2">其他道路</td>
					<td class="td2"><c:out value="${cell[18][3].cellVal}" /></td>
					<td class="td2"><c:out value="${cell[19][3].cellVal}" /></td>
				</tr>
			</table>
			
			<table width="95%" border="1" cellspacing="0" cellpadding="0" bordercolor="#666666" style="border-collapse:collapse; margin-top:20px" align="center">
				<tr>
					<th class="td1" width="70%">统计内容</th>
					<th class="td1" width="30%">合计</th>
				</tr>
				<tr>
					<td class="td2">过车总量<c:out value="${scope}" /></td>
					<td class="td2"><c:out value="${gczl}" /></td>
				</tr>
				<tr>
					<td class="td2">布控总量<c:out value="${scope}" /></td>
					<td class="td2"><c:out value="${bkzs}" /></td>
				</tr>
				<tr>
					<td class="td2">预警总量<c:out value="${scope}" /></td>
					<td class="td2"><c:out value="${yjzs}" /></td>
				</tr>
			</table>
		</td>
	</tr>	

</table>
</body>
</html>
