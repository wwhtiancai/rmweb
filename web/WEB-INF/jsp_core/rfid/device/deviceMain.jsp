<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>�豸��ά��</title>
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
				�豸��ά��
			</div>
			<div id="query">
				<div id="querytitle">
					��ѯ����
				</div>
				<form action="" method="post" name="myform" id="myform">
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
                        <col width="25%">
						<tr>
							<td class="head">
								���
							</td>
							<td class="body">
								<input type="text" name="xh" id="xh" maxlength="10" >
							</td>
							<td class="head">
								�豸����
							</td>
							<td class="body">
								<input type="text" name="sbmc" id="sbmc" maxlength="128">
							</td>
							<td class="head">
								��ַ
							</td>
							<td colspan="1" class="body">
								<input type="text" name="dz" id="dz" maxlength="256">
							</td>
                            <td class="submit">
                                <input type="button" class="button_query" value="��ѯ"
                                       onclick="query_cmd()">
                                <input type="button" class="button_new" value="����"
                                       onclick="deviceedit('')">
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
						<tr class="head">
							<td>
								���к�
							</td>
							<td>
								�豸����
							</td>
							<td>
								��ַ
							</td>
							<td>
								״̬
							</td>
							<td>
								IP��ַ
							</td>
							<td>
								MAC��ַ
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="deviceedit('<c:out value='${current.xh}'/>')">
								<td>
									<c:out value="${current.xh}" />
								</td>
								<td>
									<c:out value="${current.sbmc}" />
								</td>
								<td>
									<c:out value="${current.dz}" />
								</td>
								<td>
                                    <c:forEach var="status" items="${deviceStatus}">
                                        <c:if test="${status.status == current.zt}">
                                            <c:out value="${status.desc}" />
                                        </c:if>
                                    </c:forEach>
								</td>
								<td>
									<c:out value="${current.ip}" />
								</td>
								<td>
									<c:out value="${current.mac}" />
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
					$("#xh").val("<c:out value='${command.xh}'/>");
					$("#sbmc").val("<c:out value='${command.sbmc}'/>");
					$("#dz").val("<c:out value='${command.dz}'/>");
				</c:if>
				
			});
			
			function query_cmd(){
				var val = $('#xh').val();
				for(var i=0;i<val.length;i++){
					if(val[i]!=null && val[i]!="" ){
						var reg = /^\d+$/;
						if(!val[i].match( reg )){
							alert("����š�����Ϊ���֣�");
							return false;
						}
					}
				}
				$("#myform").attr("action","<c:url value='/device.frm?method=query-device'/>");
				closes();
				$("#myform").submit();
			}
			function deviceedit(xh) {
				openwin("/rmweb/device.frm?method=edit-device&xh="+xh, "deviceEdit");
			}
			
		</script>
	</body>
</html>