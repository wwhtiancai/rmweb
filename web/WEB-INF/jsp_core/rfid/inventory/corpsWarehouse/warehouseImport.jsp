<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>�ܶ����ά��</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">�ܶ����ά��</div>
	<form action="/rmweb/corps-warehouse.frm?method=upload-warehouse" method="post" name="myform" id="myform" enctype="multipart/form-data">
	<div id="block">
		<div id="blocktitle">�ܶ������Ϣ�ϴ�</div>
		<div id="blockmargin">8</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="10%">
			<col width="30%">
			<col width="10%">
			<col width="30%">
			<tr>
				<td class="head">Excel�����Ϣ</td>
				<td class="body">
		           	<input type="file" name="file_upload"  style="width:100%;">
				</td>
			</tr>
			<tr>
				<td class="head">��ע</td>
				<td class="body" colspan="3">
					<textarea name="bz" id="bz" rows="4" style="width:92%;"></textarea>
				</td>
			</tr>
		</table>
		
		
		<table border="0" cellspacing="0" cellpadding="0" class="detail">
			<tr>
				<td class="command">
				<input type="button" name="savebutton" id="savebutton" value="����" class="button_save">
				<input type="button" name="closebutton" onclick="javascript:window.close();" value="�ر�" class="button_close">
				</td>
			</tr>
		</table>
	</div>
	
</form>

</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script language="JavaScript" src="rmjs/json2.js" type="text/javascript"></script>
	<script language="JavaScript" type="text/javascript">
		$(function() {
			$("#savebutton").click(function() {
				closes();
				Tools_NS.showLoading("���ڵ��룬���Ե�...");
				$("#myform").submit();
			});
		});

		function success() {
			Tools_NS.showConfirmDialog({
				id : 'success',
				title : '����ɹ�',
				message : '����ɹ�',
				width: 400,
				cancellable: false,
				onConfirm: function() {
					window.close();
					window.opener.query_cmd();
				}
			})
		}
	</script>
</body>
</html>
