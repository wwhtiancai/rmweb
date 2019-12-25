<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>个性化请求</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
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
<div id="panel">
    <div id="paneltitle">
        个性化请求管理
    </div>
    <div id="query">
        <div id="querytitle">
            查询条件
        </div>
        <form action="" method="post" name="myform" id="myform">
            <input type="hidden" name="page" value="1"/>
            <input type="hidden" name="bdfzjg" id="bdfzjg" value="${bdfzjg}"/>
            <table border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="10%">
                <col width="23%">
                <col width="10%">
                <col width="23%">
                <col width="10%">
                <col width="24%">
                <tr>
                    <td class="head">卡号</td>
                    <td class="body">
                        <input type="text" id="kh" name="kh" value="${condition.kh}"/>
                    </td>
                    <td class="head">流水号</td>
                    <td class="body">
                        <input type="text" id="lsh" name="lsh" value="${condition.lsh}"/>
                    </td>
                    <td class="head">状态</td>
                    <td class="body">
                        <select id="zt" name="zt" >
                            <option value="">---请选择---</option>
                            <c:forEach var="status" items="${statusDescMap}">
                                <c:choose>
                                    <c:when test="${status.key == condition.zt}">
                                        <option value="${status.key}" selected>
                                            <c:out value="${status.value}"/>
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${status.key}">
                                            <c:out value="${status.value}"/>
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="head">号牌种类</td>
                    <td class="body">
                        <select id="hpzl" name="hpzl">
                            <option value="">--请选择号牌种类--</option>
                            <c:forEach var="licenceType" items="${licenceTypes}">
                                <c:choose>
                                    <c:when test="${licenceType.dmz == condition.hpzl}">
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
                    <td class="head">号牌号码</td>
                    <td class="body">
                        <div style="position:relative;float:left;width:30px;" class="input-wrap">
                            <input type="text" name="fzjg" id="fzjg"
                                   value='<c:out value="${fn:substring(localFzjg, 0, 1)}" />' style="cursor:pointer"
                                   readonly="readonly" maxlength="15">
                        </div>
                        <div style="position:relative;float:right;" class="input-wrap">
                            <input type="text" name="hphm" id="hphm" value="${condition.hphm}" maxlength="15" tabindex="3">
                        </div>
                    </td>
                    <td class="body" colspan="2"/>
                </tr>
                <tr>
                    <td class="submit" colspan="6">
                        <span style="float:left;">
                            <input type="checkbox" id="autoRefresh" name="autoRefresh"/>自动刷新
                        </span>
                        <input id="clearBtn" type="button" class="button_query" value="清空"/>
                        <input id="queryBtn" type="button" class="button_query" value="查询"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="queryresult" style="margin-top: 20px;">
        <c:if test="${requestList!=null}">
            <div id="result">
                <div id="resulttitle">
                    请求列表
                </div>
                <table border="0" cellspacing="1" cellpadding="0" class="list">
                    <col width="4%">
                    <col width="8%">
                    <col width="8%">
                    <col width="8%">
                    <col width="8%">
                    <col width="8%">
                    <col width="8%">
                    <col width="32%">
                    <col width="8%">
                    <col width="8%">
                    <tr class="head">
                        <td>序号</td>
                        <td>卡号</td>
                        <td>流水号</td>
                        <td>业务类型</td>
                        <td>号牌号码</td>
                        <td>号牌种类</td>
                        <td>状态</td>
                        <td>失败原因</td>
                        <td>请求人</td>
                        <td>请求时间</td>
                    </tr>
                    <c:set var="rowcount" value="0" />
                    <c:forEach items="${requestList}" var="current">
                        <tr class="out" data-xh="${current.xh}">
                            <td><c:out value="${current.xh}" /></td>
                            <td>
                                <c:out value="${current.kh}" />
                            </td>
                            <td>
                                <c:out value="${current.lsh}" />
                            </td>
                            <td>
                                <c:forEach var="types" items="${custTypes}">
                                    <c:if test="${types.type == current.ywlx}">
                                        <c:out value="${types.desc}" />
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:out value="${fn:substring(current.fzjg, 0, 1)}${current.hphm}"/>
                            </td>
                            <td>
                                <c:out value="${hpzlDescMap[current.hpzl]}" />
                            </td>
                            <td>
                                <c:out value="${statusDescMap[current.zt]}" />
                            </td>
                            <td>
                                <c:out value="${current.sbyy}"/>
                            </td>
                            <td>
                                <c:out value="${current.cjr}"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${current.cjsj}" type="both"/>
                            </td>
                        </tr>
                        <c:set var="rowcount" value="${rowcount+1}" />
                    </c:forEach>
                    <tr>
                        <td colspan="10" class="page">
                            <c:out value="${controller.clientScript}" escapeXml="false" />
                            <c:out value="${controller.clientPageCtrlDesc}"
                                   escapeXml="false" />
                        </td>
                    </tr>
                </table>
            </div>
        </c:if>
    </div>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">

    var Customize_Request_NS = {

        init : function() {
            $.datepicker.setDefaults($.datepicker.regional['']);
            $(".jscal").each(function () {
                eval($(this).html());
            });

            $("#queryBtn").click(function() {
                Customize_Request_NS.query();
            });

            $("#clearBtn").click(function(){
                $("input[type='text']").not("#fzjg").val('');
                $("select option[value='']").attr("selected", "selected");
            });

            $("#hphm").css("width", $("#hphm").parents("td").width() - 45);

            <c:if test="${condition!=null}">
            $("#fzjg").val("${fn:substring(condition.fzjg, 0, 1)}");
            </c:if>

            if (!$("#fzjg").val()) {
                $("#fzjg").val("${fn:substring(localFzjg,0,1)}");
            }

            $("#fzjg").dblclick(function() {
                if ($(".fzjg-select").length > 0) {
                    $(".fzjg-select").show();
                } else {
                    if (!Customize_Request_NS.bdfzjg) {
                        Customize_Request_NS.bdfzjg = $("#bdfzjg").val().split(",");
                    }
                    var fzjgSelector = "<div class='fzjg-select'><ul>";
                    var existsFzjg = {};
                    $(Customize_Request_NS.bdfzjg).each(function(index, element){
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

            $("#autoRefresh").change(function() {
                if($("#autoRefresh").attr("checked") == "checked") {
                    Customize_Request_NS.startAutoRefresh();
                } else {
                    Customize_Request_NS.endAutoRefresh();
                }
            });

            <c:if test="${autoRefresh == 'on'}">
                $("#autoRefresh").attr("checked", "checked");
                Customize_Request_NS.startAutoRefresh();
            </c:if>

        },

        query : function() {
            $("#myform").attr("action","<c:url value='/customize-request.frm?method=list'/>");
            closes();
            $("#myform").submit();
        },

        startAutoRefresh : function() {
            Customize_Request_NS.timer = setTimeout('Customize_Request_NS.query()',10000);
        },

        endAutoRefresh: function() {
            if (Customize_Request_NS.timer) {
                clearTimeout(Customize_Request_NS.timer);
            }
        }

    };

    $(function() {
        Customize_Request_NS.init();
    });

    var query_cmd = Customize_Request_NS.query;

</script>
</body>
</html>