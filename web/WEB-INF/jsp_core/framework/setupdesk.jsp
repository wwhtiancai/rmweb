<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>桌面设置</title>
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
<script languag="javascript">
function init(){
var strJssx;
var strCxdhs;
var tmpsx;
<c:if test="${userDeskMenus!=null}">
strCxdhs="<c:out value='${userDeskMenus}'/>"	
//初始化程序代号
if(document.all.cddh) {
	if (document.all.cddh.type=="checkbox") {	
		tmpsx=document.all["cddh"].value;
		if(strCxdhs.indexOf(tmpsx)>-1){
			document.all["cddh"].checked=true;
		}
	}else{
		 var yhz_length=document.all["cddh"].length;
		 for(var i=0;i<yhz_length;i++) {	
		 	tmpsx=document.all["cddh"].item(i).value;
		 	if(strCxdhs.indexOf(tmpsx)>-1){
				document.all["cddh"].item(i).checked=true;
			}
		 }
	}
}
</c:if>
}
//保存表单信息
function saveForm(){
var strModal;
var yhzs;
yhzs="";
var cddhs;
cddhs="";
var maxdesccount=0;
	if(document.all.cddh)
	{
		if (document.all.cddh.type=="checkbox")
		{
			//单个
			if (document.all["cddh"].checked)
			{
				cddhs=document.all["cddh"].value;
			}else{
				cddhs="";
			}
		}else{
			 var cddh_length=document.all["cddh"].length;
			 for(var i=0;i<cddh_length;i++)
			 {	
				 if(document.all["cddh"].item(i).checked==true)
				 {
				 maxdesccount=maxdesccount+1;
				 cddhs=cddhs+document.all["cddh"].item(i).value+"#";
				 }
			 }
			if (cddhs.substring(cddhs.length-1)=="#")
			{
				cddhs=cddhs.substring(0,cddhs.length-1);
			}
			 if(maxdesccount>49){
				 	alert("选择的显示桌面菜单数目大于49个！");
				 	return false;
				 }
		}
	}
document.all["cdbh"].value=cddhs;
strModal="<c:out value='${modal}'/>";
document.formedit.action="<c:url value='/sysuser.frm?method=Savedesk'/>&modal="+strModal;
document.formedit.submit();
return; 
}
//返回结果
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	alert(strMessage);
 	window.opener.location.reload();
  	window.close();
  }
  else
  {
    alert(strMessage);
    window.close();
  }
}


function check_item(obj)
{
var bj=obj.value + "-";
var qx_length=document.all["cddh"].length;

if(obj.checked==true)
{
 for(var i=0;i<qx_length;i++)
 {
  if(document.all["cddh"].item(i).value.indexOf(bj)>-1)
  {
      document.all["cddh"].item(i).checked=true;
  }
 }
}
else
{
for(var i=0;i<qx_length;i++)
 {
  if(document.all["cddh"].item(i).value.indexOf(bj)>-1)
  {
	document.all["cddh"].item(i).checked=false;
  }
 }
}
}
</script>
</head>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('桌面设置')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<div class="s1" style="height:8px;"></div>
	<form name="formedit" action="" method="post" target="paramIframe">
	<input type="hidden" name="cdbh">
<table width="98%" border="0" cellpadding="0" cellspacing="1" class="list_table" align="center">
	<c:forEach items="${foldList}" var="current">
		<tr class="text_12">
			<td width=100 bgcolor="#FAFAcd"><input type="checkbox"
				onclick="check_item(this)" name="cmldh"
				value="<c:out value='${current.mldh}'/>"><c:out
				value='${current.mlmc}' /></td>
			<td bgcolor="#FAFAFA"><c:set var="i" value="0" />
				<c:forEach items="${programList}" var="currentM">
				<c:choose>
					<c:when test="${current.mldh==currentM.mldh}">
						<input class="text_12" type="checkbox" name="cddh"
							value="<c:url value='${currentM.mldh}-${currentM.xtlb}${currentM.cdbh}'/>">
						<input type="text"
							style="background-color: #EFEFFE; border-style: solid; border-color: #EFEFFE; width: 132"
							value="<c:url value='${currentM.cxmc}'/>" readonly>
						<c:set var="i" value="${i+1}" />
						<c:if test="${i==4}">
							<c:set var="i" value="0" /><br/>
						</c:if>
					</c:when>
				</c:choose>
			</c:forEach></td>
		</tr>
	</c:forEach>
 		</table> 		
 			<table border="0" cellspacing="1" cellpadding="0" class="table_left_border_down text_12" width="98%">					
		<tr><td  class="list_body_out" align="right" >
		<input type="button" name="savebutton" onClick="saveForm()" value="保  存" class="button_close">
       <input type="button" name="Submit2" onClick="window.close()" value="退  出" class="button_close"></td>
		</tr>
	</table></form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
</html>