<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>报废维护</title>
	<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
	<link href="rmtheme/rfid/main.css" rel="stylesheet" type="text/css">
</head>
<body class="rfid">
<div id="panel" style="display:none">
	<div id="paneltitle">报废维护</div>
	<form action="" method="post" name="myform" id="myform">
	<input type="hidden" name="flag" id="flag" value="false">
	<input type="hidden" name="detailList" id="detailList">
	<input type="hidden" name="bfdh" id="bfdh" value="<c:out value='${bean.bfdh}'/>" >
	<div id="block">
		<div id="blocktitle">报废信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="30%">
			<c:if test="${bean!=null}">
			<tr>
				<td class="head">报废单号</td>
				<td class="body">
				    <c:out value='${bean.bfdh}'/>
				</td>
				<td class="head">状态</td>
				<td class="body">
						<c:forEach var="status" items="${eriScrapStatus}">
								<c:if test="${status.status == bean.zt}">
									<option value="${status.status}">
										<c:out value="${status.desc}" />
									</option>
								</c:if>
						</c:forEach>
				</td>
			</tr>
			</c:if>
			<c:if test="${bean!=null}">
			<tr>
				<td class="head">请求日期</td>
				<td class="body">
				<fmt:formatDate value='${bean.qqrq}' pattern="yyyy-MM-dd HH:mm:ss" /> 
				</td>
				<td class="head">完成日期</td>
				<td class="body">
				<fmt:formatDate value='${bean.wcrq}' pattern="yyyy-MM-dd HH:mm:ss" /> 
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="head">经办人</td>
				<td class="body">
				<c:if test="${bean!=null}">
				<c:out value='${bean.jbr}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="jbr" id="jbr">
				</c:if>
				</td>
				<td class="head">操作人</td>
				<td class="body">
				<c:if test="${bean!=null}">
				<c:out value='${bean.czr}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="czr" id="czr">
				</c:if>
				</td>
			</tr>
			<tr>
			<td class="head">备注</td>
				<td class="body" colspan="3">
				<c:if test="${bean!=null}">
				<c:out value='${bean.bz}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="bz" id="bz" >
				</c:if>
				</td>
				</tr>
				<tr>
			<td class="head">报废原因</td>
				<td class="body" colspan="3" >
					<c:choose>
						<c:when test="${bean==null|| bean.zt == 1}">
							<textarea name="bfyy" id="bfyy" rows="4" cols="100"></textarea>
						</c:when>
						<c:otherwise>
							<c:out value='${bean.bfyy}'/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
		</div>
		<c:if test="${!empty list}">
			<div id="block">
				<div id="blocktitle">
					原报废卡详情
				</div>
				<div id="blockmargin">
					8
				</div>
				<c:choose>
					<c:when test="${bean == null || bean.zt == 1}">
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="25%">
						<col width="25%">
						<col width="25%">
						<col width="25%">
						<tr class="head">
							<td>
								序号
							</td>
							<td>
								TID
							</td>
							<td>
								卡号
							</td>
							<td>
								操作
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${list}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer">
								<td>
									<c:out value='${rowcount+1}' />
								</td>
								<td>
									<c:out value="${current.tid}" />
								</td>
								<td>
									<c:out value="${current.kh}" />
								</td>
								<td>
									<a href="#"
										onclick="Eri_Scrap_Edit_NS.deleteOldDh('<c:out value="${current.xh}"/>')">删除</a>
								</td>
							</tr>
							<c:set var="rowcount" value="${rowcount+1}" />
						</c:forEach>
					</table>
					</c:when>
					<c:otherwise>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="33%">
						<col width="33%">
						<col width="34%">
						<tr class="head">
							<td>
								序号
							</td>
							<td>
								TID
							</td>
							<td>
								卡号
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${list}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer">
								<td>
									<c:out value='${rowcount+1}' />
								</td>
								<td>
									<c:out value="${current.tid}" />
								</td>
								<td>
									<c:out value="${current.kh}" />
								</td>
							</tr>
							<c:set var="rowcount" value="${rowcount+1}" />
						</c:forEach>
					</table>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
		<c:if test="${bean == null || bean.zt == 1}">
		<div id="block">
			<div id="blocktitle">
				新增报废卡
			</div>
			<div id="blockmargin">
				8
			</div>
			<table border="0" cellspacing="1" cellpadding="0" class="detail">
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<tr>
					<td class="head">
						TID
					</td>
					<td class="body">
						<input type="text" name="tid" id="tid">
					</td>
					<td class="head">
						卡号
					</td>
					<td class="body">
						<input type="text" name="kh" id="kh">
					</td>
					<td class="command">
						<input type="button" name="plusbutton" id="plusbutton"
							onclick="Eri_Scrap_Edit_NS.add()" value="添加" class="button_new">
					</td>
				</tr>
			</table>
			<table border="0" cellspacing="1" cellpadding="0" class="list"
				id="list">
				<col width="15%">
				<col width="15%">
				<col width="15%">
				<tr class="head">
					<td>
						TID
					</td>
					<td>
						卡号
					</td>
					<td>
						操作
					</td>
				</tr>
			</table>
		</div>
		</c:if>
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
				<c:if test="${bean == null || bean.zt == 1}">
				<input type="button" name="savebutton" id="savebutton" onclick="Eri_Scrap_Edit_NS.save()" value="保存" class="button_default">
				<input type="button" name="delbutton" id="delbutton" onclick="Eri_Scrap_Edit_NS.del()" value="取消" class="button_default">
				</c:if>
				<c:if test="${bean != null && bean.zt == 2}">
				<input type="button" name="finishButton" id="finishButton" onclick="Eri_Scrap_Edit_NS.finish()" value="完成" class="button_default">
				</c:if>
				<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_default">
			</td>
		</tr>
	</table>
	</form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script language="JavaScript" type="text/javascript">

	Eri_Scrap_Edit_NS = {

		detailList : [],

		init : function() {
			<c:if test="${bean!=null}">
			$("#flag").val(true);
			</c:if>
		},

		save : function() {
			if (Eri_Scrap_Edit_NS.detailList.length > 0) {
				var detailListJson = "[";
				for (var i = 0; i < Eri_Scrap_Edit_NS.detailList.length; i++) {
					detailListJson = detailListJson + "{tid:'" + Eri_Scrap_Edit_NS.detailList[i].tid + "', kh:'" + Eri_Scrap_Edit_NS.detailList[i].kh + "'},";
				}
				detailListJson = detailListJson.substr(0, detailListJson.length - 1);
				detailListJson = detailListJson + "]";
				$("#detailList").val(detailListJson);
			}
			$.get("/rmweb/eri-scrap.frm?method=save-Scrap", encodeURI($("#myform").serialize()), function(data) {
				Eri_Scrap_Edit_NS.returns(data);
			}, "json").error(function(error) {
				alert(error);
			});
		},

		add : function(){
			var tid = $("#tid").val();
			var kh = $("#kh").val();
			if(!tid && !kh) {
				alert("请输入TID或者卡号");
			} else {
				Eri_Scrap_Edit_NS.detailList.push({tid:tid,kh:kh});

				var detailObj = "<tr class=\"out\" onMouseOver=\"this.className='over'\" onMouseOut=\"this.className='out'\" style=\"cursor: pointer\"><td class='tid'>"+
						tid+"</td><td class='kh'>"+kh+"</td></td><td><a href=\"#\" onclick=\"Eri_Scrap_Edit_NS.deleteRow(this)\">删除</a></td></tr>";

				$("#list").append(detailObj);

				$("#tid").val("");
				$("#kh").val("");
			}
		},

		deleteRow : function(row){
			var tid = $(row).closest("tr").find("td.tid").text()
			var kh = $(row).closest("tr").find("td.kh").text()
			for (var i = 0; i < Eri_Scrap_Edit_NS.detailList.length; i++) {
				var rowValue = Eri_Scrap_Edit_NS.detailList[i];
				if(rowValue.kh == kh && rowValue.tid == tid){
					Eri_Scrap_Edit_NS.detailList.splice(i,1);
				}
			}
			$(row.parentNode.parentNode).remove();
		},

		returns : function(data) {
			if(data["resultId"]=="00"){
				window.opener.query_cmd();
				alert("保存成功！");
				window.close();
			}else{
				alert(decodeURIComponent(data["resultMsg"]));
				opens();
				$("#delbutton").attr("disabled",true);
			}
		},


		del : function(){
			if(confirm("是否确定删除该报废信息？")){
				closes();
				$.get("<c:url value='/eri-scrap.frm?method=delete-Scrap'/>", {bfdh: $("#bfdh").val(), _t : new Date().getTime()}, function(data) {
					Eri_Scrap_Edit_NS.returnDelete(data);
				}, "json");
			}
		},

		returnDelete : function(data) {
			if(data["resultId"]=="00"){
				window.opener.query_cmd();
				alert("删除成功！");
				window.close();
			}else{
				alert(decodeURIComponent(data["resultMsg"]));
				opens();
			}
		},

		deleteOldDh : function(xh){
			if(confirm("是否确定删除该单号？")){
				$.get("/rmweb/eri-scrap.frm?method=del-eriScrapDetail",
						{xh : xh, _t : new Date().getTime()}, function(data) {
							Eri_Scrap_Edit_NS.returnDeletes(data);
						}, "json");
			}
		},

		returnDeletes : function(data) {
			if(data["resultId"]=="00"){
				var bfdh =  $("#bfdh").val();
				alert("删除单号成功！");
				window.location.replace("/rmweb/eri-scrap.frm?method=edit-Scrap&bfdh="+bfdh);
			}else{
				alert(decodeURIComponent(data["resultMsg"]));
				opens();
			}
		},

		finish : function() {
			$.get("/rmweb/eri-scrap.frm?method=finish", {bfdh: $("#bfdh").val(), _t : new Date().getTime()}, function(data) {
				if (data && data["resultId"] == "00") {
					window.opener.query_cmd();
					alert("提交成功！");
					window.close();
				} else {
					alert("提交失败");
				}
			}, "json");
		}
	}

	$(function() {
		Eri_Scrap_Edit_NS.init();
	});

</script>
</body>
</html>
