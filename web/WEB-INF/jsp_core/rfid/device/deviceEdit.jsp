<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
	<head>
		<title>设备维护</title>
		<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				设备维护
			</div>
			<form action="" method="post" name="myform" id="myform">
				<input type="hidden" name="detailList" id="detailList">
				<input type="hidden" name="sbxh" id="sbxh"
					value="<c:out value='${bean.xh}'/>">
				<div id="block">
					<div id="blocktitle">
						设备信息
					</div>
					<div id="blockmargin">
						8
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="detail">
						<col width="10%">
						<col width="30%">
						<col width="10%">
						<col width="30%">
						<c:if test="${bean!=null}">
						<tr>
							<td class="head">
								序列号
							</td>
							<td class="body">
									<c:out value='${bean.xh}' />
							</td>
							<td class="head">
								设备名称
							</td>
							<td class="body">
								<input type="text" name="sbmc" id="sbmc" value="${bean.sbmc}">
							</td>
						</tr>
						<tr>
							<td class="head">
								地址
							</td>
							<td class="body">
								<input type="text" name="dz" id="dz" value='${bean.dz}'>
							</td>
							<td class="head">
								状态
							</td>
							<td class="body">
								<select id="zt" name="zt">
										<c:forEach var="status" items="${deviceStatus}">
											<c:choose>
												<c:when test="${status.status == bean.zt}">
													<option value="${status.status}">
														<c:out value="${status.desc}" />
													</option>
												</c:when>
											</c:choose>
										</c:forEach>
										<c:forEach var="status" items="${deviceStatus}">
											<c:choose>
												<c:when test="${status.status == bean.zt}">
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
							<td class="head">
								IP地址
							</td>
							<td class="body">
								<input type="text" name="ip" id="ip" value="${bean.ip}">
							</td>
							<td class="head">
								MAC地址
							</td>
							<td class="body">
								<input type="text" name="mac" id="mac" value="${bean.mac}">
							</td>
						</tr>
						<tr>
							<td class="head">
								创建日期
							</td>
							<td class="body">
									<fmt:formatDate value='${bean.cjrq}'
										pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td class="head">
								失效日期
							</td>
							<td class="body">
									<fmt:formatDate value='${bean.sxrq}'
										pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						</c:if>
						
						<c:if test="${bean==null}">
						<tr>
							<td class="head">
								设备名称
							</td>
							<td class="body">
								<input type="text" name="sbmc" id="sbmc" value="${bean.sbmc}">
							</td>
							<td class="head">
								地址
							</td>
							<td class="body">
								<input type="text" name="dz" id="dz" value='${bean.dz}'>
							</td>
							</tr>
							<tr>
							<td class="head">
								状态
							</td>
							<td class="body">
								<select id="zt" name="zt">
										<c:forEach var="status" items="${deviceStatus}">
											<option value="${status.status}">
												<c:out value="${status.desc}" />
											</option>
										</c:forEach>
								</select>
							</td>
							<td class="head">
								IP地址
							</td>
							<td class="body">
								<input type="text" name="ip" id="ip" value="${bean.ip}">
							</td>
							</tr>
							<tr>
							<td class="head">
								MAC地址
							</td>
							<td class="body" colspan="3">
								<input type="text" name="mac" id="mac" value="${bean.mac}" style="width: 43%;">
							</td>
						</tr>
						</c:if>
					</table>
				</div>
				<c:if test="${!empty list}">
					<div id="block">
						<div id="blocktitle">
							原工位详情
						</div>
						<div id="blockmargin">
							8
						</div>
						<table border="0" cellspacing="1" cellpadding="0" class="list">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<tr class="head">
								<td>
									设备名称
								</td>
								<td>
									工位名称
								</td>
								<td>
									工位序号
								</td>
								<td>
									状态
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
										<c:out value='${bean.sbmc}' />
									</td>
									<td>
										<c:out value="${current.gwmc}" />
									</td>
									<td>
										<c:out value="${current.gwxh}" />
									</td>
									<td>
										<c:forEach var="status" items="${deviceStatus}">
											<c:if test="${current.zt == status.status}">
												<c:out value="${status.desc}" />
											</c:if>
										</c:forEach>
									</td>
									<td>
										<a href="#"
											onclick="deleteOldseat('<c:out value="${current.xh}"/>')">删除</a>
									</td>
								</tr>
								<c:set var="rowcount" value="${rowcount+1}" />
							</c:forEach>
						</table>
					</div>
				</c:if>
				<div id="block">
					<div id="blocktitle">
						新增工位
					</div>
					<div id="blockmargin">
						8
					</div>

					<table border="0" cellspacing="1" cellpadding="0" class="detail">
						<col width="10%">
						<col width="10%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
						<tr>
							<td class="head">
								工位名称
							</td>
							<td class="body">
								<input type="text" name="gwmc" id="gwmc">
							</td>
							<td class="head">
								工位序号
							</td>
							<td class="body">
								<input type="text" name="gwxh" id="gwxh">
							</td>
							<td class="head">
								状态
							</td>
							<td class="body">
								<select id="gwzt" name="gwzt" style="width: 80%;">
									<option value="0">
										未就绪
									</option>
									<option value="1">
										生效
									</option>
									<option value="2">
										失效
									</option>
								</select>
							</td>
							<td class="command">
								<input type="button" name="plusbutton" id="plusbutton"
									onclick="add()" value="添加" class="button_new">
							</td>
						</tr>
					</table>
					<table border="0" cellspacing="1" cellpadding="0" class="list"
						id="list">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<tr class="head">
							<td>
								设备名称
							</td>
							<td>
								工位名称
							</td>
							<td>
								工位序号
							</td>
							<td>
								状态
							</td>
							<td>
								操作
							</td>
						</tr>
					</table>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="detail">
					<tr>
						<td class="command">
							<input type="button" name="savebutton" id="savebutton"
								onclick="save()" value="保存" class="button_save">
							<input type="button" name="delbutton" id="delbutton"
								onclick="del()" value="删除" class="button_del">
							<input type="button" name="closebutton"
								onclick="javascript:window.close();" value="关闭"
								class="button_close">
						</td>
					</tr>
				</table>
			</form>
		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
		<script language="JavaScript" type="text/javascript">
			var detailList = [];
			$(document).ready(function(){
			    $("#delbutton").attr("disabled",true);
			<c:if test="${bean!=null}">
			    $("#savebutton").attr("disabled",false);
			    $("#xh").val("<c:out value='${bean.xh}'/>");
				$("#sbmc").val("<c:out value='${bean.sbmc}'/>");
				$("#dz").val("<c:out value='${bean.dz}'/>");
				
			</c:if>
			});
			function save(){
			    if(!checkNull($("#sbmc"),"设备名称")) return false;
				if(!checkNull($("#dz"),"地址")) return false;
				$("#myform").attr("action","<c:url value='/device.frm?method=save-device'/>");
				closes();
				$("#myform").ajaxSubmit({
					dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
				});
			}
			
			function add(){
					var sbmc = $("#sbmc").val();
					var gwmc = $("#gwmc").val();
					var gwxh = $("#gwxh").val();
					var gwzt = $("#gwzt").val();
			        var ztDesc = $("#gwzt").find("option:selected").text();
					if(gwmc != ""){
						detailList.push({mc:gwmc,xh:gwxh,gwzt:gwzt});
			            var detailListJson = "[";
			            for (var i = 0; i < detailList.length; i++) {
			                detailListJson = detailListJson + "{mc:'" + detailList[i].mc + "', xh:'" + detailList[i].xh + "', zt:'" + detailList[i].gwzt + "'},";
			            }
			            detailListJson = detailListJson.substr(0, detailListJson.length - 1);
			            detailListJson = detailListJson + "]";
						$("#detailList").val(detailListJson);
						
						var detailObj = "<tr class=\"out\" onMouseOver=\"this.className='over'\" onMouseOut=\"this.className='out'\" style=\"cursor: pointer\"><td>"+
							sbmc+"</td><td>"+
							gwmc+"</td><td>"+gwxh+"</td><td>"+ztDesc+"</td><td><a href=\"#\" onclick=\"_deleteRaw(this)\">删除</a></td></tr>";
						
						$("#list").append(detailObj);
			
			      		$("#gwmc").val("");
			      		$("#gwxh").val("");
			      		$("#gwzt").val("");
					}
				}
				
				var _deleteRaw = function(row){
				var mc = row.parentNode.parentNode.childNodes[1].innerHTML;
				var xh = row.parentNode.parentNode.childNodes[2].innerHTML;
				for(var i in detailList){
					var rawValue = detailList[i];
					if(rawValue.mc == mc && rawValue.xh == xh){
						detailList.splice(i,1);
					}
				}
				$(row.parentNode.parentNode).remove();
			}
			function returns(data) { 
				if(data["code"]=="1"){
					window.opener.query_cmd();
					alert("保存成功！");
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
			
			function deleteOldseat(xh){
				if(confirm("是否确定删除该工位？")){
					$("#myform").attr("action","<c:url value='/device.frm?method=del-deviceSeat&xh="+xh+"'/>");
					closes();
					$("#myform").ajaxSubmit({
						dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returndeletes
					});
				}
			}
			
			function returndeletes(data) { 
				if(data["code"]=="1"){
				 var xh = $("#sbxh").val();
					window.opener.query_cmd();
					alert("删除工位成功！");
					window.location.replace("/rmweb/device.frm?method=edit-device&xh="+xh);
				}else{
					alert(decodeURIComponent(data["message"]));
					opens();
				}
			}
			
		</script>
	</body>
</html>
