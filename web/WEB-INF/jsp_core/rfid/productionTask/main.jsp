<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>�����������</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="css/xtree.css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />
</head>
<body>
<div id="panel">
    <div id="paneltitle">
        ��������
    </div>
    <div id="query">
        <div id="querytitle">
            ��ѯ����
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
                    <td class="head">��������ţ�</td>
                    <td class="body">
                        <input type="text" name="rwh" id="rwh" value="${condition.rwh}"/>
                    </td>
                    <td class="head">�������</td>
                    <td class="body">
                        <select id="rwdm" name="rwdm">
                            <option value="">---��ѡ��---</option>
                            <option value="DZBSCSH" <c:if test="${condition.rwdm eq 'DZBSCSH'}">selected</c:if>>�������ӱ�ʶ��ʼ��</option>
                        </select>
                    </td>
                    <td class="head">״̬��</td>
                    <td class="body">
                        <select id="zt" name="zt">
                            <option value="">---��ѡ��---</option>
                            <c:forEach var="current" items="${productionTaskStatus}">
                                <c:choose>
                                    <c:when test="${current.status == condition.zt}">
                                        <option value="${current.status}" selected>
                                            <c:out value="${current.desc}" />
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${current.status}">
                                            <c:out value="${current.desc}" />
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="head">��Ʒ���ƣ�</td>
                    <td class="body">
                        <select id="cpdm" name="cpdm">
                            <option value="">---��ѡ��---</option>
                            <c:forEach var="current" items="${products}">
                                <c:choose>
                                    <c:when test="${current.cpdm == condition.cpdm}">
                                        <option value="${current.cpdm}" selected>
                                            <c:out value="${current.cpmc}" />
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${current.cpdm}">
                                            <c:out value="${current.cpmc}" />
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">�ƻ����ڣ�</td>
                    <td class="body" colspan="3">
                        <h:datebox id="jhrqks" name="jhrqks" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="jhrqjs" name="jhrqjs" showType="1" width="32%"/>
                    </td>
                </tr>
                <tr>
                    <td class="submit" colspan="6">
                        <input id="clearBtn" type="button" class="button_close" value="���"/>
                        <input id="createBtn" type="button" class="button_new" value="����"/>
                        <input id="queryBtn" type="button" class="button_query" value="��ѯ"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="queryresult" style="margin-top: 20px;">
        <c:if test="${queryList!=null}">
            <div id="result">
                <div id="resulttitle">
                    ��ѯ����
                </div>
                <table border="0" cellspacing="1" cellpadding="0" class="list">
                    <col width="4%">
                    <col width="12%">
                    <col width="12%">
                    <col width="10%">
                    <col width="9%">
                    <col width="9%">
                    <col width="12%">
                    <col width="10%">
                    <col width="11%">
                    <col width="10%">
                    <tr class="head">
                        <td>
                            ���
                        </td>
                        <td>
                            ���������
                        </td>
                        <td>
                            ��Ʒ����
                        </td>
                        <td>��ʼ����</td>
                        <td>��ֹ����</td>
                        <td>������</td>
                        <td>�ƻ�����</td>
                        <td>��ʼ����</td>
                        <td>�������</td>
                        <td>״̬</td>
                    </tr>
                    <c:set var="rowcount" value="0" />
                    <c:forEach items="${queryList}" var="current">
                        <tr class="out task-row" data-rwh='<c:out value="${current.rwh}" />' style="cursor:pointer">
                            <td><c:out value="${rowcount+1}" /></td>
                            <td><c:out value="${current.rwh}" /></td>
                            <td>
                                <c:forEach var="product" items="${products}">
                                    <c:if test="${current.cpdm == product.cpdm}">
                                        <c:out value="${product.cpmc}"/>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td><c:out value="${current.qskh}"/></td>
                            <td><c:out value="${current.zzkh}"/></td>
                            <td><c:out value="${current.jbr}"/></td>
                            <td><fmt:formatDate value="${current.jhrq}" type="both"/></td>
                            <td><fmt:formatDate value="${current.ksrq}" type="both"/></td>
                            <td><fmt:formatDate value="${current.wcrq}" type="both"/></td>
                            <td>
                                <c:forEach var="status" items="${productionTaskStatus}">
                                    <c:if test="${current.zt == status.status}">
                                        <c:out value="${status.desc}" />
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        <c:set var="rowcount" value="${rowcount+1}" />
                    </c:forEach>
                    <tr>
                        <td colspan="10" class="page">
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

    var Production_Task_NS = {

        init : function() {
            $.datepicker.setDefaults($.datepicker.regional['']);
            $(".jscal").each(function () {
                eval($(this).html());
            });
            $("#createBtn").click(function() {
                openwin("/rmweb/production-task.rfid?method=create", "createProductionTask");
            });

            $("#queryBtn").click(function() {
                Production_Task_NS.query();
            });

            $("#clearBtn").click(function(){
                $("input[type='text']").val('');
                $("select option[value='']").attr("selected", "selected");
            });

            <c:if test="${condition.jhrqks != null}">
                    $("#jhrqks").val('<fmt:formatDate value="${condition.jhrqks}" type="both" pattern="yyyy-MM-dd"/>');
            </c:if>

            <c:if test="${condition.jhrqjs != null}">
                    $("#jhrqjs").val('<fmt:formatDate value="${condition.jhrqjs}" type="both" pattern="yyyy-MM-dd"/>');
            </c:if>

            $(".task-row").dblclick(function() {
                openwin("/rmweb/production-task.rfid?method=edit&rwh=" + $(this).attr("data-rwh"), "editProductionTask");
            });
        },

        query : function() {
            $("#myform").attr("action","<c:url value='/production-task.rfid?method=list'/>");
            closes();
            $("#myform").submit();
        }

    };

    $(function() {
        Production_Task_NS.init();
    });

    var query_cmd = Production_Task_NS.query;
</script>
</body>
</html>