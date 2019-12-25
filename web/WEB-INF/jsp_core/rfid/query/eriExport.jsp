<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>��������Ϣ</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">��������Ϣ</div>
	<form action="" method="post" name="myform" id="myform">
	<c:if test="${bean==null}">
	    <input type="hidden" name="detailList" id="detailList">
	</c:if>
	<div id="block">
		<div id="blocktitle">��������Ϣ</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="30%">
			<tr>
				<td class="head">��ʼ����</td>
				<td class="body">
				    <input type="text" name="qskh" id="qskh">
				</td>
				<td class="head">��ֹ����</td>
				<td class="body">
				    <input type="text" name="zzkh" id="zzkh">
				</td>
			</tr>
			
            <tr>
            	<td class="body" colspan="4" style="text-align:right;">
					<input type="button" name="exportBtn" id="exportBtn" value="����" class="button_print">
					<input type="button" name="closebutton" onclick="javascript:window.close();" value="ȡ��" class="button_close">
            	</td>
            </tr>
		</table>
		
	</div>
	</form>
		
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" type="text/javascript">
		var khArr = [];	
	
		var Export_Warehouse_NS = {
	        init : function() {
	            $("#exportBtn").click(function() {
	            	Export_Warehouse_NS.exportKH();
	            });
	        },
	        
	        exportKH : function() {
	            if(!checkNull($("#qskh"),"��ʼ����")) return false;
	            if(!checkNull($("#zzkh"),"��ֹ����")) return false;
	        	var qskh = $("#qskh").val();
	        	var zzkh = $("#zzkh").val();
	            closes();

	    		window.open("/rmweb/eri.frm?method=export-eri&qskh="+qskh+"&zzkh="+zzkh);
	        }
	    };

	    $(function() {
	    	Export_Warehouse_NS.init();
	    });
	</script>
</body>
</html>
