<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%
  String str_tilte="";
  String str_czlx=request.getParameter("czlx");//�������� wh=ά�� cx=��ѯ
  if(str_czlx.equals("wh"))
  {
    str_tilte="ϵͳ����ά��";
  }
  if(str_czlx.equals("cx"))
  {
    str_tilte="ϵͳ�����ѯ";
  }
  %>
<html>
<head>
<title><%=str_tilte%></title>
<%=JspSuport.getInstance().JS_ALL%>
</head>
<body onload="init()">
<script language="JavaScript" type="text/javascript">vio_title('�����嵥')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<Form name="formedit" action="" method="post"  target="paramIframe"> 
	<input type=hidden name="lbsx">
	<input type=hidden name="dmlb">
	<input type=hidden name="dmcd">
	<input type=hidden name="xtlb">
</Form>
	<div class="s1" style="height:3px;"></div>
<table  border="0" align="center" cellpadding="0" cellspacing="1" class="detail_table ">

				<col/>
				<col/>
				<col/>
				<col/>
				<col/>
		<thead>
					   	<tr align="center" class="detail_head" height="20">
						<td width="10%">����ֵ</td>
						<td>����˵��1</td>
						<td width="40%">��������</td>
						<td width="10%">״̬</td>
						<c:if test="${czlx=='wh'}">
						<td width="10%">����</td>
						</c:if>
				  </tr>
				  <%int i=0;%>
<c:forEach items="${codes}" var="current">
<% i=i+1;
String str_class = "list_body_tr_1";
 if (i % 2 == 0) {
str_class = "list_body_tr_2";}%>
		
		<c:if test="${current.dmsx==1}">
		 <c:set var="kfxg" value="${'��ά��'}"/>
		</c:if>
		<c:if test="${current.dmsx==0}">
		 <c:set var="kfxg" value="${'����ά��'}"/>
		</c:if>
		
		<c:if test="${current.zt==1}">
		 <c:set var="ztmc" value="${'��Ч'}"/>
		</c:if>
		<c:if test="${current.zt==0}">
		 <c:set var="ztmc" value="${'��Ч'}"/>
		</c:if>
					 <tr class="<%=str_class%>"	height="23">
					   <td><c:out value="${current.dmz}"/></td>
					   <td align="left"><c:out value="${current.dmsm1}"/></td>
					   <td align="left"><c:out value="${kfxg}"/></td>
					   <td><c:out value="${ztmc}"/></td>
					   <c:if test="${czlx=='wh'}">
					   <td><c:if test="${current.dmsx==1}"><input type="button" name="bjcode" onclick="bj('<c:out value="${current.xtlb}"/>','<c:out value="${current.dmlb}"/>','<c:out value="${current.dmz}"/>')" value="��  ��" class="button_close"></c:if></td>
					   </c:if>
					</tr>
 		</c:forEach>
  </thead>
</table>
<c:if test="${codetype!=null}">
<table border="0" cellspacing="1" cellpadding="0" class="table_left_border_down text_12" width="98%">
<tr align=right><td align=right>
<c:if test="${codetype.lbsx==1}">
<c:if test="${czlx=='wh'}">
<input type="button" name="newbutton" onclick="newcode()" value="��������" class="button">
</c:if>
</c:if>
<input name="exit" type=button class="button" style="cursor:hand;"  value=" �� �� " onclick="quit()">
</td>
</tr>
</table>

</c:if>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
<iframe name="paramIframe" style="DISPLAY: none" height="0" width="500"></iframe>
</html>
<script languag="javascript">
function bj(xtlb,dmlb,dmz){
var windowheight=screen.height;
var windowwidth =screen.width;
    windowheight=(windowheight-500)/2;
    windowwidth=(windowwidth-800)/2;
    window.open("<c:url value='/code.frm?method=editcode'/>&dmlb="+dmlb+"&dmz="+dmz+"&xtlb="+xtlb+"&czlx=<c:out value='${czlx}'/>","excmain","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=500,top="+windowheight+",left="+windowwidth);
}

function init(){
<c:if test="${codetype!=null}">
document.all["dmlb"].value="<c:out value='${codetype.dmlb}'/>";
document.all["xtlb"].value="<c:out value='${codetype.xtlb}'/>";
document.all["dmcd"].value="<c:out value='${codetype.dmcd}'/>";
document.all["lbsx"].value="<c:out value='${codetype.lbsx}'/>";
</c:if>
}
function query_cmd(){
   document.formedit.target="";
   formedit.action="code.frm?method=editOne&czlx=<c:out value='${czlx}'/>";
   document.formedit.submit();
}

//��������
function newcode(){
//
var dmcd;
dmcd="<c:out value='${codetype.dmcd}'/>";
var xtlb="<c:out value='${codetype.xtlb}'/>";
var windowheight=screen.height;
var windowwidth =screen.width;
    windowheight=(windowheight-500)/2;
    windowwidth=(windowwidth-800)/2;
    window.open("<c:url value='/code.frm?method=newcode'/>&dmlb="+document.all["dmlb"].value+"&dmcd="+dmcd+"&xtlb="+xtlb,"excmain","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=500,top="+windowheight+",left="+windowwidth);
}

//���ؽ��
function resultDel(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	alert(strMessage);
	window.close();
    window.opener.query_cmd();
  }
  else
  {
    alert(strMessage);
  }
}

//���ؽ��
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {
 	alert(strMessage);
 	<c:if test="${modal=='new'}">
	document.all["dmlb"].value="";
	document.all["lbsm"].value="";
	document.all["dmcd"].value="";
	document.all["bz"].value="";
	</c:if>
    window.opener.query_cmd();
  }
  else
  {
    alert(strMessage);
  }
}
</script>