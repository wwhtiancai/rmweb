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
                            <c:if test="${bean!=null}">
                                <c:out value='${bean.czrxm}'/>
                            </c:if>
                        </td>
                        <td class="head">交付单位</td>
                        <td class="body">
                            <c:if test="${bean!=null}">
                                <c:out value='${bean.jfdw}'/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">数量</td>
                        <td class="body">
                            <c:if test="${bean!=null}">
                                <c:out value='${bean.count}'/>
                            </c:if>
                        </td>
                        <td class="head">入库日期</td>
                        <td class="body">
                            <c:if test="${bean!=null}">
								<fmt:formatDate value='${bean.rkrq}' pattern="yyyy-MM-dd HH:mm:ss" />
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="head">备注</td>
                        <td class="body" colspan="3">
                        	<c:if test="${bean!=null}">
                                <c:out value='${bean.bz}'/>
                            </c:if>
                        </td>
                    </tr>
                </table>
                
                <table border="0" cellspacing="0" cellpadding="0" class="detail">
					<tr>
						<td class="command">
						<input type="button" name="closebutton" onclick="javascript:window.close();" value="关闭" class="button_close">
						</td>
					</tr>
				</table>
            </div>

        </form>
        
    </div>
</div>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>