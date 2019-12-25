<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>业务链接设置</title>
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
<script languag="javascript">
function init(){
}
//保存表单信息
function saveForm(){
	var begin=<c:out value='${linksize}'/>;
	var end=10;
	var mcs="";
	var dzs="";
	for(var i=begin+1;i<=10;i++){
		mcs+=document.getElementById("mc"+i).value+"!@#";
		dzs+=document.getElementById("dz"+i).value+"!@#";
	}
	document.getElementById("mcs").value=mcs;
	document.getElementById("dzs").value=dzs;
	document.formedit.action="<c:url value='/desktop.frm?method=saveExternal'/>";
	document.formedit.submit();
	resultSave("1","保存成功!"); 
}
//删除表单信息
function delexternal(xh){
	document.formedit.action="<c:url value='/desktop.frm?method=delExternal'/>&xh="+xh;
	document.formedit.submit();
	resultSave("1","删除成功!"); 
}
//返回结果
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	alert(strMessage);
 	window.opener.refresh('4','desktop.frm?method=external');
 	//window.opener.location.reload();
  	window.close();
  }
  else
  {
    alert(strMessage);
    window.close();
  }
}

</script>
</head>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('业务链接设置');</script>
<script language="JavaScript" type="text/javascript">vio_seach();</script>
	<div class="s1" style="height:8px;"></div>
	<form name="formedit" action="" method="post" target="paramIframe">
	<input type="hidden" name="mcs" id="mcs">
	<input type="hidden" name="dzs" id="dzs">
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table" align="center">
		<c:forEach items="${linklist}" var="current">
		<tr class="text_12">
			<td width="40%" bgcolor="#FAFAcd" style="padding-left:6px;height:24px;">
			<c:out value="${current.ljmc}"/>
			</td>
			<td bgcolor="#FAFAFA" style="padding-left:6px">
			<c:out value="${current.ljdz}"/>
			</td>
			<td bgcolor="#FAFAFA" align="center">
			<a href="#" onclick="delexternal(${current.xh})">删除</a>
			</td>
		</tr>
		</c:forEach>
		<c:forEach begin="${linksize+1}" end="10" varStatus="var">
		<tr class="text_12">
			<td width="40%" bgcolor="#FAFAcd" style="padding-left:6px;height:24px;">
			<input type="text" id="mc${var.index}" name="mc${var.index}" value="" style="width:98%;">
			</td>
			<td bgcolor="#FAFAFA" style="padding-left:6px">
			<input type="text" id="dz${var.index}" name="dz${var.index}" value="" style="width:98%;">
			</td>
			<td bgcolor="#FAFAFA" align="center">
			</td>
		</tr>
		</c:forEach>
 	</table> 		
 	<table border="0" cellspacing="1" cellpadding="0" class="table_left_border_down text_12" width="100%">					
		<tr><td  class="list_body_out" align="right" >
		<input type="button" name="savebutton" onClick="saveForm()" value="保  存" class="button_close">
        <input type="button" name="Submit2" onClick="window.close()" value="退  出" class="button_close"></td>
		</tr>
	</table>
	</form>
<script language="JavaScript" type="text/javascript">vio_down();</script>
</body>
</html>