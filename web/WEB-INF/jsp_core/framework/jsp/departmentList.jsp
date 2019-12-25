<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@ page import="com.tmri.pub.util.JspSuport"%>
<html>
<head>
<title></title>
<%=JspSuport.getInstance().JS_ALL%>
<%=JspSuport.getInstance().JS_XTREE%>
<script language="javascript">

</script>
<link type="text/css" rel="stylesheet" href="css/xtree.css">
</head>
<body class=left style='overflow:auto'>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td >
    <script>
    function editGlbm(s_glbm){
		parent.editGlbm(s_glbm);
	}
	<%=request.getAttribute("departmenttree") %>
	editGlbm('<%=request.getAttribute("f_glbm") %>');
    </script>
    </td>
  </tr>
</table>

</html>
