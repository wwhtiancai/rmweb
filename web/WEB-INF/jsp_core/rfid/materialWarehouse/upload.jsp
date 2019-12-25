<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>上传原料清单</title>
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
       	上传原料清单
    </div>
    <div id="materialWarehouse">
        <form action="/rmweb/material-warehouse.rfid?method=upload" method="post" name="myform" id="myform" enctype="multipart/form-data">
            <div id="block">
                <div id="blocktitle">上传原料清单</div>
                <div id="blockmargin">8</div>
                <table border="0" cellspacing="1" cellpadding="0" class="detail">
                    <col width="20%">
                    <col width="80%">
                    <tr>
                        <td class="head">上传</td>
                        <td class="body">
                        	<input type="hidden" name="rkdh" id="rkdh" value="${rkdh}">
                            <input type="file" name="file_upload" style="width:80%;">
                        </td>
                    </tr>
                </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" class="detail">
                <tr>
                    <td class="command">
                        <input type="submit" name="savebutton" id="savebutton" value="提交" class="button_save">
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

    var Upload_Material_Warehouse_NS = {
        init : function() {
            
        },

        upload : function() {
            closes();
            $("#myform").attr("action","<c:url value='/material-warehouse.rfid?method=upload'/>");
            //$("#myform").submit();
    		$("#myform").ajaxSubmit({
    			dataType:"json",async:false,
    			contentType:"application/x-www-form-urlencoded;charset=utf-8",
    			success: function(data) {
                    if (data && data["resultId"] == "00") {
            			displayDialog(2,"上传成功！","");
                        window.close();
                    }
                }
    		}); 
            
        }
    };

    $(function() {
    	Upload_Material_Warehouse_NS.init();
    });
</script>
</body>
</html>