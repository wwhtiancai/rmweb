<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>电子标签维护</title>
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
				电子标签维护
			</div>
			<div id="query">
				<div id="querytitle">
					查询条件
				</div>
				<form action="" method="post" name="myform" id="myform">
					<input type="hidden" name="page" value="1"/>
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="24%">
						<!-- <input type="hidden" name="xh" id="xh" value=""> -->
						<tr>
							<td class="head">标签ID</td>
							<td class="body">
								<input type="text" name="tid" id="tid" maxlength="256" style="width:100%">
							</td>
                            <td class="head">省份</td>
                            <td colspan="1" class="body">
                                <h:codebox list='${provinces}' id='sf' name="sf" showType="2" cssStyle="width:100%"/>
                            </td>
							<td class="head">卡号</td>
							<td class="body">
								<input type="text" name="kh" id="kh" maxlength="256" style="width:100%">
							</td>
						</tr>
						<tr>
							<td class="head">批号</td>
							<td class="body">
								<input type="text" name="ph" id="ph" maxlength="256" style="width:100%">
							</td>
                            <td class="head">状态</td>
                            <td class="body">
                                <select id="zt" name="zt" style="width: 100%;">
                                    <option value="">--请选择标签状态--</option>
                                    <c:forEach var="eriStatus" items="${eriStatus}">
                                        <option value="${eriStatus.status}">
                                            <c:out value="${eriStatus.desc}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td class="head">初始化日期</td>
                            <td colspan="1" class="body">
                                <h:datebox id="cshrqks" name="cshrqks" showType="1" width="35%"/>&nbsp;-&nbsp;<h:datebox id="cshrqjs" name="cshrqjs" showType="1" width="35%"/>
                            </td>
						</tr>
						<tr>
							<td class="submit" colspan="6">
							<%--	<input type="button" id="analyzeBtn" class="button_query" value="统计"/>--%>
								<input type="button" class="button_query" value="查询"
									onclick="query_cmd()">
								
								<input id="exportBtn" type="button" class="button_query" value="导出"
									onclick="exportByKH()">
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
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="25%">
						<tr class="head">
							<td>
								TID
							</td>
							<td>
								卡号
							</td>
							<td>
								批号
							</td>
							<td>
								省份
							</td>
							<td>
								状态
							</td>
							<td>
								初始化日期
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="eriedit('<c:out value='${current.tid}'/>')">
								<td>
									<c:out value="${current.tid}" />
								</td>
								<td>
									<c:out value="${current.kh}" />
								</td>
								<td>
									<c:out value="${current.ph}" />
								</td>
								<td>
                                    <c:forEach var="province" items="${provinces}">
                                        <c:if test="${province.dmz == current.sf}">
                                            <c:out value="${province.dmsm1}" />
                                        </c:if>
                                    </c:forEach>
								</td>
								<td>
                                    <c:forEach var="status" items="${eriStatus}">
                                        <c:if test="${status.status == current.zt}">
                                            <c:out value="${status.desc}" />
                                        </c:if>
                                    </c:forEach>
								</td>
								<td>
									<c:if test="${current.cshrq != null}">
										<fmt:formatDate value='${current.cshrq}' pattern="yyyy-MM-dd HH:mm:ss" />
									</c:if> 
								</td>
							</tr>
							<c:set var="rowcount" value="${rowcount+1}" />
						</c:forEach>
						<tr>
							<td colspan="7" class="page">
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
				$("#tid").val("<c:out value='${command.tid}'/>");
				$("#kh").val("<c:out value='${command.kh}'/>");
				$("#sf").val("<c:out value='${command.sf}'/>");
				$("#ph").val("<c:out value='${command.ph}'/>");
				$("#zt").val("<c:out value='${command.zt}'/>");
				$("#cshrqks").val("<c:out value='${command.cshrqks}'/>");
				$("#cshrqjs").val("<c:out value='${command.cshrqjs}'/>");
			</c:if>
			
		});
		
		function query_cmd(){
			$("#myform").attr("action","<c:url value='/eri.frm?method=query-eri'/>");
			closes();
			$("#myform").submit();
		}
		
		function eriedit(tid) {
			openwin("/rmweb/eri.frm?method=query-eri-detail&tid="+tid, "queryEriDetail");
			//openwin("/rmweb/eri.frm?method=edit-eri&tid="+tid, "eriEdit");
		}

		var exportByKH = function(){
			openwin("/rmweb/eri.frm?method=export-eri-page", "exportByKH");
		}
	</script>
</html>