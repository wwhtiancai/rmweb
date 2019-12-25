<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>原料入库</title>
    <link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
    <link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
    <link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="rmjs/cal/flora.all.css" type="text/css"
          media="screen" title="Flora (Default)" />
    <link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet"
          type="text/css">
    <style type="text/css">
    	.notice{
    		color:red;
    	}
    </style>
</head>
<body>
<div id="panel" style="display: none">
    <div id="paneltitle">
       	原料入库
    </div>
    <div id="materialWarehouse">
        <!-- <form action="" method="post" name="materialWarehouseForm" id="materialWarehouseForm"> -->
        
        <form action="/rmweb/material-warehouse.rfid?method=saveCount" method="post" name="myform" id="myform" enctype="multipart/form-data">
            <div id="block">
                <div id="blocktitle">入库单</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">操作人</td>
                        <td class="body">
                            <c:if test="${user!=null}">
                                <c:out value='${user.xm}'/>
                            </c:if>
                        </td>
                        <td class="head">交付单位</td>
                        <td class="body">
                            <input type="text" name="jfdw" id="jfdw"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">数量</td>
                        <td class="body">
                            <input type="text" name="count" id="count"/>
                        </td>
                        <td class="head">入库日期</td>
                        <td class="body">
                            <h:datebox id="rkrqStr" name="rkrqStr" showType="1" width="64%"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">备注</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100"></textarea>
                        </td>
                    </tr>
                </table>
                
                <table border="0" cellspacing="0" cellpadding="0" class="detail">
					<tr>
						<td class="command">
						<input type="button" name="savebutton" id="savebutton" onclick="save()" value="保存" class="button_save">
						<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
						</td>
					</tr>
				</table>
            </div>

        </form>
        
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

    var Create_Material_Warehouse_Count_NS = {
        init : function() {
        	$.datepicker.setDefaults($.datepicker.regional['']);
			$(".jscal").each(function () {
                eval($(this).html());
            });
            $("#savebutton").click(function() {
            	Create_Material_Warehouse_Count_NS.createMaterialCount();
            });

        },

        createMaterialCount : function() {
            if(!checkNull($("#jfdw"),"交付单位")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/material-warehouse.rfid?method=saveCount'/>",
                contentType:"application/x-www-form-urlencoded;charset=gb2312",
                data: $("#myform").serialize(),
                async: false,
                type: "POST",
                success: function(data) {
                    if (data && data["resultId"] == "00") {
                    	window.opener.query_cmd();
            			displayDialog(2,"保存成功！","");
                        window.close();
                    }
                },
                dataType: "json"
            });
        }
    };

    $(function() {
    	Create_Material_Warehouse_Count_NS.init();
    });
</script>
</body>
</html>