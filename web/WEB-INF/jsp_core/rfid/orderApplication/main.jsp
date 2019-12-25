<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
    <title>��������ά��</title>
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
        <c:out value="${type == 1 ? '��������' : '�������'}"/>
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
                    <td class="head">���벿��</td>
                    <td class="body">
                        <h:managementbox list='${xjbm}' id='sqbm' haveNull='1' defaultVal="${condition.sqbm}" cssStyle="width:100%"/>
                    </td>
                    <td class="head">������</td>
                    <td class="body">
                        <input type="text" id="jbr" name="jbr" value="${condition.jbr}" style="width:100%"/>
                    </td>
                    <td class="head">״̬</td>
                    <td class="body">
                        <c:choose>
                            <c:when test="${type == 1}">
                                <select id="zt" name="zt" style="width:100%">
                                    <option value="">---��ѡ��---</option>
                                    <c:forEach var="status" items="${orderApplicationStatus}">
                                        <c:choose>
                                            <c:when test="${status.status == condition.zt}">
                                                <option value="${status.status}" selected>
                                                    <c:out value="${status.desc}"/>
                                                </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${status.status}">
                                                    <c:out value="${status.desc}"/>
                                                </option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <c:out value="�����"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="head">��Ʒ���</td>
                    <td class="body">
                        <select id="cplb" name="cplb" style="width: 100%;">
                            <option value="">--��ѡ��--</option>
                            <c:forEach var="productCategory" items="${productCategories}">
                                <c:choose>
                                    <c:when test="${productCategory.cplb == condition.cplb}">
                                        <option value="${productCategory.cplb}" selected>
                                            <c:out value="${productCategory.lbmc}" />
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${productCategory.cplb}">
                                            <c:out value="${productCategory.lbmc}" />
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="head">��Ʒ����</td>
                    <td class="body">
                        <select id="cpdm" name="cpdm" style="width:100%">
                            <option value="">--����ѡ���Ʒ���--</option>
                        </select>
                    </td>
                    <td class="head">��������</td>
                    <td class="body">
                        <h:datebox id="sqrqks" name="sqrqks" showType="1" width="32%"/>&nbsp;-&nbsp;<h:datebox id="sqrqjs" name="sqrqjs" showType="1" width="32%"/>
                    </td>
                </tr>
                <tr>
                    <td class="submit" colspan="6">
                        <c:if test="${type == 1}">
                            <input id="createOrderApplicationBtn" type="button" class="button_new" value="����"/>
                        </c:if>
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
                ����б�
            </div>
            <table border="0" cellspacing="1" cellpadding="0" class="list">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="10%">
                <tr class="head">
                	<td>���뵥��</td>
                    <td> ������ </td>
                    <td> ���벿�� </td>
                    <td>��������</td>
                    <td> ��Ʒ��� </td>
                    <td> ��Ʒ���� </td>
                    <td>������Ƭ��</td>
                    <td>״̬</td>
                </tr>
                <c:set var="rowcount" value="0" />
                <c:forEach items="${queryList}" var="current">
                    <tr class="out" data-sqdh="${current.sqdh}">
                    	<td><c:out value="${current.sqdh}" /></td>
                        <td>
                            <c:out value="${current.jbr}" />
                        </td>
                        <td>
                            <c:out value="${current.bmmc}" />
                        </td>
                        <td>
                            <fmt:formatDate value="${current.sqrq}" type="both"/>
                        </td>
                        <td>
                            <c:out value="${current.lbmc}" />
                        </td>
                        <td>
                            <c:out value="${current.cpmc}" />
                        </td>
                        <td><c:out value="${current.sl}"/></td>
                        <td>
                            <c:forEach var="status" items="${orderApplicationStatus}">
                                <c:if test="${status.status == current.zt}">
                                    <c:out value="${status.desc}"/>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <c:set var="rowcount" value="${rowcount+1}" />
                </c:forEach>
                <tr>
                    <td colspan="8" class="page">
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

    var Order_Application_NS = {

        init : function() {
            $.datepicker.setDefaults($.datepicker.regional['']);
            $(".jscal").each(function () {
                eval($(this).html());
            });
            $("#createOrderApplicationBtn").click(function() {
                openwin("/rmweb/order-application.frm?method=create", "editOrderApplication");
            });

            <c:if test="${condition.cplb != null}">
                <c:choose>
                    <c:when test="${condition.cpdm != null}">
                        Order_Application_NS.fetchProductByCategory($("#cplb").val(), '<c:out value="${condition.cpdm}"/>');
                    </c:when>
                    <c:otherwise>
                        Order_Application_NS.fetchProductByCategory($("#cplb").val());
                    </c:otherwise>
                </c:choose>
            </c:if>

            $("#cplb").change(function() {
                if ($(this).val()) {
                    Order_Application_NS.fetchProductByCategory($(this).val());
                } else {
                    $("#cpdm").empty();
                    Order_Application_NS.addOption("cpdm", "", "---����ѡ���Ʒ���---");
                }
            });

            $("#queryBtn").click(function() {
                Order_Application_NS.query();
            });

            $("tr.out").dblclick(function() {
                openwin("/rmweb/order-application.frm?method=edit&type=${type}&sqdh=" + $(this).attr("data-sqdh"), "editOrderApplication");
            });
        },

        fetchProductByCategory : function(category, selectedItem) {
            $.get("product.frm", {method : 'fetch-by-category', category : category}, function(data) {
                if (data) {
                    $("#cpdm").empty();
                    if (data.length > 0) {
                        Order_Application_NS.addOption("cpdm", "", "---��ѡ��---");
                        $.each(data, function(i, product) {
                            if (selectedItem && selectedItem == product.cpdm) {
                                Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc, true);
                            } else {
                                Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                            }
                        });
                    } else {
                        Order_Application_NS.addOption("cpdm", "", "---δ�ҵ���Ӧ�Ĳ�Ʒ---");
                    }
                }
            }, "json");
        },

        addOption : function(selectorId, value, text, selected) {
            var obj = document.getElementById(selectorId);
            var opn = document.createElement("OPTION");
            obj.appendChild(opn);//��׷��
            opn.innerText = text;
            opn.value = value;
            if (selected) {
                opn.selected = true;
            }
        },

        query : function() {
            $("#myform").attr("action","<c:url value='/order-application.frm?method=list&type=${type}'/>");
            closes();
            $("#myform").submit();
        }

    };

    $(function() {
        Order_Application_NS.init();
    });
    var query_cmd = Order_Application_NS.query;
</script>
</body>
</html>