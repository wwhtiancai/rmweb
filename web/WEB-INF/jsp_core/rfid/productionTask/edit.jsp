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
        ����������ϸ
    </div>
    <div id="orderApplication">
        <form action="" method="post" name="orderApplicationForm" id="productApplyForm">
            <div id="block">
                <div id="blocktitle">��������</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">��ʼ����</td>
                        <td class="body">
                            <c:out value="${qskh}"/>
                        </td>
                        <td class="head">��ֹ����</td>
                        <td class="body">
                           <c:out value="${zzkh}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��Ʒ����</td>
                        <td class="body">
                            <c:out value="${inventories[0].cpmc}"/>
                        </td>
                        <td class="head">������</td>
                        <td class="body">
                            <c:out value="${task.czr}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��ע</td>
                        <td class="body" colspan="3">
                            <c:out value="${task.bz}"/>
                        </td>
                    </tr>
                </table>

                <div id="inventoryList">
                    <div style="float:left;margin: 30px 0 10px 0;font-weight:bold;">�����嵥</div>
                    <table border="0" cellspacing="1" cellpadding="0" class="list" id="productListTable">
                        <col width="12%">
                        <col width="12%"/>
                        <col width="20%">
                        <col width="20%">
                        <col width="21%">
                        <col width="15%">
                        <tr class="head">
                            <td>��װ���</td>
                            <td>��װ�к�</td>
                            <td>��ʼ����</td>
                            <td>��ֹ����</td>
                            <td>��������</td>
                            <td>״̬</td>
                        </tr>
                        <c:set var="rowcount" value="0" />
                        <c:forEach items="${inventories}" var="current">
                            <tr class="out">
                                <td><c:out value="${current.bzxh}" /></td>
                                <td><c:out value="${current.bzhh}"/></td>
                                <td><c:out value="${current.qskh}"/></td>
                                <td><c:out value="${current.zzkh}"/></td>
                                <td><c:out value="${current.ssbmmc}"/></td>
                                <td>
                                    <c:forEach var="status" items="${inventoryStatus}">
                                        <c:if test="${current.zt == status.status}">
                                            <c:out value="${status.desc}" />
                                        </c:if>
                                    </c:forEach>
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

    var Edit_Production_Task_NS = {

        init : function() {

        }
    };

    $(function() {
        Edit_Production_Task_NS.init();
    });
    -->
</script>
</body>
</html>