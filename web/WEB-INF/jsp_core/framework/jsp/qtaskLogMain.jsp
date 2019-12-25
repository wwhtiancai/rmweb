<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<html>
<head>
<title><c:out value="${cxmc}"/></title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" type="text/JavaScript">
function init(){
	
}
//查询
function query(){
	     document.formain.action="<c:url value='/qtm.frm?method=showTaskLog'/>";
	     document.formain.target="queryFramedddd";
	     document.formain.submit();
}
//onchange ="check();
function check()
{
	//alert($("#tjlx").find("option:selected").val());
	alert(document.all["tjlx"].value);
}
</script>
</head>
<body onLoad="init()" onUnload="closesubwin();">
<script language="JavaScript" type="text/javascript">vio_title('统计查询')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<form name="formain"  method="post">
<input type="hidden" name="xtlb" value="<c:out value='${taskLog.xtlb}'/>">
<input type="hidden" name="rwid" value="<c:out value='${taskLog.rwid}'/>">
<div class="s1" style="height: 3px;"></div>
<table border="0" cellspacing="1" cellpadding="0" class="list_table" align="center">
                       <tr>
                        <td class="list_headrig" width="100">执行序号</td>
                        <td width="300" class="list_body_out">&nbsp;<input name="zxxh" type="text" class="Input_text" value="" style="width:77"></td>
                          <td class="list_headrig" width="100">执行结果</td>
                         <td width="300" class="list_body_out">&nbsp;
                         <select name="jgbj"   style="width:120" >
 			              <option value="" selected>全部</option>
			              <option value="1" >成功</option>
			              <option value="0" >失败</option>
	                      </select></td>
						<td align="right" class="list_body_out">
						<input type="button"  class="button" value=" 查 询 " onClick="query();">&nbsp;<input name="exit" type=button class="button" style="cursor:hand;"  value=" 退 出 " onclick="quit()">
						</td>	
                       </tr>
</table>
</form>

   <iframe src="" name="queryFramedddd" id="queryFramedddd" marginwidth="0"
	marginheight="0" hspace="0" vspace="0" scrolling="auto" frameborder="0"
	style="width: 100%; height: 100%"></iframe>
   <%=JspSuport.getInstance().outputCalendar()%>
   <script language="JavaScript" type="text/javascript">vio_down()</script>
</body>   
<script language="JavaScript" type="text/JavaScript">
query();
</script>
</html>
