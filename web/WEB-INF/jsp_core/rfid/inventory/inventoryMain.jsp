<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>����ά��</title>
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
				����ѯ
			</div>
			<div id="query">
				<div id="querytitle">
					��ѯ����
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
							<td class="head">
								��װ�к�
							</td>
							<td class="body">
								<input type="text" name="bzhh" id="bzhh" maxlength="256">
							</td>
							<td class="head">
								����
							</td>
							<td class="body">
								<input type="text" name="kh" id="kh" maxlength="256">
							</td>
							<td class="head">
								��������
							</td>
							<td colspan="1" class="body">
								<select id="ssbm" name="ssbm">
                                    <option value="">--��ѡ����������--</option>
									<c:forEach var="current" items="${xjbmList}">
										<option value="${current.glbm}">
											<c:out value="${current.bmmc}" />
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="head">
								��װ���
							</td>
							<td class="body">
								<input type="text" name="bzxh" id="bzxh" maxlength="256">
							</td>
							<td class="body" colspan="4"></td>
						</tr>
						<tr>
							<td class="submit" colspan="6">
								<!-- <input type="button" class="button_new" value="����"
									onclick="eriedit('')"> -->
								<input type="button" class="button_query" value="��ѯ"
									onclick="query_cmd()">
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
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="20%">
						<col width="20%">
						<tr class="head">
							<td>
								��װ�к�
							</td>
							<td>
								��װ���
							</td>
							<td>
								��ʼ����
							</td>
							<td>
								��ֹ����
							</td>
							<td>
								��������
							</td>
							<td>
								��Ʒ����
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="eriedit('<c:out value='${current.bzhh}'/>')">
								<td>
									<c:out value="${current.bzhh}" />
								</td>
								<td>
									<c:out value="${current.bzxh}" />
								</td>
								<td>
									<c:out value="${current.qskh}" />
								</td>
								<td>
									<c:out value="${current.zzkh}" />
								</td>
								<td>
									<c:out value="${current.ssbmmc}" />
								</td>
								<td>
									<c:out value="${current.cpmc}" />
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
					$("#bzhh").val("<c:out value='${command.bzhh}'/>");
					$("#bzxh").val("<c:out value='${command.bzxh}'/>");
					$("#kh").val("<c:out value='${command.kh}'/>");
					$("#ssbm").val("<c:out value='${command.ssbm}'/>");
				</c:if>
			});
			
			function query_cmd(){
				$("#myform").attr("action","<c:url value='/inventory.frm?method=query-inventory'/>");
				closes();
				$("#myform").submit();
			}
			
			function eriedit(bzhh) {
				openwin("/rmweb/inventory.frm?method=edit-inventory&bzhh="+bzhh, "inventoryEdit");
			}
		</script>
	</body>
</html>