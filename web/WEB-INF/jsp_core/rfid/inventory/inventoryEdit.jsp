<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>库存维护</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">库存维护</div>
	<form action="" method="post" name="myform" id="myform">
	<div id="block">
		<div id="blocktitle">库存信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="30%">
			<tr>
				<td class="head">包装盒号</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.bzhh}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="bzhh" id="bzhh">
				</c:if>
				</td>
				<td class="head">所属部门</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.ssbmmc}'/>
				</c:if>
				<c:if test="${bean==null}">
					<select id="ssbm" name="ssbm" style="width: 80%;">
						<c:forEach var="current" items="${xjbmList}">
							<option value="">--请选择所属部门--</option>
							<option value="${current.glbm}">
								<c:out value="${current.bmmc}" />
							</option>
						</c:forEach>
					</select>
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">包装箱号</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.bzxh}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="bzxh" id="bzxh">
				</c:if>
				</td>
				<td class="head">产品</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.cpmc}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="cpdm" id="cpdm">
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">起始卡号</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.qskh}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="qskh" id="qskh">
				</c:if>
				</td>
				<td class="head">终止卡号</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${bean.zzkh}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="zzkh" id="zzkh">
				</c:if>
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
	
	<div id="block">
		<div id="blocktitle">电子标识信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="list">
		
			<col width="15%">
			<col width="15%">
			<col width="15%">
			<col width="15%">
			<col width="10%">
			<col width="10%">
			<tr class="head">
				<td>
					TID
				</td>
				<td>
					卡号
				</td>
				<td>
					状态
				</td>
				<td>
					初始化日期
				</td>
			</tr>
			<c:set var="rowcount" value="0" />
			<c:forEach items="${eris}" var="current">
				<tr class="out" onMouseOver="this.className='over'"
					onMouseOut="this.className='out'" style="cursor: pointer"
					onDblClick="eriDetail('<c:out value='${current.tid}'/>')">
					<td>
						<c:out value="${current.tid}" />
					</td>
					<td>
						<c:out value="${current.kh}" />
					</td>
					<td>
						<c:forEach var="status" items="${eriStatus}">
                            <c:if test="${status.status == current.zt}">
                                <c:out value="${status.desc}" />
                            </c:if>
                        </c:forEach>
					</td>
					<td>
						<c:if test="${current.cshrq != null}">
							<fmt:formatDate value='${current.cshrq}' pattern="yyyy-MM-dd HH:mm:ss" />
						</c:if> 
					</td>
				</tr>
				<c:set var="rowcount" value="${rowcount+1}" />
			</c:forEach>
		</table>
	</div>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
		<c:if test="${bean!=null}">
			$("#savebutton").hide();
		    $("#bzhh").val("<c:out value='${bean.bzhh}'/>");
			$("#qskh").val("<c:out value='${bean.qskh}'/>");
			$("#zzkh").val("<c:out value='${bean.zzkh}'/>");
			$("#ssbm").val("<c:out value='${bean.ssbmmc}'/>");
			
		</c:if>
		});
		function save(){
		    if(!checkNull($("#bzhh"),"包装盒号")) return false;
			if(!checkNull($("#qskh"),"起始卡号")) return false;
			if(!checkNull($("#zzkh"),"终止卡号")) return false;
			if(!checkNull($("#ssbm"),"所属部门")) return false;
			$("#myform").attr("action","<c:url value='/inventory.frm?method=save-inventory'/>");
			closes();
			$("#myform").ajaxSubmit({
				dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
			});
		}
		function returns(data) { 
			if(data["code"]=="1"){
				window.opener.query_cmd();
				displayDialog(2,"保存成功！","");
				window.close();
			}else{
				displayDialog(3,decodeURIComponent(data["message"]),"");
				opens();
			}
		}
		var eriDetail = function(tid){
			openwin("/rmweb/eri.frm?method=query-eri-detail&tid="+tid, "queryEriDetail");
		}
	</script>
</body>
</html>
