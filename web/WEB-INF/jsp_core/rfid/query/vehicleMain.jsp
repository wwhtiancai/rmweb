<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>车辆管理维护</title>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
        <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
		<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
		<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
			media="screen" title="Flora (Default)" />
		<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
			type="text/css">
		<style>
			.fzjg-select {
				position: absolute;
				top: 19px;
				left: 0;
			}

			.fzjg-select ul {
				width: 160px;
			}

			.fzjg-select li {
				width: 40px;
				background: #E5F7FD;
				text-align: center;
				line-height: 24px;
				cursor: pointer;
			}

			.fzjg-select li:hover {
				background: coral;
			}
		</style>
	</head>
	<body>
		<div id="panel" style="display: none">
			<div id="paneltitle">
				车辆管理维护
			</div>
			<div id="query">
				<div id="querytitle">
					查询条件
				</div>
				<form action="" method="post" name="myform" id="myform" enctype="multipart/form-data">
					<input type="hidden" name="page" value="1"/>
					<table border="0" cellspacing="1" cellpadding="0" class="query">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
						<tr>
							<td class="head">号牌种类</td>
							<td class="body">
								<select id="hpzl" name="hpzl">
									<option value="">--请选择号牌种类--</option>
									<c:forEach var="licenceType" items="${licenceTypes}">
										<option value="${licenceType.dmz}">
											<c:out value="${licenceType.dmsm1}" />
										</option>
									</c:forEach>
								</select>
							</td>
							<td class="head">号牌号码</td>
							<td class="body">
								<div style="position:relative;float:left;width:30px;" class="input-wrap">
									<input type="text" name="fzjg" id="fzjg"
										   value='<c:out value="${fn:substring(localFzjg, 0, 1)}" />' style="cursor:pointer"
										   readonly="readonly" maxlength="15">
								</div>
								<div style="position:relative;float:right;" class="input-wrap">
									<input type="text" name="hphm" id="hphm"  maxlength="15" tabindex="3">
								</div>
							</td>
							<td class="body" colspan="4"/>
						</tr>
						<tr>
							<td class="submit" colspan="8">
                                <input type="button" class="button_query" id="createBtn" name="createBtn" value="添加"/>
								<input type="button" class="button_query" value="查询"
									onclick="query_cmd()">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="queryresult"></div>
			<c:if test="${queryList!=null}">
				<input type="hidden" name="bdfzjg" id="bdfzjg" value="${bdfzjg}"/>
				<div id="result">
					<div id="resulttitle">
						查询内容
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="10%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="10%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<tr class="head">
							<td>
								号牌种类
							</td>
							<td>
								号牌号码
							</td>
							<td>
								车辆类型
							</td>
							<td>
								使用性质
							</td>
							<td>
								车身颜色
							</td>
							<td>
								所有人
							</td>
							<td>
								检验有效期止
							</td>
							<td>
								强制报废期止
							</td>
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer"
								onDblClick="eriedit('<c:out value='${current.id}'/>')">
								<td>
									<c:forEach var="licenceType" items="${licenceTypes}">
										<c:if test="${licenceType.dmz == current.hpzl }">
											<c:out value="${licenceType.dmsm1}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<c:out value="${fn:substring(current.fzjg, 0, 1)}${current.hphm}" />
								</td>
								<td>
									<c:forEach var="vehicleType" items="${vehicleTypes}">
										<c:if test="${vehicleType.dmz == current.cllx }">
											<c:out value="${vehicleType.dmsm1}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<c:forEach var="usingPurpose" items="${usingPurposes}">
										<c:if test="${fn:trim(current.syxz) == fn:trim(usingPurpose.dmz) }">
											<c:out value="${usingPurpose.dmsm1}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<c:forEach var="vehicleColor" items="${vehicleColors}">
										<c:if test="${vehicleColor.dmz == fn:substring(current.csys, 0, 1) }">
											<c:out value="${vehicleColor.dmsm1}" />
										</c:if>
									</c:forEach>
								</td>
								<td>
									<c:out value="${current.syr}" />
								</td>
								<td>
									<c:if test="${current.yxqz != null}">
										<fmt:formatDate value='${current.yxqz}' pattern="yyyy-MM-dd" />
									</c:if> 
								</td>
								<td>
									<c:if test="${current.qzbfqz != null}">
										<fmt:formatDate value='${current.qzbfqz}' pattern="yyyy-MM-dd" />
									</c:if> 
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
	</body>
	
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
	<script type="text/javascript">
		$(function() {
			Vehicle_NS.init();
		})
		
		Vehicle_NS = {
			init : function() {

				$("#hphm").css("width", $("#hphm").parents("td").width() - 45);

				<c:if test="${command!=null}">
				$("#hpzl").val("<c:out value='${command.hpzl}'/>");
				$("#hphm").val("${command.hphm}");
				$("#fzjg").val("${fn:substring(command.fzjg, 0, 1)}");
				</c:if>

				if (!$("#fzjg").val()) {
					$("#fzjg").val("${fn:substring(localFzjg,0,1)}");
				}

				$("#fzjg").dblclick(function() {
					if ($(".fzjg-select").length > 0) {
						$(".fzjg-select").show();
					} else {
						if (!Vehicle_NS.bdfzjg) {
							Vehicle_NS.bdfzjg = $("#bdfzjg").val().split(",");
						}
						var fzjgSelector = "<div class='fzjg-select'><ul>";
						var existsFzjg = {};
						$(Vehicle_NS.bdfzjg).each(function(index, element){
							var fzjgProvince = element.substring(0, 1);
							if (!existsFzjg[fzjgProvince]) {
								fzjgSelector = fzjgSelector + '<li>' + fzjgProvince + '</li>';
								existsFzjg[fzjgProvince] = true;
							}
						});
						fzjgSelector += "</ul></div>";
						$("#fzjg").after(fzjgSelector);
					}
				});
				$("body").on("click", ".fzjg-select li", function() {
					$("#fzjg").val($(this).text());
				});

				$(document).click(function () {
					$(".fzjg-select").hide();
				});

                $("#createBtn").click(function() {
                    openwin("<c:url value='vehicle.frm?method=create'/>", "vehicleCreate");
                });
			}
		}
		
		function query_cmd(){
			$("#myform").attr("action","<c:url value='/vehicle.frm?method=query-vehicle'/>");
			closes();
			$("#myform").submit();
		}
		
		function eriedit(id) {
			openwin("/rmweb/vehicle.frm?method=edit-vehicle&id="+id, "vehicleEdit");
		}
	</script>
</html>