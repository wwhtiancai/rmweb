<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>��Ʒ����</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				��Ʒ����
			</div>
			<form action="" method="post" name="myform" id="myform">
			<input type="hidden" name="flag" id="flag" value="false">
				<div id="block">
					<div id="blocktitle">
						��Ʒ��Ϣ
					</div>
					<div id="blockmargin">
						8
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="detail">
						<col width="10%">
						<col width="30%">
						<col width="10%">
						<col width="30%">
						<tr>
							<td class="head">
								��Ʒ����
							</td>
							<td class="body">
							<c:if test="${bean!=null}">
								<c:out value="${bean.cpdm}"/>
								<input type="hidden" name="cpdm" id="cpdm" value="<c:out value='${bean.cpdm}'/>"/>
							</c:if>
							<c:if test="${bean==null}">
									<input type="text" name="cpdm" id="cpdm">
							</c:if>
							</td>
							<td class="head">
								��Ʒ����
							</td>
							<td class="body">
								<input type="text" name="cpmc" id="cpmc" value="${bean.cpmc}">
							</td>
						</tr>
						<tr>
							<td class="head">
								��Ʒ���
							</td>
							<td class="body">
							<c:if test="${bean!=null}">
								<c:forEach var="status" items="${cplbStatus}">
									<c:choose>
										<c:when test="${status.cplb == bean.cplb}">
											<input type="hidden" id="cplb" name="cplb" value="${bean.cplb}"/>
											<c:out value="${status.lbmc}" />
										</c:when>
									</c:choose>
								</c:forEach>
							</c:if>
							<c:if test="${bean==null}">
							<select id="cplb" name="cplb" >
									<option value="">
										--��ѡ���Ʒ���--
									</option>
									<c:forEach var="status" items="${cplbStatus}">
										<option value="${status.cplb}">
											<c:out value="${status.lbmc}" />
												</option>
									</c:forEach>
								</select>
							</c:if>
							</td>
							<td class="head">
								״̬
							</td>
							<td class="body">
							<c:if test="${bean!=null}">
								<select id="zt" name="zt">
										<c:forEach var="productType" items="${productTypes}">
											<c:choose>
												<c:when test="${productType.status == bean.zt}">
													<option value="${productType.status}">
														<c:out value="${productType.desc}" />
													</option>
												</c:when>
											</c:choose>
										</c:forEach>
										<c:forEach var="productType" items="${productTypes}">
											<c:choose>
												<c:when test="${productType.status == bean.zt}">
												</c:when>
												<c:otherwise>
													<option value="${productType.status}">
														<c:out value="${productType.desc}" />
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select>
								</c:if>
								<c:if test="${bean==null}">
								<select id="zt" name="zt" >
									<option value="">
										--��ѡ���Ʒ״̬--
									</option>
									<c:forEach var="productType" items="${productTypes}">
										<option value="${productType.status}">
											<c:out value="${productType.desc}" />
												</option>
									</c:forEach>
								</select>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="head">
								��Ӧ������
							</td>
							<td class="body">
								<input type="text" name="gysmc" id="gysmc" value="${bean.gysmc}">
							</td>
							<td class="head">
								����ֵ
							</td>
							<td class="body">
								<input type="text" name="tzz" id="tzz" value="${bean.tzz}">
							</td>
						</tr>
					</table>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="detail">
					<tr>
						<td class="command">
							<input type="button" name="savebutton" id="savebutton"
								onclick="save()" value="����" class="button_save">
							<input type="button" name="delbutton" id="delbutton"
								onclick="del()" value="ɾ��" class="button_del">
							<input type="button" name="closebutton"
								onclick="javascript:window.close();" value="�ر�"
								class="button_close">
						</td>
					</tr>
				</table>
			</form>
		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
		<script language="JavaScript" type="text/javascript">
			$(document).ready(function(){
			    $("#delbutton").attr("disabled",true);
			<c:if test="${bean!=null}">
			 	$("#flag").val(true);
			    $("#savebutton").attr("disabled",false);
			    $("#delbutton").attr("disabled",false);
				
			</c:if>
			});
			function save(){
			   	if(!checkNull($("#cpdm"),"��Ʒ����")) return false;
				if(!checkNull($("#cplb"),"��Ʒ���")) return false;
				$("#myform").attr("action","<c:url value='/product.frm?method=save-product'/>");
				closes();
				$.ajax({
					url: "<c:url value='/product.frm?method=save-product'/>",
					type: "get",
					async: false,
					data: $("#myform").serialize(),
					dataType: "json",
					success: returns
				});
			}
			
			
			function returns(data) {
				if(data["code"]=="1"){
					window.opener.query_cmd();
					alert("����ɹ���");
					window.close();
				}else{
					alert(decodeURIComponent(data["message"]));
					opens();
					$("#delbutton").attr("disabled",true);
					<c:if test="${bean!=null}">
						$("#delbutton").attr("disabled",false);
			        </c:if>
				}
			}
			
			function del(){
				if(confirm("�Ƿ�ȷ��ɾ���ò�Ʒ��Ϣ")){
					$("#myform").attr("action","<c:url value='/product.frm?method=del-product'/>");
					closes();
					$("#myform").ajaxSubmit({
						dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndeletes
					});
				}
			}
			
			function returndeletes(data) { 
				if(data["code"]=="1"){
					window.opener.query_cmd();
					alert("ɾ����Ʒ�ɹ���");
					window.close();
				}else{
					alert(decodeURIComponent(data["message"]));
					opens();
				}
			}
		</script>
	</body>
</html>
