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
                <col width="5%">
                <col width="15%">
                <col width="5%">
                <col width="19%">
                <col width="8%">
                <col width="18%">
                <col width="20%">
                <tr>
                    <td class="head">ͳ�����ڣ�</td>
                    <td class="body" colspan="3">
                        <h:datebox id="qsrq" name="qsrq" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="zzrq"
                                                                                                           name="zzrq"
                                                                                                           showType="1"
                                                                                                           width="32%"/>
                    </td>
                    <td class="head">�����ˣ�</td>
                    <td class="body">
                        <input type="text" id="yhdh" name="yhdh" value="${yhdh}"/>
                    </td>
                    <td class="submit" colspan="6">
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
                    ������
                </td>
                <td>
                    �״�����
                </td>
                <td>
                    ����
                </td>
                <td>
                    ���
                </td>
                <td>
                    �ܼ�
                </td>
            </tr>
            <c:set var="rowcount" value="0"/>
            <c:forEach items="${result}" var="current">
                <tr class="out">
                    <td>
                        <c:out value="${current.key}"/>
                    </td>
                    <td>
                        <c:out value="${current.value['YWLX_1']}"/>
                    </td>
                    <td>
                        <c:out value="${current.value['YWLX_3']}"/>
                    </td>
                    <td>
                        <c:out value="${current.value['YWLX_4'] == null ? 0 : current.value['YWLX_4']}"/>
                    </td>
                    <td>
                        <c:out value="${current.value['TOTAL']}"/>
                    </td>
                </tr>
                <c:set var="rowcount" value="${rowcount+1}"/>
            </c:forEach>
        </table>
    </div>
</div>
</body>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
    var Statistics_Count_Customization_NS = {

        init: function () {
            $.datepicker.setDefaults($.datepicker.regional['']);
            $('#qsrq').datepicker({
                showOn: 'both',
                buttonImageOnly: true,
                buttonImage: './rmjs/cal/cal.gif',
                buttonText: 'ѡ������'
            });
            $('#zzrq').datepicker({
                showOn: 'both',
                buttonImageOnly: true,
                buttonImage: './rmjs/cal/cal.gif',
                buttonText: 'ѡ������'
            });

            <c:if test="${qsrq != null}">
            $("#qsrq").val('${qsrq}');
            </c:if>

            <c:if test="${zzrq != null}">
            $("#zzrq").val('${zzrq}');
            </c:if>

            $("#calculateBtn").click(function () {
                $("#myform").attr("action", "<c:url value='/statistics.frm?method=count-customization'/>");
                closes();
                $("#myform").submit();
            });
        }

    };

    $(function () {
        Statistics_Count_Customization_NS.init();
    });

</script>
</html>