<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>��������</title>
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
        ��������
    </div>
    <div id="orderApplication">
        <form action="" method="post" name="orderApplicationForm" id="orderApplicationForm">
            <input type="hidden" id="sqdh" name="sqdh" value="${orderApplication.sqdh}"/>
            <div id="block">
                <div id="blocktitle">���뵥</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">���벿��</td>
                        <td class="body">
                            <c:out value="${orderApplication.bmmc}"/>
                            <input type="hidden" name="sqbm" id="sqbm" value="${orderApplication.sqbm}"/>
                        </td>
                        <td class="head">������</td>
                        <td class="body">
                            <c:out value="${orderApplication.jbr}"/>
                            <input type="hidden" name="jbr" id="jbr" value="${orderApplication.jbr}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��������</td>
                        <td class="body">
                            <fmt:formatDate value="${orderApplication.sqrq}" type="both"/>
                            <input type="hidden" name="sqrq" id="sqrq" value="${orderApplication.sqrq}"/>
                        </td>
                        <td class="head">״̬</td>
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
                        <td class="head">��Ʒ���</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.lbmc}"/>
                                    <input type="hidden" id="cplb" name="cplb" value="${orderApplication.cplb}"/>
                                </c:when>
                                <c:otherwise>
                                    <select id="cplb" name="cplb">
                                        <option value="">--��ѡ��--</option>
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
                        <td class="head">��Ʒ����</td>
                        <td class="body">
                            <c:choose>
                                <c:when test="${type == 2 || orderApplication.zt > 2}">
                                    <c:out value="${orderApplication.cpmc}"/>
                                    <input type="hidden" id="cpdm" name="cpdm" value="${orderApplication.cpdm}"/>
                                </c:when>
                                <c:otherwise>
                                    <select id="cpdm" name="cpdm">
                                        <option value="">--����ѡ���Ʒ���--</option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">������Ƭ��</td>
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
                        <td class="head">��ϵ��</td>
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
                        <td class="head">��ϵ�绰</td>
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
                        <td class="head">����</td>
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
                        <td class="head">��ע</td>
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
                                <td class="head">������</td>
                                <td class="body" colspan="3">
                                    <textarea name="shyj" id="shyj" rows="4" cols="100"></textarea>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${type == 1 && orderApplication.zt > 2}">
                            <tr>
                                <td class="head">������</td>
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
                                <input type="button" name="savebutton" id="savebutton" value="����" class="button_save">
                                <c:if test="${orderApplication.zt <= 2}">
                                    <input type="button" name="deleteBtn" id="deleteBtn" value="ɾ��" class="button_del">
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <input type="button" name="approveBtn" id="approveBtn" value="ͨ��" class="button_save"/>
                                <input type="button" name="rejectBtn" id="rejectBtn" value="�ܾ�" class="button_del"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="button" name="closebutton" onclick="javascript:window.close();" value="ȡ��" class="button_close">
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
                        alert("���ͨ��");
                        window.close();
                        window.opener.Order_Application_NS.query();
                    }
                }, "json")
            });

            $("#rejectBtn").click(function() {
                $.post('<c:url value="/order-application.frm?method=edit&type=2"/>', {sqdh: $("#sqdh").val(), shyj: $("#shyj").val(), zt : 2}, function(data) {
                    if (data["resultId"] == "00") {
                        alert("��˾ܾ�");
                        window.close();
                        window.opener.Order_Application_NS.query();
                    }
                }, "json")
            });

            $("#deleteBtn").click(function() {
                if(confirm("�Ƿ�ȷ��ɾ���ö������룿")){
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
                                alert("����������ȡ��");
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
            if(!checkNull($("#cplb"),"��Ʒ���")) return false;
            if(!checkNull($("#cpdm"), "��Ʒ����")) return false;
            if(!checkNumber($("#sl"), "����")) return false;
            if(!checkNull($("#lxr"), "��ϵ��")) return false;
            if(!checkNull($("#lxdh"), "��ϵ�绰")) return false;
            if(!checkNull($("#cz"), "����")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/order-application.frm?method=edit'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#orderApplicationForm").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
                        alert("���³ɹ���");
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
                        Edit_Order_Application_NS.addOption("cpdm", "", "---��ѡ��---");
                        $.each(data, function(i, product) {
                            if (selectedItem && selectedItem == product.cpdm) {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc, true);
                            } else {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                            }
                        });
                    } else {
                        Edit_Order_Application_NS.addOption("cpdm", "", "---δ�ҵ���Ӧ�Ĳ�Ʒ---");
                    }
                }
            }, "json");
        },

        addOption : function(selectorId, value, text, selected) {
            var obj = document.getElementById(selectorId);
            var opn = document.createElement("OPTION");
            obj.appendChild(opn);//��׷��
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