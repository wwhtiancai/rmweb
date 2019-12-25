<%@page contentType="text/html; charset=gb2312"%>
<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%
String glbm=(String)request.getAttribute("glbm");
String minbmjb=(String)request.getAttribute("minbmjb");
String flag=(String)request.getAttribute("flag");
String rtn= (String)request.getAttribute("rtn");
if(rtn==null||rtn.equals("")){
	rtn = "2";
}
System.out.println(rtn);
%>
<html>
<head>
	<title>请选择管理部门</title>
</head>
<link rel='STYLESHEET' type='text/css' href='codebase/style.css'>
<link rel="STYLESHEET" type="text/css" href="codebase/dhtmlxtree.css">

<script  src="codebase/dhtmlxcommon.js"></script>
<script  src="codebase/dhtmlxtree.js"></script>
<script>
var tree;
function tondblclick(id){
  <%if(!rtn.equals("1")){%>
  var id=id+"$"+tree.getItemText(id);
  <%}%>
	window.returnValue=id;
	window.close();
}		

function tonload(){
	tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
	tree.setImagePath("codebase/imgs/csh_bluebooks/");
	
	tree.setOnDblClickHandler(tondblclick);
	tree.loadXML("department.frm?method=queryBmdmTreeList&glbm=<%=glbm%>&minbmjb=<%=minbmjb%>&flag=<%=flag%>");
}
function tok(){
var id=tree.getSelectedItemId();
  <%if(!rtn.equals("1")){%>
  var id=tree.getSelectedItemId()+"$"+tree.getSelectedItemText();
    <%}%>
	window.returnValue=id;
	window.close();
}

function tt(){
	window.close();
}

</script>

<body onload="tonload();" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" scroll="yes">
<table>
	<tr>
		<td colspan="2">
			<div id="treeboxbox_tree" style="width:370; height:350;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;"/>
		</td>
	<tr>
	<tr>
		<td align="right" style="padding-right:5px;">
			<input type="button" name="qr" value=" 确认 " onclick="tok();">
		</td>	
		<td align="left" style="padding-left:5px;">	
			<input type="button" name="qx" value=" 取消 " onclick="tt();">
		</td>
	<tr>
		
</table>


</body>
</html>
