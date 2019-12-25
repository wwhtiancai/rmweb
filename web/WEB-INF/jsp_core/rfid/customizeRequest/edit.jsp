<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>个性化操作详情</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
          media="screen" title="Flora (Default)" />
    <link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
          type="text/css">
</head>
<body>
<div id="panel">
    <div id="paneltitle">
        个性化操作详情
    </div>
    <div id="customizeRequest">
        <form action="" method="post" name="customizeRequestForm" id="customizeRequestForm">
            <div id="block">
                <div id="blocktitle">个性化操作</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">

                </table>

                <div id="operationLogs">
                    <div style="float:left;margin: 30px 0 10px 0;font-weight:bold;">操作日志</div>
                    <table border="0" cellspacing="1" cellpadding="0" class="list" id="operationListTable">
                        <col width="15%">
                        <col width="45%"/>
                        <col width="20%">
                        <col width="20%">
                        <col width="20%">
                        <tr class="head">
                            <td>操作名称</td>
                            <td>详细内容</td>
                            <td>结果</td>
                            <td>创建人</td>
                            <td>创建时间</td>
                        </tr>
                        <c:set var="rowcount" value="0" />
                        <c:forEach items="${operationLogs}" var="current">
                            <tr class="out">
                                <td><c:out value="${current.czmc}" /></td>
                                <td><c:out value="${current.xxnr}"/></td>
                                <td><c:out value="${current.jg}"/></td>
                                <td><c:out value="${current.cjr}"/></td>
                                <td><fmt:formatDate value="${current.cjsj}" type="both"/></td>
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

    var Edit_Customize_Request_NS = {

        init : function() {

        }
    };

    $(function() {
        Edit_Customize_Request_NS.init();
    });
    -->
</script>
</body>
</html>