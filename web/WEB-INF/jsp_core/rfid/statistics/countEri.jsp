<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=GBK" %>
<html>
<head>
    <title>�ƿ�ͳ��</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="css/xtree.css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)"/>
</head>
<body>
<div id="panel" style="display: none">
    <div id="paneltitle">
        �ƿ�ͳ��
    </div>
    <div id="query">
        <div id="querytitle">
            ͳ������
        </div>
        <form action="" method="post" name="myform" id="myform">
            <input type="hidden" name="page" value="1"/>
            <table border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="20%">
                <col width="70%">
                <col width="10%">
                <tr>
                    <td class="head">��֤���أ�</td>
                    <td class="body">
                        <input type="hidden" name="fzjg" id="searchFzjg">
                        <select id="fzjg" style="width: 100px">
                            <c:forEach var="current" items="${fzjgList}">
                                <option value="${current}">
                                    <c:out value="${current}" />
                                </option>
                            </c:forEach>
                        </select>
                    </td>

                    <td class="submit">
                        <input type="button" class="button_default" value="ͳ��" id="calculateBtn">
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="queryresult"></div>
    <div id="result">
        <div id="resulttitle">
            ͳ�ƽ��
        </div>
        <table border="0" cellspacing="1" cellpadding="0" class="list">
            <col width="20%">
            <col width="20%">
            <col width="20%">
            <col width="20%">
            <col width="20%">
            <tr class="head">
                <td>
                    ��������
                </td>
                <td>
                    �ƿ�����
                </td>
            </tr>
            <c:set var="rowcount" value="0"/>
            <c:forEach items="${result}" var="current">
                <tr class="out">
                    <td>
                        <c:out value="${current.CLLX}"/>
                    </td>
                    <td>
                        <c:out value="${current.ZKSL}"/>
                    </td>
                </tr>
                <c:set var="rowcount" value="${rowcount+1}"/>
            </c:forEach>
        </table>
    </div>
</div>
</body>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script type="text/javascript">
    var Statistics_Count_Eri_NS = {

        init: function () {
            <c:if test="${fzjg != null}">
            $("#fzjg").val('${fzjg}');
            </c:if>

            $("#calculateBtn").click(function () {
                $("#myform").attr("action", "<c:url value='/statistics.frm?method=countEri'/>");
                console.log($("#fzjg").val())
                console.log(encodeURI($("#fzjg").val()))
                $("#searchFzjg").val(encodeURI($("#fzjg").val()));
                console.log($("#searchFzjg").val())
                closes();
                $("#myform").submit();
            });
        }
    };

    $(function () {
        Statistics_Count_Eri_NS.init();
    });

</script>
</html>