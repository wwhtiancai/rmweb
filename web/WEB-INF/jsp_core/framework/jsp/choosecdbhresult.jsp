<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.util.StringUtil"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.pub.bean.*"%>
<%@page import="java.util.*"%>
<%
SysService sysService = (SysService)request.getAttribute("sysService");
List list = (List) request.getAttribute("querylist");
%>
<html>
<head>
	<title>请选择</title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript" type="text/javascript">
function tondblclick(){
 	var val=getradio(formedit.xh);
	window.returnValue=val;
	window.close();
}		

function tok(){
 	var val=getradio(formedit.xh);
	window.returnValue=val;
	window.close();
}

function tt(){
	window.close();
}

function chooseradio(objid){
	document.getElementById(objid).checked=true;
}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<body>
<form name="formedit" action="" method="post" target="paramIframe">
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class="text_12" bgcolor="#cccccc">	
   	<tr class="detail_center">
   	  <td width="5%">选择</td>
   	  <td width="15%">系统类别</td>	
      <td width="20%">目录名称</td>
      <td width="20%">程序名称</td>
      <td width="20%">功能名称</td>
    </tr>	
<%
	if (list != null) {
		for (int i = 0; i < list.size(); i++) {
			Program obj = (Program) list.get(i);
			String trclass="";
			if (i%2==0){
				trclass="list_body_tr_2";
			}else{
				trclass="list_body_tr_1";
			}	
			String xh=obj.getXtlb()+obj.getCdbh()+obj.getGnid();
			String val="";
			if(StringUtil.checkBN(obj.getGnid())){
				val=obj.getXtlb()+obj.getCdbh()+"$"+obj.getGnid()+"$"+obj.getMlmc()+"-"+obj.getGnmc();
			}else{
				val=obj.getXtlb()+"$"+obj.getCdbh()+"$"+obj.getMlmc()+"-"+obj.getCxmc();
			}
	 %>
	 
	<tr class="<%=trclass%>" align="center" onMouseOver="mouse_over(this)"
		     onMouseOut="mouse_out(this)" style="cursor:hand"
		     onclick="chooseradio('<%=xh%>');" ondblclick="tondblclick();">
		<td><INPUT TYPE="radio" NAME="xh" id="<%=xh%>" value="<%=val%>"></td>
        <td><%=obj.getXtmc()%></td>
        <td><%=obj.getMlmc()%></td>
        <td><%=obj.getCxmc()%></td>
        <td><%=StringUtil.transNull(obj.getGnmc())%></td>
	</tr>
	<%
		}
	}
	%>
</table>
</form>

<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1"   id="sorttable"  class="text_12">
	<tr>
		<td align="right">
			<input type="button" style="width:200" name="qr" value="确认" onclick="tok();">
			<input type="button" style="width:200" name="qx" value="取消" onclick="tt();">
		</td>
	</tr>
</table>

</body>
</html>
