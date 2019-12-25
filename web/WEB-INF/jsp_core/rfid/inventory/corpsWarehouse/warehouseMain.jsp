<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>总队入库维护</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
			
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				总队入库维护
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
						<tr>
							<td class="head">入库单号</td>
							<td class="body">
								<input type="text" name="rkdh" id="rkdh" maxlength="256">
							</td>
							<td class="head">所属部门</td>
							<td class="body">
								<select id="glbm" name="glbm">
									<c:forEach var="current" items="${xjbmList}">
										<option value="${current.glbm}">
											<c:out value="${current.bmmc}" />
										</option>
									</c:forEach>
								</select>
							</td>
							<td class="head">经办人</td>
		                    <td class="body">
		                        <input type="text" id="jbr" name="jbr" value="${condition.jbr}" />
		                    </td>
						</tr>
						<tr>
		                    <td class="head">入库日期</td>
		                    <td class="body" colspan="3">
		                        <h:datebox id="rkrqks" name="rkrqks" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="rkrqjs" name="rkrqjs" showType="1" width="32%"/>
		                    </td>
		                    <td class="head"></td>
		                    <td class="body" colspan=""></td>
		                </tr>
						<tr>
							<td class="submit" colspan="6">
								<input type="button" name="importBtn" id="importBtn" onclick="importExcel()" value="导入" class="button_print">
								<input type="button" class="button_new" value="新增"
									onclick="warehouse_edit('')">
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
						<col width="25%">
						<col width="25%">
						<col width="25%">
						<col width="25%">
						<tr class="head">
							<td>
								入库单号
							</td>
							<td>
								经办人
							</td>
							<td>
								入库时间
							</td>
							<td>
								所属部门
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="warehouse_edit('<c:out value='${current.rkdh}'/>')">
								<td>
									<c:out value="${current.rkdh}" />
								</td>
								<td>
									<c:out value="${current.jbrxm}" />
								</td>
								<td>
									<c:if test="${current.rkrq != null}">
										<fmt:formatDate value='${current.rkrq}' pattern="yyyy-MM-dd HH:mm:ss" />
									</c:if> 
								</td>
								<td>
									<c:out value="${current.bmmc}" />
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
					$("#rkdh").val("<c:out value='${command.rkdh}'/>");
					$("#glbm").val("<c:out value='${command.glbm}'/>");
					$("#jbr").val("<c:out value='${command.jbr}'/>");
					$("#rkrqks").val("<c:out value='${command.rkrqks}'/>");
					$("#rkrqjs").val("<c:out value='${command.rkrqjs}'/>");
				</c:if>
				
			});
			
			function query_cmd(){
				$("#myform").attr("action","<c:url value='/corps-warehouse.frm?method=query'/>");
				closes();
				$("#myform").submit();
			}
			
			function warehouse_edit(rkdh) {
				openwin("/rmweb/corps-warehouse.frm?method=edit-warehouse&rkdh="+rkdh, "warehouseEdit");
			}
			
			var importExcel = function(){
				openwin("/rmweb/corps-warehouse.frm?method=import-warehouse", "warehouseUpload");
			}
		</script>
	</body>
</html>