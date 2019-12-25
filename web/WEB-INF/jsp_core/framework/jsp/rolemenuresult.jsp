<%@page contentType="text/html; charset=gb2312"%>
<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%
String rolemenu=(String)request.getAttribute("rolemenu");
%>
<html>
<head>
	<title>查看角色拥有菜单</title>
</head>
<link rel='STYLESHEET' type='text/css' href='codebase/style.css'>
<link rel="STYLESHEET" type="text/css" href="codebase/dhtmlxtree.css">

<script  src="codebase/dhtmlxcommon.js"></script>
<script  src="codebase/dhtmlxtree.js"></script>
<script>
function quit(){
	window.close();
}

function init(){
	domtab.init();
}
function tab_c(idx){
	var tab=document.getElementsByName("tab")
	for(var i=0;i<tab.length;i++) {
		tab[i].style.display="none";
	}
	var idxtab=document.getElementById("tab"+idx);
	idxtab.style.display=""
}
</script>
<link href="theme/domtab/domtab.css" rel="stylesheet" type="text/css"> 
<script language="javascript" type="text/javascript"  src="theme/domtab/domtab.js"></script>
<body onload="init();">
<table border="0" cellspacing="1" cellpadding="0" align="center">
	<tr>
		<td colspan="2">
			<div id="treeboxbox_tree" style="height:90%;width:100%;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;">
			<%=rolemenu%>
			</div>
		</td>
	</tr>
</table>


</body>
</html>
