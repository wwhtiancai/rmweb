<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title>行政区划维护</title>
<%=JspSuport.getInstance().JS_ALL%>
<script languag="javascript">

function deletecode(){
if(confirm("是否确信删除该记录？")){
document.codeForm.action="<c:url value="/code.frm?method=removexzqh"/>";
document.codeForm.submit();
}
}

function init(){

<c:if test="${code!=null}">
document.all["dmlb"].value="<c:out value='${code.dmlb}'/>";
document.all["dmz"].value="<c:out value='${code.dmz}'/>";
document.all["dmsm1"].value="<c:out value='${code.dmsm1}'/>";
document.all["dmsm2"].value="<c:out value='${code.dmsm2}'/>";
document.all["dmsm3"].value="<c:out value='${code.dmsm3}'/>";
document.all["dmsm4"].value="<c:out value='${code.dmsm4}'/>";
document.all["dmsx"].value="<c:out value='${code.dmsx}'/>";
document.all["zt"].value="<c:out value='${code.zt}'/>";
document.all["xtlb"].value="<c:out value='${code.xtlb}'/>";
document.all["dmz2"].readOnly=true;
var xzqh="<c:out value='${code.dmz}'/>";
document.all["dmz1"].value=xzqh.substr(0,4);
document.all["dmz2"].value=xzqh.substr(4,2);
</c:if>
<c:if test="${code==null}">
document.all["dmz1"].value="<c:out value='${xzqh}'/>";
</c:if>
}

function saveCode(){

var strModal;
strModal="<c:out value='${modal}'/>";
codeForm.dmz.value=codeForm.dmz1.value+codeForm.dmz2.value;
if(codeForm.dmz.value.length!=6)
{
	displayInfoHtml("[00R9912J1]:行政区划代码只能为6位");
	return false;
}
if(codeForm.dmz.value.substr(2,2)=='00')
{
	displayInfoHtml("[00R9912J2]:行政区划代码第3、4位不能为'00'");
	return false;
}
if(codeForm.dmsm1.value=="")
{
	displayInfoHtml("[00R9912J3]:行政区划名称不能为空");
	return false;
}
document.codeForm.action="<c:url value='/code.frm?method=savexzqh'/>&modal="+strModal+"&cdbh=<c:out value='${cdbh}'/>";
document.codeForm.submit();

}


//返回结果
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	var strCommond = "document.codeForm.action=\"<c:url value='/fresh.frm?method=freshCode&refreshlx=4'/>\";";
	strCommond += "document.codeForm.submit();";	
	strCommond += "window.close();";
    strCommond += "window.opener.query_cmd();";  
 	displayInfoHtml(strMessage,strCommond);
  }
  else
  {
    displayInfoHtml(strMessage);
  }
}


//返回结果
function resultDel(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	alert(strMessage);
    document.codeForm.action="<c:url value='/fresh.frm?method=freshCode&refreshlx=4'/>";
	document.codeForm.submit();	 	
    window.opener.query_cmd();
    window.close();
  }
  else
  {
    alert(strMessage);
  }
}
</script>
</head>
<body onload="init()">
<script language="JavaScript" type="text/javascript">vio_title('行政区划维护')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<form name="codeForm" action="" method="post" target="paramIframe">
	<input type="hidden" name="xtlb" value="00"/> 
	<input type="hidden" name="dmlb" value="0050"/>
	<input type="hidden" name="dmz" />
	<input type="hidden" name="dmsm3" />
	<input type="hidden" name="dmsm4" />
	<input type="hidden" name="dmsx" value="1"/>
	<input type="hidden" name="ywdx" />
	<input type="hidden" name="zt" value="1"/>
	<div class="s1" style="height:3px;"></div>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" width="98%" align="center"  valign="top">
	<tr>
		<td width="15%" class="list_headrig">行政区划代码&nbsp;</td>
		<td width="30%" class="list_body_out">
		<input type="text" readonly name="dmz1"  class="text_12 text_size_40" >
		<input type="text" name="dmz2"  class="text_12 text_size_60" maxlength="<c:out value='${dmcd}'/>">
		</td>
		<td width="15%" class="list_headrig">行政区划名称&nbsp;</td>
		<td width="40%" class="list_body_out"><input type="text" name="dmsm1" maxlength="128" class="text_12 text_size_300" ></td>
	</tr>
	<tr>
		<td width="15%" class="list_headrig">详细信息&nbsp;</td>
		<td width="40%" class="list_body_out" colspan="3">
		<input type="text" name="dmsm2" maxlength="128"  class="text_12 text_size_300" >
		</td>
	</tr>	
	<td colspan="4" align="right" class="list_body_out">
		<input type="button" value=" 保 存 " onclick="saveCode()" class="button">
		<c:if test="${modal=='edit'}">
		<input type="button" value=" 删 除 " onclick="deletecode()" class="button">
		</c:if>
		<input type="button" value=" 退 出 " onclick="quit()" class="button">
		</td>
	</tr>
</table></form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>

