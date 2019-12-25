<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312" %>
<html>
<head>
    <title>${cxmc}</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body onUnload="closesubwin()">
<div id="query">
    <div id="querytitle">��ѯ����</div>
    <form action="" method="post" name="myform" id="myform">
        <table border="0" cellspacing="1" cellpadding="0" class="query" width="100%">
            <col width="10%">
            <col width="23%">
            <col width="10%">
            <col width="23%">
            <col width="10%">
            <col width="23%">
            <tr>
                <td class="head">������</td>
                <td class="body"><h:managementbox list='${glbm}' id='glbm' haveNull='1'/></td>
                <td class="head">��������</td>
                <td class="body"><input type="text" name="bmmc" id="bmmc" maxlength="32"></td>

            </tr>
            <tr>
                <td colspan="6" class="submit">
                    <input type="button" value="����" class="button_new" onclick="add()">&nbsp;
                    <input type="button" value="��ѯ" class="button_query" onClick="query_cmd()">&nbsp;
                    <input type="button" value="�ر�" class="button_close" onClick="parent.doClose();">
                </td>
            </tr>

        </table>
    </form>
</div>
<div class="queryresult"></div>
<c:if test="${queryList!=null}">
    <div id="result">
        <div id="resulttitle">��ѯ����</div>
        <table border="0" cellspacing="1" cellpadding="0" class="list">
            <col width="8%">
            <col width="20%">
            <col width="6%">
            <col width="20%">
            <col width="9%">
            <col width="9%">
            <col width="9%">
            <col width="12%">
            <tr class="head">
                <td>���Ŵ���</td>
                <td>��������</td>
                <td>���ż���</td>
                <td>�ϼ���������</td>
                <td>������</td>
                <td>��ϵ��</td>
                <td>��ϵ�绰</td>
                <td>��γ����Ϣ</td>
            </tr>
            <c:forEach items="${queryList}" var="current">
                <tr class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'"
                    style="cursor:pointer">
                    <td><c:out value="${current.glbm}"/></td>
                    <td align="left"><c:out value="${current.bmmc}"/></td>
                    <td><c:out value="${current.bmjbmc}"/></td>
                    <td align="left"><c:out value="${current.sjbmmc}"/></td>
                    <td><c:out value="${current.fzr}"/></td>
                    <td><c:out value="${current.lxr}"/></td>
                    <td><c:out value="${current.lxdh}"/></td>
                    <td>
                        <c:out value="${current.jd}"/>&nbsp;<c:out value="${current.wd}"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="8" class="page">
                    <c:out value="${controller.clientScript}" escapeXml="false"/>
                    <c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
                </td>
            </tr>
        </table>
    </div>
</c:if>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
<script type="text/javascript">
    <!--

    $(function() {
        <c:if test="${department!=null}">
            $("[limit]").limit();
            var bzjwd = "<c:out value='${bzjwd}'/>";
            if (bzjwd == "1") {
                $("#bzjwd").attr("checked", true);
            } else {
                $("#bzjwd").attr("checked", false);
            }

            var wbzjwd = "<c:out value='${wbzjwd}'/>";
            if (wbzjwd == "1") {
                $("#wbzjwd").attr("checked", true);
            } else {
                $("#wbzjwd").attr("checked", false);
            }

            $("#glbm").val("<c:out value='${department.glbm}'/>");
            $("#bmmc").val("<c:out value='${department.bmmc}'/>");
        </c:if>
    });


    function query_cmd() {
        if (!doChecking()) return;
        $("#myform").attr("action", "<c:url value='/department.vmc?method=listThird'/>");
        closes();
        $("#myform").submit();
    }
    function doChecking() {
        if (!checkPrecision($("#glbm"), "12", "������")) return false;
        if (!checkLength($("#bmmc"), "64", "��������")) return false;
        return true;
    }
    function showdetail(glbm) {
        openwin("<c:url value='/department.vmc'/>?method=detailThird&glbm=" + glbm, "department");
    }

    function add() {
        openwin("<c:url value='/department.vmc?method=addThird'/>")
    }
    //-->
</script>
</body>
</html>