<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>出库单管理</title>
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
				出库单管理
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
							<td class="head">出库单号</td>
							<td class="body">
								<input type="text" name="rkdh" id="rkdh" maxlength="256">
							</td>
							<td class="head">所属部门</td>
							<td colspan="1" class="body">
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
		                        <input type="text" id="jbr" name="jbr" value="${condition.lbr}" />
		                    </td>
						</tr>
						<tr>
		                    <td class="head">出库日期</td>
		                    <td class="body" colspan="3">
		                        <h:datebox id="ckrqks" name="ckrqks" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="ckrqjs" name="ckrqjs" showType="1" width="32%"/>
		                    </td>
		                    <td class="head">状态</td>
		                    <td class="body" colspan="">
		                    	<select id="zt" name="zt" >
		                    		<option value="0"></option>
									<c:forEach var="status" items="${exWarehouseStatus}">
										<option value="${status.status}">
											<c:out value="${status.desc}" />
										</option>
									</c:forEach>
								</select>
		                    </td>
		                </tr>
						<tr>
							<td class="submit" colspan="6">
								<input id="sumBtn" type="button" class="button_new" value="总计"
									onclick="showSum()">
								<input id="createBtn" type="button" class="button_new" value="新增"
									onclick="exwarehouse_edit('')">
								<input id="queryBtn" type="button" class="button_query" value="查询"
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
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="20%">
						<col width="20%">
						<tr class="head">
							<td>
								出库单号
							</td>
							<td>
								经办人
							</td>
							<td>
								出库时间
							</td>
							<td>
								所属部门
							</td>
							<td>
								出库数量
							</td>
							<td>状态</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="exwarehouse_edit('<c:out value='${current.ckdh}'/>')">
								<td>
									<c:out value="${current.ckdh}" />
								</td>
								<td>
									<c:out value="${current.jbrxm}" />
								</td>
								<td>
									<c:if test="${current.ckrq != null}">
										<fmt:formatDate value='${current.ckrq}' pattern="yyyy-MM-dd HH:mm:ss" />
									</c:if> 
								</td>
								<td>
									<c:out value="${current.bmmc}" />
								</td>
								<td>
									<c:out value="${current.cksl}" />
								</td>
								<td>
									<c:forEach var="status" items="${exWarehouseStatus}">
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
			var type = "<c:out value='${type}'/>";
			
			$(document).ready(function(){
				$.datepicker.setDefaults($.datepicker.regional['']);
				$(".jscal").each(function () {
	                eval($(this).html());
	            });
				
				
				if(type == 1){
					$("#createBtn").show();
				}else{
					$("#createBtn").hide();
				}
				
				<c:if test="${command!=null}">
					$("[limit]").limit();
					$("#ckdh").val("<c:out value='${command.ckdh}'/>");
					$("#glbm").val("<c:out value='${command.glbm}'/>");
					$("#jbr").val("<c:out value='${command.jbr}'/>");
					$("#ckrqks").val("<c:out value='${command.ckrqks}'/>");
					$("#ckrqjs").val("<c:out value='${command.ckrqjs}'/>");
					$("#zt").val("<c:out value='${command.zt}'/>");
				</c:if>
				
			});
			
			function query_cmd(){
				$("#myform").attr("action","<c:url value='/exwarehouse.frm?method=query-exwarehouse'/>");
				closes();
				$("#myform").submit();
			}
			
			function exwarehouse_edit(ckdh) {
				openwin("/rmweb/exwarehouse.frm?method=edit-exwarehouse&ckdh="+ckdh+"&type="+type, "exwarehouseEdit");
			}
			var showSum = function(){
				var formObj = $("#myform").serialize();
				var zt = $("#zt").val();
				if(zt == 2 || zt == 1){
					$.ajax({
		                url: "/rmweb/exwarehouse.frm?method=getSum",
		                contentType:"application/x-www-form-urlencoded;charset=gb2312",
		                data: formObj,
		                async: false,
		                type: "POST",
		                success: function(data) {
		                    if (data && data["resultId"] == "00") {
		                    	var str = "";
		                    	if(zt == 2){
			                    	str = "已出库电子标识 " + data["resultMsg"] + " 张";
		                    	}else if(zt == 1){
		                    		str = "预出库电子标识 " + data["resultMsg"] + " 张";
		                    	}
		            			displayDialog(2,str,"");
		                    }
		                },
		                dataType: "json"
		            });
				}else{
					displayDialog(1,"请改选状态为“申请”或“审核通过”！","");
				}
				
			}
		</script>
	</body>
</html>