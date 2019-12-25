<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>�豸ע��</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
		<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
		<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
			type="text/css">
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
		<script type="text/javascript">
<!--
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
		$("#xh").val("<c:out value='${command.xh}'/>");
		$("#sbh").val("<c:out value='${command.sbh}'/>");
		$("#glbm").val("<c:out value='${command.glbm}'/>");
	</c:if>
	
});

function query_cmd(){
	$("#myform").attr("action","<c:url value='/eri-equipment.frm?method=query-eri-equipment'/>");
	closes();
	$("#myform").submit();
}
function deviceedit(xh) {
	openwin("/rmweb/eri-equipment.frm?method=edit-eri-equipment&xh="+xh, "equipmentEdit");
}


//-->
</script>
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				�豸ע��
			</div>
			<div id="query">
				<div id="querytitle">
					��ѯ����
				</div>
				<form action="" method="post" name="myform" id="myform">
				<input type="hidden" name="page" value="1" />
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="23%">
						<col width="30%">
						<tr>
							<td class="head">
								�豸��
							</td>
							<td class="body">
								<input type="text" name="sbh" id="sbh" maxlength="128">
							</td>
							<td class="head">
								������
							</td>
							<td colspan="1" class="body">
								<input type="text" name="glbm" id="glbm" maxlength="256">
							</td>
							<td class="submit" >
								<input type="button" class="button_query" value="��ѯ"
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
						��ѯ����
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="10%">
						<col width="20%">
						<col width="20%">
						<col width="10%">
						<col width="20%">
						<col width="20%">
						<tr class="head">
							<td>
								��ȫģ�����
							</td>
							<td>
								�豸��
							</td>
							<td>
								������
							</td>
							<td>
								״̬
							</td>
							<td>
								��������
							</td>
							<td>
								��ע
							</td>
						</tr>
						<c:set var="rowcount" value="1" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="deviceedit('<c:out value='${current.xh}'/>')">
								<td>
									<c:out value="${current.aqmkxh}" />
								</td>
								<td>
									<c:out value="${current.sbh}" />
								</td>
								<td>
									<c:out value="${current.glbm}" />
								</td>
								<td>
                                    <c:forEach var="status" items="${statusList}">
                                        <c:if test="${status.status == current.zt}">
                                            <c:out value="${status.desc}" />
                                        </c:if>
                                    </c:forEach>
								</td>
								<td>
								<fmt:formatDate value='${current.gxrq}'
										pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									<c:out value="${current.bz}" />
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
</html>