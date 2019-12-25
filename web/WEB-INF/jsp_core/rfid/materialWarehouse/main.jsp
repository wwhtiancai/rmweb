<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>原料入库</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
			
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				原料入库
			</div>
			<div id="query">
				<div id="querytitle">
					查询条件
				</div>
				<form action="" method="post" name="myform" id="myform">
					<input type="hidden" name="page" value="1"/>
					<input type="hidden" name="type" value="${type}"/>
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
							<td class="head">经办人</td>
		                    <td class="body">
		                        <input type="text" id="jbrxm" name="jbrxm" value="${condition.jbrxm}" />
		                    </td>
		                    <td class="head">入库日期</td>
		                    <td class="body">
		                        <h:datebox id="rkrqks" name="rkrqks" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="rkrqjs" name="rkrqjs" showType="1" width="32%"/>
		                    </td>
						</tr>
						<tr>
							<td class="head">交付单位</td>
		                    <td class="body">
		                        <input type="text" id="jfdw" name="jfdw" value="${condition.jfdw}" />
		                    </td>
		                    <td class="head">状态</td>
		                    <td class="body">
		                    	<select id="zt" name="zt">
		                            <option value="">---请选择---</option>
		                            <c:forEach var="status" items="${materialWarehouseStatus}">
		                                <c:choose>
		                                    <c:when test="${status.status == condition.zt}">
		                                        <option value="${status.status}" selected>
		                                            <c:out value="${status.desc}"/>
		                                        </option>
		                                    </c:when>
		                                    <c:otherwise>
		                                        <option value="${status.status}">
		                                            <c:out value="${status.desc}"/>
		                                        </option>
		                                    </c:otherwise>
		                                </c:choose>
		                            </c:forEach>
		                        </select>   
							</td>
							<td class="body" colspan="2"></td>
						</tr>
						<tr>
							<td class="submit" colspan="6">
								<input type="button" class="button_new" id="createMaterialWarehouse" value="新增">
								<input type="button" class="button_query" value="查询"
									onclick="query_cmd()">
								<input type="button" class="button_query" value="数量"
									onclick="storageCount()">	
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="queryresult" style="margin-top: 20px;">
			<c:if test="${queryList!=null}">
				<div id="result">
					<div id="resulttitle">
						查询内容
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<tr class="head">
							<td>
								入库单号
							</td>
							<td>
								经办人
							</td>
							<td>
								入库日期
							</td>
							<td>
								交付单位
							</td>
							<td>
								状态
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
									<c:out value="${current.jfdw}" />
								</td>
								<td>
									<c:forEach var="status" items="${materialWarehouseStatus}">
		                                <c:if test="${status.status == current.zt}">
		                                    <c:out value="${status.desc}"/>
		                                </c:if>
		                            </c:forEach>
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
		</div></div>
		
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
				<c:if test="${condition!=null}">
					$("[limit]").limit();
					$("#rkdh").val("<c:out value='${condition.rkdh}'/>");
					$("#jbrxm").val("<c:out value='${condition.jbrxm}'/>");
					$("#rkrqks").val("<c:out value='${condition.rkrqks}'/>");
					$("#rkrqjs").val("<c:out value='${condition.rkrqjs}'/>");
					$("#jfdw").val("<c:out value='${condition.jfdw}'/>");
					$("#zt").val("<c:out value='${condition.zt}'/>");
				</c:if>
				
				<c:if test="${type == 2}">
					$("#zt").attr("disabled",true);
					$("#zt").val(1);
				</c:if>
				
				$("#createMaterialWarehouse").bind('click',function(){
					openwin("/rmweb/material-warehouse.rfid?method=create", "editMaterialWarehouse");
				})
			});
			
			function query_cmd(){
				$("#myform").attr("action","<c:url value='/material-warehouse.rfid?method=list'/>");
				closes();
				$("#myform").submit();
			}
			
			function warehouse_edit(rkdh) {
				openwin("/rmweb/material-warehouse.rfid?method=view&rkdh="+rkdh, "viewMaterialsWarehouse");
			}
			
			var storageCount = function(){
				window.location.href = "/rmweb/material-warehouse.rfid?method=countlist";
			}
		</script>
	</body>
</html>