<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
	<title>设备维护</title>
	<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
	<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
	<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
		  media="screen" title="Flora (Default)" />
	<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel">
	<div id="paneltitle">设备维护</div>
	<form action="" method="post" name="myform" id="myform">
		<input type="hidden" name="xh" id="xh" value="<c:out value='${bean.xh}'/>" >
		<div id="block">
			<div id="blocktitle">设备信息</div>
			<div id="blockmargin">8</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="10%">
				<col width="30%">
				<col width="10%">
				<col width="30%">
				<tr>
					<td class="head">设备号</td>
					<td class="body">
						<c:out value='${bean.sbh}'/>
					</td>
					<td class="head">状态</td>
					<td class="body">
						<select id="zt" name="zt">
								<c:forEach var="status" items="${statusList}">
									<c:choose>
										<c:when test="${status.status == bean.zt}">
											<option value="${status.status}" selected>
												<c:out value="${status.desc}" />
											</option>
										</c:when>
										<c:otherwise>
											<option value="${status.status}">
												<c:out value="${status.desc}" />
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				<td class="head">管理部门</td>
					<td class="body">
						<c:out value='${bean.glbm}'/>
					</td>
					<td class="head">更新日期</td>
					<td class="body">
					<fmt:formatDate value='${bean.gxrq}' pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td class="head">公钥</td>
					<td class="body" colspan="3">
						<c:out value='${bean.gy}'/>
					</td>
				</tr>
				<tr>
					<td class="head">备注</td>
					<td class="body" colspan="3">
						<textarea name="bz" id="bz" rows="5">
							<c:out value='${bean.bz}'/>
						</textarea>
					</td>
				</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" class="detail">
			<tr>
				<td class="command">
				<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
				<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
				</td>
			</tr>
		</table>
	</form>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script language="JavaScript" type="text/javascript">
	var detailList = [];
	$(document).ready(function(){
		$("#delbutton").attr("disabled",true);
		<c:if test="${bean!=null}">
		$("#delbutton").attr("disabled",false);
		$("#savebutton").attr("disabled",false);
		</c:if>
	});
	function save(){
		closes();
		$.post("<c:url value='/eri-equipment.frm?method=save-equipment'/>", $("#myform").serialize(), returns, "json");
	}
	function returns(data, textStatus, xhr) {
		if(data["resultId"]=="00"){
			window.opener.query_cmd();
			alert(data["resultMsg"]);
			window.close();
		}else if (data["resultId"] == "01") {
			alert(data["resultMsg"]);
			opens();
		} else {
			errorHandler(data, textStatus, xhr);
		}
	}

	function del(){
		if(confirm("是否确定删除该设备信息？")){
			$("#myform").attr("action","<c:url value='/eri-equipment.frm?method=del-eri-equipment'/>");
			closes();
			$("#myform").ajaxSubmit({
				dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndelete
			});
		}
	}

	function returndelete(data) {
		if(data["code"]=="1"){
			window.opener.query_cmd();
			alert("删除成功！");
			window.close();
		}else{
			alert(decodeURIComponent(data["message"]));
			opens();
		}
	}
</script>
</body>
</html>
