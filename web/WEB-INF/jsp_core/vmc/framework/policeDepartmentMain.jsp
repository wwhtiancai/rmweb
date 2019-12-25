<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>公安部门管理</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>


<script type="text/javascript">
<!--
	$(document).ready(function(){
		$("[limit]").limit();
		$("#bmmc").val("<c:out value='${bmmc}'/>");
	});
	function query_cmd(){
		if(!doChecking()) return;
		$("#myform").attr("action","<c:url value='/department.vmc?method=listPolice'/>");
		closes();
		$("#myform").submit();
	}
	function doChecking(){
		if(!checkLength($("#bmmc"),"64","部门名称")) return false;
		return true;
	}
	function showdetail(glbm){
		openwin("<c:url value='/department.vmc'/>?method=detailPolice&glbm="+glbm,"department");
	}
	
	function addnew() {
		openwin("/rmweb/department.vmc?method=addPolice", "newOpen");
	}
	
	
//-->
</script>
</head>
<body>
<div id="query">
	<div id="querytitle">查询条件</div>
	<form action="" method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<col width="10%">
			<col width="50%">
			<col width="40%">
			<tr>
				<td class="head">部门名称</td>
				<td class="body"><input type="text" name="bmmc" id="bmmc" maxlength="32"></td>
				<td class="submit">
					<input type="button" value="新增" class="button_new" onClick="addnew()">&nbsp;
					<input type="button" value="查询" class="button_query" onClick="query_cmd()">&nbsp;
					<input type="button" value="关闭" class="button_close" onClick="parent.doClose();">
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="queryresult"></div>
<c:if test="${queryList!=null}">
<div id="result">
	<div id="resulttitle">查询内容</div>
	<table border="0" cellspacing="1" cellpadding="0" class="list">
		<col width="12%">
		<col width="28%">
		<col width="10%">
		<col width="28%">
		<col width="14%">
		<col width="8%">
		<tr class="head">
			<td>部门代码</td>
			<td>部门名称</td>
			<td>部门级别</td>
			<td>同级交管部门</td>
			<td>警种</td>
			<td>记录状态</td>
		</tr>
		<c:forEach items="${queryList}" var="current">
		<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="showdetail('<c:out value='${current.glbm}'/>')">
			<td><c:out value="${current.glbm}"/></td>
			<td align="left"><c:out value="${current.bmmc}"/></td>
			<td><c:out value="${current.bmjbmc}"/></td>
			<td align="left"><c:out value="${current.sjbmmc}"/></td>
			<td><c:out value="${current.jzjbmc}"/></td>
		<c:if test="${current.jlzt!='1'}"><td style="color:red;">删除</td></c:if>
		<c:if test="${current.jlzt=='1'}"><td style="color:green;">正常</td></c:if>
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
</body>
</html>