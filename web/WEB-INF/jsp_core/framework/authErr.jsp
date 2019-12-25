<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html; charset=gb2312"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>系统登陆超时</title>
<link href="rmtheme/main/main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="rmjs/jquery.js" type="text/javascript"></script>
<script language="JavaScript" src="rmtheme/main/main.js" type="text/javascript"></script>
<script language="JavaScript" src="frmjs/common_func.js" type="text/javascript"></script>
<script language="JavaScript" src="rmjs/tools.js" type="text/javascript"></script>
</head>
<body>
<div id="panel" style="display:none">
	<div id="paneltitle">系统登陆超时</div>
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="text_12" bgcolor="#ffffff">
	  <tr>
	    <td colspan="3" align="center" valign="middle"><table border="0" cellspacing="1" cellpadding="0" class="text_12" width="500" align="center" bgcolor="#ffffff">
	      
	      <tr> <td align="right"><img src="theme/style/PeopleLogin.gif" width="136" height="125"></td>
	        <td bgcolor="#ffffff"><table width="300" height="150"  border="0" align="center" cellpadding="1" cellspacing="0" bordercolorlight="#A5A5A5" bordercolordark="#D5D5D5" class="text_12" bgcolor="#ffffff">
	            <tr>
				
	              <td align="center"><font color=red>信息提示：
	                    <c:out value='${message}'/>
	              </font>&nbsp;</td>
	            </tr>
	            <tr>
	              <td align="center"><input type="image" name="imageField" src="theme/style/cxdl.jpg" onClick="reload();"></td>
	            </tr>
	        </table></td>
	       
	      </tr>
	    </table></td>
	  </tr>
	</table>
</div>

</body>
</html>
