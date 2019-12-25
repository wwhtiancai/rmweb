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
        
        <form action="/rmweb/material-warehouse.rfid?method=upload" method="post" name="myform" id="myform" enctype="multipart/form-data">
            <div id="block">
                <div id="blocktitle">入库单</div>
                <div id="blockmargin">8</div>
                <div class="notice">
               		先下载excel模板，根据模板填写好标识信息后上传excel，完善信息，最后提交入库
                </div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">经办人</td>
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
                    	<td class="head">订购单号</td>
                        <td class="body">
                            <input type="text" name="dgdh" id="dgdh"/>
                        </td>
                        <td class="body" colspan="2"></td>
                    </tr>
                    <tr>
                        <td class="head">备注</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">入库详情上传</td>
                        <td class="body">
                            <input type="file" name="file_upload"  style="width:100%;">
                        </td>
                        <td class="command" colspan="2">
                            <a href="/rmweb/template/ylrkd.xls" >下载模板</a>
                            <input type="submit" name="uploadbutton" id="uploadbutton" value="上传" class="button_save">
                            <input type="button" name="savebutton" disabled="true" id="savebutton" value="提交" class="button_save">
                            <input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
                        </td>
                    </tr>
                </table>
                
                
	            <table border="0" cellspacing="0" cellpadding="0" class="detail">
	            	<col width="20%">
	            	<col width="40%">
	                <col width="40%">

	            </table>
            </div>

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

    var Create_Material_Warehouse_NS = {
        init : function() {
            $("#savebutton").click(function() {
            	Create_Material_Warehouse_NS.createMaterialApply();
            });

            <c:if test="${errMsg != null}">
            	var errMsg = "<c:out value='${errMsg}'/>"+"</br>请重新上传符合条件的Excel！";
            	displayDialog(3,errMsg,"");
            </c:if>
        },

        createMaterialApply : function() {
            if(!checkNull($("#jfdw"),"交付单位")) return false;
            closes();
            $.ajax({
                url: "<c:url value='/material-warehouse.rfid?method=save'/>",
                contentType:"application/x-www-form-urlencoded;charset=gb2312",
                data: $("#materialWarehouseForm").serialize(),
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
    	Create_Material_Warehouse_NS.init();
    });
</script>
</body>
</html>