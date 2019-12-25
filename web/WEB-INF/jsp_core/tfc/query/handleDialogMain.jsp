<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>机动车轨迹查询未反馈列表</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>

<script language="javascript">
function showdetail(cxxh) {
	var r = window.showModalDialog("tfcQuery.tfc?method=showDialog&cxxh="+cxxh,"","dialogWidth:600px;dialogHeight:300px;center:1;help:0;resizable:0;status:0;scroll:0;");
	if (typeof r != "undefined"){
		if(r["code"]=="1") {
			window.location.reload();
		}
	}
}

$(document).ready(function(){
	$("[limit]").limit();
});

</script>
</head>
<body>
<div id="panel" style="display:none; width:100%">
	<div id="paneltitle">机动车轨迹查询未反馈列表</div>
	<div class="queryresult"></div>
	<c:if test="${queryList!=null}">
	<div id="result">
		<table border="0" cellspacing="1" cellpadding="0" class="list">
			<col width="18%">
			<col width="27%">
			<col width="10%">
			<col width="15%">
			<col width="15%">
			<col width="15%">
			<tr class="head">
				<td>查询原因</td>
				<td>查询条件</td>
				<td>查询类型</td>
				<td>查询范围</td>
				<td>查询时间</td>
				<td>查询结果</td>
			</tr>
			<c:forEach items="${queryList}" var="current">
			<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="showdetail('<c:out value='${current.cxxh}'/>')">
				<td limit="14"><c:out value="${current.cxyymc}"/></td>
				<td align="left" limit="19"><c:out value="${current.cxtj}"/></td>
				<td><c:out value="${current.cxlxmc}"/></td>
				<td><c:out value="${current.cxfwmc}"/></td>
				<td limit="time"><c:out value="${current.cxsj}"/></td>
				<td ><c:out value="${current.cxjg}"/></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="6" class="page">
				<c:out value="${controller.clientScript}" escapeXml="false"/>
				<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
				</td>
			</tr>
		</table>
	</div>
	</c:if>
</div>
</body>
</html>