<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<!DOCTYPE html>
<head>
    <title>安全模块查询</title>
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
      	安全模块查询
    </div>
    <div id="query">
        <div id="querytitle">
       		查询条件
        </div>
        <form action="" method="post" name="myform" id="myform">
			<input type="hidden" name="page" value="1"/>
            <table border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="50%">
                <tr>
                    <td class="head">序列号</td>
                    <td class="body">
                        <input type="text" id="xh" name="xh" value="${condition.xh}"/>
                    </td>
                    <td class="head">类型</td>
                    <td class="body">
                        <select id="lx" name="lx" style="width: 100%;">
                            <option value="">--请选择--</option>
                            <c:forEach var="type" items="${securityModelType}">
                                <c:choose>
                                    <c:when test="${type.dm == condition.lx}">
                                        <option value="${type.dm}" selected>
                                            <c:out value="${type.mc}" />
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${type.dm}">
                                            <c:out value="${type.mc}" />
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="submit">
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
                <col width="15%">
                <col width="15%">
                <col width="15%">
                <col width="15%">
                <col width="20%">
                <col width="10%">
                <col width="10%">
                <tr class="head">
                    <td> 序列号 </td>
                    <td> 类型 </td>
                    <td> 操作人 </td>
                    <td> 出厂日期 </td>
                    <td> 初始化日期 </td>
                </tr>
                <c:set var="rowcount" value="0" />
                <c:forEach items="${queryList}" var="current">
                    <tr class="out" data-xh="${current.xh}">
                        <td> <c:out value="${current.xh}" /> </td>
                        <td>
                        	<c:forEach var="type" items="${securityModelType}">
                                <c:if test="${type.dm == current.lx}">
                                       <c:out value="${type.mc}"/>
                                </c:if>
                            </c:forEach>
                        </td>
                        <td> <c:out value="${current.czr}" /> </td>
                        <td>
                            <fmt:formatDate value="${current.ccrq}" type="date"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${current.cshrq}" type="both"/>
                        </td>
                    </tr>
                    <c:set var="rowcount" value="${rowcount+1}" />
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
<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript">
    if (!window.console) {
        window.console = {
            log: function() {
                alert(arguments[0]);
            }
        }
    }
    
    var Security_Model = {

        init : function() {
            $.datepicker.setDefaults($.datepicker.regional['']);
            $(".jscal").each(function () {
                eval($(this).html());
            });
			
            $("#queryBtn").click(function() {
            	Security_Model.query();
            });

            $("tr.out").dblclick(function() {
                openwin("/rmweb/security-model.frm?method=detail&xh=" + $(this).attr("data-xh"), "editSecurity");
            });
        },

        query : function() {
            $("#myform").attr("action","<c:url value='/security-model.frm?method=list'/>");
            closes();
            $("#myform").submit();
        }
    };

    $(function() {
    	Security_Model.init();
    });
    var query_cmd = Security_Model.query;
</script>
</body>
</html>