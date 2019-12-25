<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>电子标签解绑</title>
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
				电子标签解绑
			</div>
			<div id="query">
				<div id="querytitle">
					查询条件
				</div>
				<form action="" method="post" name="myform" id="myform">
					<input type="hidden" name="page" value="1" />
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
								卡号
							</td>
							<td class="body">
                                <c:if test="${command != null}">
                                    <input type="text" name="kh" id="kh" maxlength="12" style="width:80%" value="${command.kh}">
                                </c:if>
                                <c:if test="${command == null}">
                                    <input type="text" name="kh" id="kh" maxlength="12" style="width:80%">
                                </c:if>
							</td>
							<td class="head">
								号牌种类
							</td>
							<td class="body">
								<select id="hpzl" name="hpzl" style="width: 80%;">
									<option value="">
										--请选择号牌种类--
									</option>
									<c:forEach var="licenceType" items="${licenceTypes}">
                                        <c:choose>
                                            <c:when test="${command != null && command.hpzl == licenceType.dmz}">
                                                <option value="${licenceType.dmz}" selected>
                                                    <c:out value="${licenceType.dmsm1}" />
                                                </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${licenceType.dmz}">
                                                    <c:out value="${licenceType.dmsm1}" />
                                                </option>
                                            </c:otherwise>
                                        </c:choose>
									</c:forEach>
								</select>
							</td>
							<td class="head">
								号牌号码
							</td>
							<td class="body">
								<div style="position:relative;float:left;width:20%" class="input-wrap">
									<input type="text" name="fzjg" id="fzjg"
										   value='<c:out value="${localFzjg}" />' style="cursor:pointer"
										   readonly="readonly" maxlength="15">
								</div>
								<div style="position:relative;float:right;width:70%" class="input-wrap">
                                    <c:choose>
                                        <c:when test="${command != null}">
                                            <input type="text" name="hphm" id="hphm"  maxlength="15" tabindex="3" value="${command.hphm}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" name="hphm" id="hphm"  maxlength="15" tabindex="3">
                                        </c:otherwise>
                                    </c:choose>
								</div>
							</td>
                            
							<td class="submit">
								<input type="button" class="button_default" value="查询"
									onclick="query_cmd()">
									<input type="button" class="button_default" value="新增"
									onclick="createUnbind()">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="queryresult"></div>
			<c:if test="${queryList!=null}">
				<div id="result">
					<div id="resulttitle">
						查询内容
					</div>
					<table border="0" cellspacing="1" cellpadding="0" class="list">
						<col width="20%">
						<col width="20%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<tr class="head">
                            <td>
                             	卡号
                            </td>
							<td>
								TID
							</td>
							<td>
								号牌种类
							</td>
							<td>
								号牌号码
							</td>
                            <td>
 								操作人
                            </td>
							<td>
								解绑日期
							</td>
							
						</tr>
						<c:set var="rowcount" value="0" />
						<c:forEach items="${queryList}" var="current">
							<tr class="out" onMouseOver="this.className='over'"
								onMouseOut="this.className='out'" style="cursor: pointer">
                                <td>
                                    <c:out value="${current.kh}"/>
                                </td>
								<td>
									<c:out value="${current.tid}" />
								</td>
								<td>
									<c:forEach var="licenceType" items="${licenceTypes}">
										<c:if test="${licenceType.dmz == current.hpzl}">
                                            <c:out value="${licenceType.dmsm1}" />
                                        </c:if>
									</c:forEach>
								</td>
								<td>
									<c:out value="${fn:substring(current.fzjg, 0, 1)}${current.hphm}" />
								</td>
                                <td>
									<c:out value="${current.czr}" />
                                </td>
                                <td>
                                    <fmt:formatDate value='${current.created_at}'
                                                    pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
							</tr>
							<c:set var="rowcount" value="${rowcount+1}" />
						</c:forEach>
						<tr>
							<td colspan="11" class="page">
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
		var Eri_Unbind_Main_NS = {

			init : function() {
				$("#fzjg").dblclick(function () {
					if ($(".fzjg-select").length > 0) {
						$(".fzjg-select").show();
					} else {
						if (!Eri_Unbind_Main_NS.bdfzjg) {
							Eri_Unbind_Main_NS.bdfzjg = $("#bdfzjg").val().split(",");
						}
						var fzjgSelector = "<div class='fzjg-select'><ul>";
						var existsFzjg = {};
						$(Eri_Unbind_Main_NS.bdfzjg).each(function (index, element) {
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
				$("body").on("click", ".fzjg-select li", function () {
					$("#fzjg").val($(this).text());
				});
				$(document).click(function () {
					$(".fzjg-select").hide();
				});
			}

		};

		$(function() {
			Eri_Unbind_Main_NS.init();
		});

		
		function query_cmd(){
			if($("#hpzl").val().replace(/(^s*)|(s*$)/g, "").length !=0||
			$("#hphm").val().replace(/(^s*)|(s*$)/g, "").length !=0){
		 	if(!checkNull($("#hpzl"),"号牌种类")) return false;
			if(!checkNull($("#hphm"),"号牌号码")) return false;
			}
		
			$("#myform").attr("action","<c:url value='/eri-unbind.frm?method=query-unbind'/>");
			closes();
			$("#myform").submit();
		}
		function createUnbind() {
			openwin("/rmweb/eri-unbind.frm?method=create-unbind", "createUnbind");
		}
	</script>
</html>