<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>��Ʒ����</title>
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
				��Ʒ����
			</div>
			<div id="query">
				<div id="querytitle">
					��ѯ����
				</div>
				<form action="" method="post" name="myform" id="myform">
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="24%">
						<tr>
							<td class="head">
								��Ʒ����
							</td>
							<td class="body">
								<input type="text" name="cpdm" id="cpdm" maxlength="10" >
							</td>
							<td class="head">
								��Ʒ����
							</td>
							<td class="body">
								<input type="text" name="cpmc" id="cpmc" maxlength="32">
							</td>
							<td class="head">
								��Ʒ���
							</td>
							<td colspan="1" class="body">
								<select id="cplb" name="cplb" style="width: 80%;">
									<option value="">
										--��ѡ���Ʒ���--
									</option>
									<c:forEach var="cplbType" items="${cplbStatus}">
										<option value="${cplbType.cplb}">
											<c:out value="${cplbType.lbmc}" />
												</option>
									</c:forEach>
								</select>
							</td>
							</tr>
							<tr>
							<td class="head">
								��Ӧ������
							</td>
							<td colspan="1" class="body">
								<input type="text" name="gysmc" id="gysmc" maxlength="32">
							</td>
							<td class="head">
								����ֵ
							</td>
							<td colspan="1" class="body" >
								<input  type="text" name="tzz" id="tzz" maxlength="32">
							</td>
							<td class="head">
								״̬
							</td>
							<td class="body">
								<select id="zt" name="zt" style="width: 80%;">
									<option value="">
										--��ѡ���Ʒ״̬--
									</option>
									<c:forEach var="productType" items="${productTypes}">
										<option value="${productType.status}">
											<c:out value="${productType.desc}" />
												</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="submit" colspan="6">
								<input type="button" class="button_query" value="��ѯ"
									onclick="query_cmd()">
								<input type="button" class="button_new" value="����"
									onclick="productedit('')">
								<!-- <input type="button" class="button_close" value="�ر�"
									onclick="javascript:window.close()"> -->
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="queryresult"></div>
			<c:if test="${queryList!=null}">
				<div id="result">
					<div id="resulttitle">
						��ѯ����
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="10%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<tr class="head">
							<td>
								���
							</td>
							<td>
								��Ʒ����
							</td>
							<td>
								��Ʒ����
							</td>
							<td>
								��Ʒ���
							</td>
							<td>
								״̬
							</td>
							<td>
								��Ӧ������
							</td>
							<td>
								����ֵ
							</td>
							<td>
								��Ʒ����
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="productedit('<c:out value='${current.cpdm}'/>')">
									<td><c:out value="${rowcount+1}" /></td>
								<td>
									<c:out value="${current.cpdm}" />
								</td>
								<td>
									<c:out value="${current.cpmc}" />
								</td>
								<td>
                                    <c:forEach var="Status" items="${cplbStatus}">
										<c:if test="${Status.cplb== current.cplb}">
                                            <c:out value="${Status.lbmc}" />
                                        </c:if>
									</c:forEach>
								</td>
								<td>
									<c:forEach var="productType" items="${productTypes}">
										<c:if test="${productType.status== current.zt}">
                                            <c:out value="${productType.desc}" />
                                        </c:if>
									</c:forEach>
								</td>
								<td>
									<c:out value="${current.gysmc}" />
								</td>
								<td>
									<c:out value="${current.tzz}" />
								</td>
								<td>
									<c:out value="${current.cpbm}" />
								</td>
							</tr>
							<c:set var="rowcount" value="${rowcount+1}" />
						</c:forEach>
						<tr>
							<td colspan="8" class="page">
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
		<script type="text/javascript">
			if (!window.console) {
				window.console = {
					log: function() {
						alert(arguments[0]);
					}
				}
			}
			
			$(document).ready(function(){
				<c:if test="${command!=null}">
					$("[limit]").limit();
					$("#cpdm").val("<c:out value='${command.cpdm}'/>");
					$("#cpmc").val("<c:out value='${command.cpmc}'/>");
					$("#cplb").val("<c:out value='${command.cplb}'/>");
					$("#zt").val("<c:out value='${command.zt}'/>");
					$("#gysmc").val("<c:out value='${command.gysmc}'/>");
					$("#tzz").val("<c:out value='${command.tzz}'/>");
				</c:if>
				
			});
			
			function query_cmd(){
				$("#myform").attr("action","<c:url value='/product.frm?method=query-product'/>");
				closes();
				$("#myform").submit();
			}
			function productedit(cpdm) {
				openwin("/rmweb/product.frm?method=edit-product&cpdm="+cpdm, "editProduct");
			}
		</script>
	</body>
</html>