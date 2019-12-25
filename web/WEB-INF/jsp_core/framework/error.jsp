<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<%
  String message = (String)request.getAttribute("message");
  if(message!=null){
	  if(message.indexOf("20001")>=0){
	  	message = message.substring(6);
	  }
	  if(message.indexOf("TERR")>=0){
	  	message = message.substring(5);
	  }
  }
 %>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>系统提示</title>
<script language="JavaScript" src="js/drvjs/common.js"></script>
<script language="javascript" src='js/tools.js'></script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<%=JspSuport.getInstance().JS_ALL%>
<style type="text/css">
body{
	background-COLOR:#094E89;
}
</style>
</head>
<SCRIPT type="text/JavaScript" language=javascript>
function reload()
{
try{
	window.opener.parent.location.href = 'index.jsp';
	window.opener.location.href = 'index.jsp';
	window.location.href = 'index.jsp';
	window.close();
}catch(ex){}

}
</SCRIPT>
<body>
<script language="JavaScript" type="text/javascript">vio_title('信息提示')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="text_12" bgcolor="#ffffff">
  <tr>
    <td colspan="3" align="center" valign="middle"><table border="0" cellspacing="1" cellpadding="0" class="text_12" width="500" align="center" bgcolor="#ffffff">
      
      <tr> <td align="right"><img src="theme/style/PeopleLogin.gif" width="136" height="125"></td>
        <td bgcolor="#ffffff"><table width="300" height="150"  border="0" align="center" cellpadding="1" cellspacing="0" bordercolorlight="#A5A5A5" bordercolordark="#D5D5D5" class="text_12" bgcolor="#ffffff">
            <tr>
			
              <td align="center"><font color=red>信息提示：
                    <%=message %>
              </font>&nbsp;</td>
            </tr>
            <tr>
              <td align="center"><input name="btclose" type="button" class="button" value=" 关 闭 "  onclick="window.close();"></td>
            </tr>
        </table></td>
       
      </tr>
    </table></td>
  </tr>
</table>
<script language="JavaScript" type="text/javascript">vio_down()</script>
</body>
</html>
