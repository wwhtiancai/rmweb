<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.CachType"%>
<%@page import="java.util.List"%>
<%
	List CashServiceList = (List)request.getAttribute("CashServiceList"); 
 %>
<html>
	<head>
		<title>�ڴ����ˢ��</title>
	</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function saveForm(){
	document.all("refreshlx").value="";
	<%for(int i=0;i<CashServiceList.size();i++){
		CachType type = (CachType)CashServiceList.get(i);
	%>
		if(document.all("<%=type.getKey()%>").checked){
			document.all("refreshlx").value=document.all("refreshlx").value+"<%=type.getKey()%>"+",";
		}
	<%}%>

	if(document.all("refreshlx").value==""){
		alert("��ѡ��Ҫˢ���ڴ�����ͣ�");
		return;
	}

	if(document.all("chkother").checked==true){
		document.all("freshother").value="1";
	}else{
		document.all("freshother").value="2";
	}
	document.formedit.action="<c:url value='/fresh.frm?method=freshCachCode'/>";
	document.formedit.submit();
}


//���ؽ��
function resultSave(strResult,strMessage)
{
  if(strResult=="1")
  {
 	displayInfoHtml(strMessage);
  }
  else
  {
    displayInfoHtml(strMessage);
  }
}
function checkone(key){
	if(document.all(key).checked==false){
		document.all("chkall").checked=false;
	}
	if(key=="chkall"){
		<%for(int i=0;i<CashServiceList.size();i++){
			CachType type = (CachType)CashServiceList.get(i);
		%>  
			if(document.all("chkall").checked){
				document.all("<%=type.getKey()%>").checked=true;
			}else{
				document.all("<%=type.getKey()%>").checked=false;
			}
		<%}%>
	}
}
</script>	
	<body>
		<script language="JavaScript" type="text/javascript">vio_title('�ڴ����ˢ��')</script>
		<script language="JavaScript" type="text/javascript">vio_seach()</script>

		<form name="formedit" action="" method="post" target="paramIframe">
					<input type="hidden" name="refreshlx"/>
					<input type="hidden" name="freshother" value="1"/> 
			<span class="s1" style="height: 3px;"></span>
			<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
				<tr>
					<td width="10%" class="list_headrig">ѡ�����</td>
					<td  class="list_body_out" align="left">
					<%for(int i=0;i<CashServiceList.size();i++){
						CachType type = (CachType)CashServiceList.get(i);
						%>
						<input type="checkbox" name="<%=type.getKey()%>" value="ON" onclick="checkone('<%=type.getKey() %>');"><label for="<%=type.getKey()%>"><%=type.getName()%></label>
					<%} %>
					<input type="checkbox" name="chkall" id="chkall" value="ON" onclick="checkone('chkall');"><label for="chkall">����</label>
					</td>
				</tr>
				<tr>
					<td width="10%" class="list_headrig">����</td>
					<td  class="list_body_out" align="left"><input type="checkbox" name="chkother" id="chkother" value="ON" checked><label for="chkother">ˢ����������������</label></td>
				</tr>				
				<tr>
				<td  class="list_body_out"></td>
					<td  class="list_body_out">
							<input style="cursor: hand" class="button" name="savebutton" value=" ˢ �� " type="button" onClick="saveForm();">&nbsp;
							<input style="cursor: hand" class="button" name="closebutton" type="button" value=" �� �� " onclick="quit()"></td>
				</tr>				
				</table>
		</form>

		<script language="JavaScript" type="text/javascript">vio_down()</script>
	</body>
	<iframe name="paramIframe" style="DISPLAY: none" height="100"
		width="500"></iframe>
	<%=JspSuport.getInstance().outputCalendar()%>
	<%=JspSuport.getInstance().outputCalendar_now()%>
	</html>

