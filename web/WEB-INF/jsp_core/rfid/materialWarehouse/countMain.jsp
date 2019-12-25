<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>原料入库（数量）</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
			
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				原料入库（数量）
			</div>
			<div id="query">
				<div id="querytitle">
					查询条件
				</div>
				<form action="" method="post" name="myform" id="myform">
					<input type="hidden" name="page" value="1"/>
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="18%">
						<col width="10%">
						<col width="18%">
						<col width="10%">
						<col width="34%">
						<tr>
							<td class="head">序号</td>
							<td class="body">
								<input type="text" name="xh" id="xh" maxlength="256" value="${condition.xh}">
							</td>
							<td class="head">操作人</td>
		                    <td class="body">
		                        <input type="text" id="czrxm" name="czrxm" value="${condition.czrxm}" />
		                    </td>
		                    <td class="head">入库日期</td>
		                    <td class="body">
		                        <h:datebox id="rkrqks" name="rkrqks" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="rkrqjs" name="rkrqjs" showType="1" width="32%"/>
		                    </td>
						</tr>
						<tr>
							<td class="submit" colspan="6">
								<input type="button" class="button_new" id="createWarehouseCount" value="新增">
								<input type="button" class="button_query" value="查询"
									onclick="query_cmd()">
								<input type="button" class="button_query" value="总数"
									onclick="query_count()">
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
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="10%">
						<tr class="head">
							<td>
								序号
							</td>
							<td>
								操作人
							</td>
							<td>
								入库日期
							</td>
							<td>
								数量
							</td>
							<td>
								交付单位
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="warehouse_edit('<c:out value='${current.xh}'/>')">
								<td>
									<c:out value="${current.xh}" />
								</td>
								<td>
									<c:out value="${current.czrxm}" />
								</td>
								<td>
									<c:if test="${current.rkrq != null}">
										<fmt:formatDate value='${current.rkrq}' pattern="yyyy-MM-dd HH:mm:ss" />
									</c:if> 
								</td>
								<td>
									<c:out value="${current.count}" />
								</td>
								<td>
									<c:out value="${current.jfdw}" />
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
				
				$("#createWarehouseCount").bind('click',function(){
					openwin("/rmweb/material-warehouse.rfid?method=createCount", "editWarehouseCount");
				})
			});
			
			function query_cmd(){
				$("#myform").attr("action","<c:url value='/material-warehouse.rfid?method=countlist'/>");
				closes();
				$("#myform").submit();
			}
			
			function query_count(){
				$.ajax({
	                url: "<c:url value='/material-warehouse.rfid?method=queryCount'/>",
	                contentType:"application/x-www-form-urlencoded;charset=gb2312",
	                data: $("#myform").serialize(),
	                async: false,
	                type: "POST",
	                success: function(data) {
	                    if (data && data["resultId"] == "00") {
	                    	var str = "已入库原料卡 " + data["resultMsg"] + " 张";
	            			displayDialog(2,str,"");
	                    }
	                },
	                dataType: "json"
	            });
			}
			
			function warehouse_edit(rkdh) {
				openwin("/rmweb/material-warehouse.rfid?method=viewCount&xh="+rkdh, "viewWarehouseCount");
			}
			
		</script>
	</body>
</html>