<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>��ͨ����ͳ��</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<link href="chart/css/stat.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
<script type="text/javascript">
<!--

//-->
</script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">��ͨ����ͳ��</div>
	<div id="query">
		<div id="querytitle">ͳ������</div>
		<%=request.getAttribute("html") %>
	</div>
</div>
</body>
</html>