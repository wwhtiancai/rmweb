<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312" %>
<html>
<head>
    <title>报废审核</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
            <script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
    <script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
    <script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
    <script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/jquery.qtip.js"></script>
    <script language="JavaScript" src="rmjs/hphm/jquery-position.js" type="text/javascript"></script>
    <script language="JavaScript" src="rmjs/jquery.loadthumb.js" type="text/javascript"></script>
    <script language="JavaScript" type="text/javascript">

        $(document).ready(function () {
            $("#delbutton").attr("disabled", true);
            <c:if test="${bean.zt!=1}">
            $("#unpassbutton").attr("disabled", true);
            $("#passbutton").attr("disabled", true);
            </c:if>
        });
        function save(check) {
            $("#myform").attr("action", "<c:url value='/eri-scrap.frm?method=save-scrap-check&check="+check+"'/>");
            closes();
            $("#myform").ajaxSubmit({
                dataType: "json",
                async: false,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: returns
            });
        }


        function returns(data) {
            if (data["code"] == "1") {
                window.opener.query_cmd();
                alert("保存成功！");
                window.close();
            } else {
                alert(decodeURIComponent(data["message"]));
                opens();
                $("#delbutton").attr("disabled", true);
            }
        }


    </script>
</head>
<body>
<div id="panel" style="display:none">
    <div id="paneltitle">报废审核</div>
    <form action="" method="post" name="myform" id="myform" enctype="multipart/form-data">
        <input type="hidden" name="bfdh" id="bfdh" value="<c:out value='${bean.bfdh}'/>">
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
                                        <c:out value="${status.desc}"/>
                                    </option>
                                </c:if>
                            </c:forEach>

                            <!--  <select id="zt" name="zt" >
									<c:forEach var="status" items="${eriScrapStatus}">
											<c:choose>
												<c:when test="${status.status == bean.zt}">
													<option value="${status.status}">
														<c:out value="${status.desc}" />
													</option>
												</c:when>
											</c:choose>
										</c:forEach>
										<c:forEach var="status" items="${eriScrapStatus}">
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
									</select>-->
                        </td>
                    </tr>
                </c:if>
                <c:if test="${bean!=null}">
                    <tr>
                        <td class="head">请求日期</td>
                        <td class="body">
                            <fmt:formatDate value='${bean.qqrq}' pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="head">完成日期</td>
                        <td class="body">
                            <fmt:formatDate value='${bean.wcrq}' pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="head">经办人</td>
                    <td class="body">
                        <c:out value='${bean.jbr}'/>
                    </td>
                    <td class="head">操作人</td>
                    <td class="body">
                        <c:out value='${bean.czr}'/>
                    </td>
                </tr>
                <tr>
                    <td class="head">备注</td>
                    <td class="body" colspan="3">
                        <c:out value='${bean.bz}'/>
                </tr>
                <tr>
                    <td class="head">报废原因</td>
                    <td class="body" colspan="3">
                        <c:out value='${bean.bfyy}'/>
                    </td>
                </tr>
                <tr>
                    <td class="head">审核结果</td>
                    <td class="body" colspan="3">
                        <c:if test="${bean.zt!=1}">
                            <c:out value='${bean.shbz}'/>
                        </c:if>
                        <c:if test="${bean.zt==1}">
                            <textarea name="shbz" id="shbz" rows="4" cols="100"></textarea>
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
        <c:if test="${!empty list}">
            <div id="block">
                <div id="blocktitle">
                    报废卡详情
                </div>
                <div id="blockmargin">
                    8
                </div>
                <table border="0" cellspacing="1" cellpadding="0" class="list">
                    <col width="15%">
                    <col width="15%">
                    <col width="15%">
                    <col width="15%">
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
                    <c:set var="rowcount" value="0"/>
                    <c:forEach items="${list}" var="current">
                        <tr class="out" style="cursor: pointer">
                            <td>
                                <c:out value='${rowcount+1}'/>
                            </td>
                            <td>
                                <c:out value="${current.tid}"/>
                            </td>
                            <td>
                                <c:out value="${current.kh}"/>
                            </td>
                        </tr>
                        <c:set var="rowcount" value="${rowcount+1}"/>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <table border="0" cellspacing="0" cellpadding="0" class="detail">
            <tr>
                <td class="head" style="width:20%;">上传图片</td>
                <td class="body"><input type="file" name="file_upload" style=" width:100%;"></td>
                <td class="command" style="width:50%;">
                    <input type="button" name="button_default" id="unpassbutton" style=" width:80;" onclick="save('0')"
                           value="审核不通过" class="button_default">
                    <input type="button" name="button_default" id="passbutton" onclick="save('1')" value="审核通过"
                           class="button_default">
                    <input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭"
                           class="button_close">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
