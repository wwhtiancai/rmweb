<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%
  String str_tilte="";
  str_tilte="行政区划维护";
  %>
<html>
<head>
<title><%=str_tilte%></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function query_cmd(){             //本页刷新
document.formain.action="<c:url value="/code.frm?method=queryXzqhList"/>";
document.formain.submit();
}

function init(){
<c:if test="${code!=null}">
formain.dmz.value="<c:out value='${code.dmz}'/>";
formain.dmsm1.value="<c:out value='${code.dmsm1}'/>";
</c:if>
}
//显示信息
function showdetail(dmz) {
   ActionForm.dmz.value=dmz;
   winid = openwin("","bbb2",false);
   ActionForm.target="bbb2";
   ActionForm.action="code.frm?method=editXzqh";
   ActionForm.submit();
   winid.focus();
}
//新增代码
function newxzqh(){
//
var dmcd;
var windowheight=screen.height;
var windowwidth =screen.width;
    windowheight=(windowheight-500)/2;
    windowwidth=(windowwidth-800)/2;
    window.open("<c:url value='/code.frm?method=newxzqh'/>","excmain","resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=500,top="+windowheight+",left="+windowwidth);
}
</script>
<script language="JavaScript" src="frmjs/tools.js" type="text/javascript"></script>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<div class="s1" style="height: 3px;"></div>
	<Form name="ActionForm" action="" method="post"> 
     <input type=hidden name="dmz">
    </Form>	
<form name="formain" method="post"  action="">	
 <input type=hidden name='czlx' id='czlx' value='<c:out value='${czlx}'/>'>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
<td width="100" class="list_headrig">行政区划代码</td>
<td width="148" class="list_body_out"><input type="text" name="dmz" class="input_text" maxlength="6" class="text_size_all">
</td>
<td width="100" class="list_headrig">行政区划名称</td>
<td class="list_body_out" width="153"><input type="text" name="dmsm1" class="input_text" maxlength="12" class="text_size_all"></td>
<td align=right class="list_body_out" width="250">&nbsp;<input type="button" class="button" value=" 查 询 " style="cursor:hand" alt="" onClick="query_cmd()">&nbsp;
<input type="button" class="button" value=" 新 增 " style="cursor:hand" alt="" onClick="newxzqh()">&nbsp;
<input name="exit" type=button class="button" style="cursor:hand;"  value=" 退 出 " onclick="quit()"></td>
</tr>
</table>
</form>
<div class="s1" style="height: 3px;"></div>
	<c:if test="${queryList!=null}">
	
<table border="0" cellspacing="1" cellpadding="0" class="detail_table" align="center">
				<col/>
				<col/>
				<col/>
				<col/>
				<col/>
		<thead>
					 	<tr align="center" class="detail_head" height="20">
						<td width="50%">行政区划代码</td>
						<td width="50%">行政区划名称</td>
				  </tr>
				  <%int i=0;%>
<c:forEach items="${queryList}" var="current">	
<% i=i+1;
String str_class = "list_body_tr_1";
 if (i % 2 == 0) {
str_class = "list_body_tr_2";}%>
<tr class="<%=str_class%>"	height="23" onMouseOver="this.className='list_body_over'" onMouseOut="this.className='<%=str_class%>'" style="cursor:hand" onDblClick="showdetail('<c:out value='${current.dmz}'/>')">
						<td height="20"><c:out value="${current.dmz}"/></td>
					   <td><c:out value="${current.dmsm1}"/></td>
					</tr>
 		</c:forEach>
 		<tr>
				<td colspan="7" align="right" class="page">
					<c:out value="${controller.clientScript}" escapeXml="false"/>
					<c:out value="${controller.clientPageCtrlDesc}" escapeXml="false"/>
				</td>
	    </tr>
  </thead>
</table>
</c:if>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>