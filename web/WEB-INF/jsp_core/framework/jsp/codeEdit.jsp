<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title>ϵͳ����ά��</title>
<%=JspSuport.getInstance().JS_ALL%>
<script languag="javascript">

function deletecode(){
if(confirm("�Ƿ�ȷ��ɾ���ü�¼��")){
document.codeForm.action="<c:url value="/code.frm?method=removecode"/>";
document.codeForm.submit();
}
}

function init(){

<c:if test="${code!=null}">
document.all["dmlb"].value="<c:out value='${code.dmlb}'/>";
document.all["dmz"].value="<c:out value='${code.dmz}'/>";
document.all["dmz"].readOnly=true;
document.all["dmsm1"].value="<c:out value='${code.dmsm1}'/>";
document.all["dmsm2"].value="<c:out value='${code.dmsm2}'/>";
document.all["dmsm3"].value="<c:out value='${code.dmsm3}'/>";
document.all["dmsm4"].value="<c:out value='${code.dmsm4}'/>";
document.all["dmsx"].value="<c:out value='${code.dmsx}'/>";
document.all["zt"].value="<c:out value='${code.zt}'/>";
document.all["xtlb"].value="<c:out value='${code.xtlb}'/>";
</c:if>
var i = 0;
checkfields[i++] = new CheckObj("dmz","����ֵ",FRM_CHECK_NULL,0,1);
checkfields[i++] = new CheckObj("dmsm1","����˵��1",FRM_CHECK_NULL,0,1);
checklen = i;	
}

function saveCode(){

var strModal;
strModal="<c:out value='${modal}'/>";
if(checkallfields(checkfields,1)==0){
	return 0;
}
document.codeForm.action="<c:url value='/code.frm?method=savecode'/>&modal="+strModal+"&cdbh=<c:out value='${cdbh}'/>";
document.codeForm.submit();

}


//���ؽ��
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	strCommond="";
 	<c:if test="${modal=='new'}">
 	strCommond=strCommond+"document.all[\"dmz\"].value=\"\";";
 	strCommond=strCommond+"document.all[\"dmsm1\"].value=\"\";";
 	strCommond=strCommond+"document.all[\"dmsm2\"].value=\"\";";
 	strCommond=strCommond+"document.all[\"dmsm3\"].value=\"\";";
 	strCommond=strCommond+"document.all[\"dmsm4\"].value=\"\";";
 	</c:if>
 	strCommond=strCommond+"document.codeForm.action=\"<c:url value='/fresh.frm?method=freshCode&refreshlx=4'/>\";";
 	strCommond=strCommond+"document.codeForm.submit();";
 	strCommond=strCommond+"window.close();";
 	strCommond=strCommond+"window.opener.query_cmd();";
 	displayInfoHtml(strMessage,strCommond);
  }
  else
  {
    displayInfoHtml(strMessage);
  }
}


//���ؽ��
function resultDel(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	strCommond="document.codeForm.action=\"<c:url value='/fresh.frm?method=freshCode&refreshlx=4'/>\";";
 	strCommond=strCommond+"document.codeForm.submit();";
 	strCommond=strCommond+"window.opener.query_cmd();";
 	strCommond=strCommond+"window.close();";
 	displayInfoHtml(strMessage,strCommond);      
  }
  else
  {
    displayInfoHtml(strMessage);
  }
}
</script>
</head>
<body onload="init()">
<script language="JavaScript" type="text/javascript">vio_title('ϵͳ����ά��')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<form name="codeForm" action="" method="post" target="paramIframe">
	<input type="hidden" name="xtlb" value="<c:out value='${xtlb}'/>"/> 
	<div class="s1" style="height:3px;"></div>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" width="98%" align="center"  valign="top">
	<tr>
		<td width="10%" class="list_headrig">�������</td>
		<td width="40%" class="list_body_out"><input type="text" readonly name="dmlb"  class="text_12 text_size_120" value="<c:out value='${dmlb}'/>"></td>
		<td width="10%" class="list_headrig">����ֵ</td>
		<td width="40%" class="list_body_out"><input type="text" name="dmz" class="text_12 text_size_120" maxlength="<c:out value='${dmcd}'/>"></td>
	</tr>
	<tr>
		<td class="list_headrig">����˵��1</td>
		<td class="list_body_out"><input type="text" name="dmsm1" maxlength="128" class="text_12 text_size_120"></td>
		<td class="list_headrig">����˵��2</td>
		<td class="list_body_out"><input type="text" name="dmsm2" maxlength="128"  class="text_12 text_size_120"></td>
	</tr>
	<tr>
		<td class="list_headrig">����˵��3</td>
		<td class="list_body_out"><input type="text" name="dmsm3" maxlength="128"  class="text_12 text_size_120"></td>
		<td class="list_headrig">����˵��4</td>
		<td class="list_body_out"><input type="text" name="dmsm4"  maxlength="128" class="text_12 text_size_120"></td>
	</tr>
	<tr>
		<td class="list_headrig">��������</td>
		<td class="list_body_out">
		<select name="dmsx" class="text_12 text_size_120">
		    <option value="1">1:��ά��</option>
			<option value="0">0:����ά��</option>
		</select>
		</td>
		<td class="list_headrig">״̬</td>
		<td class="list_body_out">
		<select name="zt" class="text_12 text_size_120">
			<option value="1">1:��Ч</option>
			<option value="0">0:��Ч</option>
		</select>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="right" class="list_body_out">
		<input type="button" value=" �� �� " onclick="saveCode()" class="button">
		<c:if test="${modal=='edit'}">
		<input type="button" value=" ɾ �� " onclick="deletecode()" class="button">
		</c:if>
		<input type="button" value=" �� �� " onclick="quit()" class="button">
		</td>
	</tr>
</table></form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>

