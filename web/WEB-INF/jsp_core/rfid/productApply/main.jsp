<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>产品领用</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="css/xtree.css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
</head>
<body>
<div id="panel">
    <div id="paneltitle">
        产品领用
    </div>
    <div id="query">
        <div id="querytitle">
            查询条件
        </div>
        <form action="" method="post" name="myform" id="myform">
			<input type="hidden" name="page" value="1"/>
            <table border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="10%">
                <col width="23%">
                <col width="10%">
                <col width="23%">
                <col width="10%">
                <col width="24%">
                <tr>
                    <td class="head">领用单号</td>
                    <td class="body">
                        <input type="text" id="lydh" name="lydh" value="${condition.lydh}" style="width:100%;"/>
                    </td>
                    <td class="head">领用部门</td>
                    <td class="body">
                        <h:managementbox list='${xjbm}' id='lybm' haveNull='1' defaultVal="${condition.lybm}"/>
                    </td>
                    <td class="head">领用人</td>
                    <td class="body">
                        <input type="text" id="lyr" name="lyr" value="${condition.lbr}" style="width:100%;"/>
                    </td>
                </tr>
                <tr>
                    <td class="head">管理部门</td>
                    <td class="body">
                        <h:managementbox list='${xjbm}' id='glbm' haveNull='1' defaultVal="${condition.glbm}"/>
                    </td>
                    <td class="head">领用日期</td>
                    <td class="body">
                        <h:datebox id="lyrqks" name="lyrqks" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="lyrqjs" name="lyrqjs" showType="1" width="32%"/>
                    </td>
                    <td class="head"></td>
                    <td class="body"></td>
                </tr>
                <tr>
                    <td class="submit" colspan="6">
                        <input id="createBtn" type="button" class="button_new" value="新增"/>
                        <input id="queryBtn" type="button" class="button_query" value="查询"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="queryresult" style="margin-top: 20px;">
        <c:if test="${queryList!=null}">
            <div id="result">
                <div id="resulttitle">
                    查询内容
                </div>
                <table border="0" cellspacing="1" cellpadding="0" class="list">
                    <col width="20%">
                    <col width="20%">
                    <col width="20%">
                    <col width="20%">
                    <col width="20%">
                    <tr class="head">
                        <td>
                            领用单号
                        </td>
                        <td>
                            领用部门
                        </td>
                        <td>领用人</td>
                        <td>管理部门</td>
                        <td>领用日期</td>
                    </tr>
                    <c:set var="rowcount" value="0" />
                    <c:forEach items="${queryList}" var="current">
                        <tr class="out product-apply-row" data-lydh="${current.lydh}">
                            <td><c:out value="${current.lydh}" /></td>
                            <td><c:out value="${current.lybmmc}"/></td>
                            <td><c:out value="${current.lyr}"/></td>
                            <td><c:out value="${current.glbmmc}"/></td>
                            <td><fmt:formatDate value="${current.lyrq}" type="both"/></td>
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
        </c:if>
    </div>
</div>
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

    var Product_Apply_NS = {

        init : function() {
            $.datepicker.setDefaults($.datepicker.regional['']);
            $(".jscal").each(function () {
                eval($(this).html());
            });

            $("#createBtn").click(function() {
                openwin("/rmweb/product-apply.rfid?method=create", "createProductApply");
            });

            $("#queryBtn").click(function() {
                Product_Apply_NS.query();
            });

            $(".product-apply-row").dblclick(function() {
                openwin("/rmweb/product-apply.rfid?method=edit&lydh=" + $(this).attr("data-lydh"), "editProductApply");
            });
        },

        query : function() {
            $("#myform").attr("action","<c:url value='/product-apply.rfid?method=list'/>");
            closes();
            $("#myform").submit();
        }

    };

    $(function() {
        Product_Apply_NS.init();
    });
    var query_cmd = Product_Apply_NS.query;
</script>
</body>
</html>