<%@ page contentType="text/html; charset=gb2312" %>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@ page import="com.tmri.pub.util.JspSuport" %>
<%@ page import="com.tmri.share.frm.bean.UserSession" %>
<html>
<head>
    <title></title>
    <%
        UserSession userSession = (UserSession) session.getAttribute("userSession");
    %>
    <base target="rightfrm">
    <meta http-equiv="Content-Type" content="text/html; charset=GB2312">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="公安部,交通,管理,科研所,跨行业,交换平台,保险">
    <meta http-equiv="description" content="公安部交通管理科学研究所">
    <link href="theme/blue/blue.css?v=1" rel="stylesheet" type="text/css">
    <script language="JavaScript" src="theme/blue/blue.js?v=1" type="text/javascript"></script>
    <%=JspSuport.getInstance().JS_ALL%>
    <style type="text/css">
        body {
            background-COLOR: #094E89;
        }
    </style>
</head>
<body onLoad="doinit()" onUnload="closesubwin();">
<table border="0" cellspacing="0" cellpadding="0" class="text_12" width="100%" height="100%" align="center">
    <tr>
        <td valign="top" bgcolor="#f2f4f8">
            <table border="0" cellspacing="0" cellpadding="0" class="left_table text_12">
                <tr>
                    <td><c:out value="${menuhtml}" escapeXml="false"/></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td bgcolor="#f2f4f8" valign="bottom">
            <table border="0" cellspacing="1" cellpadding="0" id="xtll" class="text_12" style="width:100%">
                <tr height="19">
                    <td>
                        <img src="theme/style/icon_c8.gif" width="12" height="12" align="absmiddle">&nbsp;电子标识库存：<span id="dzbzkc"></span>
                    </td>
                </tr>
                <tr height="19">
                    <td><img src="theme/style/icon_c8.gif" width="12" height="12"
                             align="absmiddle">&nbsp;在线人数:<%=userSession.getZxrs() %>
                    </td>
                </tr>
                <tr height="19">
                    <td><img src="theme/style/icon_c8.gif" width="12" height="12"
                             align="absmiddle">&nbsp;当天访问数:<%=userSession.getDrfwrs() %>
                    </td>
                </tr>
                <tr height="19">
                    <td><img src="theme/style/icon_c8.gif" width="12" height="12"
                             align="absmiddle">&nbsp;上次登录时间:<%=userSession.getScdlsj() %>
                    </td>
                </tr>
                <tr height="19">
                    <td><img src="theme/style/icon_c8.gif" width="12" height="12"
                             align="absmiddle">&nbsp;上次登录IP:<%=userSession.getScdlip() %>
                    </td>
                </tr>
                <tr height="19">
                    <td><img src="theme/style/icon_c8.gif" width="12" height="12"
                             align="absmiddle">&nbsp;本月累计登录数:<%=userSession.getBydlcs() %>
                    </td>
                </tr>

                <tr height="19">
                    <td><img src="theme/style/icon_c8.gif" width="12" height="12"
                             align="absmiddle">&nbsp;密码有效期至<%=userSession.getMmyxq()%>
                    </td>
                </tr>
                <tr height="19">
                    <td><img src="theme/style/icon_c8.gif" width="12" height="12"
                             align="absmiddle">&nbsp;账户有效期至<%=userSession.getZhyxq()%>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script language="JavaScript" type="text/javascript">
    var count = 15;
    function doinit() {
    }

    $(function() {
        queryInventory();
        setInterval(queryInventory, 60000);
    });

    function queryInventory() {
        $.get("<c:url value='/be/inventory.rfid?method=query-kc'/>", {}, function(data) {
            if (data && data["resultId"] == "00") {
                var kc = data["kc"];
                $("#dzbzkc").html(kc).css("color", kc < 40 ? "red" : "");
            }
        }, "json");
    }

    function interfaceDoTableContent(m, n) {
        for (var i = 1; i <= n; i++) {
            var tablecontent_div_obj = document.getElementById('table_content' + i);
            if (m == i) {
                tablecontent_div_obj.style.display = "block";
            } else {
                tablecontent_div_obj.style.display = "none";
            }
        }
    }
    function interfaceDoMenu(c) {
        var ipos = c.indexOf("_");
        var topindex = c.substring(0, ipos);
        var fold1 = c.substr(ipos + 1);
        for (var i = 1; i <= count; i++) {
            try {
                document.getElementById("menu" + topindex + "_" + i).style.display = "none";
            } catch (e) {
            }
        }
        document.getElementById("menu" + topindex + "_" + fold1).style.display = "block";
    }
    function interfaceDoSubFold(topindex, c, m) {
        var flag;
        var t_obj = document.getElementById("tab" + topindex + "_" + c + "_" + m);
        var tdr_obj = document.getElementById("submenu" + topindex + "_" + c + "_" + m);
        if (tdr_obj.style.display == "block") {
            flag = 1;
            if (t_obj != null) {
                t_obj.className = "m2j_0"; //+
            }
        } else {
            flag = 0;
            if (t_obj != null) {
                t_obj.className = "m2j_1"; //-
            }
        }
        if (flag == 1) {
            tdr_obj.style.display = "none";
        } else {
            tdr_obj.style.display = "block";
        }
    }

    function openWindowBu(sPath) {
        var windowheight = screen.height;
        var windowwidth = screen.width;
        windowheight = (windowheight - 600) / 2;
        windowwidth = (windowwidth - 800) / 2;
        //pop=window.open(sPath,"vehmain","resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=790,height=590,top="+windowheight+",left="+windowwidth);
        var pop;
        if (sSfqp == "1") {
            pop = openwin1024(sPath, "bussiness<c:out value='${userSession.sysuser.yhdh}'/>", false);
        } else if (sSfqp == "2") {
            pop = window.open(sPath, "_blank");
        } else {
            pop = openwin1024(sPath, "bussiness<c:out value='${userSession.sysuser.yhdh}'/>", false);
        }
        pop.focus();
    }
</script>
</body>
</html>
