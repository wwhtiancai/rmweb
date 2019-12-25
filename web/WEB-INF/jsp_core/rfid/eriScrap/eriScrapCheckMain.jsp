<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>报废审核</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
		<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
		<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
			type="text/css">
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				报废审核
			</div>
			<div id="query">
				<div id="querytitle">
					查询条件
				</div>
				<form action="" method="post" name="myform" id="myform">
				<input type="hidden" name="page" value="1" />
				<input type="hidden" name="cllx" id="cllx" value = "${cllx}"/>
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="5%">
						<col width="10%">
						<col width="5%">
						<col width="10%">
						<col width="10%">
						<tr>
							<td class="head">
								报废单号
							</td>
							<td class="body" >
								<input type="text" name="bfdh" id="bfdh" maxlength="32">
							</td>
							<td class="head">
								报废原因
							</td>
								<td class="body" >
								<input type="text" name="bfyy" id="bfyy" maxlength="64">
							</td>
							<td class="submit" colspan="1">
								<input type="button" class="button_query" value="查询"
									onclick="query_cmd()">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="queryresult"></div>
			<c:if test="${queryList!=null}">
				<div id="result">
					<div id="resulttitle">
						查询内容
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="8%">
						<col width="14%">
						<col width="8%">
						<col width="10%">
						<col width="8%">
						<col width="10%">
						<col width="8%">
						<col width="8%">
						<col width="8%">
						<col width="10%">
						<col width="12%">
						<tr class="head">
							<td>
								报废单号
							</td>
							<td>
								报废原因
							</td>
							<td>
								状态
							</td>
							<td>
								请求日期
							</td>
							<td>
								完成日期
							</td>
							<td>
								经办人
							</td>
							<td>
								操作人
							</td>
							<td>
								备注
							</td>
							<td>
								审核人
							</td>
							<td>
								审核日期
							</td>
							<td>
								审核结果
							</td>
							
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="scrapcheckedit('<c:out value='${current.bfdh}'/>')">
								<td>
									<c:out value="${current.bfdh}" />
								</td>
								<td style="white-space:nowrap;cursor: pointer;" title="${current.bfyy}">
								<c:choose>
								<c:when test="${fn:length(current.bfyy)>10}">
									<c:out value="${fn:substring(current.bfyy, 0, 10)}" />..
								</c:when>
								<c:otherwise>
									<c:out value="${current.bfyy}" />
								</c:otherwise>
								</c:choose>
								</td>
								<td>
								<c:forEach var="status" items="${eriScrapStatus}">
                                        <c:if test="${status.status == current.zt}">
                                            <c:out value="${status.desc}" />
                                        </c:if>
                                    </c:forEach>
								</td>
								<td>
								<fmt:formatDate value='${current.qqrq}'
										pattern="yyyy-MM-dd" />
								</td>
								<td>
								<fmt:formatDate value='${current.wcrq}'
										pattern="yyyy-MM-dd" />
								</td>
								<td>
									<c:out value="${current.jbr}" />
								</td>
								<td>
									<c:out value="${current.czr}" />
								</td>
								<td style="white-space:nowrap;cursor: pointer;" title="${current.bz}">
										<c:choose>
								<c:when test="${fn:length(current.bz)>6}">
									<c:out value="${fn:substring(current.bz, 0, 6)}" />..
								</c:when>
								<c:otherwise>
									<c:out value="${current.bz}" />
								</c:otherwise>
								</c:choose>
								</td>
								<td>
									<c:out value="${current.scr}" />
								</td>
								<td>
									<fmt:formatDate value='${current.shrq}'
										pattern="yyyy-MM-dd" />
								</td>
								<td style="white-space:nowrap;cursor: pointer;" title="${current.shbz}">
										<c:choose>
								<c:when test="${fn:length(current.shbz)>6}">
									<c:out value="${fn:substring(current.shbz, 0, 6)}" />..
								</c:when>
								<c:otherwise>
									<c:out value="${current.shbz}" />
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<c:set var="rowcount" value="${rowcount+1}" />
						</c:forEach>
						<tr>
							<td colspan="11" class="page">
								<c:out value="${controller.clientScript}" escapeXml="false" />
								<c:out value="${controller.clientPageCtrlDesc}"
									escapeXml="false" />
							</td>
						</tr>
					</table>
				</div>
			</c:if>
		</div>
	</body>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
	<script type="text/javascript">
	if (!window.console) {
	window.console = {
		log: function() {
			alert(arguments[0]);
		}
	}
}

		$(document).ready(function(){
			$.datepicker.setDefaults($.datepicker.regional['']);
			$(".jscal").each(function () {
                eval($(this).html());
            });
	<c:if test="${command!=null}">
		$("[limit]").limit();
		$("#bfdh").val("<c:out value='${command.bfdh}'/>");
	</c:if>
	
});

function query_cmd(){
	$("#myform").attr("action","<c:url value='/eri-scrap.frm?method=query-scrap-check'/>");
	closes();
	$("#myform").submit();
}
function scrapcheckedit(bfdh) {
	openwin("/rmweb/eri-scrap.frm?method=edit-scrap-check&bfdh="+bfdh, "editScrapCheck");
}
</script>
</html>