<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.pub.service.SysService"%>
<%@page import="com.tmri.share.frm.util.Constants"%>
<%@page import="com.tmri.share.frm.util.StringUtil"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<%@page import="com.tmri.framework.bean.*"%>
<%@page import="java.util.*"%>
<%
GDepartmentService gDepartmentService = (GDepartmentService)request.getAttribute("gDepartmentService");
List list = (List) request.getAttribute("querylist");
%>
<html>
<head>
<title></title>
</head>
<%=JspSuport.getInstance().JS_ALL%>
<script language="javascript" type="text/javascript">
//显示信息
function showdetail(glbm,yhdh) 
{
  parent.editone(glbm,yhdh);
}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
<body>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class="text_12" bgcolor="#cccccc">	
   	<tr class="detail_center">
   	    <td width="5%">序号</td>
		<td>用户名</td>
		<td>姓名</td>
		<td width="5%">管理员</td>
		<td>管理部门</td>
		<td>密码有效期</td>
		<td>身份证号</td>
		<td>状态</td>
		<td>最近登录时间</td>
    </tr>	
	<%if(list!=null){
	  	SysUser user = null;String zt = "";
	  	for(int i=0;i<list.size();i++){
	  		user = (SysUser)list.get(i);
	  		if(user.getZt().equals("1")){
	  			zt = "正常";
	  		}else if(user.getZt().equals("2")){
	  			zt = "锁定";
	  		}else{
	  			zt = "停用";
	  		}
	  		String rylx="";
	  		if(user.getSfmj().equals("1")){
	  			rylx="民警";
	  		}else if(user.getSfmj().equals("2")){
	  			rylx="协警";
	  		}else if(user.getSfmj().equals("3")){
	  			rylx="工作人员";
	  		}
	  		String sfgly="";
	  		if(user.getXtgly().equals("1")){
	  			sfgly="是";
	  		}else if(user.getXtgly().equals("2")){
	  			sfgly="否";
	  		}
	  		
	  		String str_class = "list_body_tr_1";
			if (i % 2 == 0) {
				str_class = "list_body_tr_2";
			}
	%>
	<tr height="20" class="<%=str_class%>" onMouseOver="this.className='list_body_over'" 
	onMouseOut="this.className='<%=str_class%>'" style="cursor:hand" 
	onDblClick="showdetail('<%=user.getGlbm()%>','<%=user.getYhdh()%>')"">		
		<td><%=(i + 1)%></td>		  									
		<td><%=user.getYhdh()%></td>
		<td><%=StringUtil.displayMax(user.getXm(),4)%></td>
		<td><%=sfgly%></td>
		<td><%=StringUtil.displayMax(gDepartmentService.getDepartmentName(user.getGlbm()),8)%></td>
		<td><%=user.getMmyxq()%></td>
		<td><%=user.getSfzmhm()%></td>
		<td><%=zt%></td>
		<td><%=user.getZjdlsj()%></td>
	</tr>
	<%
	}
	%>
	<tr class="">
		<td colspan="10" align="right" class="page"><c:out
			value="${controller.clientScript}" escapeXml="false" /> <c:out
			value="${controller.clientPageCtrlDesc}" escapeXml="false" />
		</td>
	</tr>
	<%
	}
	%>
</table>
</body>
</html>
