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
    <c:if test="${type==1}">��������</c:if>
    <c:if test="${type==2}">��������</c:if>
    <c:if test="${type==3}">��������</c:if>
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
                            <c:out value='${materialApply.bmmc}'/>
                        </td>
                        <td class="head">������</td>
                        <td class="body">
                            <c:out value='${materialApply.jbrxm}'/>
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
                            <c:forEach var="status" items="${materialApplyStatus}">
                                <c:if test="${status.status == materialApply.zt}">
                                    <c:out value="${status.desc}"/>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��Ʒ���</td>
                        <td class="body">
                            <c:out value='${materialApply.lbmc}'/>
                        </td>
                        <td class="head">��Ʒ����</td>
                        <td class="body">
                            <c:out value='${materialApply.cpmc}'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">����</td>
                        <td class="body" colspan="3">
                            <c:out value='${materialApply.sl}'/> (Ƭ)
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��ע</td>
                        <td class="body" colspan="3">
                            <c:out value='${materialApply.bz}'/>
                        </td>
                    </tr>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <input type="button" name="deliverybutton" id="deliverybutton" value="����" class="button_save">
                        <input type="button" name="deleteBtn" id="deleteBtn" value="����" class="button_del">
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
        	var type = "<c:out value='${type}'/>";
        	if(type == 1){
       			$("#deliverybutton").hide();
       			$("#deleteBtn").hide();
        	}else if(type == 2){
        		$("#deliverybutton").hide();
        		$("#deliverybutton").show();
       			$("#deleteBtn").hide();
        	}else if(type == 3){
        		$("#deliverybutton").hide();
       			$("#deleteBtn").show();
        	}

            $("#deliverybutton").click(function() {
                Edit_Order_Application_NS.deliveryMaterial();
            });

            $("#deleteBtn").click(function() {
            	Edit_Order_Application_NS.revokeApply();
            });

            $('#sl').keydown(function(event){
            	fCheckInputIntOrBackSpace();
			});
        },

        deliveryMaterial : function() {
            closes();
            $.ajax({
                url: "<c:url value='/material-apply.rfid?method=delivery'/>",
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                data: $("#materialApplyForm").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
            			displayDialog(2,"�����ɹ���","Edit_Order_Application_NS.submitSuccessFn();");
                        
                    }else{
                    	displayDialog(3,data["resultMsg"],"");
                    }
                },
                dataType: "json"
            });
        },
        
        revokeApply: function(){
        	if(confirm("�Ƿ�ȷ�������ö������룿")){
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
                			displayDialog(2,"�������볷���ɹ���","Edit_Order_Application_NS.submitSuccessFn();");
                        }
                    }
                });
            }
        },
        
        submitSuccessFn:function(){
        	window.opener.Order_Application_NS.query();
        	window.close();
        }
    };

    $(function() {
        Edit_Order_Application_NS.init();
    });
</script>
</body>
</html>