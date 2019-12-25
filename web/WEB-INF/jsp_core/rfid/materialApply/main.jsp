<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<!DOCTYPE html>
<head>
    <title>原料订购管理维护</title>
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
      	<c:if test="${type==1}">原料订购管理</c:if>
	    <c:if test="${type==2}">原料订购交付</c:if>
	    <c:if test="${type==3}">原料订购撤销</c:if>
    </div>
    <div id="query">
        <div id="querytitle">
       		查询条件
        </div>
        <form action="" method="post" name="myform" id="myform">
			<input type="hidden" name="page" value="1"/>
			<input type="hidden" name="type" value="${type}"/>
            <table border="0" cellspacing="1" cellpadding="0" class="query">
                <col width="10%">
                <col width="23%">
                <col width="10%">
                <col width="23%">
                <col width="10%">
                <col width="24%">
                <tr>
                    <td class="head">经办人</td>
                    <td class="body">
                        <input type="text" id="jbrxm" name="jbrxm" value="${condition.jbrxm}"/>
                    </td>
                    
                    <td class="head">数量（片）</td>
                    <td class="body">
                        <input type="text" id="slks" name="slks" value="${condition.slks}" style="width:38%"/>&nbsp;-&nbsp;
                        <input type="text" id="slks" name="sljs" value="${condition.sljs}" style="width:38%"/>
                    </td>
                    <td class="head">订购日期</td>
                    <td class="body">
                        <h:datebox id="dgrqks" name="dgrqks" showType="1" width="32%"/>&nbsp;-&nbsp;
                        <h:datebox id="dgrqjs" name="dgrqjs" showType="1" width="32%"/>
                    </td>
                </tr>
                <tr>
                    <td class="head">产品类别</td>
                    <td class="body">
                        <select id="cplb" name="cplb">
                            <option value="">--请选择--</option>
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
                    <td class="head">产品名称</td>
                    <td class="body">
                        <select id="cpdm" name="cpdm">
                            <option value="">--请先选择产品类别--</option>
                        </select>
                    </td>
                    <td class="head">状态</td>
                    <td class="body">
                        <select id="zt" name="zt">
                            <option value="">---请选择---</option>
                            <c:forEach var="status" items="${materialApplyStatus}">
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
                    </td>
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
            	<c:if test="${condition.type==2}">
	                <col width="15%">
	                <col width="15%">
	                <col width="15%">
	                <col width="10%">
	                <col width="15%">
	                <col width="15%">
	                <col width="15%">
                </c:if>
                <c:if test="${condition.type!=2}">
                	<col width="15%">
	                <col width="15%">
	                <col width="15%">
	                <col width="15%">
	                <col width="20%">
	                <col width="20%">
                </c:if>
                
                <tr class="head">
                	<td> 订购单号 </td>
                    <td> 经办人 </td>
                    <td> 订购日期 </td>
                    <td> 产品名称 </td>
                    <td> 数量（片） </td>
                    <c:if test="${condition.type==2}">
                    	<td>已入库数量（片）</td>
                    </c:if>
                    
                    <td> 状态 </td>
                </tr>
                <c:set var="rowcount" value="0" />
                <c:forEach items="${queryList}" var="current">
                    <tr class="out" data-dgdh="${current.dgdh}">
                    	<td>
                            <c:out value="${current.dgdh}" />
                        </td>
                        <td>
                            <c:out value="${current.jbrxm}" />
                        </td>
                        <td>
                            <fmt:formatDate value="${current.dgrq}" type="both"/>
                        </td>
                        <%-- <td>
                            <c:out value="${current.lbmc}" />
                        </td> --%>
                        <td>
                            <c:out value="${current.cpmc}" />
                        </td>
                        <td><c:out value="${current.sl}"/></td>
                        <c:if test="${condition.type==2}">
	                    	<td><c:out value="${current.yrksl}"/></td>
	                    </c:if>
                        
                        <td>
                            <c:forEach var="status" items="${materialApplyStatus}">
                                <c:if test="${status.status == current.zt}">
                                    <c:out value="${status.desc}"/>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <c:set var="rowcount" value="${rowcount+1}" />
                </c:forEach>
                <tr>
                    <td colspan="${condition.type == 2 ? 7 : 6}" class="page">
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
            
            var type = "<c:out value='${type}'/>";
            
            if(type == 1){
            	$("#createBtn").click(function() {
                    openwin("/rmweb/material-apply.rfid?method=create", "editMaterialApply");
                });
            }else{
            	$("#createBtn").hide();
            	$("#zt").attr("disabled",true);
            }
            

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
                    Order_Application_NS.addOption("cpdm", "", "---请先选择产品类别---");
                }
            });
            
			<c:if test="${condition!=null}">
				$("#dgrqks").val("<c:out value='${condition.dgrqks}'/>");
				$("#dgrqjs").val("<c:out value='${condition.dgrqjs}'/>");
			</c:if>
			
			$('#slks').keydown(function(event){
				fCheckInputIntOrBackSpace();
			});
			$('#sljs').keydown(function(event){
				fCheckInputIntOrBackSpace();
			});

            $("#queryBtn").click(function() {
                Order_Application_NS.query();
            });

            $("tr.out").dblclick(function() {
                openwin("/rmweb/material-apply.rfid?method=view&dgdh=" + $(this).attr("data-dgdh")+"&type="+type, "viewMaterialApply");
            });
        },

        fetchProductByCategory : function(category, selectedItem) {
            $.get("product.frm", {method : 'fetch-by-category', category : category}, function(data) {
                if (data) {
                    $("#cpdm").empty();
                    if (data.length > 0) {
                        Order_Application_NS.addOption("cpdm", "", "---请选择---");
                        $.each(data, function(i, product) {
                            if (selectedItem && selectedItem == product.cpdm) {
                                Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc, true);
                            } else {
                                Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                            }
                        });
                    } else {
                        Order_Application_NS.addOption("cpdm", "", "---未找到相应的产品---");
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
        },

        query : function() {
            $("#myform").attr("action","<c:url value='/material-apply.rfid?method=list'/>");
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