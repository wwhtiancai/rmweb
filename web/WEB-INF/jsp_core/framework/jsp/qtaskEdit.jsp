<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@ page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title>修改任务</title>
<%=JspSuport.getInstance().JS_ALL%>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
</head>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('修改任务')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<div class="s1" style="height:8px;"></div>
	<form name="sysparaForm" action="" method="post" target="paramIframe">
	<table witdth ="100%" border="0" cellspacing="1" cellpadding="0" class="list_table">
		<input type="hidden" name="xtlb" value="<c:out value='${task.xtlb}'/>">
        <input type="hidden" name="rwid" value="<c:out value='${task.rwid}'/>">
        <input type="hidden" name="rwzt" value="<c:out value='${task.rwzt}'/>">
        <input type="hidden" name="rwzt" value="<c:out value='${task.qtip}'/>">
        <input type="hidden" name="rwzt" value="<c:out value='${task.yxipdz}'/>">            
		<tr>
			<td width="20%" class="list_headrig">任务名称&nbsp;</td>
			<td width="80%" class="list_body_out"><c:out value='${task.rwmc}'/></td>
		</tr>
		<tr>
			<td class="list_headrig">上次执行时间&nbsp;</td>
			<td class="list_body_out"><c:out value='${task.previousFireTime}'/></td>
		</tr>
		<tr>
			<td class="list_headrig">下次执行时间&nbsp;</td>
			<td class="list_body_out"><c:out value='${task.nextFireTime}'/></td>
		</tr>
		<tr>
			<td class="list_headrig">状态&nbsp;</td>
			<td class="list_body_out"><c:out value='${task.zt}'/></td>
		</tr>
		<c:if test="${task.qtip=='1'}">
		<tr>
			<td class="list_headrig">任务运行IP地址&nbsp;</td>
			<td class="list_body_out"><input type="text" name="yxipdz" size="50" maxlength="256" value="<c:out value='${task.yxipdz}'/>" /></td>
		</tr>
		</c:if>
		<tr>
		<td colspan="2" class="list_body_out" align="right">
		<input type="button" value="立即执行" onClick="runi()" class="button">
		<c:if test="${task.qtip=='1'}">
		<input type="button" value="保存运行服务器地址" onClick="saveCode('3')" class="button">
		</c:if>
		<input name="exit" type=button class="button" style="cursor:hand;"  value=" 退 出 " onclick="quit()"></td>
		</tr>
	</table>
	</form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>
<script languag="javascript">
function init(){
}
function runi(){
	document.sysparaForm.action="<c:url value='/qtm.frm?method=runImme'/>";
	document.sysparaForm.submit();
	}
function saveCode(zt){
	document.sysparaForm.action="<c:url value='/qtm.frm?method=saveTask'/>&newrwzt="+zt;
	document.sysparaForm.submit();
}


//返回结果
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
	alert(strMessage);
    window.close();
  }
  else
  {
    alert(strMessage);
  }
}
</script>
