<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>订购申请</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
          media="screen" title="Flora (Default)" />
    <link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
          type="text/css">
</head>
<body>
<div id="panel" style="display: none">
    <div id="paneltitle">
        订购申请
    </div>
    <div id="orderApplication">
        <form action="" method="post" name="orderApplicationForm" id="orderApplicationForm">
            <input type="hidden" id="sqdh" name="sqdh" value="${orderApplication.sqdh}"/>
            <div id="block">
                <div id="blocktitle">申请单</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">申请部门</td>
                        <td class="body">
                            <c:out value="${orderApplication.bmmc}"/>
                            <input type="hidden" name="sqbm" id="sqbm" value="${orderApplication.sqbm}"/>
                        </td>
                        <td class="head">申请人</td>
                        <td class="body">
                            <c:out value="${orderApplication.jbr}"/>
                            <input type="hidden" name="jbr" id="jbr" value="${orderApplication.jbr}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">申请日期</td>
                        <td class="body">
                            <fmt:formatDate value="${orderApplication.sqrq}" type="both"/>
                            <input type="hidden" name="sqrq" id="sqrq" value="${orderApplication.sqrq}"/>
                        </td>
                        <td class="head">状态</td>
                        <td class="body">
                            <c:forEach var="status" items="${orderApplicationStatus}">
                                <c:if test="${status.status == orderApplication.zt}">
                                    <c:out value="${status.desc}"/>
                                    <input type="hidden" name="zt" id="zt" value="${orderApplication.zt}"/>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">产品类别</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.lbmc}"/>
                                    <input type="hidden" id="cplb" name="cplb" value="${orderApplication.cplb}"/>
                                </c:when>
                                <c:otherwise>
                                    <select id="cplb" name="cplb">
                                        <option value="">--请选择--</option>
                                        <c:forEach var="productCategory" items="${productCategories}">
                                            <c:choose>
                                                <c:when test="${productCategory.cplb == orderApplication.cplb}">
                                                    <option value="${productCategory.cplb}" selected>
                                                        <c:out value="${productCategory.lbmc}" />
                                                    </option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${productCategory.cplb}">
                                                        <c:out value="${productCategory.lbmc}" />
                                                    </option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="head">产品名称</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.cpmc}"/>
                                    <input type="hidden" id="cpdm" name="cpdm" value="${orderApplication.cpdm}"/>
                                </c:when>
                                <c:otherwise>
                                    <select id="cpdm" name="cpdm">
                                        <option value="">--请先选择产品类别--</option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">数量（片）</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.sl}"/>
                                    <input type="hidden" name="sl" id="sl" value="${orderApplicaiton.sl}"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" name="sl" id="sl" value="${orderApplication.sl}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="head">联系人</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.lxr}"/>
                                    <input type="hidden" name="lxr" id="lxr" value="${orderApplication.lxr}"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" name="lxr" id="lxr" value="${orderApplication.lxr}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">联系电话</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.lxdh}"/>
                                    <input type="hidden" name="lxdh" id="lxdh" value="${orderApplication.lxdh}"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" name="lxdh" id="lxdh" value="${orderApplication.lxdh}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="head">传真</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.cz}"/>
                                    <input type="hidden" name="cz" id="cz" value="${orderApplication.cz}"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" name="cz" id="cz" value="${orderApplication.cz}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">备注</td>
                        <td class="body" colspan="3">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.bz}"/>
                                    <input type="hidden" name="bz" id="bz" value="${orderApplication.bz}"/>
                                </c:when>
                                <c:otherwise>
                                    <textarea name="bz" id="bz" rows="4" cols="100"><c:out value="${orderApplication.bz}"/></textarea>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test="${type == 2}">
                            <tr>
                                <td class="head">审核意见</td>
                                <td class="body" colspan="3">
                                    <textarea name="shyj" id="shyj" rows="4" cols="100"></textarea>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${type == 1 && orderApplication.zt > 2}">
                            <tr>
                                <td class="head">审核意见</td>
                                <td class="body" colspan="3">
                                    <c:out value="${orderApplication.shyj}"/>
                                    <input type="hidden" id="shyj" name="shyj" value="${orderApplicaiton.shyj}"/>
                                </td>
                            </tr>
                        </c:when>
                    </c:choose>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <c:choose>
                            <c:when test="${type == 1}">
                                <input type="button" name="savebutton" id="savebutton" value="保存" class="button_save">
                                <c:if test="${orderApplication.zt <= 2}">
                                    <input type="button" name="deleteBtn" id="deleteBtn" value="删除" class="button_del">
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <input type="button" name="approveBtn" id="approveBtn" value="通过" class="button_save"/>
                                <input type="button" name="rejectBtn" id="rejectBtn" value="拒绝" class="button_del"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="button" name="closebutton" onclick="javascript:window.close();" value="取消" class="button_close">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript">
    <!--
    if (!window.console) {
        window.console = {
            log: function() {
                alert(arguments[0]);
            }
        }
    }

    var Edit_Order_Application_NS = {
        init : function() {
            $("#cplb").change(function() {
                Edit_Order_Application_NS.fetchProductByCategory($(this).val());
            });

            $("#savebutton").click(function() {
                Edit_Order_Application_NS.updateOrderApplication();
            });

            $("#approveBtn").click(function() {
                $.post('<c:url value="/order-application.frm?method=edit&type=2"/>', {sqdh: $("#sqdh").val(), shyj: $("#shyj").val(), zt : 3}, function(data) {
                    if (data["resultId"] == "00") {
                        alert("审核通过");
                        window.close();
                        window.opener.Order_Application_NS.query();
                    }
                }, "json")
            });

            $("#rejectBtn").click(function() {
                $.post('<c:url value="/order-application.frm?method=edit&type=2"/>', {sqdh: $("#sqdh").val(), shyj: $("#shyj").val(), zt : 2}, function(data) {
                    if (data["resultId"] == "00") {
                        alert("审核拒绝");
                        window.close();
                        window.opener.Order_Application_NS.query();
                    }
                }, "json")
            });

            $("#deleteBtn").click(function() {
                if(confirm("是否确定删除该订购申请？")){
                    closes();
                    $.ajax({
                        url : "<c:url value='/order-application.frm?method=delete'/>",
                        type: "POST",
                        dataType: "json",
                        contentType:"application/x-www-form-urlencoded;charset=utf-8",
                        async: false,
                        data: {
                            sqdh : $("#sqdh").val()
                        },
                        success: function(data) {
                            if (data && data["resultId"] == "00") {
                                alert("订购申请已取消");
                                window.opener.Order_Application_NS.query();
                                window.close();
                            }
                        }
                    });
                }
            });


            <c:if test="${orderApplication.cplb != null}">
                <c:choose>
                    <c:when test="${orderApplication.cpdm != null}">
                        Edit_Order_Application_NS.fetchProductByCategory($("#cplb").val(), '<c:out value="${orderApplication.cpdm}"/>');
                    </c:when>
                    <c:otherwise>
                        Edit_Order_Application_NS.fetchProductByCategory($("#cplb").val());
                    </c:otherwise>
                </c:choose>
            </c:if>
        },

        updateOrderApplication : function() {
            if(!checkNull($("#cplb"),"产品类别")) return false;
            if(!checkNull($("#cpdm"), "产品名称")) return false;
            if(!checkNumber($("#sl"), "数量")) return false;
            if(!checkNull($("#lxr"), "联系人")) return false;
            if(!checkNull($("#lxdh"), "联系电话")) return false;
            if(!checkNull($("#cz"), "传真")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/order-application.frm?method=edit'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#orderApplicationForm").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
                        alert("更新成功！");
                        window.opener.Order_Application_NS.query();
                        window.close();
                    }
                },
                dataType: "json"
            });
        },

        fetchProductByCategory : function(category, selectedItem) {
            $.get("product.frm", {method : 'fetch-by-category', category : category}, function(data) {
                if (data) {
                    $("#cpdm").empty();
                    if (data.length > 0) {
                        Edit_Order_Application_NS.addOption("cpdm", "", "---请选择---");
                        $.each(data, function(i, product) {
                            if (selectedItem && selectedItem == product.cpdm) {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc, true);
                            } else {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                            }
                        });
                    } else {
                        Edit_Order_Application_NS.addOption("cpdm", "", "---未找到相应的产品---");
                    }
                }
            }, "json");
        },

        addOption : function(selectorId, value, text, selected) {
            var obj = document.getElementById(selectorId);
            var opn = document.createElement("OPTION");
            obj.appendChild(opn);//先追加
            opn.innerText = text;
            opn.value = value;
            if (selected) {
                opn.selected = true;
            }
        }
    };

    $(function() {
        Edit_Order_Application_NS.init();
    });
    -->
</script>
</body>
</html>