<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>年检</title>
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
        年检
    </div>
    <div id="queryPanel" style="margin-top:10px;">
        <table  border="0" cellspacing="1" cellpadding="0" class="query">
            <col width="8%"/>
            <col width="17%"/>
            <col width="8%"/>
            <col width="17%"/>
            <col width="8%"/>
            <col width="17%"/>
            <col width="8%"/>
            <col width="17%"/>
            <c:if test="${task ==null}">
                <tr>
                    <td class="head">
                        业务类型
                    </td>
                    <td class="body">
                        <input type="hidden" id="ywlx" name="ywlx" value="${ywlx}"/>
                        <c:forEach var="type" items="${custTypes}">
                            <c:if test="${type.type == ywlx}">
                                <c:out value="${type.desc}" />
                            </c:if>
                        </c:forEach>
                    </td>
                    <td class="head">
                        流水号
                    </td>
                    <td class="body">
                        <div class="input-wrap">
                            <input type="text" id="lsh" name="lsh" value="${task.lsh}" tabindex="1"/>
                        </div>
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
                                    <c:out value="${licenceType.dmsm1}" />
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
                            <input type="text" name="hphm" id="hphm"  maxlength="15" tabindex="3">
                        </div>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td class="command" colspan="8">
                    <c:if test="${task == null}">
                        <input type="button" name="requestBtn" id="requestBtn" value="查询" class="button_default" tabindex="4"/>
                        <input type="button" id="submitBtn" name="submitBtn" value="提交" class="button_default"/>
                    </c:if>
                    <c:if test="${task != null && (task.zt == 1 || task.zt == 0)}">
                        <input type="button" name="cancelBtn" id="cancelBtn" value="取消" class="button_default"
                               tabindex="7"/>
                    </c:if>
                    <input type="button" id="closeBtn" name="closeBtn" onclick="javascript:window.close();" value="关闭" class="button_default">
                </td>
            </tr>
        </table>
    </div>
    <div style="margin-top:20px;">
        <input type="hidden" name="bdfzjg" id="bdfzjg" value="${bdfzjg}"/>
        <input type="hidden" name="id" id="id"
               value="<c:out value='${bean.id}'/>">
        <input type="hidden" name="clxxid" id="clxxid"
               value="<c:out value='${bean.clxxid}'/>">
        <input type="hidden" name="syr" id="syr"
               value="<c:out value='${bean.syr}'/>">
        <input type="hidden" name="msg" id="msg" value='${msg}'>
        <input type="hidden" name="xh" id="xh" value="${task.xh}"/>
        <input type="hidden" name="rLsh" id="rLsh"/>
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
							<span id="kh">
								<c:if test="${eri != null}">
                                    <c:out value="${eri.kh}"/>
                                </c:if>
							</span>
                </td>
                <td class="head">TID</td>
                <td class="body" colspan="3">
							<span id="tid">
								<c:if test="${eri != null}">
                                    <c:out value="${eri.tid}"/>
                                </c:if>
							</span>
                </td>
            </tr>
            <tr>
                <td class="head" rowspan="5">
                    车辆基本信息
                </td>
                <td class="head">
                    号牌种类
                </td>
                <td class="body">
							<span id="rHpzl" name="rHpzl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.hpzl}' />
                                </c:if>
							</span>
                    <span id="wHpzl" name="wHpzl" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    号牌号码
                </td>
                <td class="body">
							<span id="rHphm" name="rHphm" class="base-info" style="width:20%">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.hphm}' />
                                </c:if>
							</span>
                    <span id="wHphm" name="wHphm" style="display:none" class="card-info" style="width:80%"></span>
                </td>
                <td class="head">
                    使用性质
                </td>
                <td class="body">
							<span id="rSyxz" name="rSyxz" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.syxz}' />
                                </c:if>
							</span>
                    <span id="wSyxz" name="wSyxz" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    车辆类型
                </td>
                <td class="body">
							<span id="rCllx" name="rCllx" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.cllx}' />
                                </c:if>
							</span>
                    <span id="wCllx" name="wCllx" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    车身颜色
                </td>
                <td class="body">
							<span id="rCsys" name="rCsys" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.csys}' />
                                </c:if>
							</span>
                    <span id="wCsys" name="wCsys" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    排量
                </td>
                <td class="body">
							<span id="rPl" name="rPl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.pl}' />
                                </c:if>
							</span>
                    <span id="wPl" name="wPl" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    总质量
                </td>
                <td class="body">
							<span id="rZzl" name="rZzl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.zzl}' />
                                </c:if>
							</span>
                    <span id="wZzl" name="wZzl" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    核定载客
                </td>
                <td class="body">
							<span id="rHdzk" name="rHdzk" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.hdzk}' />
                                </c:if>
							</span>
                    <span id="wHdzk" name="wHdzk" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    功率
                </td>
                <td class="body">
							<span id="rGl" name="rGl" class="base-info">
								<c:if test="${bean != null}">
                                    <c:out value='${bean.gl}' />
                                </c:if>
							</span>
                    <span id="wGl" name="wGl" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    检验有效期止
                </td>
                <td class="body">
							<span id="rYxqz" name="rYxqz" class="base-info">
								<fmt:formatDate value='${bean.yxqz}' pattern="yyyy-MM-dd" />
							</span>
                    <span id="wYxqz" name="wYxqz" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    强制报废期止
                </td>
                <td class="body">
							<span id="rQzbfqz" name="rQzbfqz" class="base-info">
							<fmt:formatDate value='${bean.qzbfqz}' pattern="yyyy-MM-dd" />
								</span>
                    <span id="wQzbfqz" name="wQzbfqz" style="display:none" class="card-info"></span>
                </td>
                <td class="head">
                    出厂日期
                </td>
                <td class="body">
							<span id="rCcrq" name="rCcrq" class="base-info">
							<fmt:formatDate value='${bean.ccrq}' pattern="yyyy-MM-dd" />
								</span>
                    <span id="wCcrq" name="wCcrq" style="display:none" class="card-info"></span>
                </td>
            </tr>
            <tr>
                <td class="head">
                    所有人
                </td>
                <td class="body" colspan="5">
							<span id="rSyr" name="rSyr" class="base-info">
							<c:if test="${bean != null}">
                                <c:out value='${bean.syr}' />
                            </c:if>
							</span>
                </td>
            </tr>
            <c:if test="${empty task || task ==null}">
            <tr>
                <td class="head" rowspan="2">
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
                    <textarea id="bz" name="bz" style="width:100%" rows="4"></textarea>
                </td>
            </tr>
            </c:if>
            <c:if test="${!empty task && task !=null}">
                <tr>
                    <td class="head" rowspan="4">
                        业务信息
                    </td>
                    <td class="head">
                        申请人
                    </td>
                    <td class="body">
                        <c:out value='${task.sqr}' />
                    </td>
                    <td class="head">
                        联系电话
                    </td>
                    <td class="body">
                        <c:out value='${task.lxdh}' />
                    </td>
                    <td class="head">
                        申请日期
                    </td>
                    <td class="body">
                        <fmt:formatDate value='${task.sqrq}' pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        经办人
                    </td>
                    <td class="body">
                        <c:out value='${task.jbr}' />
                    </td>
                    <td class="head">
                        办结日期
                    </td>
                    <td class="body">
                        <fmt:formatDate value='${task.wcrq}' pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td class="head">
                        业务类型
                    </td>
                    <td class="body">
                        <c:forEach var="types" items="${custTypes}">
                            <c:if test="${types.type == task.rwlx}">
                                <c:out value="${types.desc}" />
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td class="head">
                        状态
                    </td>
                    <td class="body">
                        <c:forEach var="status" items="${custStatus}">
                            <c:if test="${status.status == task.zt}">
                                <c:out value="${status.desc}" />
                            </c:if>
                        </c:forEach>
                    </td>
                    <td class="head">备注</td>
                    <td class="body" colspan="3">
                        <c:out value="${task.bz}"/>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>
</div>
<c:if test="${task == null}">
    <div id="debugInfo" style="display:none"/>
</c:if>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script language="JavaScript" src="rmjs/eri.js" type="text/javascript"></script>
<script type="text/javascript" src="jQuery-webcam-master/jquery.webcam.js"></script>
<script language="JavaScript" type="text/javascript">
    <!--

    $(function() {
        Annual_Inspection_Edit_NS.init();
    });
    var Annual_Inspection_Edit_NS = {

        init : function() {

            if($("#msg").val()!=""){
                <c:if test="${bean==null}">
                alert($("#msg").val());
                return;
                </c:if>
                alert($("#msg").val());
            }
            <c:if test="${bean!=null}">
            $("#hpzl").val("<c:out value='${bean.hpzl}'/>");
            $("#hphm").val("${fn:substring(bean.hphm, 1, -1)}");
            $("#syr").val("<c:out value='${bean.syr}'/>");
            </c:if>

            $("#fzjg").dblclick(function() {
                if ($(".fzjg-select").length > 0) {
                    $(".fzjg-select").show();
                } else {
                    if (!Annual_Inspection_Edit_NS.bdfzjg) {
                        Annual_Inspection_Edit_NS.bdfzjg = $("#bdfzjg").val().split(",");
                    }
                    var fzjgSelector = "<div class='fzjg-select'><ul>";
                    var existsFzjg = {};
                    $(Annual_Inspection_Edit_NS.bdfzjg).each(function(index, element){
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

            $("#requestBtn").click(function() {
                Annual_Inspection_Edit_NS.query();
            });

            $("#submitBtn").click(function() {
                Annual_Inspection_Edit_NS.submit();
            });

            $("#cancelBtn").click(function () {
                Annual_Inspection_Edit_NS.cancel();
            });

        },

        submit: function () {
            $.post("/rmweb/customize-task.frm?method=annual-inspection", {
                kh: Annual_Inspection_Edit_NS.kh,
                sqr: encodeURI($("#sqr").val()),
                lxdh: $("#lxdh").val(),
                bz: encodeURI($("#bz").val()),
                _t: Date.parse(new Date())
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    Tools_NS.showConfirmDialog({
                        id: 'Success',
                        title: '创建成功 ',
                        message: '年检业务已受理',
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


        query: function() {
            if ($("#hphm").val().length <= 0 || $("#hpzl").val().length <= 0 || $("#fzjg").val().length <= 0) {
                Tools_NS.showWarningDialog("请输入号牌号码和号牌种类");
                return;
            }
            Annual_Inspection_Edit_NS.hphm = $("#fzjg").val() + $("#hphm").val();
            Annual_Inspection_Edit_NS.hpzl = $("#hpzl").val();
            $.get("/rmweb/be/customize-task.rfid?method=fetch-annual-inspection-info", {
                hphm: encodeURI(Annual_Inspection_Edit_NS.hphm.toUpperCase()),
                hpzl: Annual_Inspection_Edit_NS.hpzl,
                _t: Date.parse(new Date())
            }, function (data) {
                if (data && data["resultId"] == "00") {
                    $("#kh").text(data["eri"]["kh"]);
                    $("#tid").text(data["eri"]["tid"]);
                    Annual_Inspection_Edit_NS.kh = data["eri"]["kh"];
                    $("#rHphm").text(data["bean"]["hphm"]);
                    $("#rSyr").text(data["bean"]["syr"]);
                    $("#rHpzl").text(data["bean"]["hpzl"]);
                    $("#rZzl").text(data["bean"]["zzl"]);
                    $("#rCsys").text(data["bean"]["csys"]);
                    $("#rCllx").text(data["bean"]["cllx"]);
                    $("#rSyxz").text(data["bean"]["syxz"]);
                    $("#rGl").text(data["bean"]["gl"]);
                    $("#rPl").text(data["bean"]["pl"]);
                    $("#rCcrq").text(data["bean"]["ccrq"]);
                    $("#rQzbfqz").text(data["bean"]["qzbfqz"]);
                    $("#rYxqz").text(data["bean"]["yxqz"]);
                    $("#rHdzk").text(data["bean"]["hdzk"]);
                    $("#rBxzzrq").text(data["bean"]["bxzzrq"]);
                    $("#submitBtn").enable().focus();
                } else {
                    Tools_NS.showWarningDialog("查询失败" + (data ? (': ' +data["resultMsg"]) : ''));
                }
            }, "json");

        },

        resetTask : function() {
            $.get("/rmweb/be/eri.rfid?method=customize-result",
                    {xh : $("#xh").val(), result: "0", reason: "重置任务", tid: $("#tid").text(),
                        _t: Date.parse(new Date())},
                    function(data) {
                        if (data && data["resultId"] == "00") {
                            $("#debugInfo").text("任务已重置");
                            Annual_Inspection_Edit_NS.query();
                        }
                    }, "json");
        },

        errorHandleAfterChecked : function(checkResponse) {
            $("#debugInfo").text(checkResponse);
        },

        unfreeze: function() {
            $("#requestBtn").enable();
            $("#customizeBtn").enable();
            $("#closeBtn").enable();
        },

        clearTask: function() {
            $(".card-info").text("");
            $(".base-info").text("");
            $("#kh").text("");
            $("#tid").text("");
            $("#debugInfo").text("");
            $("#uploadImg").attr("src", "");
            if ($("#ywlx").val() == 5) {
                var lsh = $("#rLsh").val();
                $("#lsh").val(lsh.substr(0, 7) + ERI_NS.pad(parseInt(lsh.substr(7), 10) + 1, 6));
            } else {
                $("#lsh").val("");
            }
            $("#rLsh").val("");
            $("#syr").val("");
            $("#clxxid").val("");
            $("#id").val("");
            $("#xh").val("");
            $("#hphm").val("");
        }

    }

    -->
</script>
</body>
</html>
