<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>日志</title>
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
        日志查询
    </div>
    <div id="query">
        <div id="querytitle">
            查询条件
        </div>
        <form action="" method="post" name="myform" id="myform">
            <input type="hidden" id="status" name="status" value="${status}" />
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
                        文件
                    </td>
                    <td class="body">
                        <input type="text" name="file" id="file" value="${file}" />
                    </td>
                    <td class="head">
                        顺序
                    </td>
                    <td class="body">
                        <select id="reverse" name="reverse">
                            <option value="0">顺序</option>
                            <option value="1" selected>倒序</option>
                        </select>
                    </td>
                    <td class="head">
                        关键值
                    </td>
                    <td class="body">
                        <input type="text" name="keyword" id="keyword"/>
                    </td>
                    <td class="head">
                        偏移量
                    </td>
                    <td class="body">
                        <input type="text" name="lines" id="lines"/>
                    </td>
                </tr>
                <tr>
                    <td class="submit" colspan="8">
                        <c:choose>
                            <c:when test="${status == 1}">
                                <input type="button" class="button_default" value="停止"
                                       onclick="Log_NS.stop()">
                            </c:when>
                            <c:otherwise>
                                <input type="button" class="button_default" value="查询"
                                       onclick="query_cmd()">
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="logPanel" style="background:black;color:white;text-align:left;padding:20px 30px;border:1px solid gray;margin-top:20px;overFlow-y:scroll;word-break:break-all;word-wrap:break-word;">
        <c:if test="${resultId != '00'}">
            <span style="font-weight:bold;color:red">${resultMsg}</span>
        </c:if>
    </div>
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
        $("#myform").attr("action","<c:url value='/system.frm?method=start-tail'/>");
        closes();
        $("#myform").submit();
    }

    var Log_NS = {
        tail : function() {
            if ($("#status").val() == 1) {
                $.get("<c:url value='/system.frm?method=tail-log'/>", {}, function(data) {
                    if (data && data["resultId"] == "00") {
                        for (var i = 0; i < data["lines"].length; i++) {
                            $("#logPanel").append(data["lines"][i] + "<br/>");
                        }
                        $("#logPanel")[0].scrollTop = $("#logPanel")[0].scrollHeight
                        setTimeout(Log_NS.tail, 2000);
                    }
                }, "json");
            }
        },

        stop: function() {
            $("#myform").attr("action","<c:url value='/system.frm?method=stop-tail'/>");
            closes();
            $("#myform").submit();
        }
    }

    $(function() {
        $("#logPanel").css("height", document.body.clientHeight - 200);
        Log_NS.tail();
    });

</script>
</html>