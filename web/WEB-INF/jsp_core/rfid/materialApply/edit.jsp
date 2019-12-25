<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<!DOCTYPE html>
<head>
    <title>订购申请</title>
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
        订购申请
    </div>
    <div id="materialApply">
        <form action="" method="post" name="materialApplyForm" id="materialApplyForm">
            <input type="hidden" id="dgdh" name="dgdh" value="${materialApply.dgdh}"/>
            <div id="block">
                <div id="blocktitle">申请单</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">订购部门</td>
                        <td class="body">
                            <c:if test="${department!=null}">
                                <c:out value='${department.bmmc}'/>
                            </c:if>
                        </td>
                        <td class="head">经办人</td>
                        <td class="body">
                            <c:if test="${user!=null}">
                                <c:out value='${user.xm}'/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">订购日期</td>
                        <td class="body">
                            <fmt:formatDate value="${materialApply.dgrq}" type="both"/>
                            <input type="hidden" name="dgrq" id="dgrq" value="${materialApply.dgrq}"/>
                        </td>
                        <td class="head">状态</td>
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
                        <td class="head">产品类别</td>
                        <td class="body">
                            <select id="cplb" name="cplb">
                                <option value="">--请选择--</option>
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
                        <td class="head">产品名称</td>
                        <td class="body">
                            <select id="cpdm" name="cpdm">
                                <option value="">--请先选择产品类别--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">数量</td>
                        <td class="body">
                            <input type="text" name="sl" id="sl" value="${materialApply.sl}"/>
                        </td>
                        <td class="body" colspan="2"></td>
                    </tr>
                    <tr>
                        <td class="head">备注</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100"><c:out value="${materialApply.bz}"/></textarea>
                        </td>
                    </tr>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <input type="button" name="savebutton" id="savebutton" value="保存" class="button_save">
                        <input type="button" name="deleteBtn" id="deleteBtn" value="取消" class="button_del">
                        <input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
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
                if(confirm("是否确定删除该订购申请？")){
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
                    			displayDialog(2,"订购申请取消成功！","");
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
            if(!checkNull($("#cpdm"),"产品代码")) return false;
            if(!checkNull($("#sl"), "数量")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/material-apply.rfid?method=edit'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#materialApplyForm").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
            			displayDialog(2,"更新成功！","");
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
                        Edit_Order_Application_NS.addOption("cpdm", "", "---请选择---");
                        $.each(data, function(i, product) {
                            if (selectedItem && selectedItem == product.cpdm) {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc, true);
                            } else {
                                Edit_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                            }
                        });
                    } else {
                        Edit_Order_Application_NS.addOption("cpdm", "", "---未找到相应的产品---");
                    }
                }
            }, "json");
        },

        addOption : function(selectorId, value, text, selected) {
            var obj = document.getElementById(selectorId);
            var opn = document.createElement("OPTION");
            obj.appendChild(opn);//先追加
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