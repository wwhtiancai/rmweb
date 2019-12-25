<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gbk"%>
<%
  String str_tilte="";
  String str_czlx=request.getParameter("czlx");//操作类型 wh=维护 cx=查询
  if(str_czlx.equals("wh"))
  {
    str_tilte="部门管理";
  }
  if(str_czlx.equals("cx"))
  {
    str_tilte="部门代码查询";
  }
  %>
<html>
<head>
<title><%=str_tilte%></title>
</head>
<script language="javascript">
function editGlbm(glbm){
    //window.open("department.frm?method=editOne&glbm="+glbm,"editresult","resizable=no,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=800,height=600");
    document.all("editresult").src ="department.frm?method=editOne&glbm="+glbm;	
 }
function query(){
	  openwin("department.frm?method=queryList","queryresult");
}
</script>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="js/drvjs/common.js"></script>
<script language="javascript" src='js/tools.js'></script>

<body>
<script language="JavaScript" type="text/javascript">vio_title('<%=str_tilte%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<form name="formain" method="post"  action="">
<table border="0" cellspacing="1" cellpadding="0" class="table_left_border_down text_12" width="100%">
             <tr>
               <td colspan="2" valign="top"></td>
             </tr>
             <tr>
               <td width="25%" valign="top">
                  <fieldset style="width:98%;border-bottom">
                    <legend>部门列表</legend>
                    <iframe src="department.frm?method=queryList" name="queryresult" id="queryresult" marginwidth="1" marginheight="1" hspace="0" vspace="0" scrolling="auto" frameborder="0" style="width:100%;height:550">
                    </iframe>
                  </fieldset>
               </td>
                <td width="75%" valign="top">
                  <fieldset style="width:98%;border-bottom">
                    <legend>部门信息</legend>
                    <iframe src="department.frm?method=editOne" name="editresult" id="editresult" marginwidth="1" marginheight="1" hspace="0" vspace="0" scrolling="auto" frameborder="0" style="width:100%;height:550">
                    </iframe>
                  </fieldset>
                </td>
             </tr>
</table>
</form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>