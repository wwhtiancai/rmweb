<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>机动车轨迹全国查询</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker-fr.js"></script>
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
<c:if test="${tollgate!=null}">
	$("[limit]").limit();
	$("#whjg").val("<c:out value='${tollgate.whjg}'/>");
	$("#whjgmc").val("<c:out value='${tollgate.whjgmc}'/>");
	$("#dldm").val("<c:out value='${tollgate.dldm}'/>");
	$("#kklx").val("<c:out value='${tollgate.kklx}'/>");
	$("#kkmc").val("<c:out value='${tollgate.kkmc}'/>");
	$("#kkdj").val("<c:out value='${tollgate.kkdj}'/>");
	$("#kkxz").val("<c:out value='${tollgate.kkxz}'/>");
	$("#ljtj").val("<c:out value='${tollgate.ljtj}'/>");
	$("#kkzt").val("<c:out value='${tollgate.kkzt}'/>");
	
</c:if>
});
function query_cmd(){
	if(!doChecking()) return;
	$("#myform").attr("action","<c:url value='/tfcQuery.tfc?method=list'/>");
	closes();
	$("#myform").submit();
}

function showdetail(gcxh){
	openwin("<c:url value='/tfcQuery.tfc'/>?method=detail&gcxh="+gcxh, "tfcDetail");
}

function doChecking(){
	//object:TFC_PASS
	//regulation:hphm(超长(15),号牌)
	//regulation:gcsj(非空,日期,号牌)
	//regulation:fzhphm(超长(15),号牌)
	if(!checkLength($("#hphm")),"15","号牌号码") return false;
	if(!checkHPHM($("#hphm")),"号牌号码") return false;
	if(!checkNull($("#gcsj")),"过车时间") return false;
	if(!checkDate($("#gcsj")).val(),"过车时间") return false;
	if(!checkLength($("#fzhphm")),"15","辅助号牌号码") return false;
	if(!checkHPHM($("#fzhphm")),"辅助号牌号码") return false;
	return true;
}

//-->
</script>
</head>
<body>

<div><a name="vio_bscx" id="vio_bscx" style="display:block">
	<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
	<div id="query">
		<div id="querytitle">全国过车查询条件</div>
		<form action="" method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="query">
			<tr>
				<td width="10%" class="head">卡口名称</td>
				<td width="23%" class="body"><h:roadbox list='${dldm}' id='dldm' haveNull='1'/></td>
				<td width="10%" class="head">方向类型</td>
				<td width="23%" class="body"><h:codebox list='${fxlx}' id='fxlx' haveNull='1'/></td>		
				<td width="10%" class="head">号牌颜色</td>
				<td width="24%" class="body"><h:codebox list='${hpys}' id='hpys' haveNull='1'/></td>
			</tr>
			<tr>
				<td class="head">号牌种类</td>
				<td class="body"><h:codebox list='${hpzl}' id='hpzl' haveNull='1'/></td>				
				<td class="head">号牌号码</td>
				<td class="body"><h:codebox list='${hphmqz}' id='hphm' haveNull='1'/></td>
				<td class="head">辅助号牌号码</td>
				<td class="body"><h:codebox list='${hphmqz}' id='pzhphm' haveNull='1'/></td>
			</tr>
			<tr>
				<td class="head">过车时间</td>
				<td class="body" colspan="3">
					<h:datebox id="gckssj" showType="1"/> 至 <h:datebox id="gcjssj" showType="1"/>
				</td>				
				<td colspan="2" class="submit"><input type="button" value="查询" class="button_query" onClick="query_cmd()">&nbsp;<input type="button" value="关闭" class="button_close" onClick="javascript:window.close()"></td>
			</tr>
		</table>
		</form>
	</div>
</table>
</a></div>



	<div class="queryresult"></div>
	<c:if test="${queryList!=null}">
	<div id="result">
		<div id="resulttitle">查询内容</div>
		<table border="0" cellspacing="1" cellpadding="0" class="list">
			<col width="33%">
			<col width="12%">
			<col width="15%">
			<col width="11%">
			<col width="9%">
			<col width="20%">
			<tr class="head">
				<td>卡口名称</td>
				<td>号牌种类</td>
				<td>号牌号码</td>
				<td>号牌颜色</td>
				<td title="单位：Km/h">车辆速度</td>
				<td>过车时间</td>
			</tr>
			<c:forEach items="${queryList}" var="current">
			<tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'" style="cursor:pointer" onDblClick="showdetail('<c:out value='${current.kkbh}'/>')">
				<td align="left" limit="19"><c:out value="${current.kkmc}"/></td>
				<td align="left" limit="19"><c:out value="${current.hpzl}"/></td>
				<td limit="11"><c:out value="${current.hphm}"/></td>
				<td><c:out value="${current.hpys}"/></td>
				<td><c:out value="${current.clsd}"/></td>
				<td limit="time"><c:out value="${current.gcsj}"/></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="7" class="page">
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