<%@ include file="/WEB-INF/jsp_core/framework/include.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.util.StringUtil"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.service.GSystemService"%>
<%@page import="com.tmri.framework.service.LogManager"%>
<%@page import="java.util.*"%>
<% 
String cdbh = (String)request.getAttribute("cdbh");
// SysService sysService = (SysService)request.getAttribute("sysService");
GSystemService sysService = (GSystemService)request.getAttribute("gSystemService");
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");

LogManager logManager = (LogManager)request.getAttribute("logManager");
String cdmc = sysService.getCdmc(Constants.SYS_XTLB_FRAME,cdbh);
List list = (List) request.getAttribute("querylist");
%>
<html>
<head>
<title><%=cdmc%></title>
</head>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js"    type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function showdetail(glbm,dmlb,dmz){
}
</script>

<body>
<table  border="0" align="center" cellpadding="0" cellspacing="1" class="detail_table ">
    <tr align="center" class="detail_head" height="20">
		<td width="4%">序号</td>
		<td width="15%">管理部门</td>
		<td width="8%">操作用户</td>
		<td width="16%">操作时间</td>
		<td>操作内容</td>
		<td width="8%">ip地址</td>
    </tr>    
<%
    if (list != null) {
        for (int i = 0; i < list.size(); i++) {
        	Log obj = (Log) list.get(i);
            String str_class = "list_body_tr_1";
            if (i % 2 == 0) {
                  str_class = "list_body_tr_2";
            }           
  %>
     <tr class="<%=str_class%>" height="23" align="center" style="cursor:hand">
		<td><%=(i + 1)%></td>
		<td><%=gDepartmentService.getDepartmentName(obj.getGlbm())%></td>
		<td><%=obj.getYhdh()%></td>
		<td><%=obj.getCzsj()%></td>
		<td title='<%=obj.getCznr()%>' align="left"><%=StringUtil.displayMax(obj.getCznr(),50)%> </td>
		<td><%=obj.getIp()%></td>
    </tr>
    <%
    }
    %>
    <tr class="">
        <td colspan="6" align="right" class="page"><c:out
            value="${controller.clientScript}" escapeXml="false" /> <c:out
            value="${controller.clientPageCtrlDesc}" escapeXml="false" />
        </td>
    </tr>
    <%
    }
    %>
  </table>

<iframe name="paramIframe" style="DISPLAY: none" height="100" width="500"></iframe>
</body>
</html>                      