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
		<TITLE>汽车电子标识发行管理系统</TITLE>
	</head>

	<script language="javascript" type="text/javascript">

    function init()
    {
      document.all.WebBrowser.ExecWB(7,1);
      //document.all.WebBrowser.ExecWB(45,1);
    }
  </script>
	<style media=print> 
.Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目--> 
.PageNext{page-break-after: always;}<!--控制分页--> 
</style>
	<object id="WebBrowser" width=0 height=0 classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2">
	</object>

	<link href="theme/gray/css/css.css" rel="stylesheet" type="text/css">
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<body onload="init()">
		<center class="Noprint">
			<input type=button value=直接打印 onclick=document.all.WebBrowser.ExecWB(6,6)>
			<input type=button value=页面设置 onclick=document.all.WebBrowser.ExecWB(8,1)>
			<input type=button value=打印预览 onclick=document.all.WebBrowser.ExecWB(7,1)>
		</center>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3" align="center">
					<font size="6px">外挂系统信息接口使用申请表</font>
				</td>
			</tr>
			<tr>
				<td colspan="3" height="10"></td>
			</tr>
			<tr>
				<td colspan="3">
				填报单位（盖章）：
				</td>
			</tr>
			<tr>
				<td colspan="3" height="5"></td>
			</tr>			
			<tr>
			    <td colspan="3">
			          联系人及电话：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 填报日期：
			    </td>
			</tr>
			<tr>
				<td colspan="3" height="10"></td>
			</tr>
		</table>
		<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="25%" height="30">外挂系统名称</td>
				<td colspan="2">&nbsp;<%=wsAppForm.getDyrjmc() %></td>
			</tr>
			<tr>
				<td width="25%" height="30">接口调用单位</td>
				<td colspan="2">&nbsp;<%=wsAppForm.getDyzdw() %></td>
			</tr>
			<tr>
				<td width="25%" height="30">软件开发单位</td>
				<td colspan="2">&nbsp;<%=wsAppForm.getDyrjkfdw()%></td>
			</tr>	
			<tr>
				<td width="25%" height="30">调用IP范围</td>
				<td>&nbsp;<%=wsAppForm.getKsip() %></td>
				<td>&nbsp;<%=wsAppForm.getJsip() %></td>
			</tr>
			<tr>
				<td width="25%" height="30">接口启用日期</td>
				<td colspan="2">&nbsp;<%=DateUtil.formatDate(wsAppForm.getJkqsrq()) %></td>
			</tr>
			<tr>
			   <td height="30" colspan="3" align="center">外挂系统访问接口列表</td>
			</tr>
			<tr>
			  <td colspan="3">
			     <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
			        <tr>
			           <td align="center" width="10%">序号</td>
			           <td align="center" width="15%">接口代号</td>
			           <td align="center" width="45%">接口名称</td>
			           <td align="center" width="30%">日均访问次数</td>
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
			                                外挂系统功能介绍：<br>&nbsp;&nbsp;&nbsp;&nbsp;<%=wsAppForm.getBz() %>	
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
