<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>���ݿ������</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="css/xtree.css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
</head>
<body>
<div id="panel">
    <div id="paneltitle">
        ���ݿ����
    </div>
    <div id="query">
        <div id="querytitle">
            ����
        </div>
        <table border="0" cellspacing="1" cellpadding="0" class="query">
            <col width="10%">
            <col width="23%">
            <col width="10%">
            <col width="23%">
            <col width="10%">
            <col width="24%">
            <tr>
                <td class="submit" colspan="6">
                    <form action="/rmweb/system.frm?method=upload-scripts" method="post" name="uploadForm" id="uploadForm" enctype="multipart/form-data">
                        <div>
                            <input type="file" name="file_upload"  style="width:800px;float:left;">
                            <input id="executeBtn" type="button" class="button_new" value="ִ��" style="float:right;"/>
                            <input type="submit" name="uploadBtn" id="uploadBtn" value="�ϴ�" class="button_save" style="float:right;margin-right:20px;">
                        </div>
                    </form>
                </td>
            </tr>
        </table>
    </div>
    <div class="queryresult" style="margin-top: 20px;">
        <c:if test="${operationList!=null}">
            <div id="result">
                <div id="resulttitle">
                    �ű��б�
                </div>
                <table border="0" cellspacing="1" cellpadding="0" class="list">
                    <col width="20%">
                    <col width="6%">
                    <col width="50%">
                    <col width="8%">
                    <col width="8%">
                    <col width="8%">
                    <tr class="head">
                        <td>
                            �ļ���
                        </td>
                        <td>״̬</td>
                        <td>���</td>
                        <td>��ʼ����</td>
                        <td>�������</td>
                        <td>������</td>
                    </tr>
                    <c:set var="rowcount" value="0" />
                    <c:forEach items="${operationList}" var="current">
                        <tr class="out">
                            <td style="text-align:left;word-break: break-all"><c:out value="${current.fileName}"/></td>
                            <td><c:out value="${current.status}"/></td>
                            <td style="text-align:left;word-break:break-all"><c:out value="${current.result}"/></td>
                            <td><fmt:formatDate value="${current.startAt}" type="both"/></td>
                            <td><fmt:formatDate value="${current.finishAt}" type="both"/></td>
                            <td><c:out value="${current.operator}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
    var Database_Manager_NS = {

        init : function() {
            $("#executeBtn").click(function() {
                $.ajax({
                    url : '<c:url value="/system.frm?method=database-manager"/>',
                    data : {},
                    dataType : "json",
                    method : "post",
                    success : function(data) {
                        if (data && data["resultId"] == "00") {
                            alert("SUCCESS");
                            Database_Manager_NS.query();
                        } else {
                            alert(data["resultMsg"]);
                        }
                    }
                });
            });
        },

        query : function() {
            window.location.href = "<c:url value='system.frm?method=database-manager'/>";
        }

    };

    $(function() {
        Database_Manager_NS.init();
    });
</script>
</body>
</html>