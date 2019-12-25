<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<!DOCTYPE html>
<head>
    <title>��������</title>
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
        ��������
    </div>
    <div id="materialApply">
        <form action="" method="post" name="materialApplyForm" id="materialApplyForm">
            <input type="hidden" id="dgdh" name="dgdh" value="${materialApply.dgdh}"/>
            <div id="block">
                <div id="blocktitle">���뵥</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">��������</td>
                        <td class="body">
                            <c:if test="${department!=null}">
                                <c:out value='${department.bmmc}'/>
                            </c:if>
                        </td>
                        <td class="head">������</td>
                        <td class="body">
                            <c:if test="${user!=null}">
                                <c:out value='${user.xm}'/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��������</td>
                        <td class="body">
                            <fmt:formatDate value="${materialApply.dgrq}" type="both"/>
                            <input type="hidden" name="dgrq" id="dgrq" value="${materialApply.dgrq}"/>
                        </td>
                        <td class="head">״̬</td>
                        <td class="body">
                            <select id="zt" name="zt">
                                <c:forEach var="status" items="${materialApplyStatus}">
                                    <c:choose>
                                        <c:when test="${status.status == materialApply.zt}">
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
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��Ʒ���</td>
                        <td class="body">
                            <select id="cplb" name="cplb">
                                <option value="">--��ѡ��--</option>
                                <c:forEach var="productCategory" items="${productCategories}">
                                    <c:choose>
                                        <c:when test="${productCategory.cplb == materialApply.cplb}">
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
                            <select id="cpdm" name="cpdm">
                                <option value="">--����ѡ���Ʒ���--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">����</td>
                        <td class="body">
                            <input type="text" name="sl" id="sl" value="${materialApply.sl}"/>
                        </td>
                        <td class="body" colspan="2"></td>
                    </tr>
                    <tr>
                        <td class="head">��ע</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100"><c:out value="${materialApply.bz}"/></textarea>
                        </td>
                    </tr>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <input type="button" name="savebutton" id="savebutton" value="����" class="button_save">
                        <input type="button" name="deleteBtn" id="deleteBtn" value="ȡ��" class="button_del">
                        <input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�" class="button_close">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
<script type="text/javascript">
    if (!window.console) {
        window.console = {
            log: function() {
                alert(arguments[0]);
            }
        }
    }

    var Edit_Order_Application_NS = {
        init : function() {
            $("#cplb").change(function() {
                Edit_Order_Application_NS.fetchProductByCategory($(this).val());
            });

            $("#savebutton").click(function() {
                Edit_Order_Application_NS.updateMaterialApply();
            });

            $("#deleteBtn").click(function() {
                if(confirm("�Ƿ�ȷ��ɾ���ö������룿")){
                    closes();
                    $.ajax({
                        url : "<c:url value='/material-apply.rfid?method=delete'/>",
                        type: "POST",
                        dataType: "json",
                        contentType:"application/x-www-form-urlencoded;charset=utf-8",
                        async: false,
                        data: {
                            dgdh : $("#dgdh").val()
                        },
                        success: function(data) {
                            if (data && data["resultId"] == "00") {
                    			displayDialog(2,"��������ȡ���ɹ���","");
                                window.opener.Order_Application_NS.query();
                                window.close();
                            }
                        }
                    });
                }
            });

            <c:if test="${materialApply.cplb != null}">
                <c:choose>
                    <c:when test="${materialApply.cpdm != null}">
                        Edit_Order_Application_NS.fetchProductByCategory($("#cplb").val(), '<c:out value="${materialApply.cpdm}"/>');
                    </c:when>
                    <c:otherwise>
                        Edit_Order_Application_NS.fetchProductByCategory($("#cplb").val());
                    </c:otherwise>
                </c:choose>
            </c:if>
            
            $('#sl').keydown(function(event){
            	fCheckInputIntOrBackSpace();
			});
        },

        updateMaterialApply : function() {
            if(!checkNull($("#cpdm"),"��Ʒ����")) return false;
            if(!checkNull($("#sl"), "����")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/material-apply.rfid?method=edit'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#materialApplyForm").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
            			displayDialog(2,"���³ɹ���","");
                        window.opener.Order_Application_NS.query();
                        window.close();
                    }else{
                    	displayDialog(3,data["resultMsg"],"");
                    }
                },
                dataType: "json"
            });
        },

        fetchProductByCategory : function(category, selectedItem) {
            $.get("product.frm", {method : 'fetch-by-category', category : category}, function(data) {
                if (data) {
                    $("#cpdm").empty();
                    if (data.length > 0) {
                        Edit_Order_Application_NS.addOption("cpdm", "", "---��ѡ��---");
                        $.each(data, function(i, product) {
                            if (selectedItem && selectedItem == product.cpdm) {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc, true);
                            } else {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                            }
                        });
                    } else {
                        Edit_Order_Application_NS.addOption("cpdm", "", "---δ�ҵ���Ӧ�Ĳ�Ʒ---");
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
        }
    };

    $(function() {
        Edit_Order_Application_NS.init();
    });
</script>
</body>
</html>