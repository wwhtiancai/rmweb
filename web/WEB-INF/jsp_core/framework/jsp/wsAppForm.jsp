<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.pub.util.*"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="java.util.*"%>
<%
    String str_tilte="接口申请登记";
%>
<html>
<head>
<title><%=str_tilte%></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function showWsAppForm(azdm,dyrjmc){
    var spath="ws.frm?method=editAppForm&azdm="+azdm+"&dyrjmc="+dyrjmc;
    spath=encodeURI(encodeURI(spath));
    document.all("editresult").src =spath;	
}
function showAppFormList(){
	document.all("queryresult").src ="ws.frm?method=queryAppFormList";
}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<script language="javascript" src='js/tools.js'></script>
<body>
<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<form name="formain" method="post"  action="">
<table border="0" cellspacing="1" cellpadding="0" class="table_left_border_down text_12" width="100%">
             <tr>
               <td width="40%" valign="top">
                  <fieldset style="width:100%;border-bottom">
                    <legend>外挂系统列表</legend>
                    <iframe src="ws.frm?method=queryAppFormList" name="queryresult" id="queryresult" marginwidth="1" marginheight="1" hspace="0" vspace="0" scrolling="no" frameborder="0" style="width:100%;height:600">
                    </iframe>
                  </fieldset>
               </td>
                <td width="60%" valign="top">
                  <fieldset style="width:100%;border-bottom">
                    <legend>外挂系统信息</legend>
                    <iframe src="ws.frm?method=editAppForm" name="editresult" id="editresult" marginwidth="1" marginheight="1" hspace="0" vspace="0" scrolling="no" frameborder="0" style="width:100%;height:600">
                    </iframe>
                  </fieldset>
                </td>
             </tr>
</table>
</form>
    <script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>