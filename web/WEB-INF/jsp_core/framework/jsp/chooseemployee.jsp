<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.service.*"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.pub.bean.*"%>
<%@page import="java.util.*"%>
<%
SysService sysService = (SysService)request.getAttribute("sysService");
List list = (List) request.getAttribute("querylist");
SysUser queryobj = (SysUser) request.getAttribute("queryobj");
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");
GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)request.getAttribute("gSysparaCodeService");
%>
<html>
<head>
	<title>请选择人员</title>
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
<div id="divcxdhs" style="height:500;overflow:scroll; border:1px solid;">
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1"   id="sorttable"  class="text_12" bgcolor="#cccccc">	
   	<tr class="detail_center">
   	  <td width="5%">选择</td>
   	  <td width="25%">管理部门</td>
      <td width="8%">协警编号</td>
      <td width="8%">姓名</td>
      <td width="10%">身份证明号码</td>
      <td width="10%">联系手机</td>
      <td width="10%">所在岗位</td>
      <td width="12%">入队时间</td>
    </tr>	
<%
	if (list != null) {
		for (int i = 0; i < list.size(); i++) {
			BasEmployee obj = (BasEmployee) list.get(i);
			String trclass="";
			if (i%2==0){
				trclass="list_body_tr_2";
			}else{
				trclass="list_body_tr_1";
			}	
	 %>
	 
	<tr class="<%=trclass%>" align="center" onMouseOver="mouse_over(this)"
		     onMouseOut="mouse_out(this)" style="cursor:hand"
		     onclick="chooseradio('<%=obj.getXh()%>');" ondblclick="tondblclick();">
		<td><INPUT TYPE="radio" NAME="xh" id="<%=obj.getXh()%>" value="<%=obj.getXm()%>#<%=obj.getSfzmhm()%>#<%=obj.getRybh()%>"></td>
		<td align="left"><%=gDepartmentService.getDepartmentName(obj.getBmdm())%></td>	
        <td><%=obj.getRybh()%></td>
        <td><%=obj.getXm()%></td>
        <td><%=obj.getSfzmhm()%></td>
        <td><%=obj.getSj()%></td>
        <td><%=gSysparaCodeService.transCode("05","155", obj.getZw().trim())%></td>
        <td> <%=obj.getRdsj()%></td>
	</tr>
	<%
		}
	}
	%>
</table>	
</div>
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
