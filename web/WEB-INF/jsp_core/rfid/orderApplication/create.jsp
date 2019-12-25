<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
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
    <div id="orderApplication">
        <form action="" method="post" name="orderApplicationForm" id="orderApplicationForm">
            <div id="block">
                <div id="blocktitle">申请单</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">申请部门</td>
                        <td class="body">
                            <c:if test="${department!=null}">
                                <c:out value='${department.bmmc}'/>
                            </c:if>
                        </td>
                        <td class="head">申请人</td>
                        <td class="body">
                            <c:if test="${user!=null}">
                                <c:out value='${user.xm}'/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">产品类别</td>
                        <td class="body">
                            <select id="cplb" name="cplb">
                                <option value="">--请选择--</option>
                                <c:forEach var="productCategory" items="${productCategories}">
                                    <option value="${productCategory.cplb}">
                                        <c:out value="${productCategory.lbmc}" />
                                    </option>
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
                    <tr id="dqkc">
                    	<td class="head">当前库存</td>
                        <td class="body" colspan="3">
                            
                        </td>
                    </tr>
                    <tr>
                        <td class="head">数量</td>
                        <td class="body">
                            <input type="text" name="sl" id="sl" style="width:60%"/>（箱，共${vestCapacity}*${boxCapacity}片）
                        </td>
                        <td class="head">联系人</td>
                        <td class="body">
                            <input type="text" name="lxr" id="lxr"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">联系电话</td>
                        <td class="body">
                            <input type="text" name="lxdh" id="lxdh"/>
                        </td>
                        <td class="head">传真</td>
                        <td class="body">
                            <input type="text" name="cz" id="cz"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">备注</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100"></textarea>
                        </td>
                    </tr>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <input type="button" name="savebutton" id="savebutton" onclick="" value="提交" class="button_save">
                        <input type="button" name="closebutton" onclick="javascript:window.close();" value="取消" class="button_close">
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

    var Create_Order_Application_NS = {
        init : function() {
            $("#cplb").change(function() {
                Create_Order_Application_NS.fetchProductByCategory($(this).val());
            });
            
            $("#cpdm").change(function() {
                Create_Order_Application_NS.fetchDqkc($(this).val());
            });

            $("#savebutton").click(function() {
                Create_Order_Application_NS.createOrderApplication();
            });
            
            $("#dqkc").hide();
        },
        
        saveSuccessFn : function(){
        	window.opener.query_cmd();
            window.close();
        },
        
        createOrderApplication : function() {
            if(!checkNull($("#cplb"),"产品类别")) return false;
            if(!checkNull($("#sl"), "数量")) return false;
            if(!checkNull($("#lxr"), "联系人")) return false;
            if(!checkNull($("#lxdh"), "联系电话")) return false;
            if(!checkNull($("#cz"), "传真")) return false;
            closes();
            
            $.ajax({
                url: "<c:url value='/order-application.frm?method=create'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#orderApplicationForm").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
                        displayDialog(2,"创建成功！","Create_Order_Application_NS.saveSuccessFn();");
                    }
                },
                dataType: "json"
            });
        },

        fetchProductByCategory : function(category) {
            $.get("product.frm", {method : 'fetch-by-category', category : category}, function(data) {
                if (data) {
                    $("#cpdm").empty();
                    if (data.length > 0) {
                        Create_Order_Application_NS.addOption("cpdm", "", "---请选择---");
                        $.each(data, function(i, product) {
                            Create_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                        });
                    } else {
                        Create_Order_Application_NS.addOption("cpdm", "", "---未找到相应的产品---");
                    }
                }
            }, "json");
        },

        addOption : function(selectorId, value, text) {
            var obj = document.getElementById(selectorId);
            var opn = document.createElement("OPTION");
            obj.appendChild(opn);//先追加
            opn.innerText = text;
            opn.value = value;
        },
        
        fetchDqkc: function(cpdm){
        	$.ajax({
                url: "<c:url value='/inventory.frm?method=fetchDqkc'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: "cpdm="+cpdm,
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
                        $("#dqkc .body").html(data["resultMsg"]);
                        $("#dqkc").show();
                    }else{
                    	displayDialog(3,data["resultMsg"],"");
                    }
                },
                dataType: "json"
            });
        }
    };

    $(function() {
        Create_Order_Application_NS.init();
    });
</script>
</body>
</html>