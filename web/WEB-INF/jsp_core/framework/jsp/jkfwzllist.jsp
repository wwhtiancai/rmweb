<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.framework.bean.FrmWsLog"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="java.util.List"%>
<%
	String str_tilte = "接口访问量统计";
	String jkoptions = (String)request.getAttribute("jkoptions");
	FrmWsLog wsLog = (FrmWsLog)request.getAttribute("wslog");
%>
<html>
	<head>
		<title><%=str_tilte%></title>
	</head>
	<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
	<%=JspSuport.getInstance().JS_ALL%>	
	<script language="JavaScript" src="frmjs/frmws.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript">
  function init()
  {
      formain.jkdysj.value=getTodayN(-7);
      formain.jkdysj1.value=getTodayN(0);
      <c:if test="${wsLog!=null}">
	     formain.jkxlh.value="<c:out value='${wsLog.jkxlh}'/>";
	     formain.jkdysj.value="<c:out value='${wsLog.jkdysj}'/>";
	     formain.jkdysj1.value="<c:out value='${wsLog.jkdysj1}'/>";
	  </c:if>  
  }
  function query_cmd()
  {   
      //本页刷新
      document.formain.action="<c:url value="/ws.frm?method=queryJkfwzl"/>";
      document.formain.submit();
  }
  function queryjkid_cmd()
  {
    
  }
  </script>

<link href="theme/style/style.css" rel="stylesheet" type="text/css">
	<body onload="init()"  onUnload="closesubwin();">
		<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
		<script language="JavaScript" type="text/javascript">vio_seach()</script>
		<div class="s1" style="height: 3px;"></div>
		<form name="formain" method="post" action="">
			<table border="0" cellspacing="1" cellpadding="0"
				class="list_table text_12" width="100%">
				<tr>
					<td width="70" class="list_headrig">统计时间段&nbsp;
					</td>
					<td class="list_body_out" width="240"><input name="jkdysj" onfocus="movelast();"  type="text" class="text_12" style="width:68">
						<img name="popcal" align="absmiddle" src="frmjs/cal/calbtn.gif" width="34" height="22" border="0" onClick="riqi('jkdysj')" style="cursor:hand">
						至
						<input name="jkdysj1" onfocus="movelast();"  type="text" class="text_12" style="width:68">
						<img name="popcal" align="absmiddle" src="frmjs/cal/calbtn.gif" width="34" height="22" border="0" onClick="riqi('jkdysj1')" style="cursor:hand">						
					</td>					
					<td width="60" class="list_head">
						&nbsp;外挂系统&nbsp;
					</td>
					<td width="100" class="list_head">
						<select name="jkxlh" style="width: 250" onchange="queryjkid_cmd()">
				    		<option value="" selected>--全部--</option>
							<c:forEach items="${wgxtList}" var="current1">
								<option value="<c:out value='${current1.jkxlh}'/>">
								<c:out value="${current1.dyrjmc}" />
							</option>
						</c:forEach>
						</select>
					</td>								
					<td align="right" class="list_body_out">
						<input type="button" class="button" value=" 统 计 " style="cursor:hand" onClick="query_cmd()">
						<input type="button" name="Submit2" class="button" onclick="quit()" value=" 退 出 "></td>
				</tr>
			</table>
		</form>
		<div class="s1" style="height: 3px;"></div>
		<c:out value="${inputStr}" escapeXml="false" />
		<script language="JavaScript" type="text/javascript">vio_down()</script>
		<%=JspSuport.getInstance().outputCalendar() %>
	</body>
</html>

