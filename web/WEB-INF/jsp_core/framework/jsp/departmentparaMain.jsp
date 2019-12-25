<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title>${cxmc}</title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function editGlbm(glbm){
	document.all("ifrm_userlist").src ="syspara.frm?method=queryDepParaList&glbm=" + glbm;	
}
</script>
</head>
<body>
<script language="JavaScript" type="text/javascript">vio_title('部门参数管理')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<table border="0" cellspacing="1" cellpadding="0" class="text_12" width="100%" align="center" >
  <tr>
    <td width="25%" height="100%" valign="top">
       <fieldset style="width:98%;border-bottom">
         <legend>部门列表</legend>
         <iframe src="syspara.frm?method=queryGlbmList" name="ifrm_deplist" id="ifrm_deplist" marginwidth="1" marginheight="1" 
         	hspace="0" vspace="0" scrolling="atuo" frameborder="0" style="width:100%;height:570">
         </iframe>
       </fieldset>
    </td>
     <td width="75%" height="100%" valign="top">
       <fieldset style="width:98%;border-bottom">
         <legend>部门参数信息</legend>
         <iframe src="syspara.frm?method=queryDepParaList" name="ifrm_userlist" id="ifrm_userlist" marginwidth="1" marginheight="1" 
         	hspace="0" vspace="0" scrolling="no" frameborder="0" style="width:100%;height:570">
         </iframe>
       </fieldset>
     </td>
  </tr>
</table>	
<table border="0" cellspacing="0" cellpadding="0" class="text_12" width="100%" align="center">
<tr><td colspan="2" class="" align="right">
<input name="exit" type="button" class="button" style="cursor:hand;"  value=" 退 出 " onclick="quit()">&nbsp;&nbsp;&nbsp;</td>
 </tr></table>
<script language="JavaScript" type="text/javascript">vio_down()</script>      
</body>
</html>