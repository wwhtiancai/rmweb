<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.framework.bean.FrmWsAppForm"%>
<%@page import="com.tmri.share.frm.util.DateUtil"%>
<%
  FrmWsAppForm wsAppForm=(FrmWsAppForm)request.getAttribute("wsAppForm");
  
%>
<html>
	<head>
		<TITLE>�������ӱ�ʶ���й���ϵͳ</TITLE>
	</head>

	<script language="javascript" type="text/javascript">

    function init()
    {
      document.all.WebBrowser.ExecWB(7,1);
      //document.all.WebBrowser.ExecWB(45,1);
    }
  </script>
	<style media=print> 
.Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ--> 
.PageNext{page-break-after: always;}<!--���Ʒ�ҳ--> 
</style>
	<object id="WebBrowser" width=0 height=0 classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2">
	</object>

	<link href="theme/gray/css/css.css" rel="stylesheet" type="text/css">
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<body onload="init()">
		<center class="Noprint">
			<input type=button value=ֱ�Ӵ�ӡ onclick=document.all.WebBrowser.ExecWB(6,6)>
			<input type=button value=ҳ������ onclick=document.all.WebBrowser.ExecWB(8,1)>
			<input type=button value=��ӡԤ�� onclick=document.all.WebBrowser.ExecWB(7,1)>
		</center>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3" align="center">
					<font size="6px">���ϵͳ��Ϣ�ӿ�ʹ�������</font>
				</td>
			</tr>
			<tr>
				<td colspan="3" height="10"></td>
			</tr>
			<tr>
				<td colspan="3">
				���λ�����£���
				</td>
			</tr>
			<tr>
				<td colspan="3" height="5"></td>
			</tr>			
			<tr>
			    <td colspan="3">
			          ��ϵ�˼��绰��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ����ڣ�
			    </td>
			</tr>
			<tr>
				<td colspan="3" height="10"></td>
			</tr>
		</table>
		<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="25%" height="30">���ϵͳ����</td>
				<td colspan="2">&nbsp;<%=wsAppForm.getDyrjmc() %></td>
			</tr>
			<tr>
				<td width="25%" height="30">�ӿڵ��õ�λ</td>
				<td colspan="2">&nbsp;<%=wsAppForm.getDyzdw() %></td>
			</tr>
			<tr>
				<td width="25%" height="30">���������λ</td>
				<td colspan="2">&nbsp;<%=wsAppForm.getDyrjkfdw()%></td>
			</tr>	
			<tr>
				<td width="25%" height="30">����IP��Χ</td>
				<td>&nbsp;<%=wsAppForm.getKsip() %></td>
				<td>&nbsp;<%=wsAppForm.getJsip() %></td>
			</tr>
			<tr>
				<td width="25%" height="30">�ӿ���������</td>
				<td colspan="2">&nbsp;<%=DateUtil.formatDate(wsAppForm.getJkqsrq()) %></td>
			</tr>
			<tr>
			   <td height="30" colspan="3" align="center">���ϵͳ���ʽӿ��б�</td>
			</tr>
			<tr>
			  <td colspan="3">
			     <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
			        <tr>
			           <td align="center" width="10%">���</td>
			           <td align="center" width="15%">�ӿڴ���</td>
			           <td align="center" width="45%">�ӿ�����</td>
			           <td align="center" width="30%">�վ����ʴ���</td>
			        </tr>
			        <c:out value="${outJsp}" escapeXml="false" />
			        <%for(int i=0;i<2;i++){ %>
			        <tr>
			           <td>&nbsp;</td>
			           <td>&nbsp;</td>
			           <td>&nbsp;</td>
			           <td>&nbsp;</td>
			        </tr>
			        <%} %>

			     </table>			     
			  </td>
			</tr>	
			<tr>
			  <td colspan="3">
			      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			     	<tr>
			          <td colspan="4">
			                                ���ϵͳ���ܽ��ܣ�<br>&nbsp;&nbsp;&nbsp;&nbsp;<%=wsAppForm.getBz() %>	
			            &nbsp;<br>
			            &nbsp;<br>
			            &nbsp;<br>
			            &nbsp;<br>
			            &nbsp;<br>
			            &nbsp;<br>	            
			          </td>
			        </tr>
			       </table>
			  </td>
			</tr>												
		</table>
	</body>
</html>
