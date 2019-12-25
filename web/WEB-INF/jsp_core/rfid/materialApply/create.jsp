<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<!DOCTYPE html>
<head>
    <title>ԭ�϶���</title>
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
       	ԭ�϶���
    </div>
    <div id="orderApplication">
        <form action="" method="post" name="materialApplyForm" id="materialApplyForm">
            <div id="block">
                <div id="blocktitle">������</div>
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
                        <td class="head">��Ʒ���</td>
                        <td class="body">
                            <select id="cplb" name="cplb">
                                <option value="">--��ѡ��--</option>
                                <c:forEach var="productCategory" items="${productCategories}">
                                    <option value="${productCategory.cplb}">
                                        <c:out value="${productCategory.lbmc}" />
                                    </option>
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
                            <input type="text" name="sl" id="sl" style="width:60%"/>���䣬��${vestCapacity}*${boxCapacity}Ƭ��
                        </td>
                        <td class="body" colspan="2"></td>
                    </tr>
                    <tr>
                        <td class="head">��ע</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100"></textarea>
                        </td>
                    </tr>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <input type="button" name="savebutton" id="savebutton" onclick="save()" value="�ύ" class="button_save">
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

    var Create_Order_Application_NS = {
        init : function() {
            $("#cplb").change(function() {
                Create_Order_Application_NS.fetchProductByCategory($(this).val());
            });
            
            $('#sl').keydown(function(event){
            	fCheckInputIntOrBackSpace();
			});

            $("#savebutton").click(function() {
                Create_Order_Application_NS.createMaterialApply();
            });
            

        	if($("#cplb option").length>1)$($("#cplb option")[1]).attr("selected",true);
        	$("#cplb").change();
    	    if($("#cplb option").length>1)$($("#cpdm option")[1]).attr("selected",true);
        },
        
        createSuccessFn:function(){
        	window.opener.Order_Application_NS.query();
            window.close();
        },

        createMaterialApply : function() {
            if(!checkNull($("#cpdm"),"��Ʒ����")) return false;
            if(!checkNull($("#sl"), "����")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/material-apply.rfid?method=save'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#materialApplyForm").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
            			displayDialog(2,"����ɹ���","Create_Order_Application_NS.createSuccessFn();");
                    }else{
                    	displayDialog(3,data["resultMsg"],"");
                    }
                },
                dataType: "json"
            });
        },

        fetchProductByCategory : function(category) {
            
           	$.ajax({
           		url:"product.frm",
           		data:{method : 'fetch-by-category', category : category},
           		async:false,
           		dataType: "json",
           		success: function(data) {
           			if (data) {
                        $("#cpdm").empty();
                        if (data.length > 0) {
                            Create_Order_Application_NS.addOption("cpdm", "", "---��ѡ��---");
                            $.each(data, function(i, product) {
                                Create_Order_Application_NS.addOption("cpdm", product.cpdm, product.cpmc);
                            });
                        } else {
                            Create_Order_Application_NS.addOption("cpdm", "", "---δ�ҵ���Ӧ�Ĳ�Ʒ---");
                        }
                    }
           		}
           	})
        },

        addOption : function(selectorId, value, text) {
            var obj = document.getElementById(selectorId);
            var opn = document.createElement("OPTION");
            obj.appendChild(opn);//��׷��
            opn.innerText = text;
            opn.value = value;
        }
    };

    $(function() {
        Create_Order_Application_NS.init();
    });
</script>
</body>
</html>