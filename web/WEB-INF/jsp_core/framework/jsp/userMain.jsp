<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<html>
<head>
<%
String CASSWITCH = (String)request.getAttribute("CASSWITCH");
String cdbh = (String)request.getAttribute("cdbh");
GSystemService gSystemService = (GSystemService)request.getAttribute("gSystemService");
Department depObj = gSystemService.getSessionUserInfo(session).getDepartment();
String cdmc = gSystemService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
String queryurl="";
String editurl="";
if(cdbh.equals("R994")){
	queryurl="sysuser.frm?method=userQuery&glbm="+depObj.getGlbm()+"&cdbh="+cdbh;
	editurl="sysuser.frm?method=userQuery&cdbh="+cdbh;
}else if(cdbh.equals("R996")){
	queryurl="sysuser.frm?method=userQuerytrack&glbm="+depObj.getGlbm()+"&cdbh="+cdbh;
	editurl="sysuser.frm?method=userQuerytrack&cdbh="+cdbh;
}



%>
<title>
用户管理
</title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript">
function editGlbm(glbm){
	document.all("ifrm_userlist").src ="<%=editurl%>"+"&glbm=" + glbm;	
}
function sycuserinfo(){
	sycform.submit();
}
function resultSubmit(flag,msg){
	displayInfoHtml(msg);
}
</script>
</head>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<body>
<script language="JavaScript" type="text/javascript">vio_title('<%=cdmc%>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
<table border="0" cellspacing="1" cellpadding="0" class="table_left_border_down text_12" width="100%">
   <tr>
     <td colspan="2" valign="top"></td>
   </tr>
   <tr>
     <td width="30%" height="100%" valign="top">
        <fieldset style="width:98%;border-bottom">
          <legend>部门列表</legend>
          <iframe src="sysuser.frm?method=queryGlbmYwlbList" name="ifrm_deplist" id="ifrm_deplist" marginwidth="1" marginheight="1" hspace="0" vspace="0" 
          	scrolling="yes" frameborder="0" style="width:100%;height:530">
          </iframe>
        </fieldset>
     </td>
      <td width="70%" height="100%" valign="top">
        <fieldset style="width:98%;border-bottom">
          <legend>用户信息</legend>
          <iframe src="<%=queryurl%>" name="ifrm_userlist" id="ifrm_userlist" marginwidth="1" marginheight="1" hspace="0" vspace="0" scrolling="no" frameborder="0" style="width:100%;height:530">
          </iframe>
        </fieldset>
      </td>
   </tr>
</table>	

 <form name="sycform" action="sysuser.frm?method=SycUserInfoToCasserver" method="post" target="paramIframe">
 </form>
 <iframe name="paramIframe" style="DISPLAY: none" height="100"
		width="500"></iframe>
<script language="JavaScript" type="text/javascript">vio_down()</script>      
</body>
</html>