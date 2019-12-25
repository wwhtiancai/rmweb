<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK" %>
<html>
<head>
    <title>电子标识更新车辆信息请求</title>
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
        电子标识更新车辆信息请求
    </div>
    <c:if test="${task == null}">
        <div id="queryPanel" style="margin-top:10px;">
            <table border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="12%"/>
                <col width="12%"/>
                <col width="15%"/>
                <col width="16%"/>
                <col width="15%"/>
                <col width="15%"/>
                <col width="15%"/>
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
                <tr>
                    <td class="head">
                        变更电子标识信息
                    </td>
                    <td class="head">
                        卡号
                    </td>
                    <td class="body" colspan="5">
                        <input type="text" id="kh" name="kh"/>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        变更车辆
                    </td>
                    <td class="head">
                        流水号
                    </td>
                    <td class="body">
                        <input type="text" id="lsh" name="lsh" value="${task.lsh}" tabindex="1"/>
                    </td>
                    <td class="head">
                        号牌种类
                    </td>
                    <td class="body">
                        <select id="hpzl" name="hpzl" tabindex="2">
                            <option value="">
                                --请选择号牌种类--
                            </option>
                            <c:forEach var="licenceType" items="${licenceTypes}">
                                <option value="${licenceType.dmz}">
                                    <c:out value="${licenceType.dmsm1}"/>
                                </option>
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
                            <input type="text" name="hphm" id="hphm" maxlength="15" tabindex="3">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="command" colspan="7">
                        <input type="button" name="requestBtn" id="requestBtn" value="查询" class="button_default"
                               tabindex="4"/>
                    </td>
                </tr>
            </table>
        </div>
    </c:if>
    <div style="margin-top:20px;">
        <input type="hidden" name="bdfzjg" id="bdfzjg" value="${bdfzjg}"/>
        <input type="hidden" name="msg" id="msg" value='${msg}'>
        <input type="hidden" name="xh" id="xh" value="${task.xh}"/>
        <input type="hidden" name="rLsh" id="rLsh" value="${task.lsh}"/>
        <input type="hidden" name="latestVersion" id="latestVersion" value="${latestVersion}"/>
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
                    电子标识信息
                </td>
                <td class="head">卡号</td>
                <td class="body">
                    <span id="rKh">
                        <c:if test="${task != null}">
                            <c:out value="${eri.kh}"/>
                        </c:if>
                    </span></td>
                <td class="head">TID</td>
                <td class="body" colspan="3">
                    <span id="rTid">
                        <c:if test="${task != null}">
                            <c:out value="${eri.tid}"/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head" rowspan="4">
                    原签注车辆信息
                </td>
                <td class="head">
                    号牌种类
                </td>
                <td class="body">
                    <span id="wHpzl" name="wHpzl" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.hpzl}"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    号牌号码
                </td>
                <td class="body">
                    <span id="wHphm" name="wHphm" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value='${old.hphm}'/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    使用性质
                </td>
                <td class="body">
                    <span id="wSyxz" name="wSyxz" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.syxz}"/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    车辆类型
                </td>
                <td class="body">
                    <span id="wCllx" name="wCllx" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.cllx}"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    车身颜色
                </td>
                <td class="body">
                    <span id="wCsys" name="wCsys" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.csys}"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    排量
                </td>
                <td class="body">
                    <span id="wPl" name="wPl" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.pl}"/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    总质量
                </td>
                <td class="body">
                    <span id="wZzl" name="wZzl" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.zzl}"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    核定载客
                </td>
                <td class="body">
                    <span id="wHdzk" name="wHdzk" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.hdzk}"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    功率
                </td>
                <td class="body">
                    <span id="wGl" name="wGl" class="card-info">
                        <c:if test="${task != null}">
                            <c:out value="${old.gl}"/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    检验有效期止
                </td>
                <td class="body">
                    <span id="wYxqz" name="wYxqz" class="card-info">
                        <c:if test="${task != null}">
                            <fmt:formatDate value='${old.yxqz}' pattern="yyyy-M-d"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    强制报废期止
                </td>
                <td class="body">
                    <span id="wQzbfqz" name="wQzbfqz" class="card-info">
                        <c:if test="${task != null}">
                            <fmt:formatDate value='${old.qzbfqz}' pattern="yyyy-M-d"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    出厂日期
                </td>
                <td class="body">
                    <span id="wCcrq" name="wCcrq" class="card-info">
                        <c:if test="${task != null}">
                            <fmt:formatDate value='${old.ccrq}' pattern="yyyy-M-d"/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head" rowspan="5">
                    更新车辆信息
                </td>
                <td class="head">
                    号牌种类
                </td>
                <td class="body">
                    <span id="rHpzl" name="rHpzl" class="base-info">
                        <c:if test="${bean != null}">
                            <c:out value="${bean.hpzl}"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    号牌号码
                </td>
                <td class="body">
                    <span id="rHphm" name="rHphm" class="base-info" style="width:20%">
                        <c:if test="${bean != null}">
                            <c:out value='${bean.hphm}'/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    使用性质
                </td>
                <td class="body">
                    <span id="rSyxz" name="rSyxz" class="base-info">
                        <c:if test="${bean != null}">
                            <c:out value='${bean.syxz}'/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    车辆类型
                </td>
                <td class="body">
                    <span id="rCllx" name="rCllx" class="base-info">
                        <c:if test="${bean != null}">
                            <c:out value='${bean.cllx}'/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    车身颜色
                </td>
                <td class="body">
                    <span id="rCsys" name="rCsys" class="base-info">
                         <c:if test="${bean != null}">
                             <c:out value='${bean.csys}'/>
                         </c:if>
                    </span>
                </td>
                <td class="head">
                    排量
                </td>
                <td class="body">
                    <span id="rPl" name="rPl" class="base-info">
                         <c:if test="${bean != null}">
                             <c:out value='${bean.pl}'/>
                         </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    总质量
                </td>
                <td class="body">
                    <span id="rZzl" name="rZzl" class="base-info">
                         <c:if test="${bean != null}">
                             <c:out value='${bean.zzl}'/>
                         </c:if>
                    </span>
                </td>
                <td class="head">
                    核定载客
                </td>
                <td class="body">
                    <span id="rHdzk" name="rHdzk" class="base-info">
                        <c:if test="${bean != null}">
                            <c:out value='${bean.hdzk}'/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    功率
                </td>
                <td class="body">
                    <span id="rGl" name="rGl" class="base-info">
                        <c:if test="${bean != null}">
                            <c:out value='${bean.gl}'/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    检验有效期止
                </td>
                <td class="body">
                    <span id="rYxqz" name="rYxqz" class="base-info">
                        <c:if test="${bean != null}">
                            <fmt:formatDate value='${bean.yxqz}' pattern="yyyy-M-d"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    强制报废期止
                </td>
                <td class="body">
                    <span id="rQzbfqz" name="rQzbfqz" class="base-info">
                        <c:if test="${bean != null}">
                            <fmt:formatDate value='${bean.qzbfqz}' pattern="yyyy-M-d"/>
                        </c:if>
                    </span>
                </td>
                <td class="head">
                    出厂日期
                </td>
                <td class="body">
                    <span id="rCcrq" name="rCcrq" class="base-info">
                        <c:if test="${bean != null}">
                            <fmt:formatDate value='${bean.ccrq}' pattern="yyyy-M-d"/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    所有人
                </td>
                <td class="body" colspan="5">
                    <span id="rSyr" name="rSyr" class="base-info">
                        <c:if test="${bean != null}">
                            <c:out value='${bean.syr}'/>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="head" rowspan="${reviewList != null ? 3 : 2}">
                    业务信息
                </td>
                <td class="head">
                    申请人
                </td>
                <td class="body">
                    <c:choose>
                        <c:when test="${task == null}">
                            <input type="text" id="sqr" name="sqr"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${task.sqr}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="head">
                    联系电话
                </td>
                <td class="body">
                    <c:choose>
                        <c:when test="${task == null}">
                            <input type="text" id="lxdh" name="lxdh"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${task.lxdh}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="head">
                    申请日期
                </td>
                <td class="body">
                    <fmt:formatDate value='${task.sqrq}' pattern="yyyy-MM-dd"/>
                </td>
            </tr>
            <tr>
                <td class="head">备注</td>
                <td class="body" colspan="5">
                    <c:choose>
                        <c:when test="${task == null}">
                            <textarea id="bz" name="bz" style="width:100%" rows="4"></textarea>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${task.bz}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <c:if test="${reviewList != null}">
                <tr>
                    <td class="head">
                        审核信息
                    </td>
                    <td class="body" colspan="5">
                        <c:forEach items="${reviewList}" var="review">
                            <div>
                                <span><fmt:formatDate value='${review.shsj}' pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;&nbsp;</span>
                                <span><c:out value="${review.shrdh}"/>&nbsp;&nbsp;</span>
                                <span style="color:${review.zt == 1 ? 'green' : 'red'}"><c:out
                                        value="${review.zt == 1 ? '同意' : '拒绝'}"/>&nbsp;&nbsp;</span>
                                <span>意见：<c:out value="${review.yj}"/></span>
                            </div>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" class="detail">
            <tr>
                <td class="command">
                    <c:if test="${task == null}">
                        <input type="button" disabled="disabled" name="submitBtn" id="submitBtn" value="提交"
                               class="button_default" tabindex="5"/>
                    </c:if>
                    <c:if test="${task != null && task.zt == 1}">
                        <input type="button" name="customizeBtn" id="customizeBtn" value="签注" class="button_default"
                               tabindex="6"/>
                    </c:if>
                    <c:if test="${task != null && (task.zt == 1 || task.zt == 0)}">
                        <input type="button" name="cancelBtn" id="cancelBtn" value="取消" class="button_default"
                               tabindex="7"/>
                    </c:if>
                    <input type="button" id="closeBtn" name="closeBtn" onclick="javascript:window.close();" value="关闭"
                           class="button_default">
                    <input type="button" id="debugBtn" name="debugBtn" value="调试" class="button_default"/>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <object classid="clsid:055318D9-DF91-437A-B3EF-D27A03806CEE" id="customizeConsole"
                style="display:none;width:200px;height:200px">
        </object>
    </div>
</div>
<div id="footer" style="position:absolute;bottom:0; width:100%;background-color:darkgray;padding:2px 0;display:none">
    <div style="color:green" id="deviceStatus">设备已连接</div>
    <div style="display:none" id="debugInfo"></div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script language="JavaScript" src="rmjs/eri.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">

    $(function () {
        Edit_Customize_Task_Modify_NS.init();
    });

    var Edit_Customize_Task_Modify_NS = {

        log : "",

        sendCommand: function (command, handler, errorHandler) {
            var result = Edit_Customize_Task_Modify_NS.customizeConsole.sendMessage(command);
            if (result == "0") {
                Edit_Customize_Task_Modify_NS.getResponse(1, handler, errorHandler);
                return true;
            } else {
                return false;
            }
        },

        getResponse: function (times, handler, errorHandler) {
            if (times > 50) {
                if (typeof errorHandler != 'undefined') {
                    errorHandler("NO_RESPONSE");
                }
            } else {
                var response = Edit_Customize_Task_Modify_NS.customizeConsole.getRecvMessage();
                if (response) {
                    var responseObj = $.parseJSON(response);
                    var result = responseObj["result"];
                    if (result == "01") {
                        alert("Reader operation not finished yet");
                    } else if (result == "02") {
                        alert("Delte AccessSpec failed");
                    } else if (result == "03") {
                        alert("AddAccessSpec msg failed")
                    } else if (result == "04") {
                        alert("EnableAccessSpec msg failed");
                    } else if (result == "05") {
                        alert("Start msg failed");
                    } else if (result == "06") {
                        alert("No report msg recvd (-3)");
                    } else if (result == "07") {
                        alert("Reader ended without any EndEvent msg recvd (再试一次可能会返回1-5或者10异常，则发卡设备可能死机 -1)");
                    } else if (result == "08") {
                        alert("Reader ended without any RW report msg recvd (-2)");
                    } else if (result == "09") {
                        alert("The tid string recvd in writeData is not equal with pre-query result");
                    } else if (result == "10") {
                        alert("Add commands to reader failed");
                    } else {
                        if (typeof handler != 'undefined') {
                            handler(responseObj);
                        }
                    }
                } else {
                    setTimeout("Edit_Customize_Task_Modify_NS.getResponse(" + (times + 1) + ", " + handler + "," + errorHandler + ")", 100);
                }
            }
        },

        init: function () {

            Edit_Customize_Task_Modify_NS.customizeConsole = document.getElementById("customizeConsole");
            try {
                var customizeActiveX = new ActiveXObject("Eri.Customize");
            }
            catch (e) {
                window.location.href = '/rmweb/system.frm?method=show-plugin';
            }

            <c:if test="${task != null && task.zt == 1}">
            Edit_Customize_Task_Modify_NS.fetchDeviceVersion();
            </c:if>

            $("#fzjg").dblclick(function () {
                if ($(".fzjg-select").length > 0) {
                    $(".fzjg-select").show();
                } else {
                    if (!Edit_Customize_Task_Modify_NS.bdfzjg) {
                        Edit_Customize_Task_Modify_NS.bdfzjg = $("#bdfzjg").val().split(",");
                    }
                    var fzjgSelector = "<div class='fzjg-select'><ul>";
                    var existsFzjg = {};
                    $(Edit_Customize_Task_Modify_NS.bdfzjg).each(function (index, element) {
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

            $("#requestBtn").click(function () {
                Edit_Customize_Task_Modify_NS.query();
            });

            $("#customizeBtn").click(function () {
                Edit_Customize_Task_Modify_NS.customize();
            });

            $("#submitBtn").click(function () {
                Edit_Customize_Task_Modify_NS.submit();
            });

            $("#cancelBtn").click(function () {
                Edit_Customize_Task_Modify_NS.cancel();
            });

            $("#requestBtn").keydown(function (e) {
                if (e.keyCode == 13) {
                    Edit_Customize_Task_Modify_NS.query();
                }
            });

            $("#customizeBtn").keydown(function (e) {
                if (e.keyCode == 13) {
                    Edit_Customize_Task_Modify_NS.customize();
                }
            });

            $("#debugBtn").click(function() {
                Tools_NS.showWarningDialog(Edit_Customize_Task_Modify_NS.log);
            });


            <c:forEach var="province" items="${provinces}">
            <c:if test="${province.dmz2 != null}">
            ERI_NS.PROVINCE[${province.dmz2}] = {
                dmz: '${province.dmz}',
                dmsm1: '${province.dmsm1}',
                dmsm2: '${province.dmsm2}'
            };
            </c:if>
            </c:forEach>
            <c:forEach var="licenceType" items="${licenceTypes}">
            <c:if test="${licenceType.dmz2 != null}">
            if ("${licenceType.dmz2}".length > 0 && ERI_NS.LICENCE_TYPE[${licenceType.dmz2}]) {
                ERI_NS.LICENCE_TYPE[${licenceType.dmz2}] = {
                    dmz: ERI_NS.LICENCE_TYPE[${licenceType.dmz2}]["dmz"] + "/" + '${licenceType.dmz}',
                    dmsm1: ERI_NS.LICENCE_TYPE[${licenceType.dmz2}]["dmsm1"] + "/" + '${licenceType.dmsm1}'
                };
            } else {
                ERI_NS.LICENCE_TYPE[${licenceType.dmz2}] = {dmz: '${licenceType.dmz}', dmsm1: '${licenceType.dmsm1}'};
            }
            </c:if>
            </c:forEach>
            <c:forEach var="vehicleColor" items="${carColors}">
            <c:if test="${vehicleColor.dmz2 != null}">
            ERI_NS.VEHICLE_COLOR[${vehicleColor.dmz2}] = {dmz: '${vehicleColor.dmz}', dmsm1: '${vehicleColor.dmsm1}'};
            </c:if>
            </c:forEach>
            <c:forEach var="vehicleType" items="${carTypes}">
            <c:if test="${vehicleType.dmz2 != null}">
            if ("${vehicleType.dmz2}".length > 0 && ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}]) {
                ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}] = {
                    dmz: ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}]["dmz"] + "/" + '${vehicleType.dmz}',
                    dmsm1: ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}]["dmsm1"] + "/" + '${vehicleType.dmsm1}'
                };
            } else {
                ERI_NS.VEHICLE_TYPE[${vehicleType.dmz2}] = {dmz: '${vehicleType.dmz}', dmsm1: '${vehicleType.dmsm1}'};
            }
            </c:if>
            </c:forEach>
            <c:forEach var="usingPurpose" items="${usingPurposes}">
            <c:if test="${usingPurpose.dmz2 != null}">
            if ("${usingPurpose.dmz2}".length > 0 && ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}]) {
                ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}] = {
                    dmz: ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}]["dmz"] + "/" + '${usingPurpose.dmz}',
                    dmsm1: ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}]["dmsm1"] + "/" + '${usingPurpose.dmsm1}'
                };
            } else {
                ERI_NS.USING_PURPOSE[${usingPurpose.dmz2}] = {
                    dmz: '${usingPurpose.dmz}',
                    dmsm1: '${usingPurpose.dmsm1}'
                };
            }
            </c:if>
            </c:forEach>
        },

        checkDeviceStatus: function () {
            Tools_NS.showLoading("正在配置签注设备，请稍等");
            Edit_Customize_Task_Modify_NS.sendCommand("1004:${cert}",
                    function (response) {
                        Edit_Customize_Task_Modify_NS.SAMCert = response["SAMCert"];
                        Edit_Customize_Task_Modify_NS.connected = response["ConnectStatus"] == "00";
                        if (Edit_Customize_Task_Modify_NS.connected) {
                            Tools_NS.closeLoading();
                            setTimeout(function () {
                                $("#lsh").focus();
                            }, 100);
                        } else {
                            Tools_NS.showWarningDialog("配置设备异常，请确认设备串口是否已连接", function () {
                                Edit_Customize_Task_Modify_NS.checkDeviceStatus();
                            });
                        }
                    }, function (error) {
                        Tools_NS.showWarningDialog("配置设备异常(" + error + ")", function () {
                            Edit_Customize_Task_Modify_NS.checkDeviceStatus();
                        });
                    });
        },

        fetchDeviceVersion: function () {
            Tools_NS.showLoading("正在连接签注设备，请稍等");
            Edit_Customize_Task_Modify_NS.sendCommand("1005", function (response) {
                if (response["MainVersion"] < $("#latestVersion").val()) {
                    window.location.href = '/rmweb/system.frm?method=show-plugin';
                } else {
                    Edit_Customize_Task_Modify_NS.checkDeviceStatus();
                }
            }, function (error) {
                if (error == "NO_RESPONSE") {
                    Tools_NS.showWarningDialog("未启动签注程序，请启动后点击“确定”重试", function () {
                        Edit_Customize_Task_Modify_NS.fetchDeviceVersion();
                    });
                }
            });
        },

        submit: function () {
            $.post("/rmweb/customize-task.frm?method=modify", {
                lsh: $("#lsh").val(),
                hphm: encodeURI(Edit_Customize_Task_Modify_NS.hphm),
                hpzl: Edit_Customize_Task_Modify_NS.hpzl,
                fzjg: encodeURI(Edit_Customize_Task_Modify_NS.fzjg),
                tid: Edit_Customize_Task_Modify_NS.tid,
                sqr: encodeURI($("#sqr").val()),
                lxdh: $("#lxdh").val(),
                bz: encodeURI($("#bz").val()),
                _t: Date.parse(new Date())
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    Tools_NS.showConfirmDialog({
                        id: 'Success',
                        title: '创建成功 ',
                        message: '变更业务需要审核，请待业务审核通过后再进行签注',
                        width: 400,
                        cancellable: false,
                        onConfirm: function () {
                            window.close();
                            window.opener.query_cmd();
                        }
                    })
                } else {
                    Tools_NS.showWarningDialog("创建失败:" + data["resultMsg"]);
                }
            }, "json")
        },

        cancel: function () {
            $.post("/rmweb/customize-task.frm?method=cancel", {xh: $("#xh").val()}, function (data) {
                if (data && data["resultId"] == "00") {
                    Tools_NS.showConfirmDialog({
                        id: 'Success',
                        title: '取消成功 ',
                        message: '该业务已取消',
                        width: 400,
                        cancellable: false,
                        onConfirm: function () {
                            window.close();
                            window.opener.query_cmd();
                        }
                    })
                } else {
                    Tools_NS.showWarningDialog("取消失败:" + data["resultMsg"]);
                }
            }, "json");
        },

        customize: function () {
            Edit_Customize_Task_Modify_NS.log = "M:s1:" + new Date().getTime() + "|";
            if (!Edit_Customize_Task_Modify_NS.SAMCert) {
                alert("请确保读写器已正确连接");
                return;
            }

            Tools_NS.showLoading("读取电子标识中，请稍等...");
            Edit_Customize_Task_Modify_NS.tid = null;
            Edit_Customize_Task_Modify_NS.data = null;
            Edit_Customize_Task_Modify_NS.mode = 3;
            Edit_Customize_Task_Modify_NS.checkCard(1);

        },

        query: function () {
            if ($("#kh").val().length != 12) {
                Tools_NS.showWarningDialog("请输入正确的卡号");
                return;
            }
            if ($("#lsh").val().length <= 0 && ($("#hphm").val().length <= 0 || $("#hpzl").val().length <= 0 || $("#fzjg").val().length <= 0)) {
                Tools_NS.showWarningDialog("请输入流水号或者输入号牌号码和号牌种类");
                return;
            }
            if ($("#lsh").val().length > 0 && $("#lsh").val().length != 13) {
                Tools_NS.showWarningDialog("请输入正确的流水号，流水号为13位字符");
                return;
            }
            Edit_Customize_Task_Modify_NS.kh = $("#kh").val();
            Edit_Customize_Task_Modify_NS.hphm = $("#fzjg").val() + $("#hphm").val();
            Edit_Customize_Task_Modify_NS.hpzl = $("#hpzl").val();
            Edit_Customize_Task_Modify_NS.fzjg = $("#fzjg").val();
            Edit_Customize_Task_Modify_NS.lsh = $("#lsh").val();
            $.get("/rmweb/be/customize-task.rfid?method=fetch-modify-info", {
                kh: Edit_Customize_Task_Modify_NS.kh,
                lsh: Edit_Customize_Task_Modify_NS.lsh,
                hphm: encodeURI(Edit_Customize_Task_Modify_NS.hphm),
                hpzl: Edit_Customize_Task_Modify_NS.hpzl,
                fzjg: encodeURI(Edit_Customize_Task_Modify_NS.fzjg),
                _t: Date.parse(new Date())
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    $("#wHphm").text(data["old"]["hphm"]);
                    $("#wSyr").text(data["old"]["syr"]);
                    $("#wHpzl").text(data["old"]["hpzl"]);
                    $("#wZzl").text(data["old"]["zzl"]);
                    $("#wCsys").text(data["old"]["csys"]);
                    $("#wCllx").text(data["old"]["cllx"]);
                    $("#wSyxz").text(data["old"]["syxz"]);
                    $("#wGl").text(data["old"]["gl"]);
                    $("#wPl").text(data["old"]["pl"]);
                    $("#wCcrq").text(data["old"]["ccrq"]);
                    $("#wQzbfqz").text(data["old"]["qzbfqz"]);
                    $("#wYxqz").text(data["old"]["yxqz"]);
                    $("#wHdzk").text(data["old"]["hdzk"]);
                    $("#wBxzzrq").text(data["old"]["bxzzrq"]);
                    $("#rKh").text(data["eri"]["kh"]);
                    $("#rTid").text(data["eri"]["tid"]);
                    $("#rHphm").text(data["new"]["hphm"]);
                    $("#rSyr").text(data["new"]["syr"]);
                    $("#rHpzl").text(data["new"]["hpzl"]);
                    $("#rZzl").text(data["new"]["zzl"]);
                    $("#rCsys").text(data["new"]["csys"]);
                    $("#rCllx").text(data["new"]["cllx"]);
                    $("#rSyxz").text(data["new"]["syxz"]);
                    $("#rGl").text(data["new"]["gl"]);
                    $("#rPl").text(data["new"]["pl"]);
                    $("#rCcrq").text(data["new"]["ccrq"]);
                    $("#rQzbfqz").text(data["new"]["qzbfqz"]);
                    $("#rYxqz").text(data["new"]["yxqz"]);
                    $("#rHdzk").text(data["new"]["hdzk"]);
                    $("#rBxzzrq").text(data["new"]["bxzzrq"]);
                    Edit_Customize_Task_Modify_NS.tid = data["eri"]["tid"];
                    $("#submitBtn").enable().focus();
                } else {
                    Tools_NS.showWarningDialog("查询失败" + data ? (': ' +data["resultMsg"]) : '');
                }
            }, "json");

        },

        checkCardStatus: function () {
            if (!Edit_Customize_Task_Modify_NS.tid) {
                Tools_NS.showConfirmDialog({
                    id: 'queryDialog',
                    title: '提示 ',
                    message: '未盘点到标识，请正确放置标识后重试',
                    width: 400,
                    onCancle: 'void()',
                    onConfirm: function () {
                        Edit_Customize_Task_Modify_NS.customize();
                        $.unblockUI();
                    }
                });
                return;
            }
            $.get("/rmweb/be/customize-task.rfid?method=check-card-status",
            {
                tid: Edit_Customize_Task_Modify_NS.tid,
                lsh: $("#lsh").val(),
                hphm: encodeURI($("#fzjg").val() + $("#hphm").val()),
                hpzl: $("#hpzl").val(),
                fzjg: encodeURI($("#fzjg").val()),
                ywlx: 4,
                xh: $("#xh").val(),
                _t: Date.parse(new Date())
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    $("#rHphm").text(data["info"]["hphm"]);
                    $("#rSyr").text(data["info"]["syr"]);
                    $("#rHpzl").text(data["info"]["hpzl"]);
                    $("#rZzl").text(data["info"]["zzl"]);
                    $("#rCsys").text(data["info"]["csys"]);
                    $("#rCllx").text(data["info"]["cllx"]);
                    $("#rSyxz").text(data["info"]["syxz"]);
                    $("#rGl").text(data["info"]["gl"]);
                    $("#rPl").text(data["info"]["pl"]);
                    $("#rCcrq").text(data["info"]["ccrq"]);
                    $("#rQzbfqz").text(data["info"]["qzbfqz"]);
                    $("#rYxqz").text(data["info"]["yxqz"]);
                    $("#rHdzk").text(data["info"]["hdzk"]);
                    $("#rBxzzrq").text(data["info"]["bxzzrq"]);
                    $("#submitBtn").enable().focus();
                    Edit_Customize_Task_Modify_NS.readCard();
                } else if (data && data["resultId"] == "99") {
                    if (data["errorCode"] == "04") {
                        Tools_NS.showConfirmDialog({
                            id: 'resetTaskDialog',
                            title: '提示 ',
                            message: '存在正在进行中的任务，是否重置任务并继续',
                            width: 400,
                            onCancel: 'void()',
                            onConfirm: function () {
                                Edit_Customize_Task_Modify_NS.resetTask();
                                $.unblockUI();
                            }
                        });
                    } else {
                        Tools_NS.showWarningDialog(data["resultMsg"], function () {
                            Edit_Customize_Task_Modify_NS.clearTask();
                        });
                    }
                } else if (data && data["resultId"] == "98") {
                    Tools_NS.showWarningDialog(data["resultMsg"], function () {
                        reload();
                    });
                } else {
                    Tools_NS.showWarningDialog(data["resultMsg"], function () {
                        Edit_Customize_Task_Modify_NS.clearTask();
                    });
                }
            }, "json");
        },

        recheck: function() {
            if (!Edit_Customize_Task_Modify_NS.tid) {
                Tools_NS.showConfirmDialog({
                    id: 'queryDialog',
                    title: '提示 ',
                    message: '未盘点到标识，请正确放置标识后重试',
                    width: 400,
                    onCancle: 'void()',
                    onConfirm: function () {
                        Edit_Customize_Task_Modify_NS.customize();
                        $.unblockUI();
                    }
                });
                return;
            } else if (Edit_Customize_Task_Modify_NS.tid != "${eri.tid}") {
                Tools_NS.showConfirmDialog({
                    id: 'queryDialog',
                    title: '提示 ',
                    message: '当前使用的电子标识（' + Edit_Customize_Task_Modify_NS.kh + '）与申请进行更改业务的电子标识（${eri.kh}）不一致<br/>' +
                    '请更新正确的电子标识后重试',
                    width: 400,
                    onCancel: 'void()',
                    onConfirm: function () {
                    Edit_Customize_Task_Modify_NS.customize();
                        $.unblockUI();
                    }
                });
                return;
            }
            Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s4:" + new Date().getTime() + "|";
            $.get("/rmweb/be/customize-task.rfid?method=check-card-status",
                    {
                        tid: Edit_Customize_Task_Modify_NS.tid,
                        lsh: $("#lsh").val(),
                        hphm: encodeURI($("#fzjg").val() + $("#hphm").val()),
                        hpzl: $("#hpzl").val(),
                        fzjg: encodeURI($("#fzjg").val()),
                        ywlx: 4,
                        xh: $("#xh").val(),
                        _t: Date.parse(new Date())
                    }, function (data) {
                        Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s5:" + new Date().getTime() + "|";
                        if (data && data["resultId"] == "00") {
                            Edit_Customize_Task_Modify_NS.fetchData();
                        } else if (data && data["resultId"] == "99") {
                            if (data["errorCode"] == "04") {
                                Tools_NS.showConfirmDialog({
                                    id: 'resetTaskDialog',
                                    title: '提示 ',
                                    message: '存在正在进行中的任务，是否重置任务并继续',
                                    width: 400,
                                    onCancel: 'void()',
                                    onConfirm: function () {
                                        Edit_Customize_Task_Modify_NS.resetTask();
                                        $.unblockUI();
                                    }
                                });
                            } else {
                                Tools_NS.showWarningDialog(data["resultMsg"], function () {
                                    Edit_Customize_Task_Modify_NS.clearTask();
                                });
                            }
                        } else if (data && data["resultId"] == "98") {
                            Tools_NS.showWarningDialog(data["resultMsg"], function () {
                                reload();
                            });
                        } else {
                            Tools_NS.showWarningDialog(data["resultMsg"], function () {
                                Edit_Customize_Task_Modify_NS.clearTask();
                            });
                        }
                    }, "json");
        },

        fetchData: function () {
            Tools_NS.showLoading("正在请求数据，请稍等...");
            $.get("/rmweb/be/customize-task.rfid?method=fetch-data",
                    {
                        xh: $("#xh").val(),
                        tid: Edit_Customize_Task_Modify_NS.tid,
                        cert: Edit_Customize_Task_Modify_NS.SAMCert,
                        lsh:$("#lsh").val(),
                        hphm: encodeURI($("#fzjg").val() + $("#hphm").val()),
                        hpzl: $("#hpzl").val(),
                        fzjg: encodeURI($("#fzjg").val()),
                        ywlx: 4,
                        _t: Date.parse(new Date())
                    }, function (data) {
                        Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s6:" + new Date().getTime() + "|";
                        if (data && data["resultId"] == "00") {
                            $("#rLsh").val(data["CustomizeTask"]["lsh"]);
                            Edit_Customize_Task_Modify_NS.data = data["info"]["frame"] + data["info"]["sign"];
                            Edit_Customize_Task_Modify_NS.writeCard();
                        } else if (data && data["resultId"] == "99") {
                            if (data["errorCode"] == "04") {
                                Tools_NS.showConfirmDialog({
                                    id: 'resetTaskDialog',
                                    title: '提示 ',
                                    message: '存在正在进行中的任务，是否重置任务并继续',
                                    width: 400,
                                    onCancel: 'void()',
                                    onConfirm: function () {
                                        Edit_Customize_Task_Modify_NS.resetTask();
                                        $.unblockUI();
                                    }
                                });
                            } else if (data["errorCode"] == "02") {
                                Tools_NS.showWarningDialog(data["resultMsg"], function () {
                                });
                            } else {
                                Tools_NS.showWarningDialog(data["resultMsg"], function () {
                                    $("#hphm").val("");
                                    $("#hpzl").val("");
                                    $("#lsh").val("");
                                    Edit_Customize_Task_Modify_NS.unfreeze();
                                });
                            }
                        } else if (data && data["resultId"] == "98") {
                            Tools_NS.showWarningDialog(data["resultMsg"], function () {
                                reload();
                            });
                        } else {
                            Tools_NS.showWarningDialog(data["resultMsg"], function () {
                                $("#hphm").val("");
                                $("#hpzl").val("");
                                $("#lsh").val("");
                            });
                        }
                    }, "json");
        },

        readCard: function () {
            Edit_Customize_Task_Modify_NS.sendCommand("1003", function (response) {
                if (response["HbReadSpecResult"] == "00") {
                    Edit_Customize_Task_Modify_NS.changeCardStatus(1, "读取成功");
                    var cardData = ERI_NS.parse(response["HbReadSpecResultData"]);
                    if (cardData["zt"] == 0) {
                        Tools_NS.showConfirmDialog({
                            id: 'cardInfoError',
                            title: '电子标识签注信息有误 ',
                            message: '当前电子标识未签注车辆信息<br/>请更换电子标识后点击【确定】重试或<br/>点击【取消】以更换业务进行办理',
                            width: 400,
                            cancellable: true,
                            onCancel: function () {
                                window.close();
                            },
                            onConfirm: function () {
                                Tools_NS.showLoading("读取电子标识中，请稍等...");
                                $(".card-info").text("");
                                Edit_Customize_Task_Modify_NS.tid = null;
                                Edit_Customize_Task_Modify_NS.data = null;
                                Edit_Customize_Task_Modify_NS.mode = 1;
                                Edit_Customize_Task_Modify_NS.checkCard(1);
                                $.unblockUI();
                            }
                        });
                    } else {
                        Edit_Customize_Task_Modify_NS.setCardData(cardData);
                        $.unblockUI();
                    }
                } else {
                    Edit_Customize_Task_Modify_NS.changeCardStatus(2, "读取失败");
                }
            }, function (error) {
                Edit_Customize_Task_Modify_NS.changeCardStatus(2, "读取异常(" + error + ")");
            });
        },

        setCardData: function (cardData) {
            Edit_Customize_Task_Modify_NS.clearCardData();
            $("#wSyxz").show().css("color", "green").text("(" + cardData["syxz"]["dmsm1"] + ")");
            $("#wHpzl").show().css("color", "green").text("(" + cardData["hpzl"]["dmsm1"] + ")");
            $("#wHphm").show().css("color", "green").text("(" + cardData["hphm"] + ")");
            $("#wCllx").show().css("color", "green").text("(" + cardData["cllx"]["dmsm1"] + ")");
            $("#wZzl").show().css("color", "green").text(cardData["zzl"] ? "(" + cardData["zzl"] + ")" : "");
            $("#wHdzk").show().css("color", "green").text(cardData["hdzk"] ? "(" + cardData["hdzk"] + ")" : "");
            $("#wPl").show().css("color", "green").text(cardData["pl"] ? "(" + cardData["pl"] + ")" : "");
            $("#wGl").show().css("color", "green").text(cardData["gl"] ? "(" + cardData["gl"] + ")" : "");
            $("#wCcrq").show().css("color", "green").text("(" + cardData["ccrq"] + ")");
            $("#wYxqz").show().css("color", "green").text("(" + cardData["yxqz"] + ")");
            $("#wQzbfqz").show().css("color", "green").text("(" + cardData["qzbfqz"] + ")");
            $("#wCsys").show().css("color", "green").text("(" + cardData["csys"]["dmsm1"] + ")");
        },

        clearCardData: function () {
            $("#wSyxz").empty().hide();
            $("#wHpzl").empty().hide();
            $("#wHphm").empty().hide();
            $("#wCllx").empty().hide();
            $("#wZzl").empty().hide();
            $("#wHdzk").empty().hide();
            $("#wPl").empty().hide();
            $("#wGl").empty().hide();
            $("#wCcrq").empty().hide();
            $("#wYxqz").empty().hide();
            $("#wQzbfqz").empty().hide();
            $("#wCsys").empty().hide();
        },

        writeCard: function () {
            if (Edit_Customize_Task_Modify_NS.data) {
                Tools_NS.showLoading("正在进行签注，请稍等...");
                $("#debugInfo").text("正在签注...");
                Edit_Customize_Task_Modify_NS.sendCommand("1002:" + Edit_Customize_Task_Modify_NS.data, function (response) {
                    Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s7:" + new Date().getTime() + "|";
                    var result = 0;
                    var reason = "";
                    if (response["HbPrivateWriteSpecResult"] == "00") {
                        if (response["HbReadSpecResult"] == "00") {
                            var checkData = ERI_NS.parse(response["HbReadSpecResultData"]);
                            if (Edit_Customize_Task_Modify_NS.checkContent(checkData)) {
                                result = 1;
                            } else {
                                reason = $("#debugInfo").text();
                            }
                        } else {
                            $("#debugInfo").text("读取数据失败");
                            reason = "读取数据失败";
                        }
                    } else if (response["HbPrivateWriteSpecResult"] == "01") {
                        $("#debugInfo").text("签注失败，配置证书链失败");
                        reason = "签注失败，配置证书链失败";
                    } else if (response["HbPrivateWriteSpecResult"] == "02") {
                        $("#debugInfo").text("签注失败（" + response["HbPrivateWriteSpecResultDescription"] + "）");
                        reason = "签注失败（" + response["HbPrivateWriteSpecResultDescription"] + "）";
                    } else if (response["HbPrivateWriteSpecResult"] == "03") {
                        $("#debugInfo").text("签注失败，数据TID与卡不一致");
                        reason = "签注失败，数据TID与卡不一致";
                    } else if (response["Tid"] != Edit_Customize_Task_Modify_NS.tid) {
                        $("#debugInfo").text("请求的数据与当前要签注的卡不匹配");
                        reason = "签注失败，数据TID与卡不匹配";
                    }
                    Edit_Customize_Task_Modify_NS.uploadWriteResult(result, reason);
                }, function (error) {
                    Tools_NS.showWarningDialog(error, function () {
                    });
                });
            } else {
                Tools_NS.showWarningDialog("未获取签注数据，请重新请求数据", function () {
                    $("#customizeBtn").attr("disabled", "disabled");
                });
            }
        },

        checkContent : function(cardData) {
            if (cardData["kh"] != $("#rKh").text().trim()) {
                $("#debugInfo").text("内容校验错误，卡号不正确");
                return false;
            }
            if (cardData["hpzl"] && $("#rHpzl").text() &&
                    cardData["hpzl"]["dmsm1"].toUpperCase().indexOf($("#rHpzl").text().trim().toUpperCase()) < 0) {
                $("#debugInfo").text("内容校验错误，号牌种类不正确");
                return false;
            }
            if (cardData["hphm"] && $("#rHphm").text() &&
                    cardData["hphm"].toUpperCase() != $("#rHphm").text().trim().toUpperCase()) {
                $("#debugInfo").text("内容校验错误，号牌号码不正确");
                return false;
            }
            if (cardData["syxz"] && $("#rSyxz").text() &&
                    cardData["syxz"]["dmsm1"].toUpperCase().indexOf($("#rSyxz").text().trim().toUpperCase()) < 0) {
                $("#debugInfo").text("内容校验错误，使用性质不正确");
                return false;
            }
            if (cardData["cllx"] && $("#rCllx").text() &&
                    cardData["cllx"]["dmsm1"].toUpperCase().indexOf($("#rCllx").text().trim().toUpperCase()) < 0) {
                $("#debugInfo").text("内容校验错误，车辆类型不正确");
                return false;
            }
            if (cardData["csys"] && $("#rCsys").text() &&
                    (cardData["csys"]["dmsm1"] + "色") != $("#rCsys").text().trim()) {
                $("#debugInfo").text("内容校验错误，车身颜色不正确");
                return false;
            }
            if (cardData["pl"] && $("#rPl").text() &&
                    parseFloat(cardData["pl"].trim().replace("L", "")) != parseFloat($("#rPl").text().trim().replace("L", ""))) {
                $("#debugInfo").text("内容校验错误，排量不正确");
                return false;
            }
            if (cardData["zzl"] && $("#rZzl").text() &&
                    parseFloat(cardData["zzl"].trim().replace("吨", "")) != parseFloat($("#rZzl").text().trim().replace("吨", ""))) {
                $("#debugInfo").text("内容校验错误，总质量不正确");
                return false;
            }
            if (cardData["hdzk"] && $("#rHdzk").text() &&
                    cardData["hdzk"] != $("#rHdzk").text().trim()) {
                $("#debugInfo").text("内容校验错误，核定载客不正确");
                return false;
            }
            if (cardData["gl"] && $("#rGl").text() &&
                    cardData["gl"] != $("#rGl").text().trim()) {
                if (parseInt($("#rGl").text().trim()) < 255 || parseInt(cardData["gl"]) != 255) {
                    $("#debugInfo").text("内容校验错误，功率不正确");
                    return false;
                }
            }
            if (cardData["yxqz"] && $("#rYxqz").text() &&
                    $("#rYxqz").text().trim().indexOf(cardData["yxqz"]) != 0) {
                $("#debugInfo").text("内容校验错误，检验有效期不正确");
                return false;
            }
            if (cardData["qzbfqz"] && $("#rQzbfqz").text() &&
                    $("#rQzbfqz").text().trim().indexOf(cardData["qzbfqz"]) != 0) {
                if ($("#rYxqz").text()) {
                    var yearOfYxqz = new Date($("#rYxqz").text().replace(/-/g, "/")).getFullYear();
                    var yearOfQzbfqz = new Date($("#rQzbfqz").text().replace(/-/g, "/")).getFullYear();
                    if (yearOfQzbfqz - yearOfYxqz < 31) {
                        $("#debugInfo").text("内容校验错误，强制报废期不正确");
                        return false;
                    }						} else {
                    $("#debugInfo").text("内容校验错误，强制报废期不正确");
                    return false;
                }
            }
            if (cardData["ccrq"] && $("#rCcrq").text() &&
                    $("#rCcrq").text().trim().indexOf(cardData["ccrq"]) != 0) {
                $("#debugInfo").text("内容校验错误，出厂日期不正确");
                return false;
            }
            return true;
        },

        uploadWriteResult: function (result, reason) {
            $("#debugInfo").text("上传签注结果...");
            Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s8:" + new Date().getTime() + "|";
            $.get("/rmweb/be/eri.rfid?method=customize-result",
                    {xh: $("#xh").val(), result: result, sbyy: encodeURI(reason), _t: Date.parse(new Date())},
                    function (data) {
                        Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s9:" + new Date().getTime() + "|";
                        if (data && data["resultId"] == "00") {
                            if (result == 1) {
                                $("#debugInfo").text("签注成功");
                                $("#customizeBtn").attr("disabled", "disabled");
                                Tools_NS.showConfirmDialog({
                                    id: 'successDialog',
                                    title: '成功 ',
                                    message: '<img src="/rmweb/frmimage/right.gif"/><span style="font-size:18px;font-weight:bold;margin-left:20px">签注成功</span>',
                                    width: 400,
                                    cancellable: false,
                                    onCancel: 'void(0)',
                                    onConfirm: function () {
                                        window.opener.query_cmd();
                                        window.close();
                                    }
                                });
                            } else {
                                $("#debugInfo").text(reason);
                                Tools_NS.showWarningDialog(reason);
                            }
                        } else {
                            Tools_NS.showConfirmDialog({
                                id: 'uploadResultDialog',
                                title: '提示',
                                message: '上传签注结果失败，是否重试',
                                width: 400,
                                onCancel: 'void()',
                                onConfirm: function () {
                                    $.unblockUI();
                                    Edit_Customize_Task_Modify_NS.uploadWriteResult(result, reason);
                                }
                            });
                        }
                    }, "json");
        },

        resetTask: function () {
            $.get("/rmweb/be/eri.rfid?method=customize-result",
                    {
                        xh: $("#xh").val(), result: "0", reason: "重置任务", tid: $("#tid").text(),
                        _t: Date.parse(new Date())
                    },
                    function (data) {
                        if (data && data["resultId"] == "00") {
                            Edit_Customize_Task_Modify_NS.customize();
                        }
                    }, "json");
        },

        //model: 0-仅盘点，1-盘点+读上半区
        checkCard: function (mode) {
            $("#rTid").text("");
            $("#rKh").text("");
            Edit_Customize_Task_Modify_NS.changeCardStatus(0, "盘点中...");
            Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s2:" + new Date().getTime() + "|";
            Edit_Customize_Task_Modify_NS.sendCommand("1001:" + (mode ? mode : "0"), function (response) {
                Edit_Customize_Task_Modify_NS.log = Edit_Customize_Task_Modify_NS.log + "s3:" + new Date().getTime() + "|";
                if (response["SelectSpecResult"] == "00") {
                    Edit_Customize_Task_Modify_NS.tid = response["Tid"];
                    $("#rTid").text(response["Tid"]);
                    if (response["SelectSpecResultData"]) {
                        var data = ERI_NS.parse(response["SelectSpecResultData"]);
                        $("#rKh").text(data["kh"]);
                    }
                    Edit_Customize_Task_Modify_NS.changeCardStatus(1, "盘点成功");
                    Edit_Customize_Task_Modify_NS.doAfterChecked();
                } else if (response["SelectSpecResult"] == "02") {
                    $("#rTid").text("");
                    $("#rKh").text("");
                    Edit_Customize_Task_Modify_NS.changeCardStatus(2, "盘点失败");
                    Tools_NS.showConfirmDialog({
                        id: 'noCardDialog',
                        title: '盘点失败',
                        message: '盘点失败，请确认已正确放置标识后，点“确定”重试。',
                        width: 400,
                        onCancel: 'void()',
                        onConfirm: function () {
                            Edit_Customize_Task_Modify_NS.customize();
                        }
                    });
                } else if (response["SelectSpecResult"] == "04") {
                    $("#rTid").text("");
                    $("#rKh").text("");
                    Edit_Customize_Task_Modify_NS.changeCardStatus(2, "盘点成功，数据获取失败");
                    Tools_NS.showConfirmDialog({
                        id: 'noCardDialog',
                        title: '盘点失败',
                        message: '盘点成功，数据获取失败，点击“确定”重试。',
                        width: 400,
                        onCancel: 'void()',
                        onConfirm: function () {
                            Edit_Customize_Task_Modify_NS.query();
                        }
                    });
                } else {
                    $("#rTid").text("");
                    $("#rKh").text("");
                    Edit_Customize_Task_Modify_NS.changeCardStatus(2, "盘点异常");
                    Edit_Customize_Task_Modify_NS.changeCardStatus(2, "盘点成功，数据获取失败");
                    Tools_NS.showConfirmDialog({
                        id: 'noCardDialog',
                        title: '盘点异常',
                        message: '盘点异常，点击“确定”重试。',
                        width: 400,
                        onCancel: 'void()',
                        onConfirm: function () {
                            Edit_Customize_Task_Modify_NS.query();
                        }
                    });
                }
            }, function (error) {
                if (error == "NO_RESPONSE") {
                    Edit_Customize_Task_Modify_NS.changeCardStatus(2, "无响应,请确认是否已正确放置标识");
                    Tools_NS.showConfirmDialog({
                        id: 'noCardDialog',
                        title: '盘点失败',
                        message: '盘点失败，请确认已正确放置标识后，点“确定”重试。',
                        width: 400,
                        onCancel: 'void()',
                        onConfirm: function () {
                            Edit_Customize_Task_Modify_NS.customize();
                        }
                    });
                } else {
                    Edit_Customize_Task_Modify_NS.errorHandleAfterChecked(error);
                }
            });
        },

        /**
         *
         * @param text
         * @param status 0-进行中，1-成功，2-失败
         */
        changeCardStatus: function (status, text) {
            $("#debugInfo").text(text);
        },

        doAfterChecked: function () {
            if (Edit_Customize_Task_Modify_NS.mode == 1) {
                //读卡
                Edit_Customize_Task_Modify_NS.readCard();
            } else if (Edit_Customize_Task_Modify_NS.mode == 2) {
                //写卡
                Edit_Customize_Task_Modify_NS.checkCardStatus();
            } else if (Edit_Customize_Task_Modify_NS.mode == 3) {
                Edit_Customize_Task_Modify_NS.recheck();
            }
        },

        errorHandleAfterChecked: function (checkResponse) {
            $("#debugInfo").text(checkResponse);
        },

        unfreeze: function () {
            $("#requestBtn").enable();
            $("#customizeBtn").enable();
            $("#closeBtn").enable();
        },

        clearTask: function () {
            $(".card-info").text("");
            $(".base-info").text("");
            $("#rKh").text("");
            $("#rTid").text("");
            $("#debugInfo").text("");
            if ($("#ywlx").val() == 5) {
                var lsh = $("#rLsh").val();
                $("#lsh").val(lsh.substr(0, 7) + ERI_NS.pad(parseInt(lsh.substr(7), 10) + 1, 6));
            } else {
                $("#lsh").val("");
            }
            $("#hphm").val("");
        }

    };
</script>
</body>
</html>
