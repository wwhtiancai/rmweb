<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title></title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="css/xtree.css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
	$("[limit]").limit();
});
function showdetail(glbm, yhdh){
	parent.editone(glbm, yhdh);
}
//-->
</script>
</head>
<body>
<div class="queryresult"></div>
<c:if test="${queryList!=null}">
<div id="result">
	<div id="resulttitle">查询内容</div>
	<table border="0" cellspacing="1" cellpadding="0" class="list">
		<col width="7%">
		<col width="10%">
		<col width="6%">
		<col width="27%">
		<col width="12%">
		<col width="18%">
		<col width="6%">
		<col width="12%">
		<tr class="head">
			<td>用户代号</td>
			<td>姓名</td>
			<td>管理员</td>
			<td>管理部门</td>
			<td>密码有效期</td>
			<td>身份证号码</td>
			<td>状态</td>
			<td>最近登录时间</td>
		</tr>
		<c:forEach items="${queryList}" var="current">
			<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="showdetail('<c:out value='${current.glbm}'/>', '<c:out value='${current.yhdh}'/>')">
				<td><c:out value="${current.yhdh}"/></td>
				<td><c:out value="${current.xm}"/></td>
				<td><c:out value="${current.xtglymc}"/></td>
				<td limit="25"><c:out value="${current.bmmc}"/></td>
				<td limit="date"><c:out value="${current.mmyxq}"/></td>
				<td><c:out value="${current.sfzmhm}"/></td>
				<td><c:out value="${current.ztmc}"/></td>
				<td limit="date"><c:out value="${current.zjdlsj}"/></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" class="page">
			<c:out value="${controller.clientScript}" escapeXml="false"/>
			<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
			</td>
		</tr>
	</table>
</div>
</c:if>
</body>
</html>
