<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>���ڹ������ݽ���������</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
<c:if test="${queryList!=null}">
	$("[limit]").limit();
</c:if>
<c:if test="${tollgate.glbm!=null}">
	$("#glbm").val("<c:out value='${tollgate.glbm}'/>");
	$("#kklx").val("<c:out value='${tollgate.kklx}'/>");
</c:if>
});
function query_cmd(){
	$("#myform").attr("action","<c:url value='/tollgate.dev?method=listAuthorization'/>");
	closes();
	$("#myform").submit();
}
function showdetail(kkbh){
	openwin("<c:url value='/tollgate.dev'/>?method=detailAuthorization&kkbh="+kkbh,"tollgate");
}
//-->
</script>
</head>
<body onUnload="closesubwin()">
<div id="panel" style="display:none">
	<div id="paneltitle">���ڹ������ݽ���������</div>
	<div id="query">
		<div id="querytitle">��ѯ����</div>
		<form action="" method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<col width="10%">
			<col width="25%">
			<col width="10%">
			<col width="25%">
			<col width="30%">
			<tr>
				<td class="head">������</td>
				<td class="body"><h:managementbox list='${glbm}' id='glbm' haveNull='1'/></td>
				<td class="head">��������</td>
				<td class="body"><h:codebox list='${kklx}' id='kklx' haveNull='1'/></td>
				<td class="submit"><input type="button" value="��ѯ" class="button_query" onClick="query_cmd()">&nbsp;<input type="button" value="�ر�" class="button_close" onClick="javascript:window.close()"></td>
			</tr>
		</table>
		</form>
	</div>
	<div class="queryresult"></div>
	<c:if test="${queryList!=null}">
	<div id="result">
		<div id="resulttitle">��ѯ����</div>
		<table border="0" cellspacing="1" cellpadding="0" class="list">
			<col width="17%">
			<col width="17%">
			<col width="7%">
			<col width="10%">
			<col width="10%">
			<col width="9%">
			<col width="6%">
			<col width="16%">
			<col width="8%">
			<tr class="head">
				<td>���ڼ��</td>
				<td>��·����</td>
				<td>��·����</td>
				<td>��������</td>
				<td>������������</td>
				<td>Ԥ����������</td>
				<td>������</td>
				<td>ʹ�ò���</td>
				<td>����ʱ��</td>
			</tr>
			<c:forEach items="${queryList}" var="current">
			<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="showdetail('<c:out value='${current.kkbh}'/>','<c:out value='${current.zt}'/>')">
				<td align="left" limit="12"><c:out value="${current.kkjc}"/></td>
				<td align="left" limit="12"><c:out value="${current.dldmmc}"/></td>
				<td><c:out value="${current.ldlxmc}"/></td>
				<td limit="5"><c:out value="${current.kklxmc}"/></td>
				<td><c:out value="${current.ljtjmc}"/></td>
				<td><c:out value="${current.yjczlxmc}"/></td>
				<td><c:out value="${current.cds}"/></td>
				<td align="left" limit="11"><c:out value="${current.sybmmc}"/></td>
				<td limit="date"><c:out value="${current.cjsj}"/></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="9" class="page">
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