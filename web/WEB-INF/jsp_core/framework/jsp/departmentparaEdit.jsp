<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<html>
<head>
<%
	SysPara sysPara = (SysPara)request.getAttribute("syspara");
	Department dep = (Department)request.getAttribute("department");
	String title = "部门参数管理";
	String cszxghtml = (String)request.getAttribute("cszxghtml");
 %>
<title>${cxmc}</title>
<%=JspSuport.getInstance().JS_ALL%>
<script language="JavaScript" src="theme/blue/blue1.js" type="text/javascript"></script>
<script languag="javascript">
function init(){
  	<%
  	if("1".indexOf(sysPara.getXsxs())>=0){
  		out.println("document.all['csz'].value = '" + sysPara.getMrz() + "';");
  	}else if("23".indexOf(sysPara.getXsxs())>=0){
  		if(sysPara.getMrz().equals("")){
  			out.println("document.all['csz'].options.selectedIndex = 0;");
  		}else{
  			out.println("document.all['csz'].value = '" + sysPara.getMrz() + "';");
  		}  		
  	}else if("4".indexOf(sysPara.getXsxs())>=0){
  		if(sysPara.getMrz().equals("0")){
  			sysPara.setMrz("2");
  		}else if(sysPara.getMrz().equals("")){
  			sysPara.setMrz("1");
  		}
  		out.println("document.all['csz'].value = '" + sysPara.getMrz() + "';");
  	}else if("56".indexOf(sysPara.getXsxs())>=0){
  		String arr[] = sysPara.getMrz().split(",");
  		String value = "";
  		out.println("var ck_length=document.all['ck_csz'].length;");
  		out.println("for(var i=0;i<ck_length;i++){");
  		out.println("tmpsx=document.all['ck_csz'].item(i).value;");
  		out.println("	if('" + sysPara.getMrz() + "'.indexOf(tmpsx)>-1){");
		out.println("		document.all['ck_csz'].item(i).checked=true;");
		out.println("	}}");
  		out.println("document.all['csz'].value = '" + sysPara.getMrz() + "';");
  	}else{
  	}
  	if(sysPara.getGjz().equals("SQGLCX")&&cszxghtml.equals("")){
  		out.println("displayInfoHtml('[00R9522J1]:请先设置授权业务类型!','window.close();');"); 		
  	}
  	%>
}

function savePara(){
    <%
    if("56".indexOf(sysPara.getXsxs())>=0){
  		out.println("var ck_length=document.all['ck_csz'].length;");
  		out.println("var s_result = '';");
  		out.println("for(var i=0;i<ck_length;i++){");
  		out.println("	if(document.all['ck_csz'].item(i).checked){");
		out.println("		s_result += document.all['ck_csz'].item(i).value + ',';");
		out.println("	}}");
		out.println("if(s_result != ''){s_result=s_result.substring(0,s_result.length-1)};");
  		out.println("document.all['csz'].value = s_result;");    	
    }
    %>
	<%=sysPara.getSjgf()%>
	
	document.sysparaForm.action="<c:url value='/syspara.frm?method=saveDeppara'/>";
	document.sysparaForm.submit();
}

function delPara(){	
    if(confirm("确认删除当前部门参数？")){
		document.sysparaForm.action="<c:url value='/syspara.frm?method=delDeppara'/>";
		document.sysparaForm.submit();
	}
}
//返回结果
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {  
    displayInfoHtml(strMessage,"exectmp();");
  }
  else
  {
    displayInfoHtml(strMessage);
  }
}
function exectmp(){
    document.sysparaForm.action="<c:url value='/fresh.frm?method=freshCode&refreshlx=3'/>";
    document.sysparaForm.submit();
    opener.reload();
}
</script>
<link href="theme/style/style.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('<%=title %>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<div class="s1" style="height:5px;"></div>
	<form name="sysparaForm" action="" method="post" target="paramIframe" style="margin:8px;">
	<table border="0" cellspacing="1" cellpadding="0" class="list_table">		
		<input type=hidden name="xtlb" value="<%=sysPara.getXtlb() %>">
		<%=JspSuport.getInstance().getWebKey(session)%>		
		<tr>
			<td width="13%" class="list_headrig">管理部门代码&nbsp;</td>
			<td class="list_body_left"><input type="text" name="glbm" class="box" value="<%=dep.getGlbm() %>" readonly></td>
			<td width="13%" class="list_headrig">管理部门名称&nbsp;</td>
			<td class="list_body_left"><input type="text" name="bmmc" class="box" value="<%=dep.getBmmc() %>" readonly></td>
		</tr>		
		<tr>
			<td width="13%" class="list_headrig">关键值&nbsp;</td>
			<td class="list_body_left"><input type="text" name="gjz" class="box" value="<%=sysPara.getGjz() %>" readonly></td>
			<td width="13%" class="list_headrig">参数名称&nbsp;</td>
			<td class="list_body_left"><input type="text" name="csmc" class="box" value="<%=sysPara.getCsmc() %>" readonly></td>
		</tr>
		<tr>
			<td class="list_headrig">参数说明&nbsp;</td>
			<td class="list_body_left" colspan="3"><textarea name="cssm" cols="80" rows="8" readonly><%=sysPara.getCssm() %></textarea></td>
		</tr>
		<tr>
			<td class="list_headrig">参数值&nbsp;</td>
			<td class="list_body_left" colspan="3"><%=cszxghtml %></td>
		</tr>
		
		<tr><td colspan="4" class="list_body_out" align="right">
		<%--
		<input type="button" value=" 删 除 " onClick="delPara()" class="button">
		--%>
		<input type="button" value=" 保 存 " onClick="savePara()" class="button">
		<input type="button" value=" 退 出 " onclick="quit()" class="button"></td>
		</tr>
	</table>
	</form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>

