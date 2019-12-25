<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@ page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title>任务日志查询</title>
<%=JspSuport.getInstance().JS_ALL%>
</head>
<body>

<script language="JavaScript" type="text/javascript">vio_seach()</script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script type="text/javascript">
function detail(zxxh){
	openwin800("<c:url value='/qtm.frm'/>?method=errorDetail&zxxh="+zxxh,"ERRORDETAIL");
}

</script>
	<c:if test="${queryList!=null}">
		<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
			<tr align="left" class="detail_head">
			    <td width="6%">执行序号</td>
				<td width="18%">任务名称</td>
				<td width="18%">执行时间</td>				
				<td width="8%">执行结果</td>
				<td width="50%">返回信息</td>
			</tr>
			<%int i=0;%>
<c:forEach items="${queryList}" var="current">	
<% i=i+1;
String str_class = "list_body_tr_1";
 if (i % 2 == 0) {
str_class = "list_body_tr_2";}%>
<tr class="<%=str_class%>"	height="23">
<td><c:out value="${current.zxxh}"/></td>
					<td><c:out value="${current.rwmc}"/></td>
					<td><c:out value="${current.zxsj}"/></td>					
					<td>
					<c:if test="${current.jgbj=='1'}">
						成功
					</c:if>
					<c:if test="${current.jgbj=='0'}">
						失败
					</c:if>					
					</td>
					<td><c:if test="${current.jgbj=='1'}">
						<a href="#" onclick="detail('<c:out value="${current.zxxh}"/>')"><c:out value="${current.fhxx}"/></a>
					</c:if>
					<c:if test="${current.jgbj=='0'}">
						<c:out value="${current.fhxx}"/>
					</c:if></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="right" class="page">
				<c:out value="${controller.clientScript}" escapeXml="false"/>
				<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
				</td>
			</tr>
		</table>
	<table border="0" cellspacing="1" cellpadding="0" class="table_left_border_down text_12" width="98%" align="center">
<tr><td align=right>

</td>
</tr>
</table>
</c:if>
     
</body>
<%=JspSuport.getInstance().outputCalendar()%>
</html>
