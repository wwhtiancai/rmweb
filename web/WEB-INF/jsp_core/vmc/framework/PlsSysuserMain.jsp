<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>公安用户管理</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
	$("[limit]").limit();
});
function query_cmd(){
	if(!doChecking()) return;
	$("#myform").attr("target", "ifrm_plssysuserlist");
	$("#myform").attr("action","<c:url value='/sysuser.vmc?method=queryPlsSysUser'/>");
	//closes();
	$("#myform").submit();
}
function new_Add(){
	if($("#glbm").val()=='') {
		alert("请选择管理部门！");
		return false;
	}
	openwin("<c:url value='/sysuser.vmc?method=editPlsSysuser&modal=new&glbm='/>" + $("#glbm").val(), "PlsSysuserEdit");
}
function doChecking(){
	return true;
}
function editone(glbm, yhdh){
	curUrl = "<c:url value='sysuser.vmc?method=editPlsSysuser&modal=edit&glbm='/>" + glbm + "&yhdh="+yhdh;
	openwin1024(curUrl, "PlsSysuserEdit", false);
}
//-->
</script>
</head>
<body onUnload="closesubwin();">
<div id="query">
	<div id="querytitle">查询条件</div>
	<form action="" method="post" name="myform" id="myform">
	<table border="0" cellspacing="1" cellpadding="0" width="100%" class="query">
		<input type="hidden" id="glbmed" name="glbmed" value="">
		<col width="10%">
		<col width="40%">
		<col width="10%">
		<col width="40%">
		<tr>
			<td class="head">管理部门</td>
			<td class="body">
			  <h:managementbox list='${bmList}'  id='glbm' haveNull='1'  cssStyle="width:160px" />
			</td>
			<td class="head">用户代号</td>
			<td class="body"><input type="text" id="yhdh" name="yhdh" value="" style="width:160px" maxlength="6">
			</td>
		</tr>
		<tr>
			<td class="head">用户姓名</td>
			<td class="body">
				<input type="text" id="xm" name="xm" maxlength="50" style="width:160px" onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"> 
			</td>
			<td class="head">操作类型</td>
			<td class="body">
				<input type="radio"	name="czqx" value="" checked>全部
				<input type="radio" name="czqx" value="1">已授权
				<input type="radio" name="czqx" value="2">未授权
			</td>
		</tr>
		<tr>
			<td class="head">操作权限</td>
			<td class="body" >
				<select id="tjsdh" name="tjsdh" style="width:160px">
					<option value=""> </option>
					<c:forEach items="${roleList}" var="current">
						<option value='<c:out value="${current.jsdh}" />'><c:out value="${current.jsmc}" /></option>
					</c:forEach>
				</select>
			</td>
			<td class="head">排序方式</td>
			<td class="body">
				<select name="order">
					<option value="yhdh" selected>用户代号</option>
					<option value="xm">姓名</option>
					<option value="sfzmhm">身份证明号码</option>
				</select>
				<select name="proper">
					<option value="asc" selected>升序</option>
					<option value="desc">降序</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="command" colspan="4">
				<input type="button" class="button_new" value="新增" onclick="new_Add()">
				<input type="button" class="button_query" value="查询" onclick="query_cmd()">
			</td>
		</tr>
	</table>
	</form>
</div>
<div class="queryresult"></div>
<iframe src="" name="ifrm_plssysuserlist" id="ifrm_plssysuserlist" marginwidth="1" marginheight="1" hspace="0" vspace="0" 
	scrolling="no" frameborder="0" style="width:100%;height:445;">
</iframe>
</body>
</html>
