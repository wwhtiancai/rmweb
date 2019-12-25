<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>ϵͳ������־</title>
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
        ϵͳ������־��ѯ
    </div>
    <div id="query">
        <div id="querytitle">
            ��ѯ����
        </div>
        <form action="" method="post" name="myform" id="myform">
            <input type="hidden" name="page" value="1" />
            <table border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <tr >
                    <td class="head">
                        ��������
                    </td>
                    <td class="body">
                        <input type="text" name="czmc" id="czmc" />
                    </td>
                    <td class="head">
                        �ؼ�ֵ
                    </td>
                    <td class="body">
                        <input type="text" name="gjz" id="gjz"/>
                    </td>
                    <td class="head">
                        ����
                    </td>
                    <td class="body">
                        <input type="text" name="xxnr" id="xxnr"/>
                    </td>
                    <td class="head">
                        �������
                    </td>
                    <td class="body">
                        <input type="text" name="jg" id="jg"/>
                    </td>
                </tr>
                <tr>
                    <td class="submit" colspan="8">
                        <input type="button" class="button_default" value="��ѯ"
                               onclick="query_cmd()">
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="queryresult"></div>
    <c:if test="${queryList!=null}">
        <div id="result">
            <div id="resulttitle">
                ��ѯ����
            </div>
            <table border="0" cellspacing="1" cellpadding="0" class="list">
                <col width="5%">
                <col width="10%">
                <col width="10%">
                <col width="50%">
                <col width="5%">
                <col width="10%">
                <col width="10%">
                <tr class="head">
                    <td>
                        ���
                    </td>
                    <td>
                        ��������
                    </td>
                    <td>
                        �ؼ�ֵ
                    </td>
                    <td>
                        ��Ϣ����
                    </td>
                    <td>
                        ���
                    </td>
                    <td>
                        ������
                    </td>
                    <td>
                        ����ʱ��
                    </td>
                </tr>
                <c:set var="rowcount" value="0" />
                <c:forEach items="${queryList}" var="current">
                    <tr class="out" onMouseOver="this.className='over'"
                        onMouseOut="this.className='out'" style="cursor: pointer">
                        <td>
                            <c:out value="${current.xh}" />
                        </td>
                        <td>
                            <c:out value="${current.czmc}"/>
                        </td>
                        <td>
                            <c:out value="${current.gjz}"/>
                        </td>
                        <td style="word-break: break-all;word-wrap: break-word">
                            <c:out value="${current.xxnr}"/>
                        </td>
                        <td>
                            <c:out value="${current.jg}" />
                        </td>
                        <td>
                            <c:out value="${current.czr}" />
                        </td>
                        <td>
                            <fmt:formatDate value='${current.cjsj}'
                                            pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                    </tr>
                    <c:set var="rowcount" value="${rowcount+1}" />
                </c:forEach>
                <tr>
                    <td colspan="11" class="page">
                        <c:out value="${controller.clientScript}" escapeXml="false" />
                        <c:out value="${controller.clientPageCtrlDesc}"
                               escapeXml="false" />
                    </td>
                </tr>
            </table>
        </div>
    </c:if>
</div>
</body>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
    if (!window.console) {
        window.console = {
            log: function() {
                alert(arguments[0]);
            }
        }
    }

    function query_cmd(){

        $("#myform").attr("action","<c:url value='/system.frm?method=show-operation-log'/>");
        closes();
        $("#myform").submit();
    }

</script>
</html>