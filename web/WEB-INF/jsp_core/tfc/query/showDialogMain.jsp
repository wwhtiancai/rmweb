<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>�������켣��ѯ����</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/hphm/hphm.css" rel="stylesheet" type="text/css">
<link href="rmjs/cal/dark.datetimepicker.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/jquery-position.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/hphm/hphm.js" type="text/javascript"></script>
<script type="text/javascript" src="rmjs/jquery.qtip.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datepicker.js"></script>
<script type="text/javascript" src="rmjs/cal/ui.datetimepicker.js"></script>

<script language="javascript">
function saveDialog() {
	if(!checkNull($("#cxnr"),"��ѯ�������")) return false;
	$("#myform").attr("action","<c:url value='/tfcQuery.tfc'/>?method=saveHandle");
	closes();
	$("#myform").ajaxSubmit({
		dataType:"json",async:false,contentType:"application/x-www-form-urlencoded;charset=utf-8",success:returns
	});
}

function returns(data) { 
	if(data["code"]=="1"){
		alert("�����ɹ���");
		window.returnValue=data;
		window.close();
	}else{
		alert(decodeURIComponent(data["message"]));
		opens();
	}
}



</script>
</head>
<body>
<div id="panel" style="display:none; width:100%">
	<div id="paneltitle">�������켣��ѯ����</div>
	<form method="post" name="myform" id="myform">
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<col width="30%">
			<col width="70%">
			<tr>
			 <td class="head"><span class="gotta">*</span>��ѯ�������</td>
			 <td class="body">
			 	<select name="cxnr" id="cxnr" style="width:100%">
					<option value="1">�ǳ�����</option>
					<option value="2">��������</option>
					<option value="3">������</option>
				</select>
			 </td>
			</tr>
			<tr>
			 <td class="head">��ע</td>
			 <td class="body">
			 	<textarea name="bz" id="bz" rows="6" style="width:98%"></textarea>
			 </td>
			</tr>
		</table>
		<input type="hidden" id="cxxh" name="cxxh" value='<c:out value="${cxxh}"/>' />
	</form>
	<table border="0" cellspacing="1" cellpadding="0" class="detail">
		<tr>
			<td class="command">
			<input type="button"  onclick="saveDialog()" value="�� ��"  class="button_save">
			<input type="button"  onclick="window.close()" value="�� ��" class="button_close">
			</td>
		</tr>
	</table>
</div>
</body>
</html>