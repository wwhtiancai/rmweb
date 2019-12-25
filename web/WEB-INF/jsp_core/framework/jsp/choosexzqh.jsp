<%@page contentType="text/html; charset=gb2312"%>
<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%
String xzqh=(String)request.getAttribute("xzqh");
String cxfw=(String)request.getAttribute("cxfw");
String lb=(String)request.getAttribute("lb");
%>
<html>
<head>
	<title>请选择行政区划</title>
</head>
<link rel='STYLESHEET' type='text/css' href='codebase/style.css'>
<link rel="STYLESHEET" type="text/css" href="codebase/dhtmlxtree.css">

<script  src="codebase/dhtmlxcommon.js"></script>
<script  src="codebase/dhtmlxtree.js"></script>
<script>
var tree;
function tondblclick(id){
  var id=id+"$"+tree.getItemText(id);
	window.returnValue=id;
	window.close();
}		

function tonload(){
	tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
	tree.setImagePath("codebase/imgs/csh_bluebooks/");
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(true);
	var url="code.frm?method=queryXzqhTreeList&xzqh=<%=xzqh%>&cxfw=<%=cxfw%>&lb=<%=lb%>&checkbox=true";
	tree.loadXML(url);
}
function tok(){
//  var id=tree.getSelectedItemId()+"$"+tree.getSelectedItemText();
//	window.returnValue=id;
  	var xzqhlist=tree.getAllCheckedBranches();
  	var valarray = xzqhlist.split(",");
  	var retarray=new Array();	
  	//retarray排序
  	valarray=valarray.sort();
  	var j=0;
  	for(i=0;i<valarray.length;i++){
  		if(valarray[i]!=''){
  			//非总队的行政区划
  			if (valarray[i].length>0)
  			{
  			   retarray[j]=new Array();
  			   retarray[j][0]=valarray[i];
  			   retarray[j][1]=tree.getItemText(valarray[i]);	
  			   j++;
  			}
  		}
	}
	window.returnValue=retarray;
	window.close();
}

function tt(){
	window.close();
}

</script>

<body onload="tonload();">
<table>
	<tr>
		<td colspan="2">
			<div id="treeboxbox_tree" style="width:360; height:300;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;"/>
		</td>
	<tr>
	<tr>
		<td align="right">
			<input type="button" name="qr" value="确认" onclick="tok();">
		</td>	
		<td align="left">	
			<input type="button" name="qx" value="取消" onclick="tt();">
		</td>
	<tr>
		
</table>


</body>
</html>
