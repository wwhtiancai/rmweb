<%@ include file="/WEB-INF/jsp_core/framework/include.jsp" %>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.tmri.pub.util.JspSuport"%>
<%@page import="com.tmri.share.frm.bean.*"%>
<html>
<head>
<%
	SysPara sysPara = (SysPara)request.getAttribute("syspara");
	String cszxghtml = (String)request.getAttribute("cszxghtml");
	String title = "";
	if(sysPara.getCslx().equals("1")){
		title = "部门参数管理";
	}else{
		title = "系统参数管理";
	}
 %>
<title><%=title %></title>
<%=JspSuport.getInstance().JS_ALL%>
<script languag="javascript">
function init(){
  	<%
  	if("1".indexOf(sysPara.getXsxs())>=0){
  		out.println("document.all['mrz'].value = '" + sysPara.getMrz() + "';");
  	}else if("23".indexOf(sysPara.getXsxs())>=0){
  		if(sysPara.getMrz().equals("")){
  			out.println("document.all['mrz'].options.selectedIndex = 0;");
  		}else{
  			out.println("document.all['mrz'].value = '" + sysPara.getMrz() + "';");
  		}  		
  	}else if("4".indexOf(sysPara.getXsxs())>=0){
  		if(sysPara.getMrz().equals("0")){
  			sysPara.setMrz("2");
  		}else if(sysPara.getMrz().equals("")){
  			sysPara.setMrz("1");
  		}
  		out.println("document.all['mrz'].value = '" + sysPara.getMrz() + "';");
  	}else if("56".indexOf(sysPara.getXsxs())>=0){
  		String arr[] = sysPara.getMrz().split(",");
  		String value = "";
  		out.println("var ck_length=document.all['ck_mrz'].length;");
  		out.println("for(var i=0;i<ck_length;i++){");
  		out.println("tmpsx=document.all['ck_mrz'].item(i).value;");
  		out.println("	if('" + sysPara.getMrz() + "'.indexOf(tmpsx)>-1){");
		out.println("		document.all['ck_mrz'].item(i).checked=true;");
		out.println("	}}");
  		out.println("document.all['mrz'].value = '" + sysPara.getMrz() + "';");
  	}else{
  	}
  	%>
}

function savePara(){
    <%
    if("56".indexOf(sysPara.getXsxs())>=0){
  		out.println("var ck_length=document.all['ck_mrz'].length;");
  		out.println("var s_result = '';");
  		out.println("for(var i=0;i<ck_length;i++){");
  		out.println("	if(document.all['ck_mrz'].item(i).checked){");
		out.println("		s_result += document.all['ck_mrz'].item(i).value;");
		out.println("	}}");
		out.println("if(s_result != ''){s_result=s_result.substring(0,s_result.length-1)};");
  		out.println("document.all['mrz'].value = s_result;");    	
    }
    %>
	<%=sysPara.getSjgf() %>
    $.post("<c:url value='/syspara.frm?method=saveSyspara'/>", $("#sysparaForm").serialize(), function(data) {
        if (data && data["resultId"] == "00") {
            parent.resultSave('1','[00K0210C1]:该系统参数保存成功！');
        } else {
            parent.resultSave(data["resultId"],data["resultMsg"]);
        }
    }, "json");
}


//返回结果
function resultSave(strResult,strMessage)
{
  if(strResult=="1") 
  {  
    displayInfoHtml(strMessage,"exectmp();");
  }else
  {
    displayInfoHtml(strMessage);
  }
}
function exectmp(){
    document.sysparaForm.action="<c:url value='/fresh.frm?method=freshsysparacode&refreshlx=2'/>";
<%--    document.sysparaForm.action="<c:url value='/fresh.frm?method=freshCode&refreshlx=2'/>";	--%>
	document.sysparaForm.submit();
	window.opener.reload();
}
</script>
</head>
<body onLoad="init()">
<script language="JavaScript" type="text/javascript">vio_title('<%=title %>')</script>
<script language="JavaScript" type="text/javascript">vio_seach()</script>
	<div class="s1" style="height:5px;"></div>
	<form name="sysparaForm" id="sysparaForm" action="" method="post" target="paramIframe" style="margin:8px">
	<table border="0" cellspacing="1" cellpadding="0" class="list_table">		
		<input type=hidden name="xtlb" value="<%=sysPara.getXtlb() %>">
		<input type=hidden name="cslx" value="<%=sysPara.getCslx() %>">
		<input type=hidden name="xgjb" value="<%=sysPara.getXgjb() %>">
		<input type=hidden name="sfxs" value="<%=sysPara.getSfxs() %>">
		<input type=hidden name="xssx" value="<%=sysPara.getXssx() %>">
		<input type=hidden name="sjlx" value="<%=sysPara.getSjlx() %>">
		<input type=hidden name="cssx" value="<%=sysPara.getCssx() %>">
		<input type=hidden name="bz" value="<%=sysPara.getBz() %>">
		<%=JspSuport.getInstance().getWebKey(session)%>		
		<tr>
			<td width="70" class="list_headrig">关键值&nbsp;</td>
			<td width="120"  class="list_body_left"><input type="text" name="gjz" class="text_12" value="<%=sysPara.getGjz() %>" readonly></td>
			<td width="70" class="list_headrig">参数名称&nbsp;</td>
			<td class="list_body_left"><input type="text" name="csmc" class="text_12"  style="width:200" value="<%=sysPara.getCsmc() %>" readonly></td>
		</tr>
		<tr>
			<td class="list_headrig" valign="top">参数说明1&nbsp;</td>
			<td class="list_body_left" colspan="3" style="word-break:break-all" align="left" ><textarea name="cssm"  rows="8" style="width:98%" readonly><%=sysPara.getCssm() %></textarea></td>
		</tr>
		<tr>
			<td class="list_headrig">参数值&nbsp;</td>
			<td class="list_body_left" colspan="3">
			<%=cszxghtml %>
			
		</td>
		</tr>
		
		<tr><td colspan="4" class="list_body_out" align="right">
		<%if(sysPara.getSfxs().equals("1")){ %>
		<input type="button" value=" 保 存 " onClick="savePara()" class="button">
		<%} %>
		<input type="button" value=" 退 出 " onclick="quit()" class="button"></td>
		</tr>
	</table>
	</form>
<script language="JavaScript" type="text/javascript">vio_down()</script>
<iframe name="paramIframe" id="paramIframe" width="500" height="500" style="DISPLAY: none"></iframe>
</body>
</html>

