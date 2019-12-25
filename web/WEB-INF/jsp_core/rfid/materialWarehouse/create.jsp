<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>ԭ�����</title>
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
       	ԭ�����
    </div>
    <div id="materialWarehouse">
        <!-- <form action="" method="post" name="materialWarehouseForm" id="materialWarehouseForm"> -->
        
        <form action="/rmweb/material-warehouse.rfid?method=upload" method="post" name="myform" id="myform" enctype="multipart/form-data">
            <div id="block">
                <div id="blocktitle">��ⵥ</div>
                <div id="blockmargin">8</div>
                <div class="notice">
               		������excelģ�壬����ģ����д�ñ�ʶ��Ϣ���ϴ�excel��������Ϣ������ύ���
                </div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                    <tr>
                        <td class="head">������</td>
                        <td class="body">
                            <c:if test="${user!=null}">
                                <c:out value='${user.xm}'/>
                            </c:if>
                        </td>
                        <td class="head">������λ</td>
                        <td class="body">
                            <input type="text" name="jfdw" id="jfdw"/>
                        </td>
                    </tr>
                    <tr>
                    	<td class="head">��������</td>
                        <td class="body">
                            <input type="text" name="dgdh" id="dgdh"/>
                        </td>
                        <td class="body" colspan="2"></td>
                    </tr>
                    <tr>
                        <td class="head">��ע</td>
                        <td class="body" colspan="3">
                            <textarea name="bz" id="bz" rows="4" cols="100"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">��������ϴ�</td>
                        <td class="body">
                            <input type="file" name="file_upload"  style="width:100%;">
                        </td>
                        <td class="command" colspan="2">
                            <a href="/rmweb/template/ylrkd.xls" >����ģ��</a>
                            <input type="submit" name="uploadbutton" id="uploadbutton" value="�ϴ�" class="button_save">
                            <input type="button" name="savebutton" disabled="true" id="savebutton" value="�ύ" class="button_save">
                            <input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�" class="button_close">
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
            	var errMsg = "<c:out value='${errMsg}'/>"+"</br>�������ϴ�����������Excel��";
            	displayDialog(3,errMsg,"");
            </c:if>
        },

        createMaterialApply : function() {
            if(!checkNull($("#jfdw"),"������λ")) return false;
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
            			displayDialog(2,"����ɹ���","");
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