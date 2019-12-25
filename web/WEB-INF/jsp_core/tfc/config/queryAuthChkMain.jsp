<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>轨迹查询授权备案审批</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>
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

$("#jlzt1").attr("checked",true); 
<c:if test="${command!=null}">
	$("[limit]").limit();
	$("#glbm").val("<c:out value='${command.glbm}'/>");
	$("#jlzt").val("<c:out value='${command.jlzt}'/>");
	<c:if test="${command.bkxj=='1'}">
	   $("#bkxj").attr("checked",true); 
	</c:if>
	<c:if test="${command.jz=='17'}">
	   $("#jz1").attr("checked",true); 
	</c:if>
	<c:if test="${command.jz=='99'}">
	   $("#jz2").attr("checked",true); 
	</c:if>
	<c:if test="${command.jlzt=='0'}">
	   $("#jlzt1").attr("checked",true); 
	   $("#jlzt2").attr("checked",false); 
	</c:if>
	<c:if test="${command.jlzt=='1'}">
	   $("#jlzt1").attr("checked",false); 
	   $("#jlzt2").attr("checked",true); 
	</c:if>
	<c:if test="${command.jlzt=='0,1'}">
	   $("#jlzt1").attr("checked",true); 
	   $("#jlzt2").attr("checked",true); 
	</c:if>
</c:if>
});
function query_cmd(){
	if(!doChecking()) return;
	
	var bkxjstr = "";
	if($("#bkxj").is(":checked"))
	   bkxjstr = "&bkxj=1";
	   
	var jlztstr = "";
	if($("#jlzt1").is(":checked"))
	   jlztstr = "0";
	if($("#jlzt2").is(":checked"))
	   jlztstr = "1";
	if($("#jlzt1").is(":checked") && $("#jlzt2").is(":checked"))
	   jlztstr = "0,1";
	$("#jlzt").val(jlztstr);   
	   
    $("#myform").attr("action","<c:url value='/queryAuthChk.tfc?method=list&tj=1'/>"+bkxjstr);
	closes();
	$("#myform").submit();
}

function doChecking(){
	return true;
}

function authcheck(xh) {
	
	openwin("/rmweb/queryAuthChk.tfc?method=checkAuth&xh="+xh, "detailOpen");
	
}

//-->
</script>
</head>
<body onUnload="closesubwin()">
<div id="panel" style="display:none">
<div id="paneltitle">轨迹查询授权备案审批</div>
	
<div id="query">
	<div id="querytitle">查询条件</div>
	<form action="" method="post" name="myform" id="myform">
	<input type="hidden" name="jlzt" id="jlzt" value=""/>
	
	<table border="0" cellspacing="1" cellpadding="0" class="query" id="pane11">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
		    <td width="10%" class="head">用户类型</td>
		    <td width="20%" colspan="1" class="body">
			    <input type="radio" name="jz" id="jz1" checked value="17">交警 <input type="radio" name="jz" id="jz2" value="99">公安
			</td>
		    <td width="10%" class="head">管理部门</td>
		    <td width="20%" colspan="1" class="body">
				<select id="glbm" name="glbm" style="width:100%;">
					<c:forEach var="current" items="${xjbmList}">
						<option value="${current.glbm}"><c:out value="${current.bmmc}"/></option>
					</c:forEach>
				</select>
			</td>
			 <td width="20%" colspan="1" class="body">
			 <input type="checkbox" name="bkxj" id="bkxj" value="">包括下级
			 </td>
	   </tr>
	   <tr>
			<td width="10%" class="head">审批状态</td>
		    <td width="20%" class="body">
				<input type="checkbox" name="jlzt1" id="jlzt1" value="0">未审批&nbsp;<input type="checkbox" name="jlzt2" id="jlzt2" value="1">已审批
			</td>
			<td colspan="3" class="submit"><input type="button" value="查询" class="button_other" onClick="query_cmd()">&nbsp;<input type="button" value="关闭" class="button_close" onClick="javascript:window.close();"></td>
		</tr>
	</table>
	</form>
</div>
<div class="queryresult"></div>
<c:if test="${queryList!=null}">
<div id="result">
	<div id="resulttitle">查询内容</div>
	<form action="" method="post" name="sqform" id="sqform">
	<table border="0" cellspacing="1" cellpadding="0" class="list">
		<col width="10%">
		<col width="11%">
		<col width="14%">
		<col width="7%">
		<col width="10%">
		<col width="10%">
		<col width="10%">
		<col width="10%">
		<tr class="head">
			<td>用户代号</td>
			<td>姓名</td>
			<td>管理部门</td>
			<td>授权权限</td>
			<td>申请时间</td>
			<td>申请经办人</td>
			<td>审批状态</td>
			<td>审批时间</td>
		</tr>
		<c:forEach items="${queryList}" var="current">
		<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="authcheck('<c:out value='${current.xh}'/>')">
			<td align="left">
			<input type="hidden" name="xm<c:out value='${current.yhdh}'/>" id="xm<c:out value='${current.yhdh}'/>" value="<c:out value='${current.xm}'/>"/>
			<input type="hidden" name="sfzmhm<c:out value='${current.yhdh}'/>" id="sfzmhm<c:out value='${current.yhdh}'/>" value="<c:out value='${current.sfzmhm}'/>"/>
			<input type="hidden" name="rybh<c:out value='${current.yhdh}'/>" id="rybh<c:out value='${current.yhdh}'/>" value="<c:out value='${current.rybh}'/>"/>
			<input type="hidden" name="glbm<c:out value='${current.yhdh}'/>" id="glbm<c:out value='${current.yhdh}'/>" value="<c:out value='${current.glbm}'/>"/>
			<input type="hidden" name="fzjg<c:out value='${current.yhdh}'/>" id="fzjg<c:out value='${current.yhdh}'/>" value="<c:out value='${current.fzjg}'/>"/>
			<input type="hidden" name="sqjbr<c:out value='${current.yhdh}'/>" id="sqjbr<c:out value='${current.yhdh}'/>" value="<c:out value='${current.sqjbr}'/>"/>
			
			<c:out value="${current.yhdh}"/>
			</td>
			<td><c:out value="${current.xm}"/></td>
			<td><c:out value="${current.bmmc}"/></td>
			<td>
			<c:out value="${current.sqqxmc}"/>
			</td>
			<td limit="date"><c:out value="${current.sqsj}"/></td>
			<td><c:out value="${current.sqjbr}"/></td>
			<td><c:out value="${current.jlztmc}"/></td>
			<td limit="date"><c:out value="${current.spsj}"/></td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="9" class="page">
			<c:out value="${controller.clientScript}" escapeXml="false"/>
			<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
			</td>
		</tr>
	</table>
	</form>
</div>
</c:if>
</div>
</body>
</html>