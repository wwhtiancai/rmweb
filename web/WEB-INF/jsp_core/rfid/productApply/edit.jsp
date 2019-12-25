<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>��������</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
    <%--<link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
          media="screen" title="Flora (Default)" />--%>
    <link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
          type="text/css">
    <style>
        .out.inventory-detail {
            background: #ABABAB;
        }
    </style>
</head>
<body>
<div id="panel" style="display: none">
    <div id="paneltitle">
        ��Ʒ����
    </div>
    <div id="orderApplication">
        <form action="" method="post" name="orderApplicationForm" id="productApplyForm">
            <div id="block">
                <div id="blocktitle">���õ�</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">���õ���</td>
                        <td class="body">
                            <c:out value="${productApply.lydh}"/>
                        </td>
                        <td class="head">������</td>
                        <td class="body">
                            <c:out value="${productApply.glbmmc}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��Ʒ���</td>
                        <td class="body">
                            <c:out value="${cpObj.cplbmc}"/>
                        </td>
                        <td class="head">��Ʒ����</td>
                        <td class="body">
                            <c:out value="${cpObj.cpmc}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">���ò���</td>
                        <td class="body">
                            <c:out value="${productApply.lybmmc}"/>
                        </td>
                        <td class="head">������</td>
                        <td class="body">
                            <c:out value="${productApply.lyr}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��ע</td>
                        <td class="body" colspan="3">
                            <c:out value="${productApply.bz}"/>
                        </td>
                    </tr>
                </table>


                <div id="productList">
                    <table border="0" cellspacing="1" cellpadding="0" class="detail" style="margin-top:30px;">
                        <tr>
                            <td class="head" width="100%" style="height:30px;text-align:left;font-weight:bold;padding-left:10px;font-size:14px;">��Ʒ�嵥</td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="1" cellpadding="0" class="list" id="productListTable">
                        <col width="5%">
                        <col width="10%">
                        <col width="15%">
                        <col width="15%">
                        <col width="20%">
                        <col width="20%">
                        <col width="15%">
                        <tr class="head">
                            <td>��λ</td>
                            <td>��/�к�</td>
                            <td>��ʼ����</td>
                            <td>��ֹ����</td>
                            <!-- <td>��Ʒ���</td>
                            <td>��Ʒ����</td> -->
                            <td>����</td>
                        </tr>
                        <c:set var="rowcount" value="0" />
                        <c:forEach items="${productApplyDetails}" var="detail">
                            <c:choose>
                                <c:when test="${fn:startsWith(detail.bzh, '1')}">
                                    <c:set var="total" value="${fn:length(detailMap[detail.bzh])}"/>
                                    <tr class="out" data-inventory-group="${detail.bzh}" data-bzh="${detail.bzh}">
                                        <td>��<%-- <c:out value="${detailMap[detail.bzh][0]}"/> --%></td>
                                        <td><c:out value="${detail.bzh}"/></td>
                                        <td><c:out value="${detailMap[detail.bzh][0].qskh}"/></td>
                                        <td><c:out value="${detailMap[detail.bzh][total - 1].zzkh}"/></td>
                                        <%-- <td><c:out value="${detailMap[detail.bzh][0].cplbmc}"/></td>
                                        <td><c:out value="${detailMap[detail.bzh][0].cpmc}"/></td> --%>
                                        <td>
                                            <a href="javascript:void(0)" class="expand-btn">չ��</a>
                                        </td>
                                    </tr>
                                    <c:forEach items="${detailMap[detail.bzh]}" var="inventory">
                                        <tr class="out inventory-detail" style="display:none" data-inventory-group="${detail.bzh}" data-bzh="${inventory.bzhh}">
                                            <td>��<%-- <c:out value="${detailMap}"/> --%></td>
                                            <td><c:out value="${inventory.bzhh}"/></td>
                                            <td><c:out value="${inventory.qskh}"/></td>
                                            <td><c:out value="${inventory.zzkh}"/></td>
                                            <td><c:out value="${inventory.cplbmc}"/></td>
                                            <td><c:out value="${inventory.cpmc}"/></td>
                                            <td></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${fn:startsWith(detail.bzh, '2')}">
                                    <tr class="out" data-inventory-group="${detail.bzh}" data-bzh="${detail.bzh}">
                                        <td>��</td>
                                        <td><c:out value="${detail.bzh}"/></td>
                                        <td><c:out value="${detailMap[detail.bzh][0].qskh}"/></td>
                                        <td><c:out value="${detailMap[detail.bzh][0].zzkh}"/></td>
                                        <%-- <td><c:out value="${detailMap[detail.bzh][0].cplbmc}"/></td>
                                        <td><c:out value="${detailMap[detail.bzh][0].cpmc}"/></td> --%>
                                        <td></td>
                                    </tr>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </table>

                </div>

            </div>
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

    var Edit_Product_Apply_NS = {

        init : function() {

            $("body").on("click", ".expand-btn", function() {
                var inventoryGroup = $(this).closest("tr").attr("data-inventory-group");
                if ($(this).hasClass("expanded")) {
                    $("tr[data-inventory-group=" + inventoryGroup + "].inventory-detail").hide();
                    $(this).removeClass("expanded");
                    $(this).text("չ��");
                } else {
                    $(this).text("����");
                    $("tr[data-inventory-group=" + inventoryGroup + "].inventory-detail").show();
                    $(this).addClass("expanded");
                }
            });
        }
    };

    $(function() {
        Edit_Product_Apply_NS.init();
    });
    -->
</script>
</body>
</html>