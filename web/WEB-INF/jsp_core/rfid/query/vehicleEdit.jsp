<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
<title>车辆管理维护</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">车辆信息查询</div>
	<form action="" method="post" name="myform" id="myform">
	<div id="block">
		<div id="blocktitle">车辆信息</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
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
				<c:if test="${bean!=null}">
                    <c:forEach var="type" items="${licenceTypes}">
                        <c:choose>
                            <c:when test="${bean.hpzl == type.dmz}">
                                <c:out value="${type.dmsm1}"/>
                            </c:when>
                        </c:choose>
                    </c:forEach>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="hpzl" id="hpzl">
				</c:if>
				</td>
				<td class="head">发证机关</td>
				<td class="body">
					<c:if test="${bean!=null}">
						<c:out value='${bean.fzjg}'/>
					</c:if>
				</td>
				<td class="head">号牌号码</td>
				<td class="body">
				<c:if test="${bean!=null}">
				    <c:out value='${fn:substring(bean.fzjg, 0, 1)}${bean.hphm}'/>
				</c:if>
				<c:if test="${bean==null}">
					<input type="text" name="hphm" id="hphm">
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="head">车辆类型</td>
				<td class="body">
					<c:if test="${bean!=null}">
						<%-- <c:out value="${bean.cllx}"/> --%>
						<c:forEach var="type" items="${vechileTypes}">
							<c:choose>
								<c:when test="${bean.cllx == type.dmz}">
									<c:out value="${type.dmsm1}"/>
								</c:when>
							</c:choose>
						</c:forEach>

					</c:if>
				</td>
				<td class="head">车身主颜色</td>
				<td class="body">
					<c:if test="${bean!=null}">
						<c:forEach var="color" items="${colors}">
							<c:choose>
								<c:when test="${fn:substring(bean.csys, 0, 1) == color.dmz}">
									<c:out value="${color.dmsm1}"/>
								</c:when>
							</c:choose>
						</c:forEach>

					</c:if>
				</td>
                <td class="head">所有人</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.syr}'/>
                    </c:if>
                    <c:if test="${bean==null}">
                        <input type="text" name="syr" id="syr">
                    </c:if>
                </td>
			</tr>
			<tr>
				<td class="head">使用性质</td>
				<td class="body">
					<c:if test="${bean!=null}">
					    <%-- <c:out value='${bean.syxz}'/> --%>
					    <c:forEach var="type" items="${usingPurposes}">
                        <c:choose>
                            <c:when test="${fn:substring(bean.syxz, 0, 1) == type.dmz}">
                                <c:out value="${type.dmsm1}"/>
                            </c:when>
                        </c:choose>
                    </c:forEach>
					</c:if>
				</td>
                <td class="head">排量</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.pl}'/>
                    </c:if>
                </td>
                <td class="head">总质量</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.zzl}'/>
                    </c:if>
                    <c:if test="${bean==null}">
                        <input type="text" name="zzl" id="zzl">
                    </c:if>
                </td>
			</tr>
			<tr>
                <td class="head">整备质量</td>
                <td class="body">
                    <c:out value='${bean.zbzl}'/>
                </td>
                <td class="head">核定载质量</td>
                <td class="body">
                    <c:out value='${bean.hdzzl}'/>
                </td>
				<td class="head">核定载客</td>
				<td class="body">
				    <c:out value='${bean.hdzk}'/>
				</td>
			</tr>
			<tr>
                <td class="head">出厂日期</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <fmt:formatDate value='${bean.ccrq}' pattern="yyyy-MM-dd" />
                    </c:if>
                </td>
				<td class="head">检验有效期止</td>
				<td class="body">
				<c:if test="${bean!=null}">
					<fmt:formatDate value='${bean.yxqz}' pattern="yyyy-MM-dd" />
				</c:if>
				</td>
				<td class="head">强制报废期止</td>
				<td class="body">
				<c:if test="${bean!=null}">
					<fmt:formatDate value='${bean.qzbfqz}' pattern="yyyy-MM-dd" />
				</c:if>
				</td>
			</tr>
            <tr>
                <td class="head">功率</td>
                <td class="body">
                    <c:out value="${bean.gl}"/>
                </td>
                <td class="head">车辆品牌</td>
                <td class="body">
                    <c:if test="${bean!=null}">
                        <c:out value='${bean.clpp}'/>
                    </c:if>
                </td>
                <td class="body" colspan="2"/>
            </tr>
            <c:if test="${bean != null && bean.xszzp != null}">
                <tr>
                    <td class="head">
                        行驶证照片
                    </td>
                    <td class="body" colspan="5">
                        <img id="uploadImg" width="320" heigth="240" style="width:320px;height:240px;"
                             src="/rmweb/be/image.rfid?method=show&path=${bean.xszzp}"/>
                    </td>
                </tr>
            </c:if>
		</table>
    </div>
    <div id="block">
        <div id="blocktitle">制卡信息</div>
        <div id="blockmargin">8</div>

			<table border="0" cellspacing="1" cellpadding="0" class="list">
				<col width="25%">
				<col width="25%">
				<col width="25%">
				<col width="25%">
				<tr class="head">
					<td>
						TID
					</td>
					<td>
						卡号
					</td>
					<td>
						制卡日期
					</td>
					<td>
						状态
					</td>
				</tr>
				<c:set var="rowcount" value="0" />
				<c:forEach items="${eriList}" var="current">
					<tr class="out">
						<td>
							<c:out value="${current.tid}" />
						</td>
						<td>
							<c:out value="${current.kh}" />
						</td>
						<td>
							<c:if test="${current.gxhrq != null}">
								<fmt:formatDate value='${current.gxhrq}' pattern="yyyy-MM-dd HH:mm:ss" />
							</c:if>
						</td>
                        <td>
                            <c:choose>
                                <c:when test="${current.zt == 0}">
                                    进行中
                                </c:when>
                                <c:when test="${current.zt == 1}">
                                    成功
                                </c:when>
                                <c:when test="${current.zt ==2 }">
                                    失败，<c:out value="${current.sbyy}"/>
                                </c:when>
                                <c:when test="${current.zt == 3}">
                                    取消
                                </c:when>
                            </c:choose>
                        </td>
					</tr>
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
	</div>
	
	<table border="0" cellspacing="0" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			    <input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
			</td>
		</tr>
	</table>
	</form>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" type="text/javascript">
		$(document).ready(function(){
			<c:if test="${bean!=null}">
			    //$("#savebutton").attr("disabled",true);
			    $("#hpzl").val("<c:out value='${bean.hpzl}'/>");
				$("#hphm").val("<c:out value='${bean.hphm}'/>");
				$("#syr").val("<c:out value='${bean.syr}'/>");
				$("#yxqz").val("<c:out value='${bean.yxqz}'/>");
				$("#bxzzrq").val("<c:out value='${bean.bxzzrq}'/>");
			</c:if>
		});
		function save(){
		    if(!checkNull($("#hpzl"),"号牌种类")) return false;
			if(!checkNull($("#hphm"),"号牌号码")) return false;
			if(!checkHPHM($("#hphm"),"号牌号码")) return false;
            $.post("<c:url value='/specialVeh.tfc?method=saveSpecial'/>", $("#myform").serialize(), function(data){
                if (data && data["resultId"] == "00") {
                    returns();
                }
            }, "json");

		}
		function returns(data) { 
			if(data["code"]=="1"){
				window.opener.query_cmd();
				alert("保存成功！");
				window.close();
			}else{
				alert(decodeURIComponent(data["message"]));
				opens();
			}
		}
	</script>
</body>
</html>
