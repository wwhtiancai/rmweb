<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>�켣������־��ϸ��Ϣ</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="rmjs/zoom/jqzoom.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/zoom/jquery.jqzoom.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/jquery.loadthumb.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
$(document).ready(function(){
	$("[limit]").limit();
});

</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">�켣������־��ϸ��Ϣ</div>
	<div id="block">
		   <div id="blocktitle">��ѯ��־��Ϣ</div>
				<table border="0" cellspacing="1" cellpadding="0" class="detail" style="table-layout:fixed">
					<col width="10%">
					<col width="23%">
					<col width="10%">
					<col width="23%">
					<col width="10%">
					<col width="24%">
					<tr>
						<td class="head">��ѯ����</td>
						<td class="body"><c:out value='${tpq.glbm}'/></td>								
						<td class="head">��ѯ����</td>
						<td class="body"><c:out value='${tpq.jz}'/></td>
						<td class="head">��ѯ��</td>
						<td class="body"><c:out value='${tpq.cxrxm}'/></td>
					</tr>
					<tr>
						<td class="head">��ѯԭ��</td>
						<td class="body"><c:out value='${tpq.cxyymc}'/></td>
						<td class="head">��ѯ����</td>
						<td class="body"><c:out value='${tpq.cxlxmc}'/></td>
						<td class="head">��ѯ��Χ</td>
						<td class="body"><c:out value='${tpq.cxfwmc}'/></td>
					</tr>
					<tr>  
						<td	class="head">�Ƿ���</td>
						<td class="body"><c:out value='${tpq.sffk}'/></td>
						<td class="head">��ѯ���</td>
						<td class="body" colspan="3"><c:out value='${tpq.cxjg}'/>
						</td>
					</tr>
					<tr height="50">  
						<td	class="head">��������</td>
						<td class="body" colspan="5" ><c:out value='${tpq.cxnr}' escapeXml="false"/></td>
					</tr>
					<tr height="50">  
						<td	class="head">��ѯ����</td>
						<td class="body" colspan="5" ><c:out value='${tpq.cxtj}' escapeXml="false"/></td>
					</tr>
					<tr height="50">  
						<td	class="head">��ע</td>
						<td class="body" colspan="5" ><c:out value='${tpq.bz}' escapeXml="false"/></td>
					</tr>
				</table>
		</div>
		<table border="0" cellspacing="1" cellpadding="0" class="detail">
			<tr>
				<td class="command">
					<input type="button" name="Submit2" onclick="window.close()" value="�ر�" class="button_close">
				</td>
			</tr>
		</table>
</div>
</body>
</html>
