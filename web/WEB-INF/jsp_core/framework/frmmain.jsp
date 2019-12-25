<%@ page contentType="text/html; charset=gb2312"%>
<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page import="com.tmri.pub.util.*"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="公安,交通,集成指挥平台,科研所">
<meta http-equiv="description" content="公安部交通管理科学研究所">
<title><%=Constants.SYS_SYSTEMTOPIC%></title>
<script language="JavaScript" type="text/javascript">
function refreshIframe(iframeid){
	window.frames[iframeid].location.reload();
}
</script>
</head>
<frameset rows="110,*" framespacing="0" frameborder="0" bordercolor="#666666">
	<frame src="main.frm?method=top" name="topfrm" id="topfrm" frameborder="0" scrolling="No" noresize marginwidth="0" marginheight="0">
  	<frameset cols="200,9,*" framespacing="0" frameborder="0" id="controlfrm">
		<frame src="main.frm?method=left" name="leftfrm" id="leftfrm" frameborder="0" scrolling="yes" noresize marginwidth="0" marginheight="0" style="overflow-x: none;overflow-y: none">
		<frame src="main.frm?method=middle" name="middlefrm" id="middlefrm" frameborder="0" scrolling="No" noresize marginwidth="0" marginheight="0">
		<frame src="main.frm?method=rightmain" name="rightfrm" id="rightfrm" frameborder="0" scrolling="Yes" noresize marginwidth="0" marginheight="0">
	</frameset>
</frameset>
</html>