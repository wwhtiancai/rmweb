<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK" %>
<html>
<head>
    <title>电子标识签注业务审核</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmtheme/rfid/main.css" type="text/css" rel="stylesheet"/>
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
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
<body class="rfid" style="background:#fff">
<div id="panel" style="height:100%">
    <div id="paneltitle">
        电子标识签注业务审核
    </div>
    <div style="margin-top:20px;">
        <input type="hidden" name="xh" id="xh" value="${task.xh}"/>
        <table border="0" cellspacing="1" cellpadding="0" class="query">
            <col width="10%">
            <col width="10%">
            <col width="20%">
            <col width="10%">
            <col width="20%">
            <col width="10%">
            <col width="20%">
            <tr>
                <td class="head">
                    业务类型
                </td>
                <td class="body" colspan="6">
                    <input type="hidden" id="ywlx" name="ywlx" value="${ywlx}"/>
                    <c:forEach var="type" items="${custTypes}">
                        <c:if test="${type.type == ywlx}">
                            <c:out value="${type.desc}"/>
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
            <c:if test="${oldEri != null}">
                <tr>
                    <td class="head">
                        原电子标识信息
                    </td>
                    <td class="head">卡号</td>
                    <td class="body"><span id="kh"><c:out value="${oldEri.kh}"/></span></td>
                    <td class="head">TID</td>
                    <td class="body" colspan="3"><span id="tid"><c:out value="${oldEri.tid}"/></span></td>
                </tr>
            </c:if>
            <c:if test="${newEri != null}">
                <tr>
                    <td class="head">
                        新电子标识信息
                    </td>
                    <td class="head">卡号</td>
                    <td class="body"><span id="kh"><c:out value="${newEri.kh}"/></span></td>
                    <td class="head">TID</td>
                    <td class="body" colspan="3"><span id="tid"><c:out value="${newEri.tid}"/></span></td>
                </tr>
            </c:if>
            <c:if test="${oldVehicle != null}">
                <tr>
                    <td class="head" rowspan="4">
                        原签注车辆信息
                    </td>
                    <td class="head">
                        号牌种类
                    </td>
                    <td class="body">
                    <span id="wHpzl" name="wHpzl" class="card-info">
                        <c:forEach var="licenceType" items="${licenceTypes}">
                            <c:if test="${licenceType.dmz == oldVehicle.hpzl}">
                                <c:out value="${licenceType.dmsm1}"/>
                            </c:if>
                        </c:forEach>
                    </span>
                    </td>
                    <td class="head">
                        号牌号码
                    </td>
                    <td class="body">
                    <span id="wHphm" name="wHphm" class="card-info">
                        <c:out value='${fn:substring(localFzjg, 0, 1)}'/><c:out value='${oldVehicle.hphm}'/>
                    </span>
                    </td>
                    <td class="head">
                        使用性质
                    </td>
                    <td class="body">
                    <span id="wSyxz" name="wSyxz" class="card-info">
                        <c:forEach var="usingPurpose" items="${usingPurposes}">
                            <c:if test="${usingPurpose.dmz == fn:trim(oldVehicle.syxz)}">
                                <c:out value="${usingPurpose.dmsm1}"/>
                            </c:if>
                        </c:forEach>
                    </span>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        车辆类型
                    </td>
                    <td class="body">
                    <span id="wCllx" name="wCllx" class="card-info">
                        <c:forEach var="carType" items="${carTypes}">
                            <c:if test="${carType.dmz == oldVehicle.cllx}">
                                <c:out value="${carType.dmsm1}"/>
                            </c:if>
                        </c:forEach>
                    </span>
                    </td>
                    <td class="head">
                        车身颜色
                    </td>
                    <td class="body">
                    <span id="wCsys" name="wCsys" class="card-info">
                        <c:forEach var="carColor" items="${carColors}">
                            <c:if test="${carColor.dmz == fn:substring(fn:trim(oldVehicle.csys),0, 1)}">
                                <c:out value="${carColor.dmsm1}"/>
                            </c:if>
                        </c:forEach>
                    </span>
                    </td>
                    <td class="head">
                        排量
                    </td>
                    <td class="body">
                    <span id="wPl" name="wPl" class="card-info">
                        <fmt:formatNumber type="number" value="${oldVehicle.pl/1000}" pattern="0.0"
                                          maxFractionDigits="2"/>L
                    </span>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        总质量
                    </td>
                    <td class="body">
                    <span id="wZzl" name="wZzl" class="card-info">
                         <c:out value='${oldVehicle.zzl}'/>KG
                    </span>
                    </td>
                    <td class="head">
                        核定载客
                    </td>
                    <td class="body">
                    <span id="wHdzk" name="wHdzk" class="card-info">
                          <c:out value='${oldVehicle.hdzk}'/>人
                    </span>
                    </td>
                    <td class="head">
                        功率
                    </td>
                    <td class="body">
                    <span id="wGl" name="wGl" class="card-info">
                        <c:out value='${oldVehicle.gl}'/>
                    </span>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        检验有效期止
                    </td>
                    <td class="body">
                    <span id="wYxqz" name="wYxqz" class="card-info">
                        <fmt:formatDate value='${oldVehicle.yxqz}' pattern="yyyy-MM-dd"/>
                    </span>
                    </td>
                    <td class="head">
                        强制报废期止
                    </td>
                    <td class="body">
                    <span id="wQzbfqz" name="wQzbfqz" class="card-info">
                        <fmt:formatDate value='${oldVehicle.qzbfqz}' pattern="yyyy-MM-dd"/>
                    </span>
                    </td>
                    <td class="head">
                        出厂日期
                    </td>
                    <td class="body">
                    <span id="wCcrq" name="wCcrq" class="card-info">
                        <fmt:formatDate value='${oldVehicle.ccrq}' pattern="yyyy-MM-dd"/>
                    </span>
                    </td>
                </tr>
            </c:if>
            <c:if test="${newVehicle != null}">
                <tr>
                    <td class="head" rowspan="5">
                        更新车辆信息
                    </td>
                    <td class="head">
                        号牌种类
                    </td>
                    <td class="body">
                    <span id="rHpzl" name="rHpzl" class="base-info">
                    <c:forEach var="licenceType" items="${licenceTypes}">
                        <c:if test="${licenceType.dmz == newVehicle.hpzl}">
                            <c:out value="${licenceType.dmsm1}"/>
                        </c:if>
                    </c:forEach>
                    </span>
                    </td>
                    <td class="head">
                        号牌号码
                    </td>
                    <td class="body">
                    <span id="rHphm" name="rHphm" class="base-info" style="width:20%">
                        <c:out value='${fn:substring(localFzjg, 0, 1)}'/><c:out value='${newVehicle.hphm}'/>
                    </span>
                    </td>
                    <td class="head">
                        使用性质
                    </td>
                    <td class="body">
							<span id="rSyxz" name="rSyxz" class="base-info">
						<c:forEach var="usingPurpose" items="${usingPurposes}">
                            <c:if test="${usingPurpose.dmz == fn:trim(newVehicle.syxz)}">
                                <c:out value="${usingPurpose.dmsm1}"/>
                            </c:if>
                        </c:forEach>
							</span>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        车辆类型
                    </td>
                    <td class="body">
							<span id="rCllx" name="rCllx" class="base-info">
						<c:forEach var="carType" items="${carTypes}">
                            <c:if test="${carType.dmz == newVehicle.cllx}">
                                <c:out value="${carType.dmsm1}"/>
                            </c:if>
                        </c:forEach>
							</span>
                    </td>
                    <td class="head">
                        车身颜色
                    </td>
                    <td class="body">
							<span id="rCsys" name="rCsys" class="base-info">
						<c:forEach var="carColor" items="${carColors}">
                            <c:if test="${carColor.dmz == fn:substring(fn:trim(newVehicle.csys),0, 1)}">
                                <c:out value="${carColor.dmsm1}"/>
                            </c:if>
                        </c:forEach>
							</span>
                    </td>
                    <td class="head">
                        排量
                    </td>
                    <td class="body">
							<span id="rPl" name="rPl" class="base-info">
                            <fmt:formatNumber type="number" value="${newVehicle.pl/1000}" pattern="0.0"
                                              maxFractionDigits="2"/>L
							</span>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        总质量
                    </td>
                    <td class="body">
							<span id="rZzl" name="rZzl" class="base-info">
                            <c:out value='${newVehicle.zzl}'/>KG
							</span>
                    </td>
                    <td class="head">
                        核定载客
                    </td>
                    <td class="body">
							<span id="rHdzk" name="rHdzk" class="base-info">
                            <c:out value='${newVehicle.hdzk}'/>人
							</span>
                    </td>
                    <td class="head">
                        功率
                    </td>
                    <td class="body">
							<span id="rGl" name="rGl" class="base-info">
							<c:out value='${newVehicle.gl}'/>
								</span>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        检验有效期止
                    </td>
                    <td class="body">
							<span id="rYxqz" name="rYxqz" class="base-info">
							<fmt:formatDate value='${newVehicle.yxqz}' pattern="yyyy-MM-dd"/>
								</span>
                    </td>
                    <td class="head">
                        强制报废期止
                    </td>
                    <td class="body">
							<span id="rQzbfqz" name="rQzbfqz" class="base-info">
							<fmt:formatDate value='${newVehicle.qzbfqz}' pattern="yyyy-MM-dd"/>
								</span>
                    </td>
                    <td class="head">
                        出厂日期
                    </td>
                    <td class="body">
							<span id="rCcrq" name="rCcrq" class="base-info">
							    <fmt:formatDate value='${newVehicle.ccrq}' pattern="yyyy-MM-dd"/>
                            </span>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        所有人
                    </td>
                    <td class="body" colspan="5">
							<span id="rSyr" name="rSyr" class="base-info">
							    <c:out value='${newVehicle.syr}'/>
                            </span>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td class="head" rowspan="3">
                    业务信息
                </td>
                <td class="head">
                    申请人
                </td>
                <td class="body">
                    <c:out value='${task.sqr}'/>
                </td>
                <td class="head">
                    联系电话
                </td>
                <td class="body">
                    <c:out value='${task.lxdh}'/>
                </td>
                <td class="head">
                    申请日期
                </td>
                <td class="body">
                    <fmt:formatDate value='${task.sqrq}' pattern="yyyy-MM-dd"/>
                </td>
            </tr>
            <tr>
                <td class="head">
                    备注
                </td>
                <td class="body" colspan="5">
                    <span id="rBz" name="rBz" class="base-info">
                        <c:out value='${task.bz}'/>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    审核意见
                </td>
                <td class="body" colspan="5">
                    <textarea id="shyj" name="shyj" rows="4" style="width:100%"></textarea>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" class="detail">
            <tr>
                <td class="command">
                    <input type="button" name="approveBtn" id="approveBtn" value="通过" class="button_default"
                           tabindex="5"/>
                    <input type="button" name="rejectBtn" id="rejectBtn" value="拒绝" class="button_default"
                           tabindex="6"/>
                    <input type="button" id="closeBtn" name="closeBtn" onclick="javascript:window.close();" value="关闭"
                           class="button_default">
                </td>
            </tr>
        </table>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script language="JavaScript" src="rmjs/eri.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">

    $(function () {
        Customize_Task_Audit_NS.init();
    });
    var Customize_Task_Audit_NS = {


        init: function () {

            $("#approveBtn").click(function () {
                Customize_Task_Audit_NS.approve();
            });

            $("#rejectBtn").click(function () {
                Customize_Task_Audit_NS.reject();
            });
            s

        },

        approve: function () {
            $.post("/rmweb/customize-task.frm?method=audit", {
                xh: $("#xh").val(),
                shyj: $("#shyj").val(),
                jg: 1,
                _t: Date.parse(new Date())
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    Tools_NS.showConfirmDialog({
                        id: 'Success',
                        title: '提交成功 ',
                        message: '审核通过',
                        width: 400,
                        cancellable: false,
                        onConfirm: function () {
                            window.opener.query_cmd();
                            window.close();

                        }
                    })
                } else {
                    Tools_NS.showWarningDialog("提交失败:" + data["resultMsg"]);
                }
            }, "json")
        },

        reject: function () {
            $.post("/rmweb/customize-task.frm?method=audit", {
                xh: $("#xh").val(),
                shyj: $("#shyj").val(),
                jg: 0,
                _t: Date.parse(new Date())
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    Tools_NS.showConfirmDialog({
                        id: 'Success',
                        title: '提交成功 ',
                        message: '审核拒绝',
                        width: 400,
                        cancellable: false,
                        onConfirm: function () {
                            window.opener.query_cmd();
                            window.close();
                        }
                    })
                } else {
                    Tools_NS.showWarningDialog("提交失败:" + data["resultMsg"]);
                }
            }, "json")
        }
    };
</script>
</body>
</html>
