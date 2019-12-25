<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>通行信息上传配置</title>
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
	$("#pzms").val("<c:out value='${pass.pzms}' />");
	fChangMs(true);
	$("#dldm").val("<c:out value='${pass.dldm}' />");
	$("#kkbh").val("<c:out value='${pass.kkbh}' />");
	$("#fzjg").val("<c:out value='${pass.fzjg}' />");
</c:if>
});
function query_cmd(){
	$("#myform").attr("action","<c:url value='/passInfCfg.tfc?method=listReg'/>");
	closes();
	$("#myform").submit();
}

function create(){
	openwin("<c:url value='/passInfCfg.tfc'/>?method=newAdd&type=add", "passInfCfg");
}

function showDetail(pzbh){
	openwin("<c:url value='/passInfCfg.tfc?method=detail&pzbh='/>" + pzbh+ "&type=mod", "passInfCfg");
}

function fChangMs(f){
	var m = $("#pzms").val();
	
	if("1" == m){
		$("#divTj").html("所在城市");
		$("#divSr").html("<input name='fzjg' id='fzjg' value='' />");
		if(f){$("#divBt").text("支队");}
	}else if("2" == m){
		$("#divTj").html("道路名称");
		$("#divSr").html("<input name='dldm' id='dldm' value='' />");
		if(f){$("#divBt").text("道路名称");}
	}else if("3" == m){
		$("#divTj").text("卡口名称");
		$("#divSr").html("<input name='kkbh' id='kkbh' value='' />");
		if(f){$("#divBt").text("卡口名称");}
	}else{
		$("#divTj").html("");
		$("#divSr").html("");
		if(f){$("#divBt").text("配置详情");}
	}
}
//-->
</script>
</head>
<body onUnload="closesubwin()">
<div id="panel" style="display:none">
	<div id="paneltitle">通行信息上传配置</div>
	<div id="query">
		<div id="querytitle">查询条件</div>
		<form action="" method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<tr>
				<td width="10%" class="head">配置模式</td>
				<td width="20%" class="body"><select name='pzms' id='pzms' style="width:100%" onchange="fChangMs(false)">
					<option value="">全部</option>
					<option value="1" selected>按支队</option>
					<option value="2">按道路</option>
					<option value="3">按卡口</option>
				</select></td>
				<td width="10%" class="head"><div id="divTj">所在城市</div></td>
				<td width="20%" class="body"><div id="divSr"><input name='fzjg' id='fzjg' value='' /></div></td>
				<td class="submit">
				<input type="button" value="新增" class="button_new" onClick="create()">
				&nbsp;<input type="button" value="查询" class="button_query" onClick="query_cmd()">
				&nbsp;<input type="button" value="关闭" class="button_close" onClick="javascript:window.close()"></td>
			</tr>
		</table>
		</form>
	</div>

<c:if test="${queryList!=null}">
		<div id="result" style="margin-top:6px">
		<div id="resulttitle">查询内容</div>
		<table border="0" cellspacing="1" cellpadding="0" class="list">
			<col width="10%">
			<col width="10%">
			<col width="40%">
			<col width="15%">
			<col width="15%">
			<col width="10%">
			<tr class="head">
				<td>编号</td>
				<td>配置模式</td>
				<td><div id="divBt">所在城市</div></td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>采集人</td>
			</tr>
			
			<c:forEach items="${queryList}" var="current">
			<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" ondblclick="showDetail('<c:out value="${current.pzbh}"/>')">
				<td align="center" ><c:out value="${current.pzbh}"/></td>
				<td align="center" ><c:out value="${current.pzms}"/></td>
				<td align="left" limit="28" style="padding-left:8px"><c:out value="${current.pzxx}"/></td>
				<td align="center" limit="date"><c:out value="${current.kssj}"/></td>
				<td align="center" limit="date"><c:out value="${current.jssj}"/></td>
				<td align="center" ><c:out value="${current.cjr}"/></td>
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