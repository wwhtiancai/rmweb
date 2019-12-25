<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>���ά��</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">��ⵥ����ά��</div>
	<form action="" method="post" name="myform" id="myform">
	<div id="block">
		<div id="blocktitle">��ⵥ������Ϣ</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="30%">
			<tr>
				<td class="head">��ⵥ��</td>
				<td class="body" colspan="3">
				<c:if test="${rkdh!=null}">
				    <c:out value='${rkdh}'/>
				    <input type="hidden" name="rkdh" id="rkdh">
				</c:if>
				</td>
				
			</tr>
			<tr>
				<td class="head">��װ���</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.bzxh}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="bzxh" id="bzxh">
				</c:if>
				</td>
				<td class="head">��װ�к�</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.bzhh}'/>
					<input type="hidden" name="bzhh" id="bzhh">
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="bzhh" id="bzhh">
				</c:if>
				</td>
			</tr>
		</table>
	</div>

	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button" name="savebutton" id="savebutton" onclick="save()" value="����" class="button_save">
			<input type="button" name="delbutton" id="delbutton" onclick="del()" value="ɾ��" class="button_del">
			<input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
		    $("#delbutton").attr("disabled",true);
		<c:if test="${rkdh!=null}">
		    $("#rkdh").val("<c:out value='${rkdh}'/>");
		</c:if>
		<c:if test="${bean!=null}">
		    $("#savebutton").attr("disabled",true);
		    $("#bzxh").val("<c:out value='${bean.bzxh}'/>");
		    $("#bzhh").val("<c:out value='${bean.bzhh}'/>");
			
		
			$("#delbutton").attr("disabled",false);
			<c:if test="${bmjb!=null}">
			$("#savebutton").attr("disabled",false);
			</c:if>
		</c:if>
		});
		function save(){
		    if(!checkNull($("#rkdh"),"��ⵥ��")) return false;
			$("#myform").attr("action","<c:url value='/warehouse.frm?method=save-warehouse-detail'/>");
			closes();
			$("#myform").ajaxSubmit({
				dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
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
				    <c:if test="${bmjb!=null}">
					$("#delbutton").attr("disabled",false);
					</c:if>
		        </c:if>
			}
		}
		
		function del(){
			if(confirm("�Ƿ�ȷ��ɾ������ⵥ���飿")){
				$("#myform").attr("action","<c:url value='/warehouse.frm?method=del-warehouse-detail'/>");
				closes();
				$("#myform").ajaxSubmit({
					dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndeletes
				});
			}
		}
		
		function returndeletes(data) { 
			if(data["code"]=="1"){
				window.opener.query_cmd();
				alert("ɾ����ⵥ����ɹ���");
				window.close();
			}else{
				alert(decodeURIComponent(data["message"]));
				opens();
			}
		}
	</script>
</body>
</html>
